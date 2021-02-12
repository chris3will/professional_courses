# 2021-02-11-CCNPhongchasanbei-inter-vlan routing..

[红茶三杯CCNP_哔哩哔哩 (゜-゜)つロ 干杯~-bilibili](https://www.bilibili.com/video/BV12W411k7ee?p=6)

一个vlan就是一个广播域

## 背景

![image-20210212084938136](2021-02-11-CCNPhongchasanbei-inter-vlan%20routing.assets/image-20210212084938136.png)

![image-20210212084958673](2021-02-11-CCNPhongchasanbei-inter-vlan%20routing.assets/image-20210212084958673.png)

单臂，即接用一台路由器的一个接口，且这个接口必须满足百兆以上的水平。在子接口内部来执行trunk协议。

![image-20210212085502073](2021-02-11-CCNPhongchasanbei-inter-vlan%20routing.assets/image-20210212085502073.png)

注意，子接口就是一个逻辑接口。

注意，在用路由器模拟pc的时候，

![image-20210212090226733](2021-02-11-CCNPhongchasanbei-inter-vlan%20routing.assets/image-20210212090226733.png)

需要执行上面这条指令，把路由功能先关闭



16min之前，大概讲解了单臂路由的实际配置过程。

且作为出口的路由设备别网机配置上默认路由。

![image-20210212090954193](2021-02-11-CCNPhongchasanbei-inter-vlan%20routing.assets/image-20210212090954193.png)

![image-20210212091342185](2021-02-11-CCNPhongchasanbei-inter-vlan%20routing.assets/image-20210212091342185.png)

可以把三层交换设备理解为一个路由设备

## 三层交换机的端口模式

![image-20210212092348529](2021-02-11-CCNPhongchasanbei-inter-vlan%20routing.assets/image-20210212092348529.png)

![image-20210212092737540](2021-02-11-CCNPhongchasanbei-inter-vlan%20routing.assets/image-20210212092737540.png)

![image-20210212094303487](2021-02-11-CCNPhongchasanbei-inter-vlan%20routing.assets/image-20210212094303487.png)

![image-20210212094515775](2021-02-11-CCNPhongchasanbei-inter-vlan%20routing.assets/image-20210212094515775.png)

刚开始也是默认打开的