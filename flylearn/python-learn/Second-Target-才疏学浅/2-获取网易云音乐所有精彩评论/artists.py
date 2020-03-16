'''
artists.py

第一步：获取所有歌手信息
地址从华语男歌手 http://music.163.com/#/discover/artist/cat?id=1001
一直到其他组合/乐队 http://music.163.com/#/discover/artist/cat?id=4003

分组为：http://music.163.com/#/discover/artist/cat?id=1001&initial=65
共收集到22408条歌手及组合数据
'''

import commconst

from sql import mysqlOperate
from bs4 import BeautifulSoup
import requests
import time

class artists(object):
    headers = commconst.headers

    artistsIdSet = set([]) # 用于去重

    def getAllArtists(self,category_id,initial, mysqlOperate, category_type):
        params = {'id': category_id, 'initial': initial}
        res = requests.get('http://music.163.com/discover/artist/cat', params=params)

        # 网页解析
        soup = BeautifulSoup(res.content.decode(), 'html.parser')
        body = soup.body

        hot_artists = body.find_all('a', attrs={'class': 'msk'})
        artists = body.find_all('a', attrs={'class': 'nm nm-icn f-thide s-fc0'})

        for artist in hot_artists:
            artist_id = artist['href'].replace('/artist?id=', '').strip()
            if artist_id in self.artistsIdSet:
                continue
            else:
                self.artistsIdSet.add(artist_id)
                artist_name = artist['title'].replace('的音乐', '')
                try:
                    mysqlOperate.insert_artists(artist_id, artist_name, category_type)
                    print("%s %s 数据成功插入数据库！" % (artist_id, artist_name))
                except Exception as e:
                    print(e)

        for artist in artists:
            artist_id = artist['href'].replace('/artist?id=', '').strip()
            if artist_id in self.artistsIdSet:
                continue
            else:
                self.artistsIdSet.add(artist_id)
                artist_name = artist['title'].replace('的音乐', '')
                try:
                    mysqlOperate.insert_artists(artist_id, artist_name, category_type)
                    print("%s %s 数据成功插入数据库！" % (artist_id, artist_name))
                except Exception as e:
                    print(e)


if __name__ == '__main__':
    time_start = time.time();

    mysqlOperate = mysqlOperate()
    mysqlOperate.create_connection()

    artists = artists()
    category_id = ['1001','1002','1003','2001','2002','2003','6001','6002','6003','7001','7002','7003','4001','4002','4003']
    category_type = ['华语男歌手','华语女歌手','华语组合/乐队',
                     '欧美男歌手','欧美女歌手','欧美组合/乐队',
                     '日本男歌手','日本女歌手','日本组合/乐队',
                     '韩国男歌手','韩国女歌手','韩国组合/乐队',
                     '其他男歌手','其他女歌手','其他组合/乐队']
    typeNum = 0
    for id in category_id:
        print ("\n\n【正在插入 id为%s的数据】" %(id))
        for i in range(65, 91):  # A - Z 还有0
            artists.getAllArtists(id, i, mysqlOperate, category_type[typeNum])
        print("【成功插入 id为%s的数据】\n\n" % (id))
        typeNum = typeNum + 1

    mysqlOperate.close_connect()

    time_end = time.time();
    print("【 ---------* 所有歌手信息都已成功插入到数据库，用时(%s)s  *------------- 】" %(time_end - time_start))

