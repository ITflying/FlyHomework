'''
redis - 基础操作
1、跟redis的连接

'''

import rediscommon

r = rediscommon.r

# 清库操作
r.flushdb()

# string类型操作
r.set('id','1')
r.incr('id')
r.incr('id',15)
r.decr('id',1)
print(r.get('id'))
r.append("name",'hello')
r.append("name",' world!')
print(r.get('name'))

# 列表操作
print("\n列表操作")
r.rpush("card","1");
r.rpush("card","2");
r.rpush("card","3");
r.lpop("card");
r.lpop("card");
print(r.lrange("card",0,-1))
r.rpush("list","1")
r.rpush("list","2")
r.rpush("list2","11")
r.brpoplpush("list2","list")
print(r.lrange("list",0,-1))

# 集合
print("\n集合操作")
r.sadd("set-key","a","b","c")
r.srem("set-key","a")
print(r.scard("set-key"))
print(r.smembers("set-key"))

# 散列
print("\n散列操作")
r.hmset("hash-key",{"k1":"v1","k2":"v2","k3":"v3"})
r.hdel("hash-key","k1")
print(r.hlen("hash-key"))
print(r.hgetall("hash-key"))
print(r.hkeys("hash-key"))
print(r.hvals("hash-key"))
print(r.hexists("hash-key","k1"))
print(r.hincrby("hash-key","k1"))

# 有序集合
print("\n有序集合操作")
r.zadd("zset-key", 2, 'a', 1, 'a')
print(r.zcard("zset-key"))
print(r.zrange("zset-key", 0, -1, withscores=True))

# 排序
print("\n排序操作")
r.rpush("sort-input", 11)
r.rpush("sort-input", 22)
print(r.sort("sort-input",alpha=True))
print(r.sort("sort-input",desc=True))
