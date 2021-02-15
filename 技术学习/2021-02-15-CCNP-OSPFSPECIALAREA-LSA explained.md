# 2021-02-15-CCNP-OSPFSPECIALAREA-LSA explained

[红茶三杯CCNP_哔哩哔哩 (゜-゜)つロ 干杯~-bilibili](https://www.bilibili.com/video/BV12W411k7ee?p=15&spm_id_from=pageDriver)

![image-20210215082055178](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215082055178.png)

---

## LSA 类型

![image-20210215082140197](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215082140197.png)

链路：就是指的接口，

状态：例如cost

lsa又不是报文，它知识lsu中存储的信息

---

![image-20210215082242869](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215082242869.png)

![image-20210215082302659](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215082302659.png)

1类LSA是每台路由设备都会产生的，是关于自己的

![image-20210215093010608](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215093010608.png)

2类LSA（网络LSA) 是由DR产生的，描述整个MA网络中所有路由器的信息；

![image-20210215093145040](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215093145040.png)

![image-20210215093743428](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215093743428.png)

![image-20210215093809888](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215093809888.png)

上图中 村委会即ABR的地位。

---



3类LSA是将某个MA的区域信息进行归纳然后向其他区域进行信息的传递 （由ABR来传播）

![image-20210215094050469](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215094050469.png)

---



且3类lsa和4类lsa共用一种结构

![image-20210215094432484](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215094432484.png)

---

![image-20210215083220208](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215083220208.png)

5类LSA由ASBR向ospf**注入**，描述的是**域外**的路由信息等

![image-20210215083545183](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215083545183.png)

此时这个ABR就很关键，产生4类LSA（主机LSA) 将城门的信息注入骨干区域，以实现每个区域对“城门”位置的了解。

收到5类LSA不代表一定能通往外部。

---

![image-20210215085829746](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215085829746.png)

---



> ![image-20210215093029448](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215093029448.png)



---



O E 是代表外部

O IA 是内部，的 是第三类lsa传递过来的信息

![image-20210215095032950](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215095032950.png)

OE 1，2 的实质性差别是在metric值的计算方式不同

show IP ospf database

![image-20210215113309597](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215113309597.png)

OE1：默认引入的metric值是20，仍然采用累加cost值的方式来进行metric值的更新

OE2：不会累加内部的接口，始终按照引入时设定的metric值的大小。（这也是可以进行默认选取的）

---



### OSPF LSDB和路由表

![image-20210215113605099](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215113605099.png)

注意，是有优先级差异的

![image-20210215113631892](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215113631892.png)

有，且只能有一个area 0

## OSPF特殊区域

### 末梢区域 stub area

对上述区域进行设置之后

![image-20210215114317614](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215114317614.png)

4类5类的lsa就不再传入area 1，虽然不再传4，5，但是会额外增加一个默认路由。（即留下了一条通路）

且这个默认路由的注入是自动的。

“如何在大型完了过中保证路由的精简”

- 汇总路由的使用 - 屏蔽明细

---

### 完全末梢区域 totally stub area

![image-20210215154226147](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215154226147.png)

此时ABR可以获取到3，4，5类的lsa，只是对特定区域都将他们进行了屏蔽。

同时也下发默认路由。

注意到，骨干区域禁止配置为末梢区域

----

### NSSA 一个小难点

![image-20210215155109445](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215155109445.png)



![image-20210215154404375](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215154404375.png)

常规来说，stub区域是不能去做重发布的

抽象简约来讲，就是阻挡一部分的外部路由，又允许部分路由注入本区域

![image-20210215154830969](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215154830969.png)

且由于这种特殊的引入方式，已经不是lsa5类的，它是新的7类lsa

1. 这个区域不会接收骨干区域，其他区域过来的4，5类lsa。
2. 本地允许注入外部路由，且以一个7类lsa的方式泛洪。且7类只允许在NSSA区域存在
3. 当7类lsa要去往其他区域的时候，会被ABR转换为5类lsa

NSSA区域的ABR不会自动向该区域注入默认路由，这也是与之前两种形式不同的地方。

> 扩展:
>
> 还有totally NSSA区域，是在NSSA的基础之上。
>
> 只允许本区域的1，2，7，会下发一条默认的3类路由（这是会自动下发的）

![image-20210215155609523](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215155609523.png)

---

### 小结：

![image-20210215155741224](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215155741224.png)

![image-20210215155754231](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215155754231.png)

#### OSPF区域类型与可能存在的LSA类型对照表

![image-20210215155857008](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215155857008.png)

注意，只要有接口属于stub区域，都要在设备上进行配置

![image-20210215160005136](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215160005136.png)

![image-20210215160052582](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215160052582.png)

---

[红茶三杯CCNP_哔哩哔哩 (゜-゜)つロ 干杯~-bilibili](https://www.bilibili.com/video/BV12W411k7ee?p=16&spm_id_from=pageDriver)

![image-20210215161146273](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215161146273.png)

### 特殊区域的配置 在工程中的应用

![image-20210215161203486](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215161203486.png)

![image-20210215161500668](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215161500668.png)

---

## OSPF 高级功能配置以及验证

![image-20210215164045182](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215164045182.png)

### 被动接口

![image-20210215164101242](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215164101242.png)

很多动态协议都有的特性

如果某个接口被设置为了passive的状态，那么他不会去接收任何的hello包，解决无用的hello包在不断的泛洪

很常用的一个功能。

在rip上收到更新会接收，不会发

ospf不发也不收

![image-20210215165227724](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215165227724.png)

---

### 注入默认路由

![image-20210215165251473](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215165251473.png)

红字即借助ospf来传递默认路由。

![image-20210215165718565](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215165718565.png)

+always字段，是不管失效，始终向ospf域传递信息

### 路由汇总

![image-20210215165746480](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215165746480.png)

有两种ospf路由汇总的方式。两种方式针对两种场合

#### 外部路由汇总

![image-20210215165937892](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215165937892.png)

由引入的源进行操作，尽量选择紧凑的网络号来囊括这些明细。

针对的是5类，7类lsa

在ASBR

#### 区域间路由汇总

![image-20210215170441619](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215170441619.png)

本区域内的明细

是在ABR上进行

对1类，2类lsa汇总

 ### virtual-link

![image-20210215170610578](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215170610578.png)

来保证某种意义上的直连，以符合ospf的要求（所有区域必须经过骨干区域area 0进行中转）

这只是一个临时的手段

![image-20210215171118400](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215171118400.png)

![image-20210215171451331](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215171451331.png)

---

### OSPF身份验证

![image-20210215171505755](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215171505755.png)

LSA可以逆向推出网络的拓扑结构

![image-20210215171649371](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215171649371.png)

![image-20210215171813177](2021-02-15-CCNP-OSPFSPECIALAREA-LSA%20explained.assets/image-20210215171813177.png)

虚链路也要进行认证的配置。

---

