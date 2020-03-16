'''
第二步：根据之前获取的所有歌手信息获取其专辑信息

专辑列表地址为：http://music.163.com/?#/artist/album?id=3069
信息请求地址为：Request URL:http://music.163.com/artist/album?id=1873
后面的id为歌手ID

PS：速度太慢了，先用一部分数据来读取评论吧
'''

from bs4 import BeautifulSoup
from sql import mysqlOperate
import commconst
import requests
import time

class album(object):
    headers = commconst.headers

    def getAllAlbumInfo(self, artist_id, artist_name, mysqlOperate):
        try:
            params = {'id': artist_id, 'limit': '200'}
            # 获取歌手个人主页
            res = requests.get('http://music.163.com/artist/album', headers=self.headers, params=params)

            # 网页解析
            soup = BeautifulSoup(res.content.decode(), 'html.parser')
            body = soup.body

            albums = body.find_all('a', attrs={'class': 'tit s-fc0'})  # 获取所有专辑
            albums_time = body.find_all('span', attrs={'class': 's-fc3'})  # 获取所有专辑推出时间

            num = 0
            for album in albums:
                albume_id = album['href'].replace('/album?id=', '')
                mysqlOperate.insert_album(artist_id, albume_id, album.get_text(), albums_time[num].get_text())
                print("%s的专辑《%s》（%s） 插入数据库成功！" % (artist_name, album.get_text(), albums_time[num].get_text()))
                num = num + 1

            print("%s的专辑都已成功插入到数据库中！" % (artist_name))
        except Exception as e:
            print(e)




if __name__ == '__main__':
    album = album()
    mysqlOperate = mysqlOperate()
    mysqlOperate.create_connection()

    print("===== 首先获取所有歌手信息 =====")
    artists = mysqlOperate.get_all_artists()
    for artist in artists:
        try:
            print("\n==== 歌手 %s 的专辑插入数据库 BEGIN === " % (artist[1]))
            album.getAllAlbumInfo(artist[0],artist[1], mysqlOperate)
            print("==== 歌手 %s 的专辑插入数据库 END === " % (artist[1]))
            time.sleep(1)
        except Exception as e:
            print(str(artist[1]) + ': ' + str(e))
            time.sleep(5)

    mysqlOperate.close_connect()