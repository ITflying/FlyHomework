'''
5-单线程爬取CSDN全部博文

爬取CSDN全部博文需要注意的是随着其主题不同，也就是样式不同，要跟着变更正则表达式。
我感觉这个最重要的就是正则表达式编写完成之后能够把每一页信息存储下来就好了
关键点：
1、获取每一页的地址
2、分析博文的形式，写出正确的正则表达式
3、将内容压缩解压到文件里面
博文爬取内容：
发表日期、是否原创标记、博文标题、博文链接、浏览量、评论量
'''

import urllib.request,os,re,sys,socket
import requests
import gzip

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

        pages = r'<div.*?pagelist">.*?<span>.*?共(.*?)页</span>'
        pattern = re.compile(pages, re.DOTALL)
        res = re.findall(pattern,data)
        pagesNum = res[0]
        return pagesNum

    # 设置要抓取的博文页面
    def setPage(self,idx):
        self.url = self.url[0:self.url.rfind("/")+1]+str(idx)

    # 正则表达式读取博文信息
    def readData(self):
        ret = []
        str = r'<div.*?article_item">.*?<span class="(.*?)"></span>.*?link_title"><a href="(.*?)">(.*?)</a>.*?' + \
              r'<span class="link_postdate">(.*?)</span>.*?</a>\((.*?)\)</span>.*?' + \
              r'</a>.*?\((.*?)\)</span>' + \
              r'.*?article_description">(.*?)</div>.*?'

        response = session.get(url=self.url, headers=self.headers)
        data = response.text

        pattern = re.compile(str, re.DOTALL)
        items = re.findall(pattern, data)
        for item in items:
            introduce = item[6].replace("&#160;"," ")
            if item[0] == "ico ico_type_Original":
                flag = '原'
            else:
                flag = '转'
            ret.append('\r\n日期：' + item[3] + '\t' + flag
                       + '\r\n标题：' + item[2].strip()
                       + '\r\n简介：' + introduce.strip()
                       + '\r\n链接：http://blog.csdn.net' + item[1]
                       + '\r\n阅读：' + item[4] + '\r\n评论：' + item[5] + '\r\n')
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