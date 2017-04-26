package test.java.util.concurrent;

import org.junit.Test;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by Administrator on 2017/3/1.
 * java.util.concurrent.ConcurrentNavigableMap 是一个支持并发访问的 java.util.NavigableMap，
 * 它还能让它的子 map 具备并发访问的能力。所谓的 "子 map" 指的是诸如 headMap()，subMap()，tailMap() 之类的方法返回的 map。
 */
public class ConcurrentNavigableMapTest {

    /**
     * headMap(T toKey)方法返回一个包含了小于给定toKey的key的子map
     * 如果对原始map里的元素做了改动，这些改动将影响到子map中的元素
     */
    @Test
    public void headMap() {
        ConcurrentNavigableMap map = new ConcurrentSkipListMap();
        map.put("1", "one");
        map.put("2", "two");
        map.put("3", "three");
        ConcurrentNavigableMap headMap = map.headMap("3");
        headMap.forEach((key,value) -> System.out.println(key + "=" + value));
        //1=one \n 2=two
    }

    /**
     * tailMap(T fromKey)方法返回一个包含了不小于（>=）给定fromKey的key的子map
     */
    @Test
    public void tailMap() {
        ConcurrentNavigableMap map = new ConcurrentSkipListMap();
        map.put("1", "one");
        map.put("2", "two");
        map.put("3", "three");
        ConcurrentNavigableMap headMap = map.tailMap("2");
        headMap.forEach((key,value) -> System.out.println(key + "=" + value));
        //2=two \n 3=three
    }

    /**
     * subMap(fromKey, toKay)方法返回介于formkey（包含）和tokey（不包含）之间的子map
     * subMap(fromKey, boolean, toKay, boolean)可以设定是否包含边界元素
     */
    @Test
    public void subMap() {
        ConcurrentNavigableMap map = new ConcurrentSkipListMap();
        map.put("1", "one");
        map.put("2", "two");
        map.put("3", "three");
        ConcurrentNavigableMap headMap = map.subMap("2", "3");
        headMap.forEach((key,value) -> System.out.println(key + "=" + value));
        //2=two
        System.out.println("-----------------");
        ConcurrentNavigableMap headMap1 = map.subMap("2", false, "3", true);
        headMap1.forEach((key,value) -> System.out.println(key + "=" + value));
        //3=three
    }
}
