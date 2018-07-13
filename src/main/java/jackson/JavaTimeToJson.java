package jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @author yanchao
 * @date 2018/6/14 14:42
 */
public class JavaTimeToJson {

    private static final Logger logger = LoggerFactory.getLogger(JavaTimeToJson.class);

    class JavaTime {
        private Date date = new Date();
        private java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
        private Calendar calendar = Calendar.getInstance();
        private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        private LocalDate localDate = LocalDate.now();
        private LocalDateTime localDateTime = LocalDateTime.now();

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public java.sql.Date getSqlDate() {
            return sqlDate;
        }

        public void setSqlDate(java.sql.Date sqlDate) {
            this.sqlDate = sqlDate;
        }

        public Calendar getCalendar() {
            return calendar;
        }

        public void setCalendar(Calendar calendar) {
            this.calendar = calendar;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }

        public LocalDate getLocalDate() {
            return localDate;
        }

        public void setLocalDate(LocalDate localDate) {
            this.localDate = localDate;
        }

        public LocalDateTime getLocalDateTime() {
            return localDateTime;
        }

        public void setLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        @Override
        public String toString() {
            return "JavaTime{" +
                    "java.util.Date=" + date +
                    ", java.sql.Date=" + sqlDate +
                    ", calendar=" + calendar +
                    ", timestamp=" + timestamp +
                    ", localDate=" + localDate +
                    ", localDateTime=" + localDateTime +
                    '}';
        }
    }

    @Test
    public void printJavaTime() {
        logger.info("java time : {}", new JavaTime());
    }

    @Test
    public void printJacksonSerializeJavaTime() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        logger.info("jackson serialize java time : {}", mapper.writeValueAsString(new JavaTime()));
        // {"date":1528959077880,
        // "sqlDate":"2018-06-14",
        // "calendar":1528959077881,
        // "timestamp":1528959077881,
        // "localDate":{"year":2018,"month":"JUNE","chronology":{"id":"ISO","calendarType":"iso8601"},"era":"CE","dayOfMonth":14,"dayOfWeek":"THURSDAY","dayOfYear":165,"leapYear":false,"monthValue":6},
        // "localDateTime":{"hour":14,"minute":51,"nano":941000000,"second":17,"dayOfMonth":14,"dayOfWeek":"THURSDAY","dayOfYear":165,"month":"JUNE","monthValue":6,"year":2018,"chronology":{"id":"ISO","calendarType":"iso8601"}}}

        mapper.registerModule(new JSR310Module());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        logger.info("with JSR310 : {}", mapper.writeValueAsString(new JavaTime()));

        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        logger.info("with JavaTimeModule : {}", mapper.writeValueAsString(new JavaTime()));
    }
}
