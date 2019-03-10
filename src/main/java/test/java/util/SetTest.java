package test.java.util;

import org.junit.Test;

import java.util.*;

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

    /**
     * 使用{@link Set#retainAll(Collection)}求两个Set集合的交集
     */
    @Test
    public void retainAll() {
        Set<Integer> superSet = new HashSet<>(Arrays.asList(1,2,3,4,5,6));
        Set<Integer> subSet = new HashSet<>(Arrays.asList(2,4,6));
        Set<Integer> resultSet = new HashSet<>(superSet);
        System.out.println(resultSet.retainAll(subSet)); //true
        System.out.println(resultSet);                   //[2, 4, 6]
        resultSet = new HashSet<>(superSet);
        System.out.println(subSet.retainAll(resultSet)); //false，如果subSet中的元素为[2,4,6,7]此处就为true
        System.out.println(subSet);                     //[2, 4, 6]
    }

    @Test
    public void treeSet() {
        Set<Integer> treeSet = new TreeSet<>();
        treeSet.add(4);
        treeSet.add(2);
        System.out.println(treeSet);
        treeSet.add(3);
        System.out.println(treeSet);
    }
}
