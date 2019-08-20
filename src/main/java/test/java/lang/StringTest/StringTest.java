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

    /**
     * format 特殊情况测试
     * 1. 如果定义格式的时候没有占位符的话，String.format()后边可以跟多个参数，不会有影响；
     * 2. 如果定义格式的时候有占位符的话，String.format()第二个参数起必须有足够的参数来对应 %s 的个数，不然会报异常，
     *      参数超过占位符的数量无影响，但要保证参数类型与顺序问题
     */
    @Test
    public void specialFormat() {
        String str = "abc";
        System.out.println(String.format(str, "ooo"));

        // java.util.MissingFormatArgumentException: Format specifier '%s'
        // str = "abc%sef%s";
        // System.out.println(String.format(str, "d"));

        str = "abc%s";
        System.out.println(String.format(str, "efg", 1, "hij"));
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
