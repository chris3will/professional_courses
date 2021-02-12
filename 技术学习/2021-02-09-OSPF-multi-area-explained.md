# 2021-02-09-OSPF-multi-area-explained

[(46) OSPF Multi Area Explained - YouTube](https://www.youtube.com/watch?v=PIMnj2oqYIo)

如果在设备如此之多的情况下，仍然使用单区域的ospf模式

![image-20210210082157158](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210082157158.png)

每一个LSDB的信息存储量将非常巨大，

同时，寻找路由的过程也将耗时巨大，

节点过多，容易产生泛洪，因为整个网络的路由设备彼此都要知道对方的信息

----

实际的multi area情况：

![image-20210210082620603](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210082620603.png)

将设备的接口进行区域划分

![image-20210210082718363](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210082718363.png)

通常建议，每个area中的路由设备不超过50台

---

一些规划上的常识

- area需要以0为基础开始标号，且该区域会被认为是backbone area，其他area 必须 “join in” backbone area。
- 需要规划每一个区域的子网 subnetting ![image-20210210082932138](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210082932138.png)

在这里 R2和R3都是 area border routers， 即ABR。他们的接口最少遍布在两个area。

这给了他们从其他各area 去summarize routes的能力

。

这样他们在把路由信息传递给R1的时候，就会有路由汇总，不会展示原有的明细路由，这样路由表需要存储的信息规模就变小了。

同时，如果R5中某一条路由挂掉了，R1发送信息的时候是不去理会的，它还是直接把信息发过去，让信息在里面被相应的路由器去处理

----

有一些信息需要明确，就是怎样判断 **backbone router**：只要在backbone area或者部分处于backbone area之间的即backbone router。

![image-20210210083809729](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210210083809729.png)

