package test.java.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 *
 * List元素排序：java中对List进行排序有两种方式：
 *     1. Collections.sort(List<T>) : T必须实现Comparable接口(java.lang.Comparable)并重写了compare()方法;
 *     2. Collections.sort(List<T>, Comparator> : 自己提供一个Comparator接口(java.util.Comparator)的实现，并重写compare()方法
 *
 * 该测试使用Comparator进行了两个字段的排序，先以age正序排序，如果age相同，按username倒序排序。
 */
public class ComparatorTest {

    @Test
    public void comparator() {
        Student wang1 = new Student("wang", 28);
        Student zhang1 = new Student("zhang", 24);
        Student zhang2 = new Student("zhang", 28);
        Student zhang3 = new Student("zhang", 13);
        Student sun1 = new Student("sun", 24);
        List<Student> list = new ArrayList<>();
        list.add(wang1);
        list.add(zhang1);
        list.add(zhang2);
        list.add(zhang3);
        list.add(sun1);
        list.forEach(student -> System.out.println(student));
        Collections.sort(list, new MyComparator()); //提供一个自定义的Comparator实现
        System.out.println("---------After Comparator sort----------");
        list.forEach(student -> System.out.println(student));
    }

}

class Student {
    //为了省略一堆getter/setter方法，节省空间，一般情况下不推荐将对象属性定义为public
    public String username;
    public int age;

    public Student (String username, int age) {
        this.username = username;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}

class MyComparator implements Comparator {
    //按age排序，age相同的话按username倒排序
    @Override
    public int compare(Object o1, Object o2) {
        Student s1 = (Student)o1;
        Student s2 = (Student)o2;
        if (s1.age == s2.age) {
            return s2.username.compareTo(s1.username);
        }
        return s1.age > s2.age ? 1 : -1;
    }
}
