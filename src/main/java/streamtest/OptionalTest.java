package streamtest;

import java.util.Optional;

/**
 * Created by Administrator on 2016/1/13.
 */
public class OptionalTest {
    public static void main(String[] args) {
        String a = "abc", b = null;
        print(a);
        print(b);

        getLength(a);
        getLength(b);
    }

    public static void print(String text) {
        Optional.ofNullable(text).ifPresent(System.out::print);
    }

    public static void getLength(String text) {
       Tool.println(Optional.ofNullable(text).map(String::length).orElse(-1));
    }
}
