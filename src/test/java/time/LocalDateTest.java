package test.java.time;

import org.junit.Assert;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * Created by Administrator on 2017/1/22.
 */
public class LocalDateTest {

    /**
     * 将字符串转换为 LocalDate 对象
     */
    @Test
    public void parse() {
        LocalDate date = LocalDate.parse("2017-01-23");
        System.out.println(date);
    }

    /**
     * ofYearDay(int year, int dayOfYear) 用来获取year年的dayOfYear天的LocalDate对象
     */
    @Test
    public void ofYearDay() {
        LocalDate date = LocalDate.ofYearDay(2017, 31);
        System.out.println(date);
    }

    /**
     * 通过 getXXX() 等方法获取具体的日期的相关信息
     */
    @Test
    public void getLocalDateInfo() {
        LocalDate localDate = LocalDate.of(2017, 1, 22);
        int year = localDate.getYear();             //获取年  2017
        Month month = localDate.getMonth();         //获取月  JANUARY
        int day = localDate.getDayOfMonth();        //获取日  22
        DayOfWeek week = localDate.getDayOfWeek();  //获取周几  SUNDAY
        int lengthOfMonth = localDate.lengthOfMonth();  //获取当月有多少天  31
        boolean leap = localDate.isLeapYear();      //是否是闰年   false
        System.out.println("Year: " + year + ",month: " + month.getValue()
                + ",day: " + day + ",week: " + week.getValue() + ",lengthOfMonth: "
                + lengthOfMonth + ",isLeapYear: " + leap);
        System.out.println(DayOfWeek.SUNDAY == week);      //true
    }

    @Test
    public void updateLocalDate() {
        LocalDate localDate = LocalDate.of(2017, 1, 22);
        //LocalDate是一个不可变类型，每次操作都会产生一个新的实例，而原有实例不受任何影响
        LocalDate localDate1 = localDate.withYear(2016);
        System.out.println(localDate.toString() + "," + localDate1);//2017-01-22,2016-01-22

        //日期计算
        localDate = localDate.plusMonths(2);
        System.out.println(localDate.toString());   //2017-03-22

        LocalDate localDate2 = localDate;
        localDate2 = localDate2.plus(1, ChronoUnit.MONTHS);
        System.out.println(localDate2);             //2017-04-22

        //修改日期方法1
        localDate = localDate.with(ChronoField.DAY_OF_MONTH, 25);
        System.out.println(localDate);      //2017-03-25

        //修改日期方法2
        localDate = localDate.withDayOfMonth(31);
        System.out.println(localDate);      //2017-03-31

        //修改月份方法1，如果之前的日期的日大于所修改的月份的最大日期，则设置为所修改月的最后一天
        localDate = localDate.with(ChronoField.MONTH_OF_YEAR, 4);
        System.out.println(localDate);      //2017-04-30,4月没有31号，就转换为 2017-04-30

        //修改月份方法2
        localDate = localDate.with(Month.FEBRUARY);
        System.out.println(localDate);      //2017-02-28，自动转换为2月最后一天
    }

    @Test
    public void temporalAdjuster() {
        LocalDate date = LocalDate.now();
        System.out.println(date.toString());    //2017-02-21
        date = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY));
        System.out.println(date);               //2017-02-22
        date = date.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(date.toString());    //2017-02-28
    }

    @Test
    public void compare() {
        LocalDate date = LocalDate.now();
        LocalDate date1 = date.plus(Period.ofMonths(1));
        System.out.println(date1);
        Assert.assertEquals(date1.compareTo(date), 1);
    }

    @Test
    public void isBefore() {
        LocalDate date = LocalDate.now();
        LocalDate date1 = date.plus(Period.ofMonths(1));
        Assert.assertTrue(date.isBefore(date1));
    }

    @Test
    public void isAfter() {
        LocalDate date = LocalDate.now();
        LocalDate date1 = date.plus(Period.ofMonths(1));
        Assert.assertTrue(date1.isAfter(date));
    }

}
