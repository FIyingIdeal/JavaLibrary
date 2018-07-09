package test.java.time;

import org.junit.Test;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */
public class LocalDateTimeTest {

    @Test
    public void now() {
        //This will query the system UTC clock to obtain the current instan
        Instant instant = Instant.now();            // 2017-02-21T05:38:19.720Z
        //A date-time without a time-zone in the ISO-8601 calendar system
        LocalDateTime time = LocalDateTime.now();   //2017-02-21T13:38:19.772
        System.out.println("Instant.now() = " + instant
                + "\nLocalDateTime.now() = " + time);
    }

    @Test
    public void parse() {
        LocalDateTime time = LocalDateTime.parse("2017-02-21T13:38:19.772");
        System.out.println(time);
    }

    //parse方法将String转化为LocalDateTime对时间字符串是有一定限制的，日期和时间之间要有一个T
    //但parse有重载的方法
    @Test
    public void parseWithDateTimeFormatter() {
        String dateTimeStr = "2017-08-02 13:38:19";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.parse(dateTimeStr, formatter);
        int day = 2;
        LocalDateTime yesterday = time.plusDays(-day);
        System.out.println(yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @Test
    public void plus() {
        LocalDateTime time = LocalDateTime.now();
        //计算给定时间2小时30分后的本地时间 方法一
        LocalDateTime inTheFuture = time.plusHours(2).plusMinutes(30);
        System.out.println(inTheFuture);

        //方法二 ： 其中plus提供了两个重载方法，在下边都有用到
        LocalDateTime time1 = time.plus(Duration.ofHours(2))
                .plus(30, ChronoUnit.MINUTES);
        System.out.println(time1);
    }

    @Test
    public void toLocalTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalTime time = dateTime.toLocalTime();
        System.out.println(time);
    }

    /**
     * 使用{@link Timestamp#valueOf(LocalDateTime)}将LocalDateTime转换为Timestamp
     * 在与数据库交互的时候会很有帮助，直接传递Timestamp，而不用在数据库与代码之间不断转换进行String与Timestamp的转换，另参考{@link java.sql.Types}
     */
    @Test
    public void toTimestamp() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        System.out.println(timestamp);
    }

    /**
     * 转换成秒，转换成毫秒需要借助{@link Instant}，参考{@link this#toInstant()}
     */
    @Test
    public void toEpochSecond() {
        long milliseconds1 = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        System.out.println(milliseconds1);
    }

    @Test
    public void toInstant() {
        long milliseconds = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println(milliseconds);
        Date date = new Date(milliseconds);
        System.out.println(date);
    }
}
