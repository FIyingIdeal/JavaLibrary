package test.java.io.DataInputStreamTest;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author yanchao
 * @date 2017/9/15 17:06
 */
public class DataInputStreamTest {

    public static void main(String[] args) {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(System.in);
            int num = dis.readInt();
            System.out.println("read num is " + num);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
