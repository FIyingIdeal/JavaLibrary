package test.java.lang.StringTest;

import org.junit.Test;
import utils.PrintUtil;

import java.util.*;

/**
 * Created by Administrator on 2016/7/18.
 */
public class StringTest {

    @Test
    public void test() {
        String a = "a";
        String b = "b";

        String ab = a + b;
        String ab1 = "ab";
        String ab2 = "ab";
        String ab3 = a + b;

        System.out.println(ab == ab1);//false
        System.out.println(ab2 == ab1);//true
        System.out.println(ab == ab2);//false
        System.out.println(ab == ab3);//false
    }

    /**
     * 字符串拼接
     * {@link String#join(CharSequence, Iterable)}
     * {@link String#join(CharSequence, CharSequence...)}
     */
    @Test
    public void join() {
        List<String> list = new ArrayList<>();
        list.add("ni"); list.add("hao"); list.add("!");
        list.forEach(element -> System.out.println(element));
        System.out.println(String.join(",", list));

        //String#join(CharSequence, CharSequence...)
        String[] strings = {"Java", "is", "a", "JVM", "language"};
        System.out.println(String.join(" ", strings));
    }

    /**
     * 字符串格式化
     * {@link String#format(String, Object...)}
     */
    @Test
    public void format() {
        String str = "%s %s(%s)";
        String packageName = "java.lang.String";
        String methodName = "public static String format";
        String params = "String format, Object... args";
        String formatStr = String.format(str, packageName, methodName, params);
        System.out.println(formatStr);

        String strWithNumber = "%s %d";
        String formatWithNumber = String.format(strWithNumber, "String with number", 123);
        System.out.println(formatWithNumber);

    }

    @Test
    public void split() {
        String num = "   ab c  d   ";
        List<String> list = Arrays.asList(num.trim().split("\\s+"));
        System.out.println(list);
    }

    /**
     * trim() 会将字符串两边的空白字符去掉
     */
    @Test
    public void trim() {
        String s = "   ab c  d   ";
        String trimStr = s.trim();
        System.out.println(trimStr + "  " + trimStr.length());
    }

    @Test
    public void tes1t() {
        PrintUtil.println(Math.negateExact(1));
        PrintUtil.println(Math.negateExact(-1));
    }

}
