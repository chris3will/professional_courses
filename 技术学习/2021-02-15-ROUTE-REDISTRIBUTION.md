# 2021-02-15-ROUTE-REDISTRIBUTION

[红茶三杯CCNP_哔哩哔哩 (゜-゜)つロ 干杯~-bilibili](https://www.bilibili.com/video/BV12W411k7ee?p=19)

P19

---

![image-20210215213900945](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210215213900945.png)

一个网络各部分部署了不同的协议，同时，我们也希望全网是可达的

## 路由重发布的概念

![image-20210215214456295](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210215214456295.png)

![image-20210215214829319](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210215214829319.png)

 ![image-20210215215042814](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210215215042814.png)

---

![image-20210215215124706](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210215215124706.png)

### 路由回馈

![image-20210215215318340](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210215215318340.png)

![image-20210215215400156](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210215215400156.png)

![image-20210215215414042](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210215215414042.png)

 会有次优路径的情况发生

### 管理距离 AD值

![image-20210215215606871](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210215215606871.png)

### 细节

![image-20210215215638033](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210215215638033.png)

#### 度量值 - 默认种子度量值 不同动态协议路由选择时的默认值

![image-20210215220010257](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210215220010257.png)

所以我们常规配置时，需要去人为指定metric值，而避免因为默认的无穷大而影响路由选择。

![image-20210215220618537](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210215220618537.png)

向无类路由协议进行重分发的时候，需要加上subnets关键字。代表所有的子网都会重分发，否则只会重分发主类网络的路由

重发布的路由一定是本地路由表中存在的路由

做重发布是要考虑到方向的

![image-20210216090923983](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210216090923983.png)

 

---

有的时候，我们不希望去用network的方式宣告直连接口

![image-20210216091120302](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210216091120302.png)

因为这样的话该接口即运行了动态路由协议，且会不断的发送hello包，这是没有意义的。这种情况推荐采用重发布直连接口

且重发布直连的接口也会被以外部路由的身份对待

![image-20210216091415662](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210216091415662.png)

本地所有的直连接口

- 物理上up
- 协议上up （ppp需要协商成功[计算机网络基础4：点对点协议PPP_snmplink的博客-CSDN博客](https://blog.csdn.net/qingwufeiyang12346/article/details/79693492)）

当然，也可以重发布静态路由。

![image-20210216091936899](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210216091936899.png)

---

### 单点重分发

#### 单向重分发

#### 双向重分发

---



### 多点重发布

#### 多点单向重发布

![image-20210216092205595](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210216092205595.png)

<img src="2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210216092223561.png" alt="image-20210216092223561" style="zoom:50%;" />

![image-20210216092453843](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210216092453843.png)

![image-20210216093038216](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210216093038216.png)

---

![image-20210216093123648](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210216093123648.png)

![image-20210216093227130](2021-02-15-ROUTE-REDISTRIBUTION.assets/image-20210216093227130.png)

