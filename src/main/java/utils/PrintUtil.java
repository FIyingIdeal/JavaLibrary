package utils;

import java.util.Arrays;

/**
 * @author yanchao
 * @date 2018/11/1 14:06
 */
public class PrintUtil {

    private PrintUtil() {}

    public static void print(String message) {
        System.out.print(message);
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void println(String... messages) {
        Arrays.stream(messages).forEach(System.out::println);
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }

    public static void println(Object... objects) {
        Arrays.stream(objects).forEach(System.out::println);
    }

    public static void newLine() {
        System.out.println();
    }
}
