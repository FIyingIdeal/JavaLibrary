package test.java.time;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

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
}
