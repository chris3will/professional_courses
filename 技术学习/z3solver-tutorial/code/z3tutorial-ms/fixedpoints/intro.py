'''
Author: Chris Wang
Date: 2021-02-21 09:26:45
LastEditors: your name
LastEditTime: 2021-02-21 10:27:26
Description: file content
'''
from z3 import *


'''

fp = Fixedpoint()

a,b,c = Bools('a b c')

fp.register_relation(a.decl(),b.decl(),c.decl())
fp.rule(a,b)
fp.rule(b,c)
fp.set(engine= 'datalog')
print("current set of rules\n",fp,"###")
print(fp.query(a))

fp.fact(c)
print("updated set of rules\n",fp,"###")
print(fp.query(a))
print(fp.get_answer())


fp =Fixedpoint()
a,b,c = Bools('a b c')
fp.register_relation(a.decl(), b.decl(), c.decl())
fp.rule(a,b)
fp.rule(b,c)
fp.fact(c)
fp.set(engine='datalog',generate_explanations=True)

print(fp.query(a))
print(fp.get_answer())
'''

fp = Fixedpoint()
fp.set(engine='datalog')

s = BitVecSort(3)
edge = Function('edge',s,s,BoolSort())
path = Function('path',s,s,BoolSort())
a = Const('a',s)
b = Const('b',s)
c = Const('c',s)

fp.register_relation(path,edge)
fp.declare_var(a,b,c)
fp.rule(path(a,b),edge(a,b))
fp.rule(path(a,c),[edge(a,b),path(b,c)])

v1 = BitVecVal(1,s)
v2 = BitVecVal(2,s)
v3 = BitVecVal(3,s)
v4 = BitVecVal(4,s)

fp.fact(edge(v1,v2))
fp.fact(edge(v1,v3))
fp.fact(edge(v2,v4))



print("current set of rules",fp,"###")

print(fp.query(path(v1,v4)),"yes we can reach v4 from v1") # 通过上面的规则定义，这里已经可以判断出可达性

# 而且由于这个是单向的图，所以能保证题意
print(fp.query(path(v3,v4)),"No we can not reach v4 from v3")


