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

为了减少配置的繁琐程度

![image-20210212101904421](2021-02-11-CCNPhongchasanbei-inter-vlan%20routing.assets/image-20210212101904421.png)

就用超网的形式来处理。

![image-20210212102042002](2021-02-11-CCNPhongchasanbei-inter-vlan%20routing.assets/image-20210212102042002.png)

如果不写明 no switch接口，则没法去连接

![image-20210212102634379](2021-02-11-CCNPhongchasanbei-inter-vlan%20routing.assets/image-20210212102634379.png)

![image-20210212102831640](2021-02-11-CCNPhongchasanbei-inter-vlan%20routing.assets/image-20210212102831640.png)

二层交换机，只能给一个vlan配置IP地址，

分vlan的时候需要注意，用户vlan是给pc用的，管理vlan是给设备用的

二层交换设备一定要配网关（在配了IP地址之后）要不然他是没法转发数据的。

![image-20210212104148343](2021-02-11-CCNPhongchasanbei-inter-vlan%20routing.assets/image-20210212104148343.png)