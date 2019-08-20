package test.java.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author yanchao
 * @date 2019-08-19 20:08
 */
@Slf4j
public class PriorityQueueTest {

    private static class Person implements Comparable<Person> {

        private String name;
        private Integer age;

        public Person(String name, Integer age) {
            this.age = age;
            this.name = name;
        }


        @Override
        public int compareTo(Person o) {
            return this.age.compareTo(o.age);
        }
    }

    @Test
    public void constructor() {
        // 如果不指定 Comparator 的话，那插入到 PriorityQueue 中的元素必须是一个实现了 Comparable 接口的类对象
        PriorityQueue<Person> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(new Person("zhang", 20));
        priorityQueue.offer(new Person("wang", 12));
        priorityQueue.offer(new Person("li", 48));
        priorityQueue.offer(new Person("zhao", 22));

        /**
         * PriorityQueue 底层使用的是一个最大化堆或最小化堆，通过 foreach 来获取的话，不是按照指定顺序获取的，而是通过堆的层级来获取的
         */
        for (Person person : priorityQueue) {
            log.info("{}'s age is {}", person.name, person.age);
        }

        /**
         * 通过 poll() 可以按照指定的顺序来获取堆内的元素
         */
        while (!priorityQueue.isEmpty()) {
            Person person = priorityQueue.poll();
            log.info("{}'s age is {}", person.name, person.age);
        }
    }

    @Test
    public void constructorWithComparator() {
        Comparator<Person> comparator = (o1, o2) -> o1.name.compareTo(o2.name);
        // 指定了 Comparator 的话，即使插入的对象是一个 Comparable 对象，也会优先使用 Comparator 来对插入的元素进行排序
        PriorityQueue<Person> priorityQueue = new PriorityQueue<>(comparator);
        priorityQueue.offer(new Person("zhang", 20));
        priorityQueue.offer(new Person("wang", 12));
        priorityQueue.offer(new Person("li", 48));
        priorityQueue.offer(new Person("zhao", 22));

        while (!priorityQueue.isEmpty()) {
            Person person = priorityQueue.poll();
            log.info("{}'s age is {}", person.name, person.age);
        }
    }
}
