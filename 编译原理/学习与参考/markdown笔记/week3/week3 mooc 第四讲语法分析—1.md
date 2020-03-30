# week3 mooc 第四讲语法分析—1

## 4-1 自顶向下分析概述

> 语法分析的任务，根据给定的文法识别数据中的各类短语并构造一个语法分析树

### 自顶向下的分析

![image-20200307190657169](C:\Users\Chris\AppData\Roaming\Typora\typora-user-images\image-20200307190657169.png)

### 最左推导

![image-20200307190714476](C:\Users\Chris\AppData\Roaming\Typora\typora-user-images\image-20200307190714476.png)

找最左的非终结符

****

### 最右推导 规范推导

![image-20200307191415007](C:\Users\Chris\AppData\Roaming\Typora\typora-user-images\image-20200307191415007.png)

因为两者都保证了推导是按照某种顺序进行的，所以均可保证是唯一 的

### 自顶向下的语法分析采用最左推导方式

- 总是选择每个句型的最左非终结符进行替换
- 根据输入流的下一个终结符，选择最左非终结符的一个候选式

### 自顶向下语法分析的通用形式

![image-20200307191650004](C:\Users\Chris\AppData\Roaming\Typora\typora-user-images\image-20200307191650004.png)

注意，是递归下降分析

### 预测分析 predictive parsing

![image-20200307192000200](E:\thridSpring\编译原理\markdown笔记\week3\image-20200307192000200.png)

## 4-2 文法转换

### 问题1 

同一非终结符存在多个候选式具有相同的前缀，导致回溯现象发生![image-20200307194121756](E:\thridSpring\编译原理\markdown笔记\week3\image-20200307194121756.png)

### 问题2  左递归

左递归发会使递归下降分析器陷入无限循环

![image-20200307194205182](E:\thridSpring\编译原理\markdown笔记\week3\image-20200307194205182.png)

### 消除直接左递归

![image-20200307194330165](E:\thridSpring\编译原理\markdown笔记\week3\image-20200307194330165.png)

### 更一般的消除左递归

![image-20200307194343396](E:\thridSpring\编译原理\markdown笔记\week3\image-20200307194343396.png)

### 消除间接左递归

![image-20200307194837782](E:\thridSpring\编译原理\markdown笔记\week3\image-20200307194837782.png)

### 消除左递归算法

![image-20200307194955984](E:\thridSpring\编译原理\markdown笔记\week3\image-20200307194955984.png)

### 提取左公因子

![image-20200307200732511](E:\thridSpring\编译原理\markdown笔记\week3\image-20200307200732511.png)

![image-20200307200744402](E:\thridSpring\编译原理\markdown笔记\week3\image-20200307200744402.png)



## 4-3 LL(1)文法



### S_型文法

![image-20200309005613050](E:\thridSpring\编译原理\markdown笔记\week3\image-20200309005613050.png)

#### 例子

![image-20200309005627920](E:\thridSpring\编译原理\markdown笔记\week3\image-20200309005627920.png)

注意观察这个非终结符能否推出空产生式；A的后面可以紧跟着出现哪些终结符，决定了谁可以在A后出现

### 非终结符的后继符号集

![image-20200309010056015](E:\thridSpring\编译原理\markdown笔记\week3\image-20200309010056015.png)

注意输入符号是互不相交的，我们可以做唯一的选择

****

### 产生式的可选集

![image-20200309010217118](E:\thridSpring\编译原理\markdown笔记\week3\image-20200309010217118.png)

如果右侧是以终结符打头，那么该select集合只含一个元素 

但是注意q_文法的可选输入符号的集合，无论是单个终结符还是FOLLOW(A)实际上都是终结符，灵活性不强

****

### 串首终结符

![image-20200309010807732](E:\thridSpring\编译原理\markdown笔记\week3\image-20200309010807732.png)

a->空的意思，即a可以推导出非终结符，而该非终结符又能推导出空串 

### LL(1)

![image-20200309011122363](E:\thridSpring\编译原理\markdown笔记\week3\image-20200309011122363.png)

![image-20200309011248513](E:\thridSpring\编译原理\markdown笔记\week3\image-20200309011248513.png)

为了避免两个产生式的可选集互不相交，最多只能有一方可推导出空串

****

## 集合的计算

![image-20200309012542560](E:\thridSpring\编译原理\markdown笔记\week3\image-20200309012542560.png)

![image-20200309012603120](E:\thridSpring\编译原理\markdown笔记\week3\image-20200309012603120.png)

![image-20200309014117002](E:\thridSpring\编译原理\markdown笔记\week3\image-20200309014117002.png)

![image-20200309095613071](E:\thridSpring\编译原理\markdown笔记\week3\image-20200309095613071.png)

分析法分为递归分析与非递归分析

****