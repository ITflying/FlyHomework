'''
redis-7种常见使用场景实现
'''
# -*- coding: utf-8 -*-

import time,redis,json,threading
import rediscommon

r = rediscommon.r

class simpleRedis:
    # 简单字符串缓存
    def simpleStr(self):
        user_data = {"id":10086,"name":"周星驰","age":18}
        data_json = json.dumps(user_data)
        self.r.set("userData:10086",data_json)
        self.r.expire("userData:10086", 1)
        res_str = self.r.get("userData:10086")
        res_json = json.loads(res_str)
        print("返回的用户名字为：",res_json['name'])
        time.sleep(2)
        res_str = self.r.get("userData:10086")
        print("数据是否已经过期？=》",("过期" if (res_str == None) else "没有过期"))
        web_address = {"google":['google.com','google.com.hk']}
        self.r.hset("address","google",json.dumps(web_address.get("google")))
        res_str = self.r.hget("address", "google")
        res_json = json.loads(res_str)
        print("返回的谷歌地址列表：",res_json)

    # 简单队列
    def simpleList(self):
        self.r.rpush("list_user_key",json.dumps({"id":10086,"name":"周星驰","age":19}))
        self.r.rpush("list_user_key",json.dumps({"id":10085,"name":"吴孟达","age":18}))
        self.r.rpush("list_user_key",json.dumps({"id":10084,"name":"朱茵","age":17}))
        print("当前队列数据为：",self.r.lrange("list_user_key",0,-1))
        self.r.lpop("list_user_key")
        print("当前队列数据为：",self.r.lrange("list_user_key",0,-1))

    # 简单发布订阅
    # 发布
    def publiser(self, n):
        for i in range(n):
            self.r.publish('chanel',i)
            time.sleep(1)
    def sub(self):
        threading.Thread(target=self.publiser,args=(3,)).start()
        pubsub = self.r.pubsub()
        pubsub.subscribe(['chanel'])
        count = 0
        for item in pubsub.listen():
            print(item)
            count = count + 1
            if count == 3:
                pubsub.unsubscribe()
            if count > 4:
                break

    # 简单计数器 incr decr
    def add_counter(self):
        time_str =time.strftime("%Y%m%d %H:%M:%S")
        print("now:",time_str)
        time.sleep(1)
        for i in range(3):
            self.r.incr("counter")
            print(self.r.get("counter"))
            time.sleep(1)
    def counter(self):
        # 多启动几个运行窗口
        threading.Thread(target=self.add_counter())
        threading.Thread(target=self.add_counter())
        threading.Thread(target=self.add_counter())

    # 简单排行榜
    def rank(self):
        self.r.zadd("money",100,'white',150,'red',200,'yellow')
        # 从大到小排列
        print(self.r.zrevrange("money",0,-1))
        # 从小到大排列
        print(self.r.zrange("money",0,-1))

    # 简单字符串悲观锁
    def get_lock(self, expire = 5):
        # 如果之前不存在数据，那么就返回true，否则返回false
        dely_time = time.time() + expire
        is_lock = self.r.setnx("lock", dely_time)
        if is_lock == False:
            lock_time = json.loads(self.r.get("lock"))
            if time.time() > lock_time:
                self.unlock()
                is_lock = self.r.setnx("lock", dely_time)
        return True if is_lock else False
    def unlock(self):
        return self.r.delete("lock")
    def op_lock(self):
        is_lock = self.get_lock(10)
        if (is_lock):
            print("成功获得锁！可以进行对应操作")
            time.sleep(5)
            print("操作成功")
        else:
            print("获取锁失败！")

    # 简单事务的乐观锁
    # 乐观锁就是先获取数据，然后修改的时候跟第一次对比判断是否发生了改变
    def optimistic_lock(self):
        self.r.set("age",18)
        print("当前年龄为：", json.loads(self.r.get("age")))
        # 开启事务
        with self.r.pipeline() as pipe:
            while 1:
                # pipe.watch命令会监视给定的key，当exec时候如果监视的key从调用watch后发生过变化，则整个事务会失败。
                # 注意是pipe.watch不是r.watch。囧，最开始写的是r.watch，还说一直为啥不生效。
                pipe.watch("age")
                self.r.set("age", 200)
                pipe.multi()
                pipe.set("age",28)
                pipe.execute()
                break
        print("当前年龄为：", json.loads(self.r.get("age")))

    def run(self):
        self.r.flushdb()
        self.optimistic_lock()

if __name__ == "__main__":
    sr = simpleRedis()
    sr.run()
