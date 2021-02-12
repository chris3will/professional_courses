# ospf-step by step

[(43) OSPF Explained | Step by Step - YouTube](https://www.youtube.com/watch?v=kfvJ8QVJscc)

一种协议，

内部网关协议

被广泛使用与支持的

链路状态协议



用来去学习route，路由。

结果就是每一个路由器都会学习到网络的路由信息。（信息量是非常大的

在网络中， 各个设备之间

![image-20210209192355655](ospf-step%20by%20step.assets/image-20210209192355655.png)

传递的就是如图LSA

每个设备单独用来存储LSA的地方就是LSDB （database）

----

## 怎样work的。3step

1. become neighbours - two routers running ospf on the same link agree to form a neighbour relationship



2. Exchange database information - the neighbour routers swap their LSDB information with each other

3. Choose the best routes - 每一台设备根据路由表，以及LSDB传递的信息来学习自己的最佳路径



Router ID (RID) 用来识别每个设备。可以人工设定，ospf协议也会帮忙选定

### 选取router id的步骤

1. 人工设定
2. 选取最高的“up”状态夏的loopback IP 地址
3. 选取最高的“up”状态的非loopback IP 地址接口

---

邻居建立关系，首先会发送hello包，且发送之前，也并不知道领取接口的打开状态以及是谁

<img src="ospf-step%20by%20step.assets/image-20210209192957273.png" alt="image-20210209192957273" style="zoom:33%;" />

<img src="ospf-step%20by%20step.assets/image-20210209193003890.png" alt="image-20210209193003890" style="zoom:33%;" />

![image-20210209193118969](ospf-step%20by%20step.assets/image-20210209193118969.png)

返回的时候，就会明确neighbor关系

![image-20210209193146582](ospf-step%20by%20step.assets/image-20210209193146582.png)

----

![image-20210209193319875](ospf-step%20by%20step.assets/image-20210209193319875.png)

ospf会根据优先级选出DR和BDR。这些结果会影响选择。

相当于一个路由反射器（DR，识别出状态的变化，并对其他路由器做出提醒）

---

![image-20210209193526794](ospf-step%20by%20step.assets/image-20210209193526794.png)

这种身份的对比，受制于router id

![image-20210209193627466](ospf-step%20by%20step.assets/image-20210209193627466.png)

在这种信息交换状态下，会传递一种信息

![image-20210209193739906](ospf-step%20by%20step.assets/image-20210209193739906.png)

link state request，它的结果会被返回

![image-20210209193814312](ospf-step%20by%20step.assets/image-20210209193814312.png)



被动方会返回update消息

之后主动方再次做出回应

![image-20210209193954125](ospf-step%20by%20step.assets/image-20210209193954125.png)

注意，最后两个都会进入full状态

![image-20210209194043974](ospf-step%20by%20step.assets/image-20210209194043974.png)

这是一个点对点的过程

----

**选择的过程**

最优路径的选择，是考虑了cost，即有一个cost calculation的过程

![image-20210209194252659](ospf-step%20by%20step.assets/image-20210209194252659.png)

