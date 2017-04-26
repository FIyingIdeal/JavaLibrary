package test.java.util.generic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 * Java泛型测试
 */
public class Generic {

    @Test
    public void genericTest1() {
        List<? extends Number> numbers = new ArrayList<Integer>();
        //add报错，numbers存放的元素只可以确定为Number或其子类型，但具体是什么类型不清楚，到底是Integer、Double还是Float...
        //所以无法add元素给它，但获取元素的时候可以确保一定是Number类型的
        //numbers.add(123);
        List<? super Integer> list = new ArrayList<>();
        list.add(123);
        Integer ele = (Integer)list.get(0);
        System.out.print(ele);
    }

    @Test
    public void superTest() {
        Plate<? super Fruit> plate = new Plate<Fruit>(new Fruit());
        plate.set(new Fruit());

    }

}
