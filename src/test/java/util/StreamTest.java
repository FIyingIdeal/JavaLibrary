package test.java.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/2/14.
 */
public class StreamTest {

    @Test
    public void test() {
        List<Integer> nums = Arrays.asList(1,1,null,
                2,3,4,5,6,null,7,8,9,10);
        System.out.println(
                nums.stream().filter(num -> num != null).distinct()
                        .mapToInt(num -> num * 2).peek(System.out::println)
                        .skip(2).limit(4).sum()
        );
    }

    @Test
    public void reduce() {
        List<Integer> nums = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        //reduce函数有两个参数，第一个参数是上次函数执行的返回值（也称为中间结果），
        //第二个参数是stream中的元素，这个函数把这两个值相加，
        // 得到的和会被赋值给下次执行这个函数的第一个参数。
        // 要注意的是：**第一次执行的时候第一个参数的值是Stream的第一个元素，
        // 第二个参数是Stream的第二个元素**。这个方法返回值类型是Optional，
        // 这是Java8防止出现NPE的一种可行方法
        int sum1 = nums.stream().reduce((sum, num) -> sum + num).get();
        System.out.println(sum1);   //55

        //以下reduce重载的方法允许用户提供一个循环计算的初始值，如果Stream为空，就直接返回该值
        // 而且这个方法不会返回Optional，因为其不会出现null值
        int sum2 = nums.stream().reduce(4, (sum, num) -> sum + num);
        System.out.println(sum1);   //55
        List<Integer> nullNums = Arrays.asList();
        int nullsum = nullNums.stream().reduce(0, (sum, num) -> sum + num);
        System.out.println("nullNum = " + nullsum);

        int sum3 = nums.stream().reduce(1, (sum, num) -> sum + num);
        System.out.println(sum3);   //56,前边的1会参与运算
    }

    /**
     * 统计个数
     */
    @Test
    public void count() {
        List<Integer> nums = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        System.out.println(nums.stream().count());  //10
    }

    @Test
    public void max() {
        List<Integer> nums = Arrays.asList(1,2,3,46,5,6,7,8,9,10);
        System.out.println("max => " + nums.stream().max((o1, o2) -> o1.compareTo(o2)).get());
    }
}
