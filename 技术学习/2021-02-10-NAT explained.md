# 2021-02-10-NAT explained

[(47) NAT Explained | Overload, Dynamic & Static - YouTube](https://www.youtube.com/watch?v=qij5qpHcbBk&list=PLF1hDMPPRqGxpYdo0ctaa7MxfOi9vjs1u&index=19)

![image-20210210103929160](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210103929160.png)

NAT的使用场景（个人记忆是为了缓解IP地址空间不够用的现状，且主要是指IPv4

---

解决的过程主要是引入了private address，即私有地址的概念	 

![image-20210210104205093](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210104205093.png)

这三柱地址区间是很重要的。这三组地址不允许发布到公网上 

![image-20210210104332837](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210104332837.png)

通常一个家庭，ISP网络服务提供商只会分配一个公网地址，那么多设备要实现同时上网的需求，就需要用到NAT技术。将私有地址转换为公有地址。

 

---

首先是Overload （PAT） aka. port address translation

![image-20210210104645185](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210104645185.png)

简单理解，就是利用网关路由器，做了一个端口映射的工作

----

Dynamic 方式的NAT。 不存储IP地址

需要创建一个公网地址的pool

![image-20210210104932282](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210104932282.png)

然后内网的设备需要与公网交流的时候，就从pool中取地址，与本数据包的源ip进行替换

---

当然还有static静态的方式。通常会用于网页服务器，因为他们的端口号总是固定的。

