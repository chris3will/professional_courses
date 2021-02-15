# 2021-02-12-CCNP-routing selection

[红茶三杯CCNP_哔哩哔哩 (゜-゜)つロ 干杯~-bilibili](https://www.bilibili.com/video/BV12W411k7ee?p=11)

![image-20210213084443354](2021-02-12-CCNP-routing%20selection.assets/image-20210213084443354.png)

---

路由 重要职责之一

- 维护路由表
- 数据转发（可以查找路由） （ 没有找到就转发默认路由，然后转发

![image-20210213084641904](2021-02-12-CCNP-routing%20selection.assets/image-20210213084641904.png)

## 路由信息的来源

![image-20210213084723273](2021-02-12-CCNP-routing%20selection.assets/image-20210213084723273.png)

## 管理距离AD值

![image-20210213085049553](2021-02-12-CCNP-routing%20selection.assets/image-20210213085049553.png)

虚线框就是表示路由选择域

ospf的AD值比rip要小，不会展示在路由表中，但是实际还是被设备存储的

## 最长匹配原则

![image-20210213085355287](2021-02-12-CCNP-routing%20selection.assets/image-20210213085355287.png)

无类：不受IP地址空间的限制。默认的查找行为就是最长匹配原则限制的。

bit by bit，也要注意覆盖的空间是否被遗漏了。

上图从上向下，掩码长度在逐渐变短。

### 超网

![image-20210213090107681](2021-02-12-CCNP-routing%20selection.assets/image-20210213090107681.png)

相当于是向前去借用网络号；移动那个网络位和主机位之间的分隔线

缺省地址，指的就是0.0.0.0

![image-20210213090423425](2021-02-12-CCNP-routing%20selection.assets/image-20210213090423425.png)

前缀长度、掩码长度都在变化。



---

![image-20210213090450321](2021-02-12-CCNP-routing%20selection.assets/image-20210213090450321.png)



路由器上默认都是 ip classless



匹配路由，如果是有类的方式，则先看网络号是否有匹配，如果已经确定要深入网络号了，则如果没有找到匹配的路由之后则会直接舍弃，而不是无类路由未匹配到之后还能选择默认路由。

### 路由表的查找

![image-20210213094717430](2021-02-12-CCNP-routing%20selection.assets/image-20210213094717430.png)

只要是不同的前缀，就是不同的路由。

![image-20210213094913469](2021-02-12-CCNP-routing%20selection.assets/image-20210213094913469.png)

![image-20210213095244454](2021-02-12-CCNP-routing%20selection.assets/image-20210213095244454.png)

有类无类的路由选择协议和 路由查找方式要区分对待

讨论的是路由接收的动作

ripv1 比较古老，工程中已经绝版。

无类的路由选择协议是目前比较常用的内容。

概念-怎么用、怎么查看-机制，原理-底层，报文层面（任何协议都是靠packet）

![image-20210214085117766](2021-02-12-CCNP-routing%20selection.assets/image-20210214085117766.png)

“主类网络边界” 会把路由进行汇总之后，再进行发送；上面这种状态已经成为了历史

## 静态路由

![image-20210214085837102](2021-02-12-CCNP-routing%20selection.assets/image-20210214085837102.png)

应用于网络结构比较简单，较为死板的环境。

![image-20210214085941629](2021-02-12-CCNP-routing%20selection.assets/image-20210214085941629.png)

![image-20210214090053469](2021-02-12-CCNP-routing%20selection.assets/image-20210214090053469.png)

![image-20210214090304345](2021-02-12-CCNP-routing%20selection.assets/image-20210214090304345.png)

老的接口，默认开启了arp的代理。

### 浮动静态路由

![image-20210214090434693](2021-02-12-CCNP-routing%20selection.assets/image-20210214090434693.png)

![image-20210214090845165](2021-02-12-CCNP-routing%20selection.assets/image-20210214090845165.png)

![image-20210214091156374](2021-02-12-CCNP-routing%20selection.assets/image-20210214091156374.png)

