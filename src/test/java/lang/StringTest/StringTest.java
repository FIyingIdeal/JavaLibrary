package test.java.lang.StringTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        System.out.println(ab == ab1);//false
        System.out.println(ab2 == ab1);//true
        System.out.println(ab == ab2);//false
    }

    @Test
    public void joinTest() {
        List<String> list = new ArrayList<>();
        list.add("ni"); list.add("hao"); list.add("!");
        list.forEach(element -> System.out.println(element));
        System.out.println(String.join(",", list));
    }

    @Test
    public void mapForEachTest() {
        Map<String, String> map = new HashMap<>();
        map.put("first", "1");
        map.put("second", "2");
        map.put("third", "3");
        map.forEach((key,value) -> System.out.println(key + "=" + value));
    }
}
