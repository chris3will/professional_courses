# route-map学习

![image-20210110075451202](route-map%E5%AD%A6%E4%B9%A0.assets/image-20210110075451202.png)

## route-map的一个示例

可以有效处理重发布中不同的筛选需求。

![image-20210110075625703](route-map%E5%AD%A6%E4%B9%A0.assets/image-20210110075625703.png)

这个是重发布的实际指令

![image-20210110075615242](route-map%E5%AD%A6%E4%B9%A0.assets/image-20210110075615242.png)

但是，还不能区分对待，因为这个时候从ospf1 分发进rip区域中的内容的metric统一为2，这个定值，而没有各自的区分；

注意，route-map中的隐含deny指的是不匹配，而acl中的deny是直接丢弃

## route-map 的配置

![image-20210110080507989](route-map%E5%AD%A6%E4%B9%A0.assets/image-20210110080507989.png)

![image-20210110080601144](route-map%E5%AD%A6%E4%B9%A0.assets/image-20210110080601144.png)

![image-20210110084117416](route-map%E5%AD%A6%E4%B9%A0.assets/image-20210110084117416.png)

只要有一条信息不满足，就不set。

两条代表或

## 配置例子1：用于重分发

![image-20210110084426178](route-map%E5%AD%A6%E4%B9%A0.assets/image-20210110084426178.png)

acl只能匹配前缀，不能匹配掩码

![image-20210110092051055](route-map%E5%AD%A6%E4%B9%A0.assets/image-20210110092051055.png)

用路由策略去吸引流量的这个思想，是很重要的

## 跟着例子学习

很多重发布，都会关联route-map来方便实际的路由策略实施

想把路由器的loopback接口分发进ospf区域中，采用redistribute操作，重发布直连接口进入网段当中

![image-20210114093540066](route-map%E5%AD%A6%E4%B9%A0.assets/image-20210114093540066.png)

添加access-list

![image-20210114094124944](route-map%E5%AD%A6%E4%B9%A0.assets/image-20210114094124944.png)

acl匹配路由的时候，前缀必须要保持一致



![image-20210114094731851](route-map%E5%AD%A6%E4%B9%A0.assets/image-20210114094731851.png)

这样，ospf中的路由就通过重发布发布进入了rip中被r1接收

注意，connected关键字代表了本地所有的直连路由。只要接口是双up的，那么就会被引入进程。

如果只想通告某几个进程，则重发布的时候，只需要设定一个route-map就行



### 可以分流



### 同时需要 避免路由反馈。

match 和 set的内容可以很丰富



