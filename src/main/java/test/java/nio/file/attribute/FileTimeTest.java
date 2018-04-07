package test.java.nio.file.attribute;

import org.junit.Test;

import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class FileTimeTest {

    public FileTime getCurrentFileTime() {
        return FileTime.fromMillis(System.currentTimeMillis());
    }

    @Test
    public void fromMillis() {
        FileTime time = FileTime.fromMillis(System.currentTimeMillis());
        System.out.println(time);
    }

    @Test
    public void to() {
        long t1 = getCurrentFileTime().to(TimeUnit.DAYS);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {}
        long t2 = getCurrentFileTime().to(TimeUnit.DAYS);

        System.out.println(t1 + ", " + t2);
        System.out.println(t1 == t2);
    }
}
