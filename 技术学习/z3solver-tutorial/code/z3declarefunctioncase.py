'''
Author: Chris Wang
Date: 2021-02-19 08:53:52
LastEditors: your name
LastEditTime: 2021-02-19 09:29:52
Description: file content
'''

from z3 import *

# # 先把变量声明好
# x = Int('x')
# c = Int('c')
# t = Int('t')

# s = Solver()

# f = Function('f', IntSort(),IntSort())

# # x*t + c = result
# # x,result = [(1,55), (12,34), (13,300)]

# s.add(f(x)==(x*t+c))
# s.add(f(1)==55,f(12)==34,f(13)==300)

# t = s.check()
# if(t==sat):
#     print(s.model())
# else:
#     print(t)

'''
上面是原帖，里面包含了错误的思路。
The assertion f(x) == x*t + c is not defining the function f for all x. It is just saying that the value of f for the given x is x*t + c. Z3 supports universal quantifiers. However, they are very expensive, and Z3 is not complete when a set of constraints contains universal quantifiers since the problem becomes undecidable. That is, Z3 may return unknown for this kind of problem.

Note that f is essentially a "macro" in your script. Instead of using a Z3 function for encoding this "macro", we can create a Python function that does the trick. That is, a Python function that, given a Z3 expression, returns a new Z3 expression. Here is a new script. The script is also available online at: http://rise4fun.com/Z3Py/Yoi Here is another version of the script where c and t are Real instead of Int: http://rise4fun.com/Z3Py/uZl
'''

c = Int('c')
t = Int('t')

def f(x):
    return x *t +c

# data is a list of pairs (x, r)

def find(data):
    s = Solver()
    # 把那几组数据，全部导入进约束
    s.add([ f(x) == r for (x,r) in data])
    t = s.check()
    if s.check()==sat:
        print(s.model())
    else:
        print(t)

find([(1,55)])
find([(1,55), (12,34)])
find([(1,55), (12,34), (13,300)])

