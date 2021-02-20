'''
Author: Chris Wang
Date: 2021-02-20 16:45:15
LastEditors: your name
LastEditTime: 2021-02-20 17:36:25
Description: file content
'''
from z3 import *

'''

# x,y = Reals('x y')
# g = Goal()
# g.add(Or(x>0,x<0),x==y+1,y<0,Or(y>1,y<-1))

# t = Tactic('split-clause')
# r = t(g)
# for gg in r:
#     print(gg)

x,y,z = Reals('x y z')
g =Goal()
g.add(Or(x==0,x==1),
    Or(y==0,y==1),
    Or(z==0,z==1),
    x+y+z>2)

# Split all clause 这里即用组合的方式展示tactics的效果
# 例如上图中有三个Or，下面要配合Repat，OrElse，函数来执行

split_all = Repeat(OrElse(Tactic('split-clause'), Tactic('skip')))
print(split_all(g))

split_at_most_2 = Repeat(OrElse(Tactic('split-clause'),
                Tactic('skip')),
                1)
print(split_at_most_2(g)) 

split_solve= Then(Repeat(OrElse(Tactic('split-clause'),
            Tactic('skip'))),
            Tactic('solve-eqs'))
print(split_solve(g))

for s in split_all(g):
    print(s)


# 相当于一个组合容器，对自己将要在求解过程中执行的行为进行打包
bv_solver = Then('simplify',
            'solve-eqs',
            'bit-blast',
            'sat').solver()

x,y = BitVecs('x y',16)
solve_using(bv_solver, x | y == 13,x > y)


bv_solver = Then(With('simplify', mul2concat = True),
                'solve-eqs',
                'bit-blast',
                'aig',
                'sat').solver()
x,y = BitVecs('x y',16)
bv_solver.add(x*32 + y ==13, x & y < 10,y>-100)
print(bv_solver.check())

m = bv_solver.model()
print(x*32+y,"==",m.evaluate(x*32+y))
print(x&y,"==",m.evaluate(x&y))
'''


x,y = Ints('x y')
s = Tactic('smt').solver()
s.add(x>y+1)
print(s.check())
print(s.model())