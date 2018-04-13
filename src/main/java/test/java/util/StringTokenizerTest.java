package test.java.util;

import org.junit.Test;

import java.util.StringTokenizer;

/**
 * @author yanchao
 * @date 2018/4/10 11:37
 */
public class StringTokenizerTest {

    @Test
    public void test() {
        StringTokenizer parser = new StringTokenizer("{prop1=col1,prop2=col2}", "{}=, ", false);
        while (parser.hasMoreTokens()) {
            String property = parser.nextToken();
            String column = parser.nextToken();
            System.out.println(property + ", " + column);
        }

        System.out.println("=====");
        StringTokenizer parser1 = new StringTokenizer("{col1,col2}", "{}=, ", false);
        while (parser1.hasMoreTokens()) {
            String property = parser1.nextToken();
            String column = parser1.nextToken();
            System.out.println(property + ", " + column);
        }
    }
}
