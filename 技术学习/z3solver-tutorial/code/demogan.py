'''
Author: Chris Wang
Date: 2021-02-20 08:44:33
LastEditors: your name
LastEditTime: 2021-02-20 08:51:23
Description: file content
'''

from z3 import *

p,q = Bools('p q')
demorgan = And(p,q) == Not(Or(Not(p), Not(q)))
print(simplify(demorgan))

def prove(f):
    s = Solver()
    s.add(Not(f)) # 加入一种假设，即f不成立，去查f是否invalid在任何输入下均成立
    # print(s.check())
    if(s.check() == unsat):
        print("proved")
    else:
        print("failed to prove")

print("proving demorgan..")
prove(demorgan)    
