package test.java.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */
public class CollectionsTest {

    public List<String> getList() {
        List<String> list = new ArrayList<>();
        list.add("2");list.add("3");
        list.add("4");list.add("1");
        return list;
    }

    //排序
    @Test
    public void sort() {
        List<String> list = getList();

        Collections.sort(list);     //排序，默认是升序，Collections.reverseOrder()返回一个倒叙排序的Collector对象
        System.out.println(list);   //[1, 2, 3, 4]

        //java8之前的方式，compare中的第一个元素在前为正序，同sort(list)效果一致
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println(list);

        //java8 Lambda形式，倒序排序
        Collections.sort(list, (o1, o2) -> o2.compareTo(o1));
        System.out.println(list);
    }

    //乱序
    @Test
    public void shuffle() {
        List<String> list = getList();
        Collections.shuffle(list);      //打乱集合元素
        System.out.println(list);       //多次执行结果不同
    }

    //倒序，与add顺序相反
    @Test
    public void reverse() {
        List<String> list = getList();
        Collections.reverse(list);      //倒序，按照原来的add顺序倒着输出，不是由大到小
        System.out.println(list);
    }

    @Test
    public void reverseOrder() {
        System.out.println(Collections.reverseOrder().getClass().getName());
        List<String> list = getList();
        System.out.println("Before sort : " + list);
        Collections.sort(list, Collections.reverseOrder());
        System.out.println("After sort : " + list);
    }

}
