'''
聊天室-利用redis的订阅/发布来完成一个简单的聊天室的构建

可以开启两个模拟聊天室
这个有空的时候可以结合angular做个升级版本的聊天室
'''

import rediscommon,time,json,threading

r = rediscommon.r

def listen():
    pubsup = r.pubsub()
    pubsup.subscribe(['chat'])
    for item in pubsup.listen():
        if item['type'] == 'message':
            print("\n"+json.loads(item['data']))

def say():
    user_name = input("请输入用户名：")
    while True:
        time_str = time.strftime("%Y-%m-%d %H:%M:%S")
        words = input("请输入你想说的话：")
        output = user_name + "(" +time_str +")" + ":" + words;
        r.publish("chat", json.dumps(output))
        time.sleep(.1)

def chat():
    print("欢迎来到简陋的聊天室")
    thread_list = []
    thread_list.append(threading.Thread(target=listen))
    thread_list.append(threading.Thread(target=say))

    for t in thread_list:
        t.setDaemon(True)
        t.start()

    for t in thread_list:
        t.join()

if __name__ == "__main__":
    chat()