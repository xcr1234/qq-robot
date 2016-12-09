#smart QQ聊天机器人

##这个API已经失效了，请大家访问：
https://github.com/xcr1234/smartqq

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

如果maven执行失败，可以尝试修改pom.xml，将repositories中的
```xml
<repository>
            <id>webqq-core github</id>
            <url>https://raw.githubusercontent.com/xcr1234/webqq-core/master/mvn-repo-snapshot</url>
</repository>
```
改为
```xml
<repository>
    <id>webqq-core coding</id>
    <url>https://coding.net/u/xcr_abcd/p/webqq-core/git/raw/master/mvn-repo-snapshot</url>
</repository>
```

---

**该项目仅供学习交流使用！**

---

技术参考：

https://github.com/xcr1234/webqq-core

http://www.tuling123.com/


