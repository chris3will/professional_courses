# week1 mooc第二章 程序设计语言及其文法 

## 2-1 基本概念

把语言及其文法的知识提供给计算机

### 字母表

![image-20200227161529899](C:\Users\Chris\AppData\Roaming\Typora\typora-user-images\image-20200227161529899.png)

![image-20200227162547057](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227162547057.png)

![image-20200227162729922](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227162729922.png)

### 串 string

![image-20200227162807327](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227162807327.png)

> 注意，长度为0可以是串

### 串上的运算

#### 连接

![image-20200227162852865](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227162852865.png)

#### 幂

![image-20200227162956234](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227162956234.png)

## 2-2 文法的定义

![image-20200227165150533](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227165150533.png)

****

### 文法的形式化定义

![image-20200227165356365](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227165356365.png)

_____

分为**终结符**集合和非终结符集合

![image-20200227165415358](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227165415358.png)

![image-20200227165423422](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227165423422.png)

****

![image-20200227165515361](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227165515361.png)

产生式就是用来产生串的式子

![image-20200227165702676](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227165702676.png)

![image-20200227165719342](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227165719342.png)

S 是一个特殊的非终结符

![image-20200227165845983](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227165845983.png)

****

### 产生式的简写

![image-20200227170157109](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227170157109.png)

因为E是产生式的最大成分，所以以他为S

### 符号约定

![image-20200227170413981](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227170413981.png)

****

![image-20200227170444694](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227170444694.png)

****

![image-20200227170536377](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227170536377.png)

****

文法符可以使终结符也可以是非终结符



## 2-3 语言的定义

### 推导derivation和规约reduction

![image-20200227170943346](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227170943346.png)

可以一次类推，这个推导是具有传递性质的

![image-20200227171027491](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227171027491.png)

****

#### 一个英文文法例子

![image-20200227171247998](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227171247998.png)

从文法的开始符号推导得到一个字符串

规约是推导的逆过程

****

### 句子和句型

![image-20200227171939256](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227171939256.png)

****

### 语言的形式化

![image-20200227172118170](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227172118170.png)

文法解决了无穷语言的有穷表示问题

注意，语言是由句子构成的，不是句型

### 语言上的运算

![image-20200227172536266](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227172536266.png)

由字母打头的字母数字串表示的集合

## 2-4 文法的分类

![image-20200227172820276](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227172820276.png)

乔姆斯基文法分类体系

****

### 0型文法

![image-20200227172849123](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227172849123.png)

### 1型文法

![image-20200227172906728](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227172906728.png)

上下文指明时才可以进行替换

不包含空产生式

****

### 2型文法 CFG

![image-20200227173102131](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227173102131.png)

左部是非终结符，不需要考虑上下文；上面这个例子的左部，全都是非终结符满足条件

****

### 3型文法 正则文法

![image-20200227173339181](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227173339181.png)

****

### 四种文法间的关系

![image-20200227184325991](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227184325991.png)

## 2-5 上下文无关文法 分析树

可以描述大部分的构造

![image-20200227184554279](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227184554279.png)

### 分析树是推导的图形化表示

![image-20200227184802145](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227184802145.png)

### 句型的短语

![image-20200227184934999](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227184934999.png)

### 二义性文法

- 一个文法可以为某个句子生成多颗分析树，则称这个文法是二义性的

![image-20200227185537322](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227185537322.png)

计算机此时无法进行确切的操作，想纠正就要引入非终结符或者空产生式

_____

可以引入消歧规则

我们目前对二义性文法的判定只能给出充分条件



## 测试

![image-20200227190437746](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227190437746.png)

****

![image-20200227190451045](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227190451045.png)

****

![image-20200227190530014](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227190530014.png)

****

![image-20200227190802003](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227190802003.png)

注意，要正确理解文法的表示形式，就是不断递归，以有限的生成式来表现无限的句子

****

![image-20200227190955753](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227190955753.png)

****

![image-20200227191138778](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227191138778.png)

****

![image-20200227191257245](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227191257245.png)