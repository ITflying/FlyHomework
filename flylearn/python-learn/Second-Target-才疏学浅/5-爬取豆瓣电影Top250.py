'''
5-爬取豆瓣电影Top250.py

自己尝试获取豆瓣电影top250榜单，并存入本地和数据库
要用到：多线程（每个页面开启一个页面），代理（每五个页面切换一个代理使用）、文件和数据库操作

首页地址：https://movie.douban.com/top250
第一页地址：https://movie.douban.com/top250?start=50&filter=
参数：start:225 filter:

'''

from bs4 import BeautifulSoup
import requests,re,os
import pymysql.cursors


class doubanSpliderMovie():
    headers = {
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
        'Accept-Encoding': 'gzip, deflate, sdch',
        'Accept-Language': 'zh-CN,zh;q=0.8,en;q=0.6',
        'Cache-Control': 'no-cache',
        'Connection': 'keep-alive',
        'Cookie': 'bid=coMkWiK1MAk; _pk_ref.100001.4cf6=%5B%22%22%2C%22%22%2C1516607492%2C%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DF7C_HGlkgSb9RUG3mcqrKZ1rtJOX-1bCqcp3AwNHx1za3IQU6JX2X-XmCpVW5d83%26wd%3D%26eqid%3D9b5cd80900000fda000000065a659804%22%5D; _pk_ses.100001.4cf6=*; __utma=30149280.1169425706.1516607492.1516607492.1516607492.1; __utmc=30149280; __utmz=30149280.1516607492.1.1.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utma=223695111.123661738.1516607492.1516607492.1516607492.1; __utmb=223695111.0.10.1516607492; __utmc=223695111; __utmz=223695111.1516607492.1.1.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utmt_douban=1; __utmb=30149280.1.10.1516607492; __yadk_uid=ndAhRlQo71r4ebLrXA8d4Y6r2uQE5F53; _pk_id.100001.4cf6=fe39f4c9a16a8d18.1516607492.1.1516607711.1516607492.',
        'DNT': '1',
        'Host': 'movie.douban.com',
        'Pragma': 'no-cache',
        'Referer': 'https://movie.douban.com/',
        'Upgrade-Insecure-Requests': '1',
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36'
    }

    url = "https://movie.douban.com/top250"
    pageMovieNum = 25
    totalMovie = 250

    targetPath = "D:\\Project_GitHub\\python-learn\\tmp\\reptile\\douban"

    # 获取页面的地址，也就是起始数量
    def getPageArray(self):
        pageArray = []
        page = int(self.totalMovie / self.pageMovieNum)
        for i in range(0, int(self.totalMovie), int(self.pageMovieNum)):
            pageArray.append(i)
        return pageArray

    # 获取单页的数据
    def getSinglePageInfo(self, pageNum):
        params = {'start': pageNum, 'filter': ""}
        res = requests.get(self.url, headers=self.headers, params=params)

        # 网页解析
        soup = BeautifulSoup(res.content.decode(), 'html.parser')
        body = soup.body

        ol = body.find("ol",attrs={'class','grid_view'})
        movieItems = ol.find_all("div",attrs={'class','item'})

        ret = []
        for movieItem in movieItems:
            rank = movieItem.find("div",attrs={"class","pic"}).em.string

            hdInfo = movieItem.find("div",attrs={"class","info"}).find("div",attrs={"class","hd"})
            doubanAdd = hdInfo.a['href']
            name = ['无','无','无','无']
            spanLen = int(len(hdInfo.find_all('span')))
            for i in range(spanLen):
                name[i] = hdInfo.find_all('span')[i].string
            if name[2] == '无' and name[1] != '无':
                name[1] = name[2]
                name[2] = '无'
            cnName = name[0].lstrip()
            enName = name[1].lstrip()
            otherName = name[2].lstrip()

            bdInfo = movieItem.find("div", attrs={"class", "info"}).find("div", attrs={"class", "bd"})
            desc = bdInfo.find_all('p')[0].get_text()
            peopleDesc = re.findall(re.compile(pattern=r'(.*?)\n'), desc)[1].lstrip()
            yearTypeDesc = re.findall(re.compile(pattern=r'(.*?)\n'), desc)[2].lstrip()
            recordNum = str(re.findall("\d+",bdInfo.find("div",attrs={"class","star"}).find_all("span")[3].get_text()))[2:-2]
            if bdInfo.find("p",attrs={"class","quote"}):
                reason = bdInfo.find("p", attrs={"class", "quote"}).span.get_text()
            else:
                reason = ""

            outputData = "【排序】：" + rank + "\n【豆瓣地址】："+ doubanAdd + "\n【中文名】："+ cnName + "\n【英文名】："+ enName + \
                         "\n【别名】：" + otherName + "\n【导演主演】：" + peopleDesc + "\n【年份类型】：" + yearTypeDesc + "\n【评论人数】：" + recordNum + \
                         "\n【推荐理由】：“" + reason + "”\n\n"
            ret.append(outputData)
            self.saveDateToDB(rank, doubanAdd, cnName, enName, otherName, peopleDesc, yearTypeDesc, recordNum, reason)

        self.saveDateToFile(pageNum, (pageNum + 25), ret)

    # 判断是否为空，为空返回空白符
    def checkNull(self,str):
        if str:
            return str
        else:
            return ""

    # 存储到文件夹当中
    def saveDateToFile(self,beginRank,endRank,datas):
        if not os.path.isdir(self.targetPath):
            os.mkdir(self.targetPath)
        path = self.targetPath + "\\page_" + str(beginRank + 1) + "_" + str(endRank) + ".txt"
        file = open(path,"wb")
        pageDes = "=====  当前页从第 " + str(beginRank + 1) + " 位电影到第 " + str(endRank) + " 位电影  =====\n"
        file.write(pageDes.encode("utf-8"))
        for data in datas:
            file.write(data.encode("utf-8"))
        file.close()
        print("当前页从第 " + str(beginRank + 1) + " 位电影到第 " + str(endRank) + " 位电影" + "已经成功写入到文件中，文件地址为："+path)

    # 存储到数据库当中
    def createDbConnection(self):
        self.connection = pymysql.connect(host='localhost', user='root', password='root', db='music163', port=3306,charset="utf8")

    def saveDateToDB(self, rank, doubanAdd, cnName, enName, otherName, peopleDesc, yearTypeDesc, recordNum, reason):
        cursor = self.connection.cursor()
        with self.connection.cursor() as cursor:
            sql = "INSERT INTO t_douban_movie_top(rank, douban_add, cn_name, en_name, other_name, people_desc, year_type_desc, record_num, reason)" + \
                  "VALUES(%s, %s, %s ,%s, %s, %s, %s, %s ,%s)"
            cursor.execute(sql, (rank, doubanAdd, cnName, enName, otherName, peopleDesc, yearTypeDesc, recordNum, reason))
        self.connection.commit()

    def closeDbConnection(self):
        self.connection.close()

    def run(self):
        pageArray = self.getPageArray()
        self.createDbConnection()
        for pageNum in pageArray:
            self.getSinglePageInfo(pageNum)
        self.closeDbConnection()
        print("所有电影都已成功写入文件和数据库当中")

if __name__ == "__main__":
    splider = doubanSpliderMovie()
    splider.createDbConnection()
    try:
        splider.getSinglePageInfo(225)
        print("所有电影都已成功写入文件和数据库当中")
    except Exception as e:
        print('错误: ' + str(e))
    splider.closeDbConnection()

