package test.java.util;

import java.util.UUID;

/**
 * Created by Administrator on 2016/7/22.
 */
public class UUIDTest {

    public String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public String format(String s) {
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }

    public static void main(String[] args) {
        UUIDTest test = new UUIDTest();
        String str = test.getUUID();
        System.out.println(str);
        System.out.println(test.format(str).length());
    }
}
