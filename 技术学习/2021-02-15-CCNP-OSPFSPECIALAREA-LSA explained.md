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