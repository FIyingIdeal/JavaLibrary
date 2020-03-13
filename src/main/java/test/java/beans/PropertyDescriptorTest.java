package test.java.beans;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * PropertyDescriptor是描述对象属性相关信息的，类似如下形式
 * java.beans.PropertyDescriptor[name=age; propertyType=int; readMethod=public int test.java.beans.Person.getAge(); writeMethod=public void test.java.beans.Person.setAge(int)]
 * 可以获取  属性名  set方法  get方法
 *
 * 使用内省（Introspector）获取对象的BeanInfo信息，再通过BeanInfo获取属性的描述器（PropertyDescriptor），
 * 通过PropertyDescriptor可以获取到各个属性的getter/setter方法，之后利用反射执行getter/setter方法即可
 *
 * @author yanchao
 * @date 2017/4/26.
 */

public class PropertyDescriptorTest {

    public static void main(String[] args) {
        Person person = new Person("admin", 'M', 26);
        Map<String, Object> map = new HashMap<>();
        map.put("name", "lisi");
        map.put("sex", 'F');
        map.put("age", 24);
        bean2Map(person);
        map2Bean(map);

    }

    private static void bean2Map(Person person) {
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : descriptors) {
                System.out.println(descriptor);
                String key = descriptor.getName();
                if (Objects.equals("class", key)) {
                    continue;
                }
                map.put(key, descriptor.getReadMethod().invoke(person));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(map);
    }

    private static void map2Bean(Map<String, Object> map) {
        Person person = new Person();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(person.getClass());
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : descriptors) {
                String key = descriptor.getName();
                if (map.containsKey(key)) {
                    descriptor.getWriteMethod().invoke(person, map.get(key));
                }
            }
            System.out.println("Person == " + person.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Person {

    public Person() {}

    public Person(String name, char sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    private String name;
    private char sex;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                '}';
    }
}
