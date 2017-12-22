package test.java.util;

import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by Administrator on 2017/2/16.
 * remove元素请参考：https://segmentfault.com/q/1010000003775035?_ea=362485
 */
public class ArrayListTest {

    /**
     * 不要在foreach循环里进行元素的remove/add操作。
     * remove元素请使用Iterator方式，如果并发操作，需要对Iterator对象加锁
     */
    @Test
    public void iterator() {
        List<String> list = new ArrayList<>();
        list.add("1"); list.add("2"); list.add("3");

        //在foreach中执行remove操作会正常执行但运行结果异常
        /*for (String temp : list) {
            if (Objects.equals("1", temp)) {
                //java.util.ConcurrentModificationException List初始为两个元素的时候不会异常...
                list.remove(temp);
            }
        }
        System.out.println(list);*/   //[1, 2]

        //这种方式list一直在变化，index=0的元素删除后，原来index=1的元素就会在下一次遍历的时候变为index=0
        //这样该元素就会无法删掉....
        /*for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i) + ":" + list.remove(i));
            System.out.println(list.size());
        }*/

        //使用Iterator执行集合的remove操作
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String temp = iterator.next();
            if (Objects.equals("1", temp)) {
                iterator.remove();
                //java.util.ConcurrentModificationException List初始为两个元素的时候不会异常...
                //list.remove(temp);
            }
        }
        System.out.println(list);
    }

    @Test
    public void contain() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        List<Integer> subList = new ArrayList<>(Arrays.asList(1,2,3));
        System.out.println(list.containsAll(subList));
    }

    @Test
    public void removeIf() {

        List<Integer> numbers = new ArrayList<Integer>();
        numbers.add(10);
        numbers.add(15);
        numbers.add(20);
        numbers.add(25);

        Predicate<Integer> predicate = num -> num % 2 == 0;

        System.out.println("Original List : " + numbers);
        numbers.removeIf(predicate);
        System.out.println("Odd numbers : " + numbers);

        //Arrays.asList()生成的List(是一个Arrays.ArrayList，而不是常用的那个ArrayList)是只读的，不能进行add和remove操作
        //
        //List<Integer> nums = Arrays.asList(1,2,3,4,5,6);
        List<Integer> nums = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        //Predicate<Integer> predicate = num -> num % 2 == 0;
        //System.out.println("before remove : " + nums);
        nums.removeIf(predicate);
        System.out.println("after remove : " + nums);
    }

}
