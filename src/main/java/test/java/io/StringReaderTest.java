package test.java.io;

import org.junit.Test;

import java.io.*;

/**
 * @author yanchao
 * @date 2019-08-01 13:58
 */
public class StringReaderTest {


    @Test
    public void test() {
        String str = "This a message for StringReader Test!";
        // StringReader stringReader = new StringReader(str);
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes());
             FileOutputStream fileOutputStream = new FileOutputStream("/Users/tal/Downloads/a.txt")) {
            int b;
            while ((b = byteArrayInputStream.read()) != -1) {
                fileOutputStream.write(b);
            }
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
