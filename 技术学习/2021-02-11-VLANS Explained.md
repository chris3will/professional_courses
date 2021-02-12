# 2021-02-11-VLANS Explained

virtual local Area network

可以将一篇区域化分为更小的可控区域

“broadcast traffic”

![image-20210211163545690](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210211163545690.png)

这个普通的lan，由switch连接的几台设备，共同组成了一个广播区域。设备增加之后，这些流量会非常多。



---

当然，我们可以采用增加硬件，即路由设备的方式，利用接口来隔断广播区域

![image-20210211163714823](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210211163714823.png)

但是成本会比较高；

![image-20210211163804199](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210211163804199.png)

而上图的这种方式，可扩展性又会比较低，

![image-20210211163845150](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210211163845150.png)

https://www.youtube.com/watch?v=A9lMH0ye1HU&list=PLF1hDMPPRqGxpYdo0ctaa7MxfOi9vjs1u&index=22

![image-20210211164016605](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210211164016605.png)

在一个区域中，每一个接口都可以彼此进行交互

![image-20210211164115129](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210211164115129.png)

不同的交换机上，也可以共同拥有一样的vlan

但是为了保证一种合理的数据交换方式，在交换机的port上又化分为两种，一种为access port，一种为trunk port。

传输数据时的区分依据就是 tag。 这个东西可以用来判断数据的归属

![image-20210211164534175](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210211164534175.png)

vlans对于pc来说是隐形的。交换机通过trunk接口发送的时候，会加上一些information来进行区分。

![image-20210211164626676](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210211164626676.png)

----

native vlans

如果一个交换机接收到了一个untagged的frame，那么会认为其来自对方的native vlan，或者说默认vlan，（vlan 1）

用它的原因：

1. hubs 不能读写tag，只能转发
2. 

