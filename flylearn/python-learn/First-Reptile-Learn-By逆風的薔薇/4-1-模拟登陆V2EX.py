'''
4-1-模拟登陆V2EX

在做完模拟登陆知乎知乎，自己尝试做做v2ex的模拟登陆
1、首先查看V2ex登陆请求参数，他们是采用了三个随机码分别作为登录名、密码、验证码参数，可以用正则表达式提取
2、验证码可以通过https://www.v2ex.com/_captcha?once=78463下载显示
3、通过获取时间轴页返回状态以及用户账户名来判断是否登录成功
'''

import urllib.request,os,re,sys,socket
import requests

targetPath = "D:\\Project_GitHub\\python-learn\\tmp\\reptile\\wind"

# 设置请求头等数据
session = requests.session()

agent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36"
headers = {
    "HOST": "www.v2ex.com",
    "Referer": "https://www.v2ex.com/signin",
    'User-Agent': agent
}

# 获取登录名、密码、验证码三个随机码
def getParams():
    response = session.get("https://www.v2ex.com/signin", headers=headers)
    data = response.text
    cer = re.compile('<input type=\"text\" class=\"sl\" name=\"(.*)\" value=',flags=0) # 获取用户名|验证码随机码
    strlist = cer.findall(data)
    cer = re.compile('<input type=\"password\" class=\"sl\" name=\"(.*)\" value=',flags=0)
    pwdStrList = cer.findall(data)
    cer = re.compile('<input type=\"hidden\" value=\"(.*)\" name=\"once\"', flags=0)
    onceStrList = cer.findall(data)
    cer = re.compile('<input type=\"hidden\" value=\"(.*)\" name=\"next\" />', flags=0)
    nextStrList = cer.findall(data)
    randomlist = [strlist[0], pwdStrList[0], strlist[1], onceStrList[0], nextStrList[0]] # 分别是 用户名、密码、验证码随机码、once和netx的值
    return randomlist

# 获取用户账户名
def getAccountName(data):
    cer = re.compile('<a href=\"/member/(.*)\" class=',flags=0)
    strlist = cer.findall(data)
    nameStr = strlist[0]
    post = nameStr.find('\"')
    return nameStr[0:post]

# 获取验证码并根据下载的图片输入验证码 https://www.v2ex.com/_captcha?once=78463
def getCaptcha(once):
    # 将sesion中存储的图片下载到本地
    captcha_url = "https://www.v2ex.com/_captcha?once="+once
    t = session.get(url=captcha_url,headers=headers)
    with open(targetPath+"//captcha.jpg","wb") as f:
        f.write(t.content)
        f.close()

    # 验证码图片处理
    from PIL import Image
    try:
        im = Image.open(targetPath+"//captcha.jpg")
        im.show()
        im.close()
    except:
        pass

    capatche = input("请输入验证码：")
    return capatche

# 登录操作
def V2EXLogin(account,passwd):
    print("V2EX登录")
    post_url = "https://www.v2ex.com/signin"
    parmas = getParams()
    post_data = {
        parmas[0]: account,
        parmas[1]: passwd,
        parmas[2]: getCaptcha(parmas[3]),
        "once": parmas[3],
        "next": parmas[4]
    }
    response_text = session.post(post_url, data=post_data, headers=headers)

# 判断是否登录
def isLogin():
    # 通过时间轴页面返回状态码来判断是否为登录状态
    inbox_url = "https://www.v2ex.com/t"
    # allow_redirects使重定向为false，否则未登陆的用户也会跳转到登录页面返回200的状态码
    response = session.get(inbox_url, headers=headers, allow_redirects=False)
    if response.status_code == 200:
        print("登陆的账户名为：",getAccountName(response.text))
        return "登录成功"
    else:
        return "登录失败"

V2EXLogin("youraccountname","yourpasswordd")
print("账户登录结果：",isLogin())
