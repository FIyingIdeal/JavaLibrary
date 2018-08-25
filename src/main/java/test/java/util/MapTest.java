package test.java.util;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/5/16.
 */
public class MapTest {

    private Map<Integer, String> map = new HashMap<Integer, String>();
    {
        map.put(1, "1");
        map.put(2, "2");
        map.put(3, "3");
    }

    @Test
    public void newMap() {
        Map<Integer, String> newMap = new HashMap<>(map);
        newMap.put(4, "4");
        System.out.println(map);
        System.out.println(newMap);
    }

    @Test
    public void values() {
        System.out.println(map.values());
    }

    @Test
    public void entrySetAndStream() {
        /*map.put(4, "4");
        String result = map.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue(), "3"))
                .map(m -> m.getValue()).collect(Collectors.joining(","));
        System.out.println(result);

        Map<Integer, String> collect = map.entrySet().stream()
                .filter(entry -> entry.getKey() == 4)
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));*/

        Map<Integer, String> map1 = new HashMap<>();
        map1.put(1, "a");
        map1.put(2, null);
        map.entrySet().stream().forEach(entry -> map1.putIfAbsent(entry.getKey(), entry.getValue()));
        System.out.println(map1);
        //System.out.println(collect);
    }

    @Test
    public void ListMapTest() {
        List<Map<Integer, String>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            Map<Integer, String> map = new HashMap<>();
            map.put(i, String.valueOf(i));
            list.add(map);
        }
        System.out.println(list);

        list.stream().forEach(item -> item.putIfAbsent(7, "7"));
        System.out.println(list);
    }

    /**
     * 如果指定的key存在，则返回该key对应的value，如果不存在，则返回指定的值
     */
    @Test
    public void getOrDefault() {
        // map.put(4, null); // 如果key值存在的话，就不会返回defaultValue
        System.out.println(map.getOrDefault(4, "8"));
    }


    @Test
    public void forEach() {
        map.forEach((k, v) -> System.out.println(k + "=" + v));
    }

    /**
     * repalceAll() 用来替换Map中所有Entry的value值
     */
    @Test
    public void replaceAll() {
        map.replaceAll((k, v) -> "a");
        map.forEach((k, v) -> System.out.println(k + "=" + v));
    }

    /**
     * 如果key关联的value不存在（value=null），则关联新的value值，返回key关联的旧的值
     * 注意：key关联的value不存在可能是key存在，但value为null；或甚至连key都不存在；
     *      putIfAbsent()方法针对上述两种可能都会处理赋值
     */
    @Test
    public void putIfAbsent() {
        map.put(4, null);
        String value = map.putIfAbsent(4, "4");     // 4=4，上边的null会被覆盖掉
        System.out.println("value = " + value);     // null
        String value1 = map.putIfAbsent(4, "44");   // 4=4，上边的4不会被覆盖掉，44被忽略，旧值4被返回
        System.out.println("value = " + value1);    // 4
        map.putIfAbsent(5, "5");
        map.forEach((k, v) -> System.out.println(k + "=" + v));
    }

    /**
     * remove(key, value)接收2个参数，key和value，如果key关联的value值与指定的value值相等（equals)，则删除这个元素
     */
    @Test
    public void remove() {
        map.remove(3, "4");
        map.forEach((k, v) -> System.out.println(k + "=" + v));
        map.remove(3, "3");
        map.forEach((k, v) -> System.out.println(k + "=" + v));
    }

    /**
     * replace(key, oldValue, newValue)如果key关联的值与指定的oldValue的值相等，则替换成新的newValue
     */
    @Test
    public void replace() {
        map.replace(3, "3", "c");
        map.forEach((k, v) -> System.out.println(k + "=" + v));
    }

    /**
     * replace(key, value) 如果map中存在key，则替换成value值，否则返回null
     */
    @Test
    public void replace1() {
        map.replace(2, "b");
        map.forEach((k, v) -> System.out.println(k + "=" + v));
    }

    /**
     * 如果指定的key不存在，则通过指定的K -> V计算出新的值设置为key的值
     */
    @Test
    public void computeIfAbsent() {
        map.computeIfAbsent(1, k -> "a");   // 1=1
        map.forEach((k, v) -> System.out.println(k + "=" + v));
        map.computeIfAbsent(4, k -> "d");   // 4=d
        map.forEach((k, v) -> System.out.println(k + "=" + v));
    }

    /**
     * 如果指定的key存在，则根据旧的key和value计算新的值newValue
     * 如果newValue不为null，则设置key新的值为newValue, 如果newValue为null, 则删除该key的值
     */
    @Test
    public void computeIfPresent() {
        map.computeIfPresent(3, (k, v) -> "3");
        map.forEach((k, v) -> System.out.println(k + "=" + v));
        map.computeIfPresent(3, (k, v) -> null);
        map.forEach((k, v) -> System.out.println(k + "=" + v));
        map.computeIfPresent(4, (k, v) -> "4");
        map.forEach((k, v) -> System.out.println(k + "=" + v));
    }

    /**
     * compute() 方法是computeIfAbsent()与computeIfPresent()的综合体
     */
    @Test
    public void compute() {
        map.compute(4, (k, v) -> "4");  // 4 = 4
        map.forEach((k, v) -> System.out.println(k + "=" + v));
        map.compute(4, (k, v) -> "d");  // 4 = d
        map.forEach((k, v) -> System.out.println(k + "=" + v));
        map.compute(4, (k, v) -> null); // key为4的不存在
        map.forEach((k, v) -> System.out.println(k + "=" + v));
    }

    /**
     * 如果指定的key不存在，则设置指定的value值，否则根据key的旧的值oldvalue，value计算出新的值newValue, 如果newValue为null, 则删除该key，否则设置key的新值newValue
     * merge() 方法与compute() 相似，唯一的不同是多了一个参数用来设置当key不存在的时候的value值
     */
    @Test
    public void merge() {
        map.merge(4, "4", (k, v) -> "d"); // 4 = 4
        map.forEach((k, v) -> System.out.println(k + "=" + v));
        map.merge(4, "4", (k, v) -> "d"); // 4 = d
        map.forEach((k, v) -> System.out.println(k + "=" + v));
        map.merge(4, "4", (k, v) -> null); // key为4不存在了
        map.forEach((k, v) -> System.out.println(k + "=" + v));
        map.merge(4, "4", (k, v) -> "d"); // 4 = 4
        map.forEach((k, v) -> System.out.println(k + "=" + v));
    }
}
