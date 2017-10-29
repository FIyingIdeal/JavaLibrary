package test.java.io.DataInputStreamTest;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/**
 * @author yanchao
 * @date 2017/9/15 17:06
 */
public class DataInputStreamTest {

    public static void main(String[] args) {
        try {
            DataInputStream dis = new DataInputStream(System.in);
            int num = dis.readInt();
            System.out.println("read num is " + num);
            dis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
