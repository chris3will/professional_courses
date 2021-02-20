'''
Author: Chris Wang
Date: 2021-02-20 09:46:13
LastEditors: your name
LastEditTime: 2021-02-20 09:50:52
Description: file content
'''

from z3 import *

x = BitVec('x',32)
y = BitVec('y',32)

# Claim: (x^y < 0 iffx and y have opposite signs)

trick = (x^y) < 0

# Naive way to check if x and y have opposite signs
opposite = Or(And(x<0,y>=0),
And(x>=0,y<0))

prove(trick == opposite) # z3可以让我们去检测一些情况是否拥有相同的判断结果
