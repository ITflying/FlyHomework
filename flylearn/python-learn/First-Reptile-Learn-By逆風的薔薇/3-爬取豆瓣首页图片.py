'''
3、爬取豆瓣首页图片

使用urlib将豆瓣首页图片爬取下来并保存到指定文件夹当中
关键点：
1、re.findall r'(https:[^\s]*?(png|gif|jpg))'正则表达式筛选图片
2、urlretrieve将远程数据下载到本地
'''

import urllib.request,os,re,sys,socket

# 定义保存文件的函数
targetPath = "D:\\Project_GitHub\\python-learn\\tmp\\reptile\\wind"
def getPicPath(path):
    # 检测当前路径的有效性
    if not os.path.isdir(targetPath):
        os.mkdir(targetPath)

    # 设置每个图片的路径
    pos = path.rindex('/')
    picPath = os.path.join(targetPath,path[pos+1:]) # 这里是拼接文件地址
    return picPath

# 设置网页
url = "https://www.douban.com/"
headers = {'User-Agent':'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                        'Chrome/63.0.3239.84 Safari/537.36'}

# 设置请求
request = urllib.request.Request(url=url,headers=headers)

# 获取爬取结果
response = urllib.request.urlopen(request)
data = response.read()

# 循环下载图片到指定位置
for link,t in set(re.findall(r'(https:[^\s]*?(png|gif|jpg))',str(data))):
    print(link)
    try:
        urllib.request.urlretrieve(link,getPicPath(link))
    except:
        print('FATILED')