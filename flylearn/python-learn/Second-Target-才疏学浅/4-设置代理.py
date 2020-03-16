'''
使用多线程可以开启多个任务，而多个线程我希望能使用不同的IP访问网站
这个时候就需要学习如何设置代理了
防止IP被封

高匿和透明区别：
1、透明代理的意思是客户端根本不需要知道有代理服务器的存在，但是它传送的仍然是真实的IP。你要想隐藏的话，不要用这个。
2、普通匿名代理能隐藏客户机的真实IP，但会改变我们的请求信息，服务器端有可能会认为我们使用了代理。
不过使用此种代理时，虽然被访问的网站不能知道你的ip地址，但仍然可以知道你在使用代理，当然某些能够侦测ip的网页仍然可以查到你的ip。
3、高匿名代理不改变客户机的请求，这样在服务器看来就像有个真正的客户浏览器在访问它，这时客户的真实IP是隐藏的，服务器端不会认为我们使用了代理。

最后的执行结果由于有了网站有了反爬虫，所以拒绝连接

学习地址：
http://blog.csdn.net/c406495762/article/details/60137956
http://blog.csdn.net/lammonpeter/article/details/52917264
'''

from urllib import request
from bs4 import BeautifulSoup

import requests
import random


def set_proxy_by_hand():
    # 访问网址
    url = 'http://www.whatismyip.com.tw/'
    # 这是代理IP
    proxy = {'http': '106.46.136.112:808'}
    # 创建ProxyHandler
    proxy_support = request.ProxyHandler(proxy)
    # 创建Opener
    opener = request.build_opener(proxy_support)
    # 添加User Angent
    opener.addheaders = [('User-Agent',
                          'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36')]
    # 安装OPener
    request.install_opener(opener)
    # 使用自己安装好的Opener
    response = request.urlopen(url)
    # 读取相应信息并解码
    html = response.read().decode("utf-8")
    # 打印信息
    print(html)

def request_set_proxy():
    print("requests里面有个proxies可以简化以上过程")
    # r = requests.post('http://music.163.com/weapi/v1/resource/comments/R_SO_4_' + str(music_id),
    #                   headers=headers, params=params, data=data, proxies=proxies)

# 获取高匿IP代理网站首页的代理IP
class get_xici_ip_list():
    def get_ip_list(self, url, headers):
        web_data = requests.get(url, headers=headers)
        soup = BeautifulSoup(web_data.text, 'lxml')
        ips = soup.find_all('tr')
        ip_list = []
        for i in range(1, len(ips)):
            ip_info = ips[i]
            tds = ip_info.find_all('td')
            ip_list.append(tds[1].text + ':' + tds[2].text)
        return ip_list

    def get_random_ip(self, ip_list):
        proxy_list = []
        for ip in ip_list:
            proxy_list.append('http://' + ip)
        proxy_ip = random.choice(proxy_list)
        proxies = {'http': proxy_ip}
        return proxies

    def use(self):
        url = 'http://www.xicidaili.com/nn/'
        headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36'
        }
        ip_list = self.get_ip_list(url, headers)
        proxies = self.get_random_ip(ip_list)
        print(proxies)

if __name__ == "__main__":
    get_xici_ip_list = get_xici_ip_list()
    get_xici_ip_list.use()