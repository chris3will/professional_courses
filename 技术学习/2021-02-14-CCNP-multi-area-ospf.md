# 2021-02-14-CCNP-multi-area-ospf

[红茶三杯CCNP_哔哩哔哩 (゜-゜)つロ 干杯~-bilibili](https://www.bilibili.com/video/BV12W411k7ee?p=13&spm_id_from=pageDriver)

![image-20210214151406128](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214151406128.png)

![image-20210214151449088](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214151449088.png)

因为是开放的，所以允许各个厂商都能进行使用

我们在RFC中基本都能看到标准化文档。

报文是封装在三层的IP包当中来传递信息的。

注意这两个组播的地址

ospf是接口敏感型的协议。

---

![image-20210214151804733](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214151804733.png)

跳数，作为衡量路由优劣的标准。传输的是路由状态，很不是路由信息

LSA 链路状态的描述，这是ospf中发送的内容，发送的信息是基于某一个接口，某一个邻居的描述。把信息泛洪出去，然后自己自己运行spf算法，在自身设备上生成一棵无环的树

---

![image-20210214152113483](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214152113483.png)

注意 router -id 必须是在区域内唯一的。

如果路由器设置了loopback，则选ip地址最大的回环接口作为ip地址，否则将选链路接口中地址最大的ip地址为router-id

---



![image-20210214152506411](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214152506411.png)

注意，我们在ospf中，探讨是基于接口的。

优先级的范围是0-255的。

BDR，DR都会在区域中监听专门为组播预置的ip地址，

![image-20210214152856722](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214152856722.png)

且其他常规路由器发生变化时统一会像DR，BDR泛洪，且网络信息状态更新也是交给DR来实现的

[OSPF - The DR and BDR Roles (packetflow.co.uk)](https://www.packetflow.co.uk/ospf-the-dr-and-bdr-roles/#:~:text=Within OSPF%2C the role of,same%2C multiaccess broadcast network segment.)

这个是DR和BDR的科普

>Within OSPF, the role of the Designated Router (DR) and a Backup Designated Router (BDR) is to act as a central point for exchanging of OSPF information between multiple routers on the same, multiaccess broadcast network segment. Each non-DR and non-BDR router **only exchanges routing information with the DR and BDR**, instead of the exchanging updates with every router on the segment. This significantly reduces the amount of OSPF routing updates sent across the network
>
>- 选取方式
>  - 优先级
>  - router id

### OSPF链路开销 COST

![image-20210214153526093](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214153526093.png)

这个依据的是接口，而不是链路，需要格外注意。

不建议直接在接口处进行带宽的修改，因为这很可能会影响到其他的协议

计算的时候，出现小数就都四舍五入成整数

![image-20210214155141034](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214155141034.png)

这个是挺常用的，注意单位



### OSPF链路的开销

![image-20210214155203776](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214155203776.png)

cost的计算：

![image-20210214155319095](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214155319095.png)

例如从1.0网段到r1，途径每个路由器的入接口的cost值的累加即为所求

### OSPF的三张表

![image-20210214155422448](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214155422448.png)

邻接关系，即看两者的状态是不是FULL。邻居表是我们用来排错的第一工具。

## OSPF 报文类型

![image-20210214155642932](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214155642932.png)

注意，是在三层报文当中的。

1. hello
2. dbd 做一些基本的对话，协商，队lsa先做一个摘要 
3. lsr
4. lsu 在这里封装了lsa完整的报文
5. lsack

---

### 邻居建立过程

![image-20210214160303256](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214160303256.png)

![image-20210214160507102](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214160507102.png)

DBD可以包含LSA也可以不包含

![image-20210214160823025](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214160823025.png)

DBD这个报文，开头有标志位，init位 协商主从，more位（1代表后面还有更多的数据，0代表这是最后一个），master （1代表自己应该是主动，）

![image-20210214161112354](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214161112354.png)

![image-20210214161352965](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214161352965.png)

![image-20210214161437462](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214161437462.png)

![image-20210214161442557](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214161442557.png)

### OSPF邻接关系

![image-20210214162534242](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214162534242.png)

### 链路状态数据的结构

![image-20210214162820936](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214162820936.png)

每条LSA都有一个seq num，如果序列号跟高，则代表更新，才会在本地进行更新，且会通知其他设备。

### OSPF链路状态序列号

![image-20210214162933118](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214162933118.png)

## OSPF 多区域的概念

### OSPF单区域

![image-20210214162954931](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214162954931.png)

![image-20210214163106522](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214163106522.png)

注意这里的好处。划分区域之后，就会有ABR、ASBR这个东西，划分区域之后可以减少泛洪。

> **ABR etc**
>
> Area [border router](https://www.sciencedirect.com/topics/computer-science/border-router) (ABR) A router that connects one or more areas to the OSPF backbone.
>
> **Autonomous system border router (ASBR)** A router that is connected to one or more logical entities (AS), usually through an exterior routing protocol such as BGP.

![image-20210214163347595](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214163347595.png)

area 0，作为骨干区域，是他区域数据传输的汇聚点

![image-20210214163726136](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214163726136.png)

![image-20210214164216949](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214164216949.png)

![image-20210214164233053](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214164233053.png)

### OSPF验证命令

![image-20210214164241749](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214164241749.png)

## OSPF网络类型

![image-20210214165030367](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214165030367.png)

明确是一个接口敏感型的协议

### 点到点类型

![image-20210214170606645](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214170606645.png)

### 广播型多路访问（以太型）

![image-20210214170824810](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214170824810.png)

串行接口建立连接比以太网速度要快。

2way状态下，又WAIT计时器来进行监控。

---

![image-20210214171334919](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214171334919.png)

![image-20210214171344278](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214171344278.png)

“帧中继”非广播，

![image-20210214171636515](2021-02-14-CCNP-multi-area-ospf.assets/image-20210214171636515.png)

注意，DLCI只在本地有效

![img](2021-02-14-CCNP-multi-area-ospf.assets/v2-a1b0cca1a46165092390b8e3329e2ada_b.jpg)

frame-relay 这个关键字就是和帧中继最密切的了。

明确帧中继是非广播型的多路访问介质

rip与eigrp的关闭水平分割的命令是不同的

---

![image-20210215081740030](2021-02-14-CCNP-multi-area-ospf.assets/image-20210215081740030.png)

