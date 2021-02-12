# 2021-02-12-Syslog Explained

[(51) Syslog Explained | Cisco CCNA 200-301 - YouTube](https://www.youtube.com/watch?v=BMVHHX02T4Q&list=PLF1hDMPPRqGxpYdo0ctaa7MxfOi9vjs1u&index=23)

我们需要取定位问题的发生位置

随着网络规模的扩大，我们需要及时了解问题发生的位置与如何处理

![image-20210212083236898](C:%5CUsers%5CChris%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20210212083236898.png)

专门利用udp数据，并建立一个syslog server，来集中处理各种信息

一些原因：

- log 信息很重要，是troubleshooting的重要依据
- data retention。之前的cisco设备存储log信息都是在RAM中，所以掉电就会丢失

![image-20210212083626139](2021-02-12-Syslog%20Explained.assets/image-20210212083626139.png)

---

![image-20210212083737241](2021-02-12-Syslog%20Explained.assets/image-20210212083737241.png)

可以根据严重性选择是否向server报告信息

![image-20210212083825796](2021-02-12-Syslog%20Explained.assets/image-20210212083825796.png)

logging 指令很常用

service指令也很常用。

