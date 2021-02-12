# distribute-list 分发列表

可以句下列信息过滤更新：

入站接口

出站接口

从另一种路由协议重分发



针对路由信息



分发列表无法管控lsa

**LSA**（链路状态通告）**是**链接状态协议使用的一个分组，它包括有关邻居和通道成本的信息。 **LSA**被路由器接收用于维护它们的路由选择表。 **LSA**: Link-State Advertisement。

![image-20210115083102149](distribute-list%20%E5%88%86%E5%8F%91%E5%88%97%E8%A1%A8.assets/image-20210115083102149.png)



当要过滤到本地始发的外部路由

![image-20210114125101724](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210114125101724.png)

配置r1，在当前配置rip之后，需要通告自己配置的ip的接口信息



如果路由表有冗余信息，则需要clear ip route *，把路由信息清空，然后重新部署路由协议即可满足条件。



采用ospf部署方式的时候，采用distribute-list策略的时候需要对路由器的in-out的方向都要考虑清楚，（因为分发列表处理的是路由信息，rip这些协议会直接和距离矢量这些有关系）



路由器的路由接口，在采用ospf协议的时候，可以使用宣告的方式引入进来，即直接使用network    1.1.1.1 0.0.0.0 a 0 类似这样的语句进行处理。 	

![image-20210114151620509](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210114151620509.png)

r2上在ospf下会先收入LSA，但是不会放入路由表让自身产生路由，它会放到后面。

即R2，接收R1发来的公告，拦截之后仍然会传递给R3



distribute-list 1 out rip(末尾加rip，即注明这个协议的来源，可以有针对性的处理)

在双点双向重分发中的应用



## case 6

![image-20210115080355082](distribute-list%20%E5%88%86%E5%8F%91%E5%88%97%E8%A1%A8.assets/image-20210115080355082.png)

只希望针对从rip引入的路由生效，这样就可以在末尾加上协议的名称（即说明重分发的来源）

## case 7

![image-20210115080723825](distribute-list%20%E5%88%86%E5%8F%91%E5%88%97%E8%A1%A8.assets/image-20210115080723825.png)

路由器d如图，会根据红线从两个区域学到来自A外部的路由信息。所以此时希望利用分发列表过滤到从OSPF区域收到的左部画圈的那部分路由信息





为了方便区分这组路由，可以进行tag标记：<img src="distribute-list%20%E5%88%86%E5%8F%91%E5%88%97%E8%A1%A8.assets/image-20210115081042236.png" alt="image-20210115081042236" style="zoom:80%;" />

这个策略本身可以起到一定的路径备份作用，但是此时若AD直连线路失效，则D就无法到达A。

![image-20210115081440775](distribute-list%20%E5%88%86%E5%8F%91%E5%88%97%E8%A1%A8.assets/image-20210115081440775.png)



## 小结：

分发列表可以控制路由的传递，但注意其过滤的是路由信息的内容，所以in 和 out方向的处理机制也有不同的地方

![image-20210115074911735](distribute-list%20%E5%88%86%E5%8F%91%E5%88%97%E8%A1%A8.assets/image-20210115074911735.png)

![image-20210115074932714](distribute-list%20%E5%88%86%E5%8F%91%E5%88%97%E8%A1%A8.assets/image-20210115074932714.png)

对上图的r2来说，在r1对两条路由重分发，即可实现过滤。且对于链路状态协议来说，in影响的是自己本身，它已经收到lsa了，但是并没有将其纳入路由表更新的考虑之中

![image-20210115075658348](distribute-list%20%E5%88%86%E5%8F%91%E5%88%97%E8%A1%A8.assets/image-20210115075658348.png)

使用该协议可以查看路由器LSA接收的数据状态

![image-20210115075854558](distribute-list%20%E5%88%86%E5%8F%91%E5%88%97%E8%A1%A8.assets/image-20210115075854558.png)

