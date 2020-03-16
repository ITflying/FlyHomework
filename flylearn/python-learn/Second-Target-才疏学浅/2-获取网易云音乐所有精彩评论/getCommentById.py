"""
getCommentById.py

根据歌曲ID来获取歌曲的热门评论

这里可以采用多线程和不同代理完成更快的读取，但是我不会设置Python，先学习一下再来修改，先单线程

PS:遇到了表情插入会报错的问题，解决方案就是
1、将字段字符集设置为utf8mb4，排序方式为：utf8mb4_general_ci
2、在sql插入之前先执行SET NAMES utf8mb4即可
            sql = "SET NAMES utf8mb4"
            cursor.execute(sql)
"""
from sql import mysqlOperate
from saveFile import saveComment
import requests,re
import json
import datetime,time

import commconst

class comment(object):
    headers = commconst.headers

    parametes = commconst.comment_parametes

    data = commconst.comment_data

    # 设置代理
    proxies = {'http': 'http://127.0.0.1:10800'}

    def get_wonderful_comments(self,musicId):
        res = requests.post('http://music.163.com/weapi/v1/resource/comments/R_SO_4_' + str(musicId),
                            headers=self.headers,params=self.parametes,data=self.data)
        return res.json()

    def insert_wonderful_comments(self,musicId, musicName, mysqlOperate):
        res = requests.post('http://music.163.com/weapi/v1/resource/comments/R_SO_4_' + str(musicId),
                            headers=self.headers, params=self.parametes, data=self.data)
        res = res.json()
        for hotcomment in res['hotComments']:
            # Unix毫秒时间戳转化为时间
            createTime = datetime.datetime.fromtimestamp(hotcomment['time'] / 1000)
            mysqlOperate.insert_songs_comments(musicId, musicName, hotcomment['user']['nickname'], hotcomment['likedCount'], str(createTime)[0:19], hotcomment['content'])


if __name__ == '__main__':
    comment = comment()
    mysqlOperate = mysqlOperate()
    mysqlOperate.create_connection()

    songs = mysqlOperate.get_all_songs()
    for song in songs:
        print("\n === 歌曲《%s》的精彩评论装载数据库 BEGIN === " % (song[1]))
        comment.insert_wonderful_comments(song[0],song[1], mysqlOperate)
        print(" === 歌曲《%s》的精彩评论装载数据库 END === \n" % (song[1]))

    mysqlOperate.close_connect()
    print("【所有歌曲评论已经完成装载】")