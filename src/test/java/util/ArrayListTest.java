package test.java.util;

import org.junit.Test;

import java.util.*;

/**
 * Created by Administrator on 2017/2/16.
 */
public class ArrayListTest {

    /**
     * 不要在foreach循环里进行元素的remove/add操作。
     * remove元素请使用Iterator方式，如果并发操作，需要对Iterator对象加锁
     */
    @Test
    public void iterator() {
        List<String> list = new ArrayList<>();
        list.add("1"); list.add("2");

        //在foreach中执行remove操作会正常执行但运行结果异常
        /*for (String temp : list) {
            if (Objects.equals("1", "temp")) {
                list.remove(temp);
            }
        }
        System.out.println(list);*/   //[1, 2]

        //使用Iterator执行集合的remove操作
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String temp = iterator.next();
            if (Objects.equals("1", temp)) {
                list.remove(temp);
            }
        }
        System.out.println(list);
    }

}
