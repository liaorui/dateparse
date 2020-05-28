
[English | [中文](README-ZH.md)]

Java Parse many date strings without knowing format in advance. Inspired by Golang Project [dateparse](https://github.com/araddon/dateparse).

### Usage：
```java
// Normal parse
ZonedDateTime date = DateParse.parseAny("03/19/2012 10:11:59");

// Parse with Local Timezone
ZonedDateTime date = DateParse.parseLocal("Mon, 02 Jan 2006 15:04:05 -0700");

// formate ZoneDateTime to yyyy-MM-dd HH:mm:ss with UTC timezone
String dateStr = DateParse.format(date);
```

### What is RFC-3339?

RFC-3339 is a subset/profile defined by W3C of the formats defined in ISO-8601, to simplify date and time exhange in modern Internet protocols.  

Typical formats include:  

2017-12-27T23:45:32Z - No fractional seconds, UTC/Zulu time  
2017-12-27T23:45:32.999Z - Millisecond fractions, UTC/Zulu time  
2017-12-27T23:45:32.999999Z - Microsecond fractions, UTC/Zulu time  
2017-12-27T23:45:32.999999999Z - Nanosecond fractions, UTC/Zulu time  
2017-12-27T18:45:32-05:00 - No fractional seconds, EST time  
2017-12-27T18:45:32.999-05:00 - Millisecond fractions, EST time  
2017-12-27T18:45:32.999999-05:00 - Microsecond fractions, EST time  
2017-12-27T18:45:32.999999999-05:00 - Nanosecond fractions, EST time  