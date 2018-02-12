package test.java.net;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author yanchao
 * @date 2018/1/24 14:40
 */
public class URLTest {

    @Test
    public void openStream() {
        try {
            URL url = new URL("http://www.baidu.com");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            while ((str = reader.readLine()) != null) {
                System.out.println(str);
            }
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
