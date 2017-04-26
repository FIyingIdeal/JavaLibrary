package test.java.util.stream;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/2/14.
 */
public class CollectorsTest {

    public List<Person> getPersons() {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Person person = new Person("name" + i, i + 20);
            persons.add(person);
        }
        return persons;
    }

    /**
     * Collectors.toList()
     */
    @Test
    public void toList() {
        List<Person> persons = getPersons();
        List<String> names = persons.stream().map(
                Person::getName).collect(Collectors.toList());
        System.out.println(names);
    }

    @Test
    public void toCollection() {
        List<Person> persons = getPersons();
        Set<String> names = persons.stream().map(
                Person::getName).collect(Collectors.toCollection(TreeSet::new));
        List<String> names1 = persons.stream().map(
                Person::getName).collect(Collectors.toCollection(ArrayList::new));
        System.out.println(names);

    }

    @Test
    public void toSet() {
        List<Person> persons = getPersons();
        Set<String> names = persons.stream().map(
                Person::getName).collect(Collectors.toSet());
        System.out.println(names);
    }

    @Test
    public void joining() {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        System.out.println(list.stream().map(Object::toString)
                .collect(Collectors.joining(":", "(", ")")));

        List<String> list1 = Arrays.asList("1","2","3");
        String s = list1.stream().collect(Collectors.joining(":", "(", ")"));
        System.out.println(s);
    }

    @Test
    public void summingInt() {
        List<Person> persons = getPersons();
        int ageCount = persons.stream().collect(
                Collectors.summingInt(Person::getAge));
        System.out.println(ageCount);
    }

    @Test
    public void groupingBy() {
        List<Person> persons = getPersons();
        persons.add(new Person("name5", 20));
        persons.add(new Person("name5", 24));
        //根据Person的name分组
        Map<String, List<Person>> map = persons.stream()
                .collect(Collectors.groupingBy(Person::getName));
        System.out.println(map);

        //根据Person的name分组，对每一个分组内的所有Person的age进行求和
        Map<String, Integer> totalAgeByName = persons
                .stream().collect(Collectors.groupingBy(
                        Person::getName, Collectors.summingInt(
                                Person::getAge)));
        System.out.println(totalAgeByName);
    }

    @Test
    public void partitioningBy() {
        List<Person> persons = getPersons();
        Map<Boolean, List<Person>> map = persons.stream()
                .collect(Collectors.partitioningBy(person -> person.getAge() > 25));
        System.out.println(map);

        long count = persons.stream().filter(person -> person != null).count();
        System.out.println(count);
    }

    @Test
    public void averagingInt() {
        List<Person> persons = getPersons();
        //先筛选出所有age，然后再求平均值，但比较麻烦
        /*List<Integer> ages = persons.stream().map(Person::getAge)
                .collect(Collectors.toList());
        Double averageAge1 = ages.stream().collect(
                Collectors.averagingInt(item -> item));*/

        //返回值必须为double，不能是int，不然报错
        Double averageAge = persons.stream().collect(
                Collectors.averagingInt(Person::getAge));

        System.out.println(averageAge);
    }

}




class Person {
    private String name;
    private int age;

    public Person() {}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ", age=" + age +
                '}';
    }
}
