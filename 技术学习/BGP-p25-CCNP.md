# ![image-20210209154141035](BGP-p25-CCNP.assets/image-20210209154141035.png)p25 CNP

## BGP概述

![image-20210119092006321](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210119092006321.png)

网络结构区域划分更细致了

![image-20210119100708761](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210119100708761.png)

1. 可以减少ISA泛洪
2. 做一些路由汇总，提高网络处理能力
3.  ASR，ASBR的职能、增强对LSA的把控

重发布都是为了满足我们的路由需求。

![image-20210119101918009](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210119101918009.png)

as内部实现全网路径可达。

但是如果有多个AS，且AS之间需要路由互访，且路由条目很多的时候需要有BGP（一个公司的高层）



---

![image-20210119102246068](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210119102246068.png)

BGP申请AS号，是需要去申请的。（公网）

![image-20210119102650228](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210119102650228.png)

一方面和rip可能有点相似

当前主要是用V4

在IGP内网中，我们操控路由选择的工具不多，主要就是操控metric值来实现；

但是在BGP中metric是作为众多路径属性之一。

![image-20210119102936283](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210119102936283.png)

![image-20210119103323492](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210119103323492.png)

本身rip眼中，一跳是一个路由器

在BGP中，一跳是一个AS

传递的过程是一个倒叙排列

![image-20210119103548101](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210119103548101.png)‘![image-20210119104015948](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210119104015948.png)

 之前的IGP都是通过 **直连，组播**这些方式来发现邻居。ospf的报文包是封装在IP中，而RIP是基于UDP的。

在这里不同的便是BGP是基于TCP协议的。

BGP不会自己主动发现邻居。不会周期性的更新自己的路由表

![image-20210119104809119](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210119104809119.png)

BGP有五种报文消息。

![image-20210119104930418](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210119104930418.png)

![image-20210119105234429](BGP-p25-CCNP.assets/image-20210119105234429.png)

![image-20210119123807974](BGP-p25-CCNP.assets/image-20210119123807974.png)

![image-20210119124106647](BGP-p25-CCNP.assets/image-20210119124106647.png)

### BGP peer

![image-20210119124131564](BGP-p25-CCNP.assets/image-20210119124131564.png)

注意，对等体并不要求直连

#### EBGP

![image-20210119124209631](BGP-p25-CCNP.assets/image-20210119124209631.png)

ebgp一般采用直连接口去实现，相邻的EBGP对等体之间发的包TTL是1，也印证了这一点。

## BGP属性

## BGP的基本配置



- p26



![image-20210128080956809](BGP-p25-CCNP.assets/image-20210128080956809.png)

在尝试将BGP重发布进ospf当中时，一定要考虑做一些过滤。因为BGP的路由条目是很多的，这个过程中一定要做好把控

---



![image-20210128081843010](BGP-p25-CCNP.assets/image-20210128081843010.png)

### BGP同步

![image-20210128083729792](BGP-p25-CCNP.assets/image-20210128083729792.png)

对于第一条，除非又收到IGP获悉，才能传给EBGP，这样是为了防止路由黑洞；这也是同步规则最致力于解决的问题。

#### 解决路由黑洞

![image-20210128084203578](BGP-p25-CCNP.assets/image-20210128084203578.png)

将BGP重发布进ospf。当然这个动作时很不建议去执行的，过程一定要谨慎。

![image-20210128084337885](BGP-p25-CCNP.assets/image-20210128084337885.png)

在as内部跑一个BGP全互联，即可保证不会丢失某节点EBGP传来的数据包

即做到了无死角

![image-20210128084626797](BGP-p25-CCNP.assets/image-20210128084626797.png)

> **查资料-水平分割**
>
> 水平分割 split horizon：水平分割是一种在两台路由器之间阻止逆向路由的技术。
>
> 逆向路由：reverse route：路由的指向与报文的流动方向相反的路由。这样做很重要的原因是**不会**把从路由学习到的可靠信息再返回给这台路由器。
>
> 

![image-20210128150456394](BGP-p25-CCNP.assets/image-20210128150456394.png)

只有传递给EBGP邻居，aspath才会更新。（离开as的时候才会发生改变）

既然同as内aspath不变化，那么内部就会有产生环路的风险。

![image-20210128152608339](BGP-p25-CCNP.assets/image-20210128152608339.png)

从某一个ibgp邻居获得路由，不能在内部传递给任何一个ibgp邻居

![image-20210128152836983](BGP-p25-CCNP.assets/image-20210128152836983.png)

![image-20210128153113057](BGP-p25-CCNP.assets/image-20210128153113057.png)

BGP表学到的内容，得到的就是BEST。

在as内部，IBGP的AD值会比较大，因为as通常会采用IGP来决定路由

## BGP基本配置

### 创建BGP进程

![image-20210128153807495](BGP-p25-CCNP.assets/image-20210128153807495.png)

至少要有一个子命令才能激活协议

![image-20210128153828116](BGP-p25-CCNP.assets/image-20210128153828116.png)

配置bgp时，需要用neighbor 去指明邻居，可以用ip也可以用回环接口，注意第三条，as决定了邻居建立的是EBGP还是IBGP



即使启动了BGP进程，但是若没有装载路由条目，则展示路由表便是空的

![image-20210129081710782](BGP-p25-CCNP.assets/image-20210129081710782.png)

IGP协议 network后接的是本地的直连接口，会主动发hello包去发现并建立连接

而BGP协议中，network后接的是IGP路由表

![image-20210129082158917](BGP-p25-CCNP.assets/image-20210129082158917.png)

这种写法，是无类的路由通告，前缀必须和路由表一模一样，

![image-20210129082314060](BGP-p25-CCNP.assets/image-20210129082314060.png)

这个是有类的写法，且要配合 auto-summary，倘若启用了 no auto-summary 则这种network的宣告是无效的

![image-20210129082507745](BGP-p25-CCNP.assets/image-20210129082507745.png)

\> 代表最右，且只有最右的这条路由才会被通告给邻居。

#### bgp同步

![image-20210129082818627](BGP-p25-CCNP.assets/image-20210129082818627.png)

#### router-id

![image-20210129082907115](BGP-p25-CCNP.assets/image-20210129082907115.png)

### 指定更新源

![image-20210129083059758](BGP-p25-CCNP.assets/image-20210129083059758.png)

部署IBGP的时候，建议使用loopback接口为更新源，因为在as内部运行IGP的前提下， loopback接口总能互通，并传递必要的BGP信息





![image-20210129083652774](BGP-p25-CCNP.assets/image-20210129083652774.png)

如图所示，在配置时，首先要指明邻居，其次是指明自己的更新接口，（而不是默认的直连接口出口

![image-20210129090134251](BGP-p25-CCNP.assets/image-20210129090134251.png)

nexthop的变化是有条件的。但是A还不知道要走B，他只知道应该去C。可以在AB之间添加一条静态路由，但是这并不是最优的方式

![image-20210129090343430](BGP-p25-CCNP.assets/image-20210129090343430.png)

![image-20210129090457621](BGP-p25-CCNP.assets/image-20210129090457621.png)

设置多跳，multi-hop时的注意：

由于ebgp协议中，两个直连路由器发包的ttl为1，而之前部署的接口为loopback，至少实际需要两条，我们就需要多跳去实际设置一下

![image-20210129091414620](BGP-p25-CCNP.assets/image-20210129091414620.png)

这是一条配置两个直连路由器之间的指令



注意

![image-20210129093459567](BGP-p25-CCNP.assets/image-20210129093459567.png)



![image-20210129093437348](BGP-p25-CCNP.assets/image-20210129093437348.png)

# BGP p27

![image-20210129094827890](BGP-p25-CCNP.assets/image-20210129094827890.png)

### BGP身份验证

![image-20210129094853509](BGP-p25-CCNP.assets/image-20210129094853509.png)

![image-20210129101143631](BGP-p25-CCNP.assets/image-20210129101143631.png)

![image-20210129101520979](BGP-p25-CCNP.assets/image-20210129101520979.png)

![image-20210129101908473](BGP-p25-CCNP.assets/image-20210129101908473.png)

![image-20210129102528459](BGP-p25-CCNP.assets/image-20210129102528459.png)

# BGP p28 路径属性

![image-20210129103654645](BGP-p25-CCNP.assets/image-20210129103654645.png)

![image-20210129104616339](BGP-p25-CCNP.assets/image-20210129104616339.png)

## weight 权重值

![image-20210129105350802](BGP-p25-CCNP.assets/image-20210129105350802.png)

这是思科私有属性，其他设备上跑BGP可能不支持这个属性；本router有效，（是自己认为的重要的内容）

有两点需要注意

- weight不能进行传递
- 不能对其他BGP peer做out方向的设定

### local preference

![image-20210129110258905](BGP-p25-CCNP.assets/image-20210129110258905.png)

在as内部发会作用，公认自觉。优先级的值是可以更改的。

### as path

![image-20210130080819636](BGP-p25-CCNP.assets/image-20210130080819636.png)

除了是公认强制属性，也是防环的重要内容。

#### as-path的四种类型

![image-20210130080939340](BGP-p25-CCNP.assets/image-20210130080939340.png)

![image-20210130081325190](BGP-p25-CCNP.assets/image-20210130081325190.png)

所以，加上as—set这个常见于发生过路由汇总的场合的属性，即可让汇总的路由器产生的信息继承明细的aspath，且其顺序不重要，只要包含即可用于检测环路

### origin 公认强制属性

![image-20210130081915930](BGP-p25-CCNP.assets/image-20210130081915930.png)

### MED 可选非传递属性，可以理解为一个度量值

![image-20210130082805437](BGP-p25-CCNP.assets/image-20210130082805437.png)

设计的初衷，是在AS之间去影响流量。

![image-20210130083955370](BGP-p25-CCNP.assets/image-20210130083955370.png)

且留意到，并不提倡利用MED在as内部来介入路径选择，因为这样并不符合其设计初衷

![image-20210130084404164](BGP-p25-CCNP.assets/image-20210130084404164.png)

### NEXT_HOP

![image-20210130084802995](BGP-p25-CCNP.assets/image-20210130084802995.png)

就是去往某一路由前缀的下一跳目的地

![image-20210130085340103](BGP-p25-CCNP.assets/image-20210130085340103.png)

如果时将IGP引入BGP,那么将会继承在IGP中的下一跳

一些特殊情况。![image-20210130085752504](BGP-p25-CCNP.assets/image-20210130085752504.png)

> 这部分来说，和next-hop油管的案例，场景有很多，实践中不断去加强和检验吧。

### COMMUNITY

![image-20210130091513747](BGP-p25-CCNP.assets/image-20210130091513747.png)

（团体字）

![image-20210130092911465](BGP-p25-CCNP.assets/image-20210130092911465.png)

![image-20210130093645874](BGP-p25-CCNP.assets/image-20210130093645874.png)

#### 几个必要的数值

![image-20210130094837688](BGP-p25-CCNP.assets/image-20210130094837688.png)

> **BGP联邦**
>
> ![image-20210130095138835](BGP-p25-CCNP.assets/image-20210130095138835.png)
>
> 联邦的EBGP和正常的EBGP之间的关系还是有区别的

![image-20210130095436826](BGP-p25-CCNP.assets/image-20210130095436826.png)

![image-20210130095646941](BGP-p25-CCNP.assets/image-20210130095646941.png)

![image-20210130095916110](BGP-p25-CCNP.assets/image-20210130095916110.png)

# p30 路由策略-路由汇总

![image-20210130100337426](BGP-p25-CCNP.assets/image-20210130100337426.png)

![image-20210130100402556](BGP-p25-CCNP.assets/image-20210130100402556.png)

很多协议实际上都有汇总的能力。

![image-20210130104101384](BGP-p25-CCNP.assets/image-20210130104101384.png)	![image-20210130104357183](BGP-p25-CCNP.assets/image-20210130104357183.png)

当然，不是继承所有的属性，仅仅是继承部分的属性。

![image-20210130105335016](BGP-p25-CCNP.assets/image-20210130105335016.png)

![image-20210130105558995](BGP-p25-CCNP.assets/image-20210130105558995.png)

summary-only是拒绝所有明细，但实际上加上某些关键字我们可以拒绝部分明细

![image-20210130141323533](BGP-p25-CCNP.assets/image-20210130141323533.png)

![image-20210130141651872](BGP-p25-CCNP.assets/image-20210130141651872.png)

![image-20210130142230357](BGP-p25-CCNP.assets/image-20210130142230357.png)

![image-20210130143309412](BGP-p25-CCNP.assets/image-20210130143309412.png)

这个attributemap可以调控汇总路由的特定属性

# p31 正则表达式 在BGP中的应用

![image-20210130143418527](BGP-p25-CCNP.assets/image-20210130143418527.png)

> BGP设计与实现 这本书值得推荐

![image-20210130145701574](BGP-p25-CCNP.assets/image-20210130145701574.png)

![image-20210130150138743](BGP-p25-CCNP.assets/image-20210130150138743.png)

![image-20210130150143949](BGP-p25-CCNP.assets/image-20210130150143949.png)

乘法字符必须搭配常规字符

![image-20210130150603046](BGP-p25-CCNP.assets/image-20210130150603046.png)

![image-20210130150747967](BGP-p25-CCNP.assets/image-20210130150747967.png)

![image-20210130151231458](BGP-p25-CCNP.assets/image-20210130151231458.png)

![image-20210130152841778](BGP-p25-CCNP.assets/image-20210130152841778.png)

![image-20210130153218555](BGP-p25-CCNP.assets/image-20210130153218555.png)

![image-20210130153615264](BGP-p25-CCNP.assets/image-20210130153615264.png)

可以通过正则表达式来过滤as-path以筛选满足条件的路由，配合prefix-list，accest-list即可。

# p32 通过community操纵路由

![image-20210130153859424](BGP-p25-CCNP.assets/image-20210130153859424.png)

本身应用的场景决定了其部署的场景是很庞大的。

![image-20210130154337486](BGP-p25-CCNP.assets/image-20210130154337486.png)

![image-20210130155252758](BGP-p25-CCNP.assets/image-20210130155252758.png)

![image-20210131083459683](BGP-p25-CCNP.assets/image-20210131083459683.png)

![image-20210131083616245](BGP-p25-CCNP.assets/image-20210131083616245.png)

如果没有定义联邦，和no-export是一个意思。

![image-20210131085215104](BGP-p25-CCNP.assets/image-20210131085215104.png)

在观察之前要基的清理一下bgp的表，且这个过程中用前缀列表去匹配会有更好的效果

![image-20210131090204292](BGP-p25-CCNP.assets/image-20210131090204292.png)

 

![image-20210131090417458](BGP-p25-CCNP.assets/image-20210131090417458.png)

这个动作相当于acl里的去匹配ip

![image-20210131090533431](BGP-p25-CCNP.assets/image-20210131090533431.png)

这一条community-list 11，匹配了那些携带community值为100：11的路由

![image-20210131090846965](BGP-p25-CCNP.assets/image-20210131090846965.png)

留意到，要添加community值，指令某位要添加一个additive，否则将是覆盖。且community值不仅仅是数字，还有很多关键字同样都属于community值

### community-list （很多方面很类似acl

去进行newformat的时候的指令为：

在configure terminal中调用ip bgp-community newformat

他能将整数处理为形如100：11的模样，且要保持一直，才能在community-list中去匹配。

![image-20210201081227853](BGP-p25-CCNP.assets/image-20210201081227853.png)

列表的匹配也是讲道理的，分成两行和一行，是 或 与 与的关系

![image-20210201081500419](BGP-p25-CCNP.assets/image-20210201081500419.png)

无论在哪定义了route-map，最后一定要落实到具体的接口上，需要应用了它，实际才能产生效果

![image-20210201082201748](BGP-p25-CCNP.assets/image-20210201082201748.png)

![image-20210201082332029](BGP-p25-CCNP.assets/image-20210201082332029.png)

当想要删除某个community值的时候，用到community-list去进行permit那个想要删除的值

![image-20210201093810002](BGP-p25-CCNP.assets/image-20210201093810002.png)

![image-20210201093827592](BGP-p25-CCNP.assets/image-20210201093827592.png)

# p33 BGP路由策略

![image-20210201094128555](BGP-p25-CCNP.assets/image-20210201094128555.png)

整体的BGP filters，过滤能力是很强大的

![image-20210201094503591](BGP-p25-CCNP.assets/image-20210201094503591.png)

## prefix-list

![image-20210201094620030](BGP-p25-CCNP.assets/image-20210201094620030.png)

update source不要利索当然的去设定，要不然很可能出错

## distribute-list

在IGP中反复进行过滤使用，且字段后接实际用acl定义的列表

![image-20210201111626764](BGP-p25-CCNP.assets/image-20210201111626764.png)

![image-20210201112347182](BGP-p25-CCNP.assets/image-20210201112347182.png)

这是第二种方式

![image-20210202085040023](BGP-p25-CCNP.assets/image-20210202085040023.png)

注意到，实际上distribute-list后面可以连接的内容是很丰富的

![image-20210202085531273](BGP-p25-CCNP.assets/image-20210202085531273.png)

这个案例实际上就是测试分发列表对于重发布的路由是否能同样起到过滤的效果

![image-20210202090104470](BGP-p25-CCNP.assets/image-20210202090104470.png)

## Route-map

![image-20210202090135595](BGP-p25-CCNP.assets/image-20210202090135595.png)

各种协议，各种场合都可能会遇到route-map

### match

![image-20210202090335916](BGP-p25-CCNP.assets/image-20210202090335916.png)

![image-20210202090409286](BGP-p25-CCNP.assets/image-20210202090409286.png)

### set

匹配感兴趣册策略，集中在路径属性的把控上

![image-20210202090447670](BGP-p25-CCNP.assets/image-20210202090447670.png)

![image-20210202090501647](BGP-p25-CCNP.assets/image-20210202090501647.png)

注意network场景下的影响范围，它涉及到路由器周边的所有设备，都会受到宣告的影响，如果只想针对某个路由器针对性处理，则可以选用neighbor进行选择

![image-20210202093310178](BGP-p25-CCNP.assets/image-20210202093310178.png)

![image-20210202093859691](BGP-p25-CCNP.assets/image-20210202093859691.png)

### policy-list，一个小的特性

![image-20210202094405331](BGP-p25-CCNP.assets/image-20210202094405331.png)

![image-20210202094734480](BGP-p25-CCNP.assets/image-20210202094734480.png)

![image-20210202094804435](BGP-p25-CCNP.assets/image-20210202094804435.png)

![image-20210202100601340](BGP-p25-CCNP.assets/image-20210202100601340.png)

上述语句的含义为当RP2匹配的路由还存在时， 仅更新12.12.12.0.当这条路由挂掉的时候，模式转换为更新RP1所匹配的内容

### ORF特性

![image-20210202101555727](BGP-p25-CCNP.assets/image-20210202101555727.png)

两台路由器如果要建立ORF关系，则之前的BGP关系就需要重建，因为某种程度来说ORF就是BGP的一种扩展能力。

![image-20210202102012030](BGP-p25-CCNP.assets/image-20210202102012030.png)

orf的sender可以是路由的接收者，这一点身份转换需要注意，理解其含义即可认识到

![image-20210202163722845](BGP-p25-CCNP.assets/image-20210202163722845.png)

这种很多的配置，我还是想，在实践中去尝试吧。

![image-20210202163813147](BGP-p25-CCNP.assets/image-20210202163813147.png)

# BGP deaggregation 路由拆分

![image-20210202164003133](BGP-p25-CCNP.assets/image-20210202164003133.png)

做路由拆分的前提是，首先自己这台路由器上部署过路由汇总

我们从汇总路由中便可以拆分出明细，而拆分出的明细是什么就有自己来决定了

> 背景
>
> ![image-20210202164538590](BGP-p25-CCNP.assets/image-20210202164538590.png)

![image-20210202164552781](BGP-p25-CCNP.assets/image-20210202164552781.png)

## 配置

注意两个route-map的编写是要符合规范的

![image-20210202164709931](BGP-p25-CCNP.assets/image-20210202164709931.png)

![image-20210202164820112](BGP-p25-CCNP.assets/image-20210202164820112.png)

> 案例
>
> ![image-20210202165040004](BGP-p25-CCNP.assets/image-20210202165040004.png)
>
> 这个是在路由器中放汇总路由的指令，as-set说明汇总路由将继承路径属性
>
> ![image-20210202165149103](BGP-p25-CCNP.assets/image-20210202165149103.png)
>
> 这个时候发现，汇总路由是优化过的，之前的明细路由就被抑制了

![image-20210202165713816](BGP-p25-CCNP.assets/image-20210202165713816.png)

注意，汇总的就是要两个match，一个更新源，一个汇总的前缀列表

明细，都是set

# p34 路由反射器，联邦

解决同一个问题的两个不同的方案

![image-20210202165918197](BGP-p25-CCNP.assets/image-20210202165918197.png)

![image-20210203075939758](BGP-p25-CCNP.assets/image-20210203075939758.png)

回顾这个水平分割规则，即一个路由器从ibgp邻居收到的路由不会再发送给其他的ibgp邻居

![image-20210203080332604](image-20210203080332604.png)

![image-20210203080515553](BGP-p25-CCNP.assets/image-20210203080515553.png)

![image-20210203080639458](BGP-p25-CCNP.assets/image-20210203080639458.png)

注意动作上的描述，**发送**，**反射**

## 示例 1

![image-20210203080937724](BGP-p25-CCNP.assets/image-20210203080937724.png)

配置是在RR路由器上进行的，所以其自身明白哪些路由器是客户，哪些不是

![image-20210203081142910](BGP-p25-CCNP.assets/image-20210203081142910.png)

两个重要的RR防环路径属性

![image-20210203081433873](BGP-p25-CCNP.assets/image-20210203081433873.png)

第一个是起源，第二个是蔟，是一个簇ID（列表），

![image-20210203081813752](BGP-p25-CCNP.assets/image-20210203081813752.png)

![image-20210203081959244](BGP-p25-CCNP.assets/image-20210203081959244.png)

![image-20210203103345460](BGP-p25-CCNP.assets/image-20210203103345460.png)

![image-20210203103830593](BGP-p25-CCNP.assets/image-20210203103830593.png)

这样，由于两台RR的ID一致，就不会发生重复的传递工作了

下面即为路由反射器的配置

例如下面标红的即为1.1.1.1为自己的客户

![image-20210203104508208](BGP-p25-CCNP.assets/image-20210203104508208.png)

为了实现全互联，避免受水平分割规则的限制



# p35联邦

![image-20210203105113558](BGP-p25-CCNP.assets/image-20210203105113558.png)

confederation

![image-20210206094349078](BGP-p25-CCNP.assets/image-20210206094349078.png)

联邦内部，可以进一步划分**成员as**。设定联邦的好处，注意BGP邻居关系，不是不同的IBGP和EBGP了

注意此时路由传递的路径属性就会发生一些变化了。

## 联邦内的BGP路由属性

![image-20210206094822236](BGP-p25-CCNP.assets/image-20210206094822236.png)

且实际上，不管怎么划分，单独的as内部如果有多路由器，为了实现互通还是会先采用ospf协议来进行，且用各自的loopback接口来操作

![image-20210206095548353](BGP-p25-CCNP.assets/image-20210206095548353.png)

![image-20210206095718237](BGP-p25-CCNP.assets/image-20210206095718237.png)

BGP知识点：[BGP知识点解析-Steven.Q的学习笔记-51CTO博客](https://blog.51cto.com/steven24/111159)

![image-20210206095940847](BGP-p25-CCNP.assets/image-20210206095940847.png)

注意

![image-20210206100312369](BGP-p25-CCNP.assets/image-20210206100312369.png)

即使是联邦AS内部的EBGP关系，nexthop属性仍然不发生变化

![image-20210206100547754](BGP-p25-CCNP.assets/image-20210206100547754.png)

![image-20210206101437038](BGP-p25-CCNP.assets/image-20210206101437038.png)

### LP属性

![image-20210206101502705](BGP-p25-CCNP.assets/image-20210206101502705.png)

### as-path

![image-20210206101915090](BGP-p25-CCNP.assets/image-20210206101915090.png)

![image-20210206102902368](BGP-p25-CCNP.assets/image-20210206102902368.png)

联邦的扩展性不高，且队设备要求过多。不一定适用于大多数的场景

# p36 BGP选路规则详解

![image-20210206103327776](BGP-p25-CCNP.assets/image-20210206103327776.png)

路径属性很丰富，对需求的匹配能力很强，且适用于大规模网络

![image-20210206104007564](BGP-p25-CCNP.assets/image-20210206104007564.png)

## 1. 优选具有最大weight值的路由

![image-20210206105310853](BGP-p25-CCNP.assets/image-20210206105310853.png)

作用在本地，值越大越优先

值也是可以进行修改的

![image-20210206105628192](BGP-p25-CCNP.assets/image-20210206105628192.png)

![image-20210206105926810](BGP-p25-CCNP.assets/image-20210206105926810.png)

用route-map去操控也是首选方案

## local preference

![image-20210206110914719](BGP-p25-CCNP.assets/image-20210206110914719.png)

和weight的配置方式是类似的

![image-20210208090444967](BGP-p25-CCNP.assets/image-20210208090444967.png)

## 优选起源于本地的路由

![image-20210208090943658](BGP-p25-CCNP.assets/image-20210208090943658.png)

## 优选AS-path最短的路由

![image-20210208094332402](BGP-p25-CCNP.assets/image-20210208094332402.png)

注意prepend字段，

![image-20210208094726984](BGP-p25-CCNP.assets/image-20210208094726984.png)

![image-20210208094959870](BGP-p25-CCNP.assets/image-20210208094959870.png)

![image-20210208095128476](BGP-p25-CCNP.assets/image-20210208095128476.png)

## 比较origin code （igp  > egp > incomplete)

![image-20210208095300910](BGP-p25-CCNP.assets/image-20210208095300910.png)

修改注入方式，则可以且换路由的起源。

当然也可以采用route-map的形式来进行处理。

![image-20210208095814411](BGP-p25-CCNP.assets/image-20210208095814411.png)

## 优选MED最小的路由

![image-20210208100001033](BGP-p25-CCNP.assets/image-20210208100001033.png)

![image-20210208100006681](BGP-p25-CCNP.assets/image-20210208100006681.png)

network某一个路由的时候，直接关联route-map就可以

## 优选EBGP邻居的路由

## 优选到NEXTHOP最近的路由

![image-20210208101957693](BGP-p25-CCNP.assets/image-20210208101957693.png)

![image-20210208102626174](BGP-p25-CCNP.assets/image-20210208102626174.png)

注意路由反射器，对路由进行反射的时候，并不会修改next-hop的数值大小

会读字段

![image-20210208103529799](BGP-p25-CCNP.assets/image-20210208103529799.png)

## 负载均衡

![image-20210208103559456](BGP-p25-CCNP.assets/image-20210208103559456.png)

![image-20210208104711454](BGP-p25-CCNP.assets/image-20210208104711454.png)

配置一个多路径选项

![image-20210208104730954](BGP-p25-CCNP.assets/image-20210208104730954.png)

路由表中会出现，两个下一跳

![image-20210208104855469](BGP-p25-CCNP.assets/image-20210208104855469.png)

![image-20210208105042671](BGP-p25-CCNP.assets/image-20210208105042671.png)

## 优选最老的EBGP邻居来传递路由，降低翻滚的影响

![image-20210208105232998](BGP-p25-CCNP.assets/image-20210208105232998.png)

但是在IBGP环境中并不适用

！ 如果不希望看交情来选路由则按照下述方法即可

![image-20210209084455886](BGP-p25-CCNP.assets/image-20210209084455886.png)

## 优选router-id最小的BGP邻居的路由

![image-20210209084615380](BGP-p25-CCNP.assets/image-20210209084615380.png)

![image-20210209084806561](BGP-p25-CCNP.assets/image-20210209084806561.png)

![image-20210209084940807](BGP-p25-CCNP.assets/image-20210209084940807.png)

两个RR属性，originator 起源，cluster list

![image-20210209085141997](BGP-p25-CCNP.assets/image-20210209085141997.png)

![image-20210209085930904](BGP-p25-CCNP.assets/image-20210209085930904.png)

## 优选cluster-list最短的路由 可以实践一下

![image-20210209085953086](BGP-p25-CCNP.assets/image-20210209085953086.png)

## 优选邻居ip地址最小的路由

![image-20210209090115193](BGP-p25-CCNP.assets/image-20210209090115193.png)

![image-20210209090238987](BGP-p25-CCNP.assets/image-20210209090238987.png)

# p38 BGP非等价负载均衡 Cost Community

![image-20210209090400293](BGP-p25-CCNP.assets/image-20210209090400293.png)



![image-20210209092150398](BGP-p25-CCNP.assets/image-20210209092150398.png)

这个时候，只考虑了接口是1比1分配，却未考虑带宽大小，这显然是不合理的

第三行的指令即为bgp link bandwidth（这是一个扩展的community属性）

负载均衡也有很多种形式，比如说，基于数据包的和基于目的地址的

![image-20210209100025035](BGP-p25-CCNP.assets/image-20210209100025035.png)

![image-20210209100221687](BGP-p25-CCNP.assets/image-20210209100221687.png)

限制：

![image-20210209100231023](BGP-p25-CCNP.assets/image-20210209100231023.png)

# P39 cost community

cost这个值，也是一个重要的扩展

community值，在send的时候，区别是很大的；

扩展的值，一定要后接extended



![image-20210209100633458](BGP-p25-CCNP.assets/image-20210209100633458.png)

> 且cost community包含两个部分，1是cost id 2是 cost value

![image-20210209101206692](BGP-p25-CCNP.assets/image-20210209101206692.png)

## 两个插入点

![image-20210209101237650](BGP-p25-CCNP.assets/image-20210209101237650.png)

![image-20210209101307712](BGP-p25-CCNP.assets/image-20210209101307712.png)

## limitation

![image-20210209101432681](BGP-p25-CCNP.assets/image-20210209101432681.png)

## 如何影响最佳的路径选择

![image-20210209101531759](BGP-p25-CCNP.assets/image-20210209101531759.png)

![image-20210209101830331](BGP-p25-CCNP.assets/image-20210209101830331.png)

![image-20210209103636948](BGP-p25-CCNP.assets/image-20210209103636948.png)

不能上来就先比value，一定要按照id的先后来比较

# p40 移除私有协议的影响

## 移除私有AS



![image-20210209153812012](BGP-p25-CCNP.assets/image-20210209153812012.png)

![image-20210209154703707](BGP-p25-CCNP.assets/image-20210209154703707.png)

## Dual AS

![image-20210209155657484](BGP-p25-CCNP.assets/image-20210209155657484.png)

![image-20210209155723840](BGP-p25-CCNP.assets/image-20210209155723840.png)

这给了我们一种环境迁移的手段

![image-20210209160038061](BGP-p25-CCNP.assets/image-20210209160038061.png)

![image-20210209160350529](BGP-p25-CCNP.assets/image-20210209160350529.png)

开启特性，指明second as号

![image-20210209160934692](BGP-p25-CCNP.assets/image-20210209160934692.png)

## BGP policy accounting

![image-20210209161751588](BGP-p25-CCNP.assets/image-20210209161751588.png)

### 配置步骤

![image-20210209161850303](BGP-p25-CCNP.assets/image-20210209161850303.png)

