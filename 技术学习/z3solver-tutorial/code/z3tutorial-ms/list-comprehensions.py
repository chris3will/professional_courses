'''
Author: Chris Wang
Date: 2021-02-20 08:56:47
LastEditors: your name
LastEditTime: 2021-02-20 08:58:22
Description: file content
'''
from z3 import *

print[x+1 for x in range(5)]

#create two lists containing 5 integer variables

X = [ Int('x%s' %i)for i in range(5)]
Y = [Int('y%s' %i) for i in range(5)]

print(X)