'''
redis-订阅发布
'''
import time,threading
import rediscommon

r = rediscommon.r

def publisher(n):
    time.sleep(1)
    for i in range(n):
        r.publish('channel', i)
        time.sleep(1)

def run_pubsup():
    threading.Thread(target=publisher, args=(3,)).start()
    pubsub = r.pubsub() # 查看订阅状态
    pubsub.subscribe(['channel']) # 订阅
    count = 0
    for item in pubsub.listen():
        print(item)
        count = count + 1
        if count == 4:
            pubsub.unsubscribe()
        if count == 5:
            break

r.flushdb()
run_pubsup()