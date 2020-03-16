'''
2-获取网易云音乐所有精彩评论.py

学习地址：https://github.com/RitterHou/music-163
按照思路：
1、首先尝试获取一首歌评论内容（根据歌曲ID）
歌曲地址：http://music.163.com/#/song?id=522510615
歌曲评论请求地址：http://music.163.com/weapi/v1/resource/comments/R_SO_4_524003251?csrf_token= (post方式)
属性参数：hotComments

2、在获取一首歌的评论内容之后，那么该怎么获取所有歌曲的ID呢
我刚开始想的是从 000000000 - 999999999 遍历过去，就会有一亿个结果，但是这种方式有点傻。。
根据RitterHou的想法，应该先获取所有歌手，再通过歌手获取所有专辑信息，通过专辑信息获取所有歌曲，再通过歌曲获取评论
'''


