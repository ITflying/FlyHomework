'''
1-获取斗鱼排行榜的各类信息

刚开始，我查看了一下页面，发现斗鱼是采用伪元素before:来显示排行榜信息的，查看了一下连接，发现没有获取排行榜的连接
之后找了一段时间发现原来斗鱼直接在最开始请求的时候就用rankListData和Unicode封装了排行榜信息，解析出来就好了
关键点：
1、排行分区用地址获得
2、排行分类有四类（巨星主播榜、主播粉丝榜、土豪实力榜、主播壕友榜）、统计类型有三种（日周月）
3、原有主播房间名的获取局限于主播房间的样式，如果主播房间样式变化了就没办法正常获取，我直接获取了其加载数据中的房间名
感受：
不同排行榜的数据封装名不一样，中英文混用，大部分都是通过伪元素加载数据的

学习地址：https://github.com/SFLAQiu/SpiderDemo/blob/master/spider/DouYu.py
PS（2019年7月12日09:21:41）：已失效
'''

import urllib.request,os,re,sys,socket
import requests
from enum import Enum
import json,time


targetPath = "D:\\Project_GitHub\\python-learn\\tmp\\reptile\\wind"

# 设置请求头等数据
session = requests.session()

agent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36"
headers = {
    "HOST": "www.douyu.com",
    'User-Agent': agent,
}

# 斗鱼排行榜分类
class ERankCategory(Enum):
    game = "热门游戏"
    PCgame = "电脑游戏"
    wzry = "王者荣耀"
    ktyx = "客厅游戏"
    sjyx = "手机游戏"
    yl = "娱乐"

# 斗鱼排行榜枚举类型
class ERankName(Enum):
    anchor = "巨星主播榜"
    fans = "主播粉丝榜"
    haoyou = "土豪实力榜"
    user = "主播壕友榜"

# 斗鱼排行榜统计类型（主播粉丝榜没有日周月区别，只有周亲密度榜、周新增数榜）
class EStatType(Enum):
    day = "日"
    week = "周"
    month = "月"
    hot = "周亲密度"
    charm = "周新增数"



# 获取排行榜信息
# id排名；nickname主播名；room_id房间号；
def get_rank_info(rankName = ERankName.anchor, rankSort = EStatType.day, rankType = 'game'):
    url = 'https://www.douyu.com/directory/rank_list/' + rankType
    response = session.get(url=url, headers=headers)
    data = response.text

    # 数据类型检查
    if not isinstance(rankName, ERankName):
        raise Exception("rankName 类型错误，必须是ERankName枚举")
    if not isinstance(rankSort, EStatType):
        raise Exception("statType 类型错误，必须是EStatType枚举")

    rankName = '%sListData' % rankName.name
    rankSort = '%sListData' % rankSort.name

    # 正则解析出排行榜JSON数据
    # re.I(忽略大小写)、re.L(依赖locale)、re.M(多行模式)、re.S(.匹配所有字符)、re.U(依赖Unicode)、re.X(详细模式)
    mt = re.search(r'rankListData\s+?=(.*?);', data, re.S)

    # 返回所有括号匹配的字符，以tuple格式
    grps = mt.groups()
    # 数据转json
    rankListDataStr = grps[0]
    rankListData = json.loads(rankListDataStr)
    rankList = rankListData[rankName][rankSort]

    # 重新排序，按照取得元素的id来排序
    rankList.sort(key=lambda k: (k.get('id', 0)), reverse=False)
    return rankList;

# 获取主播房间的信息
# [数据地址](https://www.douyu.com/ztCache/WebM/room/71017)
# 参数：show_time（时间戳）上次直播时间，room_name房间名，show_details主播介绍，second_lvl_name（分类名）
def get_nick_room_info(roomId):
    url = 'https://www.douyu.com/ztCache/WebM/room/%s'%roomId
    response = session.get(url=url, headers=headers)
    data = response.text
    roomAllData = json.loads(data)
    roomDataStr = roomAllData['$ROOM']
    roomData = json.loads(roomDataStr)

    # 时间戳转化为时间
    showTime = time.strftime("%Y-%m-%d %H:%M:%S",time.localtime(roomData['show_time']))
    roomInfo = {'roomName':roomData['room_name'],'showTime':showTime,'categroy':roomData['second_lvl_name']}
    return roomInfo

# 获取不同榜单封装后的数据，因为不同排行榜需要展示的数据也不一样
def get_detail_info(rank,rankName):
    isLive = {"True": "直播中", "False": "未直播"}

    if (rankName.name == "anchor"):
        roomInfo = get_nick_room_info(rank['room_id'])
        print("%s %s 房间号(%s)：%s \t[是否直播：%s，荣誉值：%s，主播等级：%s，上次直播时间：%s，分类：%s]" % (
            rank['id'], rank['nickname'], rank['room_id'],
            roomInfo['roomName'], isLive[str(rank['is_live'])], rank['sc'], rank['anchorLevelInfo']['level'],
            roomInfo['showTime'], roomInfo['categroy']))
    elif (rankName.name == "fans"):
        print(rank)
        print(rank)
    elif (rankName.name == "haoyou"):
        print(rank)
        print(rank)
    elif (rankName.name == "user"):
        print(rank)
        print(rank)
    else:
        print("没有该榜单信息")

# 获取排行榜数据
def get_anchor_rank_info(rankName = ERankName.anchor, rankSort = EStatType.day, rankType = 'game'):
    rankList = get_rank_info(rankName, rankSort, rankType);
    print("【%s-%s-%s排行榜】" % (ERankCategory[rankType].value, rankName.value, rankSort.value))
    for rank in rankList:
        get_detail_info(rank, rankName)

def run():
    rankName = ERankName.anchor
    rankSort = EStatType.week
    category = 'game'
    get_anchor_rank_info(rankName,rankSort,category)


# 启动程序
run()
