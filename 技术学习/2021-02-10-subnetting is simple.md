# 2021-02-10-subnetting is simple

[(47) subnetting is simple - YouTube](https://www.youtube.com/watch?v=ecCuyq-Wprc)

> Example used in the video:  One day your supervisor walks to you, saying: “here is the network ID 192.168.4.0/24,  please create three separate networks or subnets for a coffee shop: Sunny Cafe.”   The three separate subnets/networks are: One is for the office,  one for the front desk and storage room, and one is for public use.  Your task is to list each subnet  network ID,  subnet mask,  Host ID Range, # of usable host IDs, and Broadcast ID.  One last question: How many subnets are wasted after subnetting?  Subnetting Practice With the same network ID: 192.168.4.0/24,  you are required  to get 6 subnets. List each of new network ID, subnet mask, host ID range,  # of usable hosts, and broadcast ID.  One last question: how many subnets are wasted after subnetting?  Answer for your reference:  We will have 8 subnets. subnet mask is /27 for all 8 subnets. The number of usable host IDs is 30 for all 8 subnets. I  list below 8 subnets’ network ID,host ID range, and broadcast ID in the order.    #1: 192.168.4.0,   192.168.4.1-192.168.4.30,      192.168.4.31 #2: 192.168.4.32,  192.168.4.33-192.168.4.62,    192.168.4.63 #3: 192.168.4.64,   192.168.4.65-192.168.4.94,   192.168.4.95 #4: 192.168.4.96,   192.168.4.97-192.168.4. 126,    192.168.4.127 #5: 192.168.4.128,  192.168.4.129-192.168.4. 158,   192.168.4.159 #6: 192.168.4.160 ,  192.168.4.161-192.168.4. 190,     192.168.4.191 #7: 192.168.4.192,    192.168.4.193-192.168.4. 222,     192.168.4.223 #8: 192.168.4.224,   192.168.4.225-192.168.4. 254,       192.168.4.255

----

这是一个教人去划分子网的案例

先准备一张表

![image-20210210084648128](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210084648128.png)

![image-20210210090227439](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210090227439.png)

根据需求，需要得到三个子网，我们从上述表格中选取一列

![image-20210210090417358](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210090417358.png)

在这之中，4 代表要划分出4各子网空间。

64代表每个子网空间会用有64个host id

/26 就是这些划分出的子网的，新的子网掩码

---

![image-20210210092803994](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210092803994.png)

部分ID有指定的作用

![image-20210210092842964](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210092842964.png)

![image-20210210093049215](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210093049215.png)

---

题外话：

我们为什么需要子网验码 mask

![image-20210210100511920](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210100511920.png)

子网掩码可以用来区分本地和远程的设备

![image-20210210103416423](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210103416423.png)

首先，设备A会调用ARP去找设备B的MAC地址

![image-20210210103757083](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210103757083.png)

![image-20210210103802112](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210103802112.png)

