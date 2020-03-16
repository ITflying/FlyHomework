'''
7-使用BeautifulSoup来爬取博文

使用BeautifulSoup来爬取博文就是将之前的正则表达式获取的内容现在采用BeautifulSoup提取元素来进行获取
'''

import urllib.request,os,re,sys,socket
import requests
import gzip
from bs4 import BeautifulSoup

targetPath = "D:\\Project_GitHub\\python-learn\\tmp\\reptile\\wind"

# 设置请求头等数据
session = requests.session()

agent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36"
headers = {
    "Connection": "keep-alive",
    "HOST": "blog.csdn.net",
    'User-Agent': agent,
    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
    "Accept-Encoding": "gzip, deflate, sdch",
    "Accept-Language": "zh-CN,zh;q=0.8"
}

# 定义保存文件函数
def saveFile(datas, idx):
    path = targetPath + "\\page_"+str(idx)+".txt"
    file = open(path,'wb')
    page = "当前页" +str(idx)+"\n"
    file.write(page.encode("utf-8"))
    # 将博文信息写入文件(以utf-8保存的文件声明为gbk)
    for data in datas:
        data = str(data)+"\n"
        file.write(data.encode("utf-8"))
    file.close()

# 解压缩数据
def ungzip(data):
    try:
        #print("正在解压缩...")
        data = gzip.decompress(data)
        #print("解压完毕...")
    except:
        print("未经压缩，无需解压...")
    return data

# 利用面向对象的思想，定义CSDN爬虫类
class CSDNSpider:
    def __init__(self, pageIdx = 1, url="http://blog.csdn.net/sunhuaqiang1/article/list/1"):
        # 默认当前页
        self.pageIdx = pageIdx
        self.url = url
        self.headers = headers

    # 获取总页数
    def getTotalPages(self):
        response = session.get(url=self.url, headers=self.headers)
        data = response.text

        soup = BeautifulSoup(data, 'html5lib')
        tag = soup.find("div","pagelist")
        pagesData = tag.span.get_text()
        # 输出“392条  共20页”，找到其中的数字
        pagesNum = re.findall(re.compile(pattern=r'共(.*?)页'), pagesData)[0]
        return pagesNum

    # 设置要抓取的博文页面
    def setPage(self,idx):
        self.url = self.url[0:self.url.rfind("/")+1]+str(idx)

    # 利用BeautifulSoup和正则表达式读取博文信息
    def readData(self):

        ret = []
        str = r'<div.*?article_item">.*?<span class="(.*?)"></span>.*?link_title"><a href="(.*?)">(.*?)</a>.*?' + \
              r'<span class="link_postdate">(.*?)</span>.*?</a>\((.*?)\)</span>.*?' + \
              r'</a>.*?\((.*?)\)</span>' + \
              r'.*?article_description">(.*?)</div>.*?'

        response = session.get(url=self.url, headers=self.headers)
        data = response.text

        soup = BeautifulSoup(data, 'html5lib')
        # 找到所有的博文代码模块
        items = soup.find_all('div', "list_item article_item")

        for item in items:
            # 文章标识、标题、链接、简介、日期、阅读次数、评论个数
            flagClass = item.find('div',"article_title").span.get('class')
            title = item.find('span', "link_title").a.get_text()
            link = item.find('span', "link_title").a.get('href')
            introduce = item.find('div',"article_description").get_text()
            writeTime = item.find('span', "link_postdate").get_text()
            readers = re.findall(re.compile(r'\((.*?)\)'), item.find('span', "link_view").get_text())[0]
            comments = re.findall(re.compile(r'\((.*?)\)'), item.find('span', "link_comments").get_text())[0]

            if "ico_type_Original" in flagClass:
                flag = '原'
            elif "ico_type_Translated" in flagClass:
                flag = '译'
            elif "ico_type_Repost" in flagClass:
                flag = '转'
            else:
                flag = '无'

            ret.append('\r\n日期：' + writeTime + '\t' + flag
                       + '\r\n标题：' + title.strip()
                       + '\r\n简介：' + introduce.strip()
                       + '\r\n链接：http://blog.csdn.net' + link
                       + '\r\n阅读数：' + readers + '\r\n评论数：' + comments + '\r\n')
        return ret


# 定义爬虫对象
cs = CSDNSpider()
# 获得总页数
pageNum = int(cs.getTotalPages())
print("博文总页数：",pageNum)

# 循环取一页的数据
for idx in range(pageNum):
    cs.setPage(idx)
    print("当前页：",idx+1)
    # 读取所有博文，返回类型为list
    papers = cs.readData()
    saveFile(papers, idx+1)