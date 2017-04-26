package test.java.time;

import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/20.
 */
public class InstantTest {

    /**
     * 获取当前日期
     */
    @Test
    public void now() {
        //This will query the system UTC clock to obtain the current instant
        Instant instant = Instant.now();
        //以ISO-8601输出
        System.out.println(instant);    //2017-02-20T09:32:43.134Z

        System.out.println(Instant.now().atOffset(ZoneOffset.ofHours(8)));
    }

    /**
     * 将符合UTC格式的字符串转换为Instant对象
     */
    @Test
    public void parse() {
        Instant instant = Instant.parse("2017-02-20T09:48:35Z");
        System.out.println(instant);
    }

    /**
     * 将毫秒转换为Instant对象
     */
    @Test
    public void ofEpochMilli() {
        Instant instant = Instant.ofEpochMilli(new Date().getTime());
        System.out.println(instant);
    }

    /**
     * 将秒转换为Instant对象
     */
    @Test
    public void ofEpochSecond() {
        Instant instant = Instant.ofEpochSecond(new Date().getTime()/1000);
        System.out.println(instant);
    }

    /**
     * Instant加法计算。Instant是一个不可变类，通过plus方法计算后会产生一个新的实例
     */
    @Test
    public void plus() {
        Instant instant = Instant.now();
        System.out.println(instant);
        Instant instant1 = instant.plus(Duration.ofHours(5).plusMinutes(4));
        System.out.println(instant1);
    }

    /**
     * Instant减法计算。
     */
    @Test
    public void minus() {
        Instant instant = Instant.now();
        System.out.println(instant);
        //使用Duration.ofHours()
        Instant instant1 = instant.minus(Duration.ofHours(5));    //minus ： 缺少，减去，减号，符号，负的
        System.out.println(instant1);
        //使用ChronoUnit.HOURS
        Instant instant2 = instant.minus(5, ChronoUnit.HOURS);
        System.out.println(instant2);
    }

    //计算两个Instant之间的时间差
    @Test
    public void periodUntil() {
        Instant instant = Instant.now();
        Instant instant1 = instant.plus(Duration.ofHours(1));
        //时间差的表示形式指定为分钟 ChronoUnit.MINUTES
        long diffAsMinutes = ChronoUnit.MINUTES.between(instant, instant1);
        System.out.println(diffAsMinutes);
    }

    //比较两个Instant对象的大小，返回值有三种 ： -1,0,1
    @Test
    public void compareTo() {
        Instant instant = Instant.now();
        Instant instant1 = instant.plus(Duration.ofHours(1));
        int i = instant1.compareTo(instant);
        System.out.println(i);  //-1
    }

    @Test
    public void isBefore() {
        Instant instant = Instant.now();
        Instant instant1 = instant.plus(Duration.ofHours(1));
        Assert.assertTrue(instant.isBefore(instant1));
        Assert.assertFalse(instant.isBefore(instant));//Instant自己比较的话，不在自己之前也不在自己之后
    }

    @Test
    public void isAfter() {
        Instant instant = Instant.now();
        Instant instant1 = instant.plus(Duration.ofHours(1));
        Assert.assertTrue(instant1.isAfter(instant));
        Assert.assertFalse(instant1.isAfter(instant1));
    }
}
