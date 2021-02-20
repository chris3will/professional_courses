'''
Author: Chris Wang
Date: 2021-02-20 09:53:19
LastEditors: your name
LastEditTime: 2021-02-20 09:56:26
Description: file content
'''
from z3 import *
dog,cat,mouse = Ints('dog cat mouse')

solve(dog>=1,
    cat>=1,
    mouse>=1,
    dog+cat+mouse==100,
    1500*dog+100*cat+25*mouse==10000)