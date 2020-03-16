'''
1、简单网页爬虫

使用urlib将豆瓣首页所有信息爬取下来，并使用相关属性获取其值
'''

import urllib.request

# 设置网页
url = "https://www.douban.com/"

# 设置请求
request = urllib.request.Request(url);

# 获取爬取结果
response = urllib.request.urlopen(request)
data = response.read()

# 设置解码方式
data = data.decode("utf-8")

# 打印结果
print(data)

# 打印各类参数

print("\n返回类型：",type(response))
print("\nurl：",response.geturl())
print("\n信息：",response.info())
print("\n返回代码：",response.getcode())