# prefix-list

- 用来匹配路由的

需要用特定的工具去匹配特定的路由条目

目前最常用的工具即ACL

![image-20210115093026160](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210115093026160.png)

采用反掩码，可以过滤很多新的内容

![image-20210115094948738](prefix-list.assets/image-20210115094948738.png)

计算反掩码

![image-20210115095815547](prefix-list.assets/image-20210115095815547.png)

![image-20210115100207424](prefix-list.assets/image-20210115100207424.png)

这个时候，我们就需要采用扩展ACL的策略来进行处理

![image-20210115100423431](prefix-list.assets/image-20210115100423431.png)

四组 32位数据，左边两个对应着ip地址，右边两个对应着对掩码的约数

但是这种扩展ACL实际上也不太方便去部署，计算上需要花费不少功夫。

所以采用前缀列表去处理这种情况

## 正文： 前缀列表

![image-20210115145851253](prefix-list.assets/image-20210115145851253.png)

### 配置

![image-20210115150341752](prefix-list.assets/image-20210115150341752.png)



![image-20210115150328423](prefix-list.assets/image-20210115150328423.png)

![image-20210115152522404](prefix-list.assets/image-20210115152522404.png)

![image-20210115152630750](prefix-list.assets/image-20210115152630750.png)

22位是完全一样的。

ip prefix-list test permit 192.168.4.0/22

同时再限制掩码的长度，

![image-20210115153057285](prefix-list.assets/image-20210115153057285.png)

![image-20210115153204021](prefix-list.assets/image-20210115153204021.png)

![image-20210115153338451](prefix-list.assets/image-20210115153338451.png)

![image-20210115154043401](prefix-list.assets/image-20210115154043401.png)

