'''
6-配置BeautifulSoup4+lxml+html5lib

BeautifulSoup4和html5lib都可以直接用pip install直接安装，lxml则需要下载xml再安装

BeautifulSoup4能够高效的解析各类文档，从而达到快速提取网页元素的功能
该例子就是简单的使用BeautifulSoup4解析网页的例子
'''

html_doc = """
<html><head><title>The Dormouse's story</title></head>
<body>
<p class="title"><b>The Dormouse's story</b></p>

<p class="story">Once upon a time there were three little sisters; and their names were
<a href="http://example.com/elsie" class="sister" id="link1">Elsie</a>,
<a href="http://example.com/lacie" class="sister" id="link2">Lacie</a> and
<a href="http://example.com/tillie" class="sister" id="link3">Tillie</a>;
and they lived at the bottom of a well.</p>

<p class="story">...</p>
"""

from bs4 import BeautifulSoup

# 注意： 在声明BeautifulSoup对象的时候要明确解析器 soup = BeautifulSoup(html,'html5lib')，否则写为 soup = BeautifulSoup(html) 会有警告。
soup = BeautifulSoup(html_doc,'html5lib')

print("\n可以提取网页元素")
print(soup.find_all('a'))

print("\n从文档中找到所有<a>标签的链接")
for link in soup.find_all('a'):
    print(link.get('href'))


print("\n可以格式化输出页面")
print(soup.prettify())