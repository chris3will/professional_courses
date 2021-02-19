'''
Author: Chris Wang
Date: 2021-02-18 10:47:19
LastEditors: your name
LastEditTime: 2021-02-18 10:47:42
Description: file content
'''

from z3 import *
import binascii

s = '5616f5962674d26741d2810600a6c5647620c4e3d2870177f09716b2379012c342d3b584c5672195d653722443f1c39254360007010381b721c741a532b03504d2849382d375c0d6806251a2946335a67365020100f160f17640c6a05583f49645d3b557856221b2'

encrypted = []
for i in range(0, len(s), 2):
    encrypted.append(binascii.unhexlify(s[i+1] + s[i])[0])

print('message len:', len(encrypted)-1)
print(encrypted)
# 声明变量，encrypted 是已知，因此 IntVal 即可
encrypted = [IntVal(i) for i in encrypted]
message = [Int('flag%d' % i) for i in range(len(encrypted)-1)]
# 创建一个求解器，求解全靠它
solver = Solver()

ml = len(encrypted) - 1

# 添加明文字符的约束条件
for i in range(ml):
    if i == ml - 33:
        solver.add(message[i] == ord('|'))
    else:
        # 肯定是可见字符，因此限定范围如下
        solver.add(message[i] < 127)
        solver.add(message[i] >= 32)
# 添加明文和密文对照关系的约束条件
for i in range(ml):
    solver.add(encrypted[i+1] == (message[i] + message[ml-32+i%32] + encrypted[i]) % 126)

if solver.check() == sat:
    m = solver.model()
    s = []
    for i in range(ml):
        s.append(m[message[i]].as_long())
    print(bytes(s))
else:
    print('unsat')