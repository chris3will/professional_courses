# week3 编译原理 汤普森

![image-20200309150448270](C:\Users\Chris\AppData\Roaming\Typora\typora-user-images\image-20200309150448270.png)

![image-20200309150454279](C:\Users\Chris\AppData\Roaming\Typora\typora-user-images\image-20200309150454279.png)

![image-20200309150503508](C:\Users\Chris\AppData\Roaming\Typora\typora-user-images\image-20200309150503508.png)

![image-20200309150354142](C:\Users\Chris\AppData\Roaming\Typora\typora-user-images\image-20200309150354142.png)

a(letter)*a | a

算法具有确定性

****

## 几个函数的实现

![image-20200309151248300](C:\Users\Chris\AppData\Roaming\Typora\typora-user-images\image-20200309151248300.png)

通过计算闭包来消除转换

![image-20200309151320690](C:\Users\Chris\AppData\Roaming\Typora\typora-user-images\image-20200309151320690.png)

![image-20200309151741963](C:\Users\Chris\AppData\Roaming\Typora\typora-user-images\image-20200309151741963.png)

状态集合1的e闭包 124

状态集合2   2

状态1 在a上转移到3

状态12 集合的e闭包 124

e-closure(move(2,a)) 集合的闭包234

> I(a) = e-closure(move(2,a)) = {2,3,4}

## 子集构造算法 subset construction

> 目的是构造一个DFA ，本质上是一个五元式
>
> ![image-20200309152051466](E:\thridSpring\编译原理\markdown笔记\week3\image-20200309152051466.png)

S状态就是幂集的某一个subset

f函数是通过计算move函数和闭包的计算过程中构造出来的

****

![image-20200309152837073](E:\thridSpring\编译原理\markdown笔记\week3\image-20200309152837073.png)

{1,2,6}:  I(a) = e-closure({3,7}) = {3,4,7,8}

{1,2,6}:  I(a) = e-closure({3,7}) = {3,4,7,8}, I(b) = {5,8}

凡是接受了某种元素的状态都是接受状态

识别a或者ab的一个dfa

![image-20200309154420703](E:\thridSpring\编译原理\markdown笔记\week3\image-20200309154420703.png)

{0,1,2,4,7}: 	I(a) = e-closure({3,8}) = {1,2,3,4,6,7,8}
I(b) = e-closure({5}) = {1,2,4,5,6,7}

{0,1,2,4,7}: 	I(a) = e-closure({3,8}) = {1,2,3,4,6,7,8}
I(b) = e-closure({5}) = {1,2,4,5,6,7}

![image-20200309154855127](E:\thridSpring\编译原理\markdown笔记\week3\image-20200309154855127.png)

****

![image-20200309162949124](E:\thridSpring\编译原理\markdown笔记\week3\image-20200309162949124.png)

接受状态和非接收状态是可以区别的

空串区分接受状态和非接收状态

![img](E:\thridSpring\编译原理\markdown笔记\week3\7D``5N7HEKO%JFU{2[Y@9`M.jpg)

![img](E:\thridSpring\编译原理\markdown笔记\week3\YRW]N}SJ$}MP1ZOC`%P@D1W.jpg)

![img](E:\thridSpring\编译原理\markdown笔记\week3\ZFZAF]L9@4T3XGHSLH[JXL.jpg)

![image-20200309165828833](E:\thridSpring\编译原理\markdown笔记\week3\image-20200309165828833.png)

![image-20200309170016607](E:\thridSpring\编译原理\markdown笔记\week3\image-20200309170016607.png)

1. 状态集合和接收集合
2. 蔓延条件，在所有字母上判别是否等价

![img](E:\thridSpring\编译原理\markdown笔记\week3\NI_G@CKHIMY8FLRX}[W_RN6.jpg)

