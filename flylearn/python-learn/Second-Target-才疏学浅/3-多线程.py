'''
在爬取网易云音乐所有精彩评论的时候，等到了第二步，获取所有的歌手的专辑信息的时候，三万个歌手，几十个专辑，速度太慢了
所以我就想学习学习python的多线程来加快速度

目前感觉就是开启多个线程，然后利用Join让每个子线程都能完成

学习地址：
http://www.cnblogs.com/fnng/p/3670789.html
http://www.cnblogs.com/fnng/p/3691053.html
'''

import threading
from time import sleep, ctime


class MyThread(threading.Thread):
    def __init__(self, func, args, name=''):
        threading.Thread.__init__(self)
        self.name = name
        self.func = func
        self.args = args

    def run(self):
        self.func(*self.args)


def super_play(file, time):
    for i in range(2):
        print('Start playing： %s! %s' % (file, ctime()))
        sleep(time)


list = {'爱情买卖.mp3': 3, '阿凡达.mp4': 5}

# 创建线程
threads = []
files = range(len(list))

for k, v in list.items():
    t = MyThread(super_play, (k, v), super_play.__name__)
    threads.append(t)

if __name__ == '__main__':
    # 启动线程
    for i in files:
        threads[i].start()
    for i in files:
        threads[i].join()

# 主线程
print('end:%s' % ctime())