'''
Author: Chris Wang
Date: 2021-02-25 19:50:32
LastEditors: your name
LastEditTime: 2021-02-26 09:11:45
Description: file content
'''

import csv
import pandas as pd
import ipaddress

df = pd.read_csv('./ips.csv',names = ['Ranges'])
print(df)

# 创建ipv4对象

# ipaddress.ip_network('10.128.160.0/19')

# net = ipaddress.ip_network('10.1.12.0/24')
# print(dir(net))

# # 查看subnet
# print(net.with_hostmask) # 输出的是反掩码
# print(net.with_netmask) # 正常的格式，后接验码

# print(dir(net.hosts()))

# ad1 = net.hosts().__next__()
# print(ad1)
# ad2 = net.hosts().__next__()
# print(ad2)


# ips = [str(ip) for ip in net.hosts()]
# print(ips)


# expand them

import ipaddress

def explode_nets(net):
    net = ipaddress.ip_network(net, strict=False)

    return [str(ip) for ip in net.hosts()]

df['Ranges'] = df['Ranges'].apply(lambda x: explode_nets(x))

# df[:,'RangesE'] = df.Ranges.apply(lambda x: explode_nets(x))
df.explode(column='Ranges')
print(df)
 # 注意 host bits可能会报错，需要在生成ip network类的时候，可以把strict=False设置一下

net = ipaddress.ip_network('10.128.168.0/24')
ip = ipaddress.ip_address('10.128.168.1')
print(ip)
print(dir(ip))
print(ip.is_private)

# to check an ip whether belongs to a Network

print(ip in net)
df['Result'] = df['Ranges'].apply(lambda x: x in net)
