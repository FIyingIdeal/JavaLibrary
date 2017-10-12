package test.java.util;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalendarTest {

    /**
     * getInstance()获取当前时间，该方法还有重载方法
     */
    @Test
    public void getInstance() {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.toString());

        //格林威治时间（冬令时）。注意夏令时（"GMT+1"，与北京相差7个小时）与冬令时（GMT，与北京相差8个小时）
        Calendar localeCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        System.out.println(localeCalendar.get(Calendar.HOUR_OF_DAY));
    }

    /**
     * 通过getTime()方法获取{@link Date}
     */
    @Test
    public void getTime() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        System.out.println(date);
    }

    @Test
    public void add() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = calendar.getTime();
        System.out.println(sf.format(date));
    }

    @Test
    public void set() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        System.out.println(calendar.getTime());
    }
}
