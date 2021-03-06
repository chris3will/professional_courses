# week1 mooc 第一章与第三章部分



学习编译器的构成原理

![image-20200227142503649](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227142503649.png)

> 可重定位指的是其位置都是相对的，在内存中存放的起始位置L不是固定的；要与其他可重定位的库文件来链接，连接器也可以用来解决外部内存地址问题
>
> 加载器来修改可重定位地址

_____

## 1-2 编译系统的结构

编译的本质，是一个翻译的过程

以传统的为例，先 

1. 分析源语言

- 语义分析semantic analysis
- 词法分析
  - 分出来词性和词类
- 语法分析 synatx analysis
  - 识别各类短语从而获得句子的结构
- 语义分析
  - 根据结构，分析各个短语在句子中充当什么成分，从而确定名次成分与核心谓语动词之间的关系
- 给出中间表示形式

> 它是独立于目标语言的存在，我们通过中间表示得到桥梁

我们借此得到橘子的语义

2. 生成目标语言

   ![image-20200227144227830](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227144227830.png)

> phase
>
> 中间表示独立于具体语言，起到了中间桥梁的作用
>
> 多个阶段在实现过程中可能会被组合到一起
>
> 例如语法制导翻译，语法分析与语义分析和中间代码生成直接一起进行

## 1-3 词法分析概述

### 主要任务

从左向右朱行扫描源程序的字符，识别出各个单词，确定单词的类型。将识别出的单词转换成同意的机内表示 -- **词法单元 token形式**

token: < 种别码，属性值>

![image-20200227145057479](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227145057479.png)

我们能枚举的标识符是有限的， 所以我们将其分配一个统一的码

_____

词法分析后，我们可以得到一个token序列；注意是从左向右得到的；

一次一码单词，第二个分量都是空值

## 1-4 语法分析概述

这是第二个阶段

语法分析器 parser 从词法分析器输出的token序列中 **识别出各类短语**，并构造语法分析数 parser tree

文法是由一系列规则定义生成的

![image-20200227153120760](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227153120760.png)

> 声明语句D
>
> ​	类型T 连接上一个标志符ID
>
> 一个标识符本身可以构成一个序列IDS

![image-20200227153303981](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227153303981.png)

____

## 1-5 语义分析概述

编译第三个阶段

### 主要任务

一类是声明语句，一类是可执行对象

1. 收集标识符的属性信息

   > 种属 kind
   >
   > 类型 type
   >
   > 存储位置、长度
   >
   > 值
   >
   > 作用域
   >
   > 参数和返回值信息

2. 语义检查

   > 为了应对一些情况
   >
   > 1. 变量或过程未经声明就使用
   > 2. 变量或过程名重复声明
   > 3. 运算分量类型不匹配
   > 4. ![image-20200227154105414](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227154105414.png)

## 1-6 中间代码生成及编译器后端概述

### 常用的中间表示形式

#### 三地址码

​	由类似于汇编语言的指令序列组成，每个指令最多有三个操作数

#### 语法结构树 syntax trees

常用的三地址指令

![image-20200227154511901](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227154511901.png)

![image-20200227154725485](E:\thridSpring\编译原理\markdown笔记\week1\image-20200227154725485.png)

目标代码生成以源程序的中间表示形式作为输入，并把它映射到目标语言

- 目标代码生成的一个重要任务是为程序中使用的变量合理分配寄存器

- 代码优化，为改进代码所进行的等价程序变化，使其运行地更快，占用的内存空间更小

## 小测

1. 用高级语言编写的程序经过编译后的程序叫目标程序 .obj
2. 编译程序是一种翻译程序
3. 编译程序中语法分析接收器以**单词**为单位的输入
4. 语法分析器的任务就是分析单词串是如何构成语句和声明的

