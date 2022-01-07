# 实时计算课程大作业 实时数据可视化界面
* ## 1.课题背景
> 某在线电子图书商城当前注册会员数有近千万会员，遍布于全国多个省份，日常在线业务量较大。为了提高商城的用户知名度，该公司拟计划开展大型促销活动（类似于双十一的电商购物活动）。为了准确、实时统计当前销售数量，公司拟委托你开发一个促销活动的大数据看板，用于当日活动启动后销售订单实时数据的变化情况。

> 老师给出了一个生成数据的软件，它会自己将数据写入到本地的mysql当中，然后就要就利用数据编写一个实时数据看板。
* ## 2.项目说明
> 本项目主要是使用canal读取MySQL的binlog文件，将读取到的信息发送到kafka集群中，然后通过flink处理后写入redis数据库中，最后用flask读取数据，Ajax接收并更新界面的数据以达到实时看板的功能。

> 编程语言：
>> * scala 2.11.5（只要和下面的版本对上即可）
>> * jdk 1.8.0_281
>> * python 3.8（推荐anaconda）

> 组件版本：
>> * canal.deployer-1.1.5（canal可以放在本地，也可以放在虚拟机中，放虚拟机就要将本地MySQL的权限设置好，推荐本地）
>> * kafka_2.11-0.10.0.1（在虚拟机上搭建kafka集群）
>> * flink-1.10.1-bin-scala_2.11（虚拟机上搭建flink集群，本地用maven开发）
>> * redis（推荐安装在本地，或者在虚拟机上搭建redis集群）

> 可视化组件版本
>> * flask 1.1.2（本地开发）
>> * Ajax（本质就是html）
>> * echarts（可视化）
>> * 虽然网上有很多的可视化大屏的模板，还是推荐自己学着写，研究模板也是挺费时间的，那些酷酷的东西实现起来比较复杂，所以模板比较难以入门，不如自己开发，或者学会了再去研究。

> 教程推荐：
>> * flink（java版）教程：https://www.bilibili.com/video/BV1qy4y1q728?from=search&seid=9151155942386776091&spm_id_from=333.337.0.0
>> * flink（Scala版）教程：https://www.bilibili.com/video/BV1Qp4y1Y7YN?from=search&seid=9151155942386776091&spm_id_from=333.337.0.0
>> * 可视化教程：https://www.bilibili.com/video/BV177411j7qJ?spm_id_from=333.999.0.0

* ## 3.使用说明
> 首先这两个项目顾名思义，一个是flink流计算的项目，用java开发的；另一个是可视化的项目，用python和html开发的。
>> * 为什么说是java开发而在flink maven项目中是导入的scala？
>>> 我一开始是使用scala语言开发的flink项目，但因为我是使用redis的增量计算，并没有找到scala关于redis的支持，而java在这方面很全，遂转之。scala项目中可以写java，所以就没动了。
>> * 为什么javascript占了项目语言那么高的比例呢？
>>> 因为项目里有echarts的js文件。
>> * 项目文件按说明
>>> * FlinkStromTest\src\main\java\demo\finalVersion.java
>>> 开发flink代码的最终版本
>>> * FlinkStromTest\src\main\java\redis\JRedisUtil.java
>>> java的redis工具类
>>> * visidomTest\show\utils.py
>>> python的工具类，主要包括redis操作
>>> * visidomTest\show\app.py
>>> flask的主程序
>>> * visidomTest\show\templates\index.html
>>> 主界面
>>> * visidomTest\show\static\css\index.css
>>> 主界面样式表
>>> * visidomTest\showstatic\js
>>> echarts的支持、ec_center.js等模板以及ajax函数controller.js
