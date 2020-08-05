package test.java.lang.StringTest;

import org.junit.Test;

/**
 * @author yanchao
 * @date 2020-08-01 23:06
 */
public class StringBuilderTest {


    @Test
    public void insert() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, 1);
        System.out.println(sb.toString());
    }

}
