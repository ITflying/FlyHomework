'''
4、手工输入验证码模拟登陆知乎.

模拟登陆知乎，利用知乎私信来判断是否登录成功并打包显示首页内容
关键点：
1、通过登录分析，登录需要发送_xsrf、password、captcha_type:cn、phone_num/email四个参数
2、正则表达式获取随机码
3、pil获取验证码图片并手动输入验证码
4、最后判断即可
最关键的是：知乎在应用了“点击倒立的文字”之后同样也可以利用这个之前的图片地址下载验证码图片来进行验证

感谢CSDN博主oldbig_lin提供的代码：http://blog.csdn.net/JavaLixy/article/details/78010318?ref=myrecommend
2017年12月14日18:47:43 验证码图片手动输入可行
'''

import urllib.request,os,re,sys,socket
import requests

targetPath = "D:\\Project_GitHub\\python-learn\\tmp\\reptile\\wind"

# 设置请求头等数据
session = requests.session()

agent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36"
headers = {
    "HOST": "www.zhihu.com",
    "Referer": "https://www.zhizhu.com",
    'User-Agent': agent
}

# 首先获取知乎页面的随机码 _xsrf
def getXsrf():
    response = session.get("https://www.zhihu.com", headers=headers)
    data = response.text
    cer = re.compile('name=\"_xsrf\" value=\"(.*)\"',flags=0)
    strlist = cer.findall(data)
    return strlist[0]

# 获取用户账户名
def getAccountName(data):
    cer = re.compile('<span class=\"name\">(.*)</span>',flags=0)
    strlist = cer.findall(data)
    return strlist[0]

# 获取验证码并根据下载的图片输入验证码，在知乎应用了“点击倒立的文字”之后同样也可以利用这个之前的图片地址下载验证码图片来进行验证
def getCaptcha():
    # 将sesion中存储的图片下载到本地
    import time
    t = str(int(time.time())*1000)
    captcha_url = "https://www.zhihu.com/captcha.gif?r={0}&type=login".format(t)
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
def zhihuLogin(account,passwd):
    if re.match("^1\d{10}",account):
        print("手机号登录")
        post_url = "https://www.zhihu.com/login/phone_num"
        post_data = {
            "_xsrf": getXsrf(),
            "phone_num": account,
            "password": passwd,
            "captcha": getCaptcha()
        }
        response_text = session.post(post_url, data=post_data, headers=headers)
    else:
        print("邮箱登录：",account)
        post_url = "https://www.zhihu.com/login/email"
        post_data = {
            "_xsrf": getXsrf(),
            "email": account,
            "password": passwd,
            "captcha": getCaptcha()
        }
        response_text = session.post(post_url, data=post_data, headers=headers)

# 判断是否登录
def isLogin():
    # 通过个人中心页面返回状态码来判断是否为登录状态
    inbox_url = "https://www.zhihu.com/inbox"
    # allow_redirects使重定向为false
    response = session.get(inbox_url, headers=headers, allow_redirects=False)
    if response.status_code == 200:
        print("登陆的账户名为：",getAccountName(response.text))
        return "登录成功"
    else:
        return "登录失败"

zhihuLogin("youraccountname","yourpasswordd")
print("账户登录结果：",isLogin())
