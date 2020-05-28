package io.github.araddon;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liaorui
 * @date 2020/5/27
 */
public class DateTest {

    @Test
    public void testParse1() {
        print(DateParse.formatLocal(DateParse.parseLocal("Mon, 02 Jan 2006 15:04:05 -0700")));
        print(DateParse.format(DateParse.parseLocal("Mon, 02 Jan 2006 15:04:05 -0700")));
        print(DateParse.format(DateParse.parseAny("Mon, 02 Jan 2006 15:04:05 -0700")));
        print(DateParse.format(DateParse.parseAny("Mon, 02 Jan 2006 15:04:05")));

        print(DateParse.formatLocal(DateParse.parseLocal("2014年04月08日")));

        print(DateParse.format(DateParse.parseAny("Mon, 02 Jan 2006 15:04:05 -0700")));

        print(DateParse.format(DateParse.parseAny("03/19/2012 10:11:59")));

        print(DateParse.format(DateParse.parseAny("1590582730")));
        print(DateParse.format(DateParse.parseAny("1590582730000")));

        print(DateParse.format(DateParse.parseAny("2014/04/08 22:05")));

        print(DateParse.toDate(DateParse.parseAny("Mon, 02 Jan 2006 15:04:05 -0700")));
        print(DateParse.toDate(DateParse.parseLocal("Mon, 02 Jan 2006 15:04:05 -0700")));
        print(DateParse.toDate(DateParse.parseAny("03/19/2012 10:11:59")));
    }

    private void print(String str) {
        System.out.println(str);
    }

    private void print(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateFormat.format(date));
    }

}
