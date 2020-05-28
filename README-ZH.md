
[[English](README.md) | 中文]

将任意格式的日期字符串转换成ZonedDateTime对象。核心代码来自Golang项目[dateparse](https://github.com/araddon/dateparse)，通过JNA进行调用。 

用法：
```java
//  转换成UTC（GMT+0）的时间
ZonedDateTime date = DateParse.parseAny("03/19/2012 10:11:59");

// 用当地时区进行转换
ZonedDateTime date = DateParse.parseLocal("Mon, 02 Jan 2006 15:04:05 -0700");

// 将ZonedDateTime格式化成yyyy-MM-dd HH:mm:ss，使用UTC时区
String dateStr = DateParse.format(date);
```