'''
3、根据专辑获取歌曲信息

请求地址为：Request URL:http://music.163.com/album?id=26971
参数：id:26971 专辑ID
'''

from sql import mysqlOperate
from bs4 import BeautifulSoup
import commconst
import requests
import time

class song(object):
    headers = commconst.headers

    def getAllSongs(self,album_id, album_name, mysqlOperate):
        params = {"id":album_id}
        res = requests.get("http://music.163.com/album",headers=self.headers, params=params)

        soup = BeautifulSoup(res.content.decode(), "html.parser")
        body = soup.body

        songs = body.find("ul",attrs={'class':'f-hide'}).find_all("li")

        for song in songs:
            mysqlOperate.insert_songs(album_id, song.find("a")['href'].replace('/song?id=', ''), song.get_text())
            print("专辑《%s》的歌曲《%s》插入到数据库成功！" % (album_name, song.get_text()))

if __name__ == '__main__':
    song = song()
    mysqlOperate = mysqlOperate()

    mysqlOperate.create_connection()
    colums = mysqlOperate.get_all_albums()
    for colum in colums:
        try:
            print("\n ==== 专辑《%s》的歌曲插入到数据库 BEGIN ==== " % (colum[1]))
            song.getAllSongs(colum[0], colum[1], mysqlOperate)
            print("\n ==== 专辑《%s》的歌曲插入到数据库 END ==== " % (colum[1]))
            time.sleep(1)
        except Exception as e:
            print("ERR:"+str(colum[0]) + ': ' + str(e))
            time.sleep(5)
    mysqlOperate.close_connect()