package test.java.util.generic;

import org.junit.Test;
import test.java.util.generic.dependence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 * Java泛型测试
 */
public class Generic {

    // 泛型擦除
    @Test
    public void getGenericClass() {
        Class c1 = new ArrayList<Integer>().getClass();
        Class c2 = new ArrayList<String>().getClass();
        // true
        System.out.println("c1 == c2 ? " + (c1 == c2));
        // c1 = class java.util.ArrayList , c2 = class java.util.ArrayList
        System.out.println("c1 = " + c1 + " , c2 = " + c2);
    }

    @Test
    public void getTypeParameters() {

    }

    /**
     * 数组是支持协变的
     */
    @Test
    public void arrayXB() {
        Number number = new Integer(33);
        System.out.println(number);
        //协变：如果类A(Number)是类B(Integer)的基类，那么A[]也是B[]的基类
        Number[] numbers = new Integer[4];
        //此处编译时不会报错，但在运行时会进行类型检查，因为23.4不是Integer类型所以会报异常
        //numbers[0] = 23.4;  //java.lang.ArrayStoreException: java.lang.Double
        System.out.println(numbers[0]);
        //但泛型不支持协变，且泛型的主要目标之一是将这种错误检测移入到编译期
        List<? extends Number> numberList = new ArrayList<Integer>();
        //直接报错，因为只知道该List中要存放Number或其子类型，但不知道具体是什么类型....只能存放null
        //numberList.add(23);
        numberList.add(null);

    }

    @Test
    public void genericTest1() {
        //泛型不支持协变
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

    static class Reader<T> {
        T readExact(List<T> list) {
            return list.get(0);
        }
    }

    List<Apple> apples = Arrays.asList(new Apple());
    List<Fruit> fruit = Arrays.asList(new Fruit());

    @Test
    public void readerTest() {
        Reader<Fruit> fruitReader = new Reader<Fruit>();
        Fruit f = fruitReader.readExact(fruit);
        Reader<Apple> appleReader = new Reader<>();
        Fruit a = appleReader.readExact(apples);
    }

    public <T> void fill(T[] array, T num) {
        Arrays.fill(array, num);
    }

    public void testFill() {
        //error : 自动包装不能应用于数组，而任何基本类型不能作为泛型的类型参数
        //fill(new int[3], 2);
        fill(new Integer[3], 3);
    }

}

//一个类不能实现同一个泛型接口的两种变体，由于擦除的原因，这两个变体会成为相同的接口，但如果将泛型去掉的话是可以的...
//class ParentImp extends ParentAbstractClass implements ParentInterface<ParentImp> {}
