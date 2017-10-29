package test.java.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */
public class ArraysTest {

    @Test
    public void asList() {
        String[] array = new String[]{"first", "second", "third"};
        List<String> list = Arrays.asList(array);
        System.out.println(list);   //[first, second, third]
        //asList的返回对象是一个Arrays内部类，并没有实现集合的修改方法。
        // Arrays.asList体现的是适配器模式，只是转换接口，后台的数据仍是数组
        //list.add("fouth");    //此句异常
        array[0] = "last";
        System.out.println(list);  //[last, second, third]
    }
}
