'''
Author: Chris Wang
Date: 2021-02-20 10:46:57
LastEditors: your name
LastEditTime: 2021-02-20 16:45:02
Description: file content
'''

from z3 import *

p,q,r = Bools('p q r')

conject = Function('conject',BoolSort())

s = Solver()
conject = (And(Implies(p,q),Implies(q,r),Implies(p,r)))
# s.add(Not(Implies(conject(),(And(Implies(p,q),Implies(q,r),Implies(p,r))))))
s.add((conject))

print(s.check())
m = s.model()
print(m)
