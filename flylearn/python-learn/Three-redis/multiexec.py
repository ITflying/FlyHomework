'''
redis-事务（伪）
将几个命令一起执行
'''
import time,threading
import rediscommon

r = rediscommon.r

def erro_show():
    print(r.incr('notrans:'))
    time.sleep(.1)
    r.incr('notrans:',-1)

def right_show():
    pipline = r.pipeline()
    pipline.incr("trans:")
    time.sleep(.1)
    pipline.incr("trans:", -1)
    print(pipline.execute()[0])

def show():
    for i in range(3):
        threading.Thread(target=right_show).start()
    time.sleep(.5)

show()