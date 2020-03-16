"""
saveFile.py

用于进行存储工作的函数或者类都定义于此
目前设置成保存到本地

"""
import os

class saveComment():
    def __init__(self, targetPath= "D:\\Project_GitHub\\python-learn\\tmp\\reptile\\music163"):
        self.targetPath = targetPath

    def saveCommentFile(self,commentDatas):
        # 检测当前路径的有效性
        if not os.path.isdir(self.targetPath):
            os.mkdir(self.targetPath)
            
        path = self.targetPath + "\\musci163.txt"
        file = open(path,"wb")
        for comment in commentDatas:
            comment = str(comment) + "\n"
            file.write(comment.encode("utf-8"))
        file.close()