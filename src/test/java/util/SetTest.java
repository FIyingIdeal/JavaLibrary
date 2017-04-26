package test.java.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/2/16.
 */
public class SetTest {

    /**
     * addAll方法的参数类型为Collection
     * 可利用该方法实现对List中元素去重
     */
    @Test
    public void addAll() {
        List<String> list= new ArrayList<>();
        list.add("1");list.add("1");list.add("2");
        Set<String> set = new HashSet<>();
        set.addAll(list);   //
        System.out.println(set);
    }
}
