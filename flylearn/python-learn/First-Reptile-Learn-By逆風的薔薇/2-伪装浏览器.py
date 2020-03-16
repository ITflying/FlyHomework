'''
2、伪装浏览器

使用urlib将豆瓣首页所有信息爬取下来，并使用相关属性获取其值
1、增加请求头，伪装浏览器
2、增加导出文件函数
'''

import urllib.request,os

# 定义保存文件的函数
targetPath = "D:\\Project_GitHub\\python-learn\\tmp\\reptile\\wind"
def saveFile(data):
    # 检测当前路径的有效性
    if not os.path.isdir(targetPath):
        os.mkdir(targetPath)

    f = open(targetPath+"\\doubanIndex2.html", 'wb')
    f.write(data)
    f.close()

# 设置网页
url = "https://www.douban.com/"
headers = {'User-Agent':'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                        'Chrome/63.0.3239.84 Safari/537.36'}

# 设置请求
request = urllib.request.Request(url=url,headers=headers);

# 获取爬取结果
response = urllib.request.urlopen(request)
data = response.read()

saveFile(data)

# 设置解码方式
data = data.decode("utf-8")

# 打印结果
print(data)

# 打印各类参数

print("\n返回类型：",type(response))
print("\nurl：",response.geturl())
print("\n信息：",response.info())
print("\n返回代码：",response.getcode())