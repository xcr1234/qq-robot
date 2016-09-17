#smart QQ聊天机器人

主要功能：
开启后扫描二维码登录QQ，您的QQ消息将被图灵机器人接管（限好友消息），支持以下功能；

![主要功能](func.png)

---

##运行：
配置好Java环境变量，  
进入bin目录运行start.bat。  

    
##通过Maven编译

配置好maven，运行com.qq.Program主类。

命令为：

```
mvn clean compile exec:java -Dexec.mainClass="com.qq.Program"    
```

**仅供学习交流。**

---

技术参考：

https://github.com/xcr1234/webqq-core

http://www.tuling123.com/


