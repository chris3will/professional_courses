# week2 mooc第三章 词法分析

## 3-1 正则表达式



![image-20200302084700342](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302084700342.png)

注意，递归的思想很关键

****

### 定义

![image-20200302084830361](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302084830361.png)

****

![image-20200302085045109](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302085045109.png)

最后一条的语言表示的是，要么是单个a，要么是若干个a后接一个b

****

![image-20200302085826582](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302085826582.png)

### 正则语言

可以用RE定义的语言叫做正则语言regular language或正则集合regular set

### RE的代数定律

![image-20200302085948905](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302085948905.png)

****

### 正则文法与正则表达式等价

![image-20200302090551277](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302090551277.png)

## 3-2 正则定义

![image-20200302090651869](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302090651869.png)

****

![image-20200302091145944](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302091145944.png)

## 3-3 有穷自动机

![image-20200302091337468](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302091337468.png)

### FA模型

![image-20200302091450746](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302091450746.png)

### FA的表示

![image-20200302091524970](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302091524970.png)

### FA定义（接收）的语言

![image-20200302091727787](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302091727787.png)

### 最长子串匹配原则 LSMP

终态上还有符号，则有穷自动机继续前进

![image-20200302091806846](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302091806846.png)

## 3-4 有穷自动机的分类

### 确定的有穷自动机 DFA deterministic finite automata

![image-20200302092132486](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302092132486.png)

#### DFA实例

![image-20200302092244340](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302092244340.png)

### 非确定的有穷自动机 NFA

![image-20200302092412338](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302092412338.png)

****

### DFA和NFA的等价性

![image-20200302094558305](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302094558305.png)

但是表现形式上有差别，NFA的表现方式更加直观，反而对DFA需要进行一些的分析才能知道。

但是在计算机中DFA比NFA更容易实现

![image-20200302094716785](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302094716785.png)

****

### 带有空边的NFA

![image-20200302094745613](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302094745613.png)

### 带有和不带有空边的NFA的等价性

![image-20200302094935832](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302094935832.png)

### DFA的算法实现

![image-20200302095148464](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302095148464.png)

## 3-5 从正则表达式到有穷自动机

先到更加直观的NFA，再考虑如果边成含有状态回退的DFA

不断的拆解即可

需要下面的思路与一些基本规范

![image-20200302095643345](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302095643345.png)

![image-20200302095703261](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302095703261.png)

### 鲜明的例子

![image-20200302095722089](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302095722089.png)

## 3-6 从NFA到DFA的转换

状态状态可以组合成一个新的状态集合

![image-20200302100717987](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302100717987.png)

### 带有空边的状态转换

![image-20200302101144261](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302101144261.png)

## 3-7 识别单词的DFA

![image-20200302101602951](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302101602951.png)

### 识别无符号数的DFA

![image-20200302101637632](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302101637632.png)

![image-20200302101645780](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302101645780.png)

### 识别各进制无符号整数的DFA

![image-20200302102452992](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302102452992.png)

以token的形式表示，种别码与其value

### 词法分析阶段的错误处理

![image-20200302102644106](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302102644106.png)

当前信息不可以达到中止状态，在转换表中对应项为空

****

### 错误处理

![image-20200302102759993](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302102759993.png)

#### 错误恢复策略

![image-20200302102822724](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302102822724.png)



## 测试

![image-20200302104928339](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302104928339.png)

![image-20200302104952660](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302104952660.png)

![image-20200302105007621](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302105007621.png)

![image-20200302105039298](E:\thridSpring\编译原理\markdown笔记\week1\image-20200302105039298.png)

## [词法分析器的作用](https://www.cnblogs.com/zinthos/p/3963007.html)

> 读入源程序的输入字符、将他们组成词素，生成并输出一个词法单元序列，每个词法单元对应于一个词素。当词法分析器发现了一个**标识符**的词素时，要将这个词素添加到符号表中。

1. 词法分析器的两个级联的处理阶段
   1. 扫描阶段：简单处理、删除注释、压缩空白字符
   2. 词法分析阶段：处理扫描阶段的输出并生成词法单元

### 把编译部分分为词法分析和语法分析的原因

1. 简化编译器设计
2. 提高编译效率
3. 增强编译器的可以执行

### 三个术语

- 词法单元：由一个词法单元和一个可选的属性值构成
- 模式：一个词法单元的词素可能具有的形式
- 词素：源程序中的一个字符序列，他和某个词法单元的模式匹配，并被词法分析器识别为该词法的一个实例

