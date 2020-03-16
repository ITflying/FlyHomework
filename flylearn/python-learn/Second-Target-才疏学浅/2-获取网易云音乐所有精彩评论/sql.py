"""
sql.py

用于进行数据库操作的一系列方法
步骤一般是：创建连接、执行语句、处理结果

一般 Python 用于连接 MySQL 的工具：pymysql
"""
import pymysql.cursors

class mysqlOperate():
    def __init__(self, host = 'localhost', user = 'root', password = 'root', dbName = 'music163', port=3306, charset="utf8"):
        self.host = host
        self.user = user
        self.password = password
        self.db = dbName
        self.port = port
        self.charset = charset

    # 创建连接
    def create_connection(self):
        self.connection = pymysql.connect(host=self.host, user=self.user, passwd=self.password, db=self.db, port=self.port, charset=self.charset)

    # 获取数据库表数量
    def get_all_tables(self):
        with self.connection.cursor() as cursor:
            sql = "show tables"
            print("目前共有数据库表：%s 个" % (cursor.execute(sql,())))

    # 插入歌手信息
    def insert_artists(self,artist_id, artist_name, category_type):
        with self.connection.cursor() as cursor:
            sql = "INSERT INTO `artists`(`ARTIST_ID`, `ARTIST_NAME`, `artist_type`) VALUES (%s, %s, %s)"
            cursor.execute(sql, (artist_id, artist_name, category_type))
        self.connection.commit()

    # 获取所有歌手信息 [0]id [1]artistsId [2] artisitsName
    def get_all_artists(self):
        with self.connection.cursor() as cursor:
            sql = "SELECT DISTINCT ARTIST_ID,ARTIST_NAME FROM artists"
            cursor.execute(sql)
            return cursor.fetchall()

    # 插入歌手的专辑信息
    def insert_album(self,artist_id, ALBUM_ID, album_name,album_time):
        with self.connection.cursor() as cursor:
            sql = "INSERT INTO albums (artist_id, album_id, album_name,album_time) VALUES (%s, %s, %s ,%s)"
            cursor.execute(sql, (artist_id, ALBUM_ID, album_name, album_time))
        self.connection.commit()

    # 获取所有专辑信息
    def get_all_albums(self):
        with self.connection.cursor() as cursor:
            sql = "SELECT DISTINCT album_id, album_name FROM albums"
            cursor.execute(sql)
            return cursor.fetchall()

    # 插入专辑的歌曲信息到数据库
    def insert_songs(self,ALBUM_ID, MUSIC_ID, MUSIC_NAME):
        with self.connection.cursor() as cursor:
            sql = "INSERT INTO musics (ALBUM_ID, MUSIC_ID, MUSIC_NAME) VALUES (%s, %s, %s)"
            cursor.execute(sql, (ALBUM_ID, MUSIC_ID, MUSIC_NAME))
        self.connection.commit()

    # 获取所有歌曲信息
    def get_all_songs(self):
        with self.connection.cursor() as cursor:
            sql = "SELECT DISTINCT MUSIC_ID, MUSIC_NAME FROM musics"
            cursor.execute(sql)
            return cursor.fetchall()

    # 插入所有歌曲的精彩评论信息
    def insert_songs_comments(self, MUSIC_ID, music_name, nickname, liked_count, create_time, content):
        with self.connection.cursor() as cursor:
            sql = "SET NAMES utf8mb4"
            cursor.execute(sql)
            sql = "INSERT INTO comments (MUSIC_ID, music_name, nickname, liked_count, create_time, content) " \
                                        "VALUES (%s, %s, %s, %s, %s, %s)"
            cursor.execute(sql, (MUSIC_ID, music_name, nickname, liked_count, create_time, content))
        self.connection.commit()


    # 关闭连接
    def close_connect(self):
        self.connection.close()


if __name__ == "__main__":
    mysqlOperate = mysqlOperate()
    mysqlOperate.create_connection()

    # mysqlOperate.insert_artists("Tst","Test")
    artists = mysqlOperate.get_all_artists()

    mysqlOperate.close_connect()