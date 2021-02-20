'''
Author: Chris Wang
Date: 2021-02-20 09:28:43
LastEditors: your name
LastEditTime: 2021-02-20 09:35:20
Description: file content
'''
# 一个初中问题，加速度，初始速度这样

from z3 import *

d,a,t,v_i,v_f = Reals('d a t v__i, v__f')

equations = [
    d == v_i * t + (a*t**2)/2,
    v_f == v_i + a*t,
]

print("Kinematic equations:")
print(equations)

# Given v_i,v_f and a, find d

problem = [
    v_i == 30,
    v_f ==0,
    a == -8
]

print("Problem:")
print(problem)

print("Solutions:")
solve(equations+problem)
