'''
Author: Chris Wang
Date: 2021-02-20 09:39:58
LastEditors: your name
LastEditTime: 2021-02-20 09:44:17
Description: file content
'''

from z3 import *
# 这是一种常常在C语言种使用的策略，来检测一个数据是否是2的倍数

x = BitVec('x',32)
powers = [ 2**i for i in range(32)]
# 虽然我不知道这是为什么
fast = And(x!=0, x&(x-1)==0)
slow = Or([x ==p for p in powers])

print(fast)
prove(fast == slow)

print("trying to prove buggy version")
fast = x &(x-1) ==0
prove(fast == slow)