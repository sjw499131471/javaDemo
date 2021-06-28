package com.sjw.basic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTest {

    public static void main (String[] args) throws Exception {
        //测试最近一个月的时间区间，当前时间向前推一个月
        timeRange();

        //字符串和日期的相互转换
        convert_date_string();
    }
    
    private static void convert_date_string () throws ParseException {
        String dateStr = "2012-11-12 12:10:14";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(dateStr);//字符串转日期
        String dateStr2 = format.format(date);//日期转字符串
        System.out.println(dateStr2);
    }

    private static void timeRange () {
        Calendar cal = Calendar.getInstance();;
        cal.setTime(new Date());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String timeStart = format.format(cal.getTime());
        cal.add(Calendar.MONTH,  -1);
        String timeEnd = format.format(cal.getTime());
        System.out.println(timeStart + "-" + timeEnd);
    }

}
