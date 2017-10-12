package streamtest;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2016/1/12.
 */
public class StreamTest {

    //int[] to Stream
    public IntStream getIntStream() {
        return IntStream.of(new int[] {1,1,2,3,4,5,6});
    }

    //String[] to Stream
    public Stream<String> getStringStream() {
        String[] language = new String[]{"Java", "C", "C++", "Perl", "Python"};
        return Stream.of(language);
    }

    public void printStream() {
        getIntStream().forEach(System.out::print);
        Tool.newLine();
    }

    @Test
    public void sort() {
        Tool.println("sort : ");
        //sort by default rule
        getStringStream().sorted().forEach(System.out::print);
        Tool.newLine();
        //sort by our own rule(Comparator by Lambda)
        getStringStream().sorted((a,b) -> {return b.compareTo(a);}).forEach(System.out::print);
        Tool.newLine();
    }

    @Test
    public void distinct() {
        Tool.println("distinct : ");
        getIntStream().distinct().forEach(System.out::print);
        Tool.newLine();
    }

    @Test
    public void skip() {
        Tool.println("skip : ");
        getIntStream().skip(2).forEach(System.out::print);
        Tool.newLine();
    }

    @Test
    public void map() {
        Tool.println("map : ");
        //List to stream
        List<String> worldList = new ArrayList<String>();
        worldList.add("hello world");
        worldList.add("java");
        worldList.add("c");
        //worldList.stream().map(String::toUpperCase).forEach(System.out::print);
        List<String> upperCaseWorldList = worldList.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(upperCaseWorldList);
        Tool.newLine();
        getIntStream().map(n -> n * n).forEach(System.out::print);
        Tool.newLine();
    }

    //flatMap 把 input Stream 中的层级结构扁平化，就是将最底层元素抽出来放到一起，最终 output 的新 Stream 里面已经没有 List 了，都是直接的数字。
    @Test
    public void flatMap() {
        Tool.println("flatMap : ");
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1,2),
                Arrays.asList(2,3,4),
                Arrays.asList(5,6,7)
        );
        Stream<Integer> outputStream = inputStream.flatMap(child -> child.stream());
        outputStream.forEach(System.out::print);
        //报错：(foreach是一个terminal操作)对stream执行完terminal操作后Stream的元素就被“消耗”掉了，无法对一个stream执行两次terminal操作
        //outputStream.forEach(System.out::print);
        Tool.newLine();
    }

    @Test
    public void filter() {
        Tool.println("filter : ");
        getIntStream().filter(n -> n % 2 == 0).forEach(System.out::print);
        Tool.newLine();

        Integer[] array = {1,23,3,4,5,6};
        Integer[] event = Stream.of(array).filter(n -> n%2==0).toArray(Integer[]::new);
        Stream.of(array).forEach(item -> System.out.print(item + " "));
        Tool.newLine();
        Stream.of(event).forEach(System.out::print);
    }

    @Test
    public void reduce() {
        Tool.println("reduce : ");
        String concat = Stream.of("a","b","c","d").reduce("", String::concat);
        Tool.println("concat => " + concat);
        double min = Stream.of(1.0, 2.3, -1.9, -9.0).reduce(Double.MAX_VALUE, Double::min);
        //double min1 = Stream.of(1.0, 2.3, -1.9, -9.0).reduce(Double::min).get();
        Tool.println("min => " + min);
        //有初始值，可确定返回类型
        int sum = Stream.of(1,2,3,4,5).reduce(0, Integer::sum);
        Tool.println("sum => " + sum);
        //无初始值，返回类型为Optional,需要通过get()来获取
        int sum1 = Stream.of(3,4,5,6,7).reduce(Integer::sum).get();
        Tool.println("sum1 => " + sum1);
        //filter and sum
        int sum2 = Stream.of(1,3,6,4,3,6,8,5).filter(n -> n > 3).reduce(0, Integer::sum);
        Tool.println("sum2 => " + sum2);
    }

    class Person {
        public int num;
        public String name;
        public Person(){}
        public Person(int num, String name) {
            Tool.println("Create Person" + num);
            this.num = num;
            this.name = name;
        }
        public String getName() {
            //Tool.println(this.name);
            return this.name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getNum() {
            return this.num;
        }
        public void setNum(int num) {
            this.num = num;
        }
    }

    @Test
    public void short_circuiting() {
        Tool.println("short_circuiting : ");
        Person[] persons = new Person[10];
        for (int i = 0; i < 10; i++) {
            persons[i] = new Person(i, "name" + i);
        }
        Tool.newLine();
        //由于使用了limit这个short-circuiting操作，在p.getName()的时候只执行5次，而不是有多少个Person就执行多少次，
        List<String> nameList = Stream.of(persons).map(p -> p.getName()).limit(5).skip(2).collect(Collectors.toList());
        Tool.println(nameList);

        //在使用了sorted操作以后，limit/skip操作无法达到short-circuiting的目的
        List<Person> personList = Stream.of(persons).sorted((p1, p2) -> {
            return p1.getName().compareTo(p2.getName());
        }).limit(2).collect(Collectors.toList());
        personList.forEach(p -> Tool.println("    " + p.getName()));
    }

    @Test
    public void IOToStream() {
        Tool.println("IOToStream : 文件最长一行的长度 ");
        try {
            BufferedReader br = new BufferedReader(new FileReader("F:/test.txt"));
            //int longest = br.lines().map(String::length).reduce(Integer::max).get();
            int longest1 = br.lines().mapToInt(String::length).max().getAsInt();
            //Tool.println(longest);
            Tool.println(longest1);
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void distinctAndSort() {
        Tool.println("distinctAndSort : 全文不同的字符串并排序");
        try {
            BufferedReader br = new BufferedReader(new FileReader("F:/test.txt"));
            List<String> wordList = br.lines().flatMap(line -> Stream.of(line.split(" "))).filter(str -> str.length() > 0).distinct().sorted().collect(Collectors.toList());
            Tool.println(wordList);
            br.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void match() {
        Tool.println("match : ");
        Tool.println("allMatch(全部匹配返回true) => " + getIntStream().allMatch(n -> n > 5));
        Tool.println("anyMatch(有一个匹配返回true) => " + getIntStream().anyMatch(n -> n > 5));
        Tool.println("noneMatch(全部不匹配返回true) => " + getIntStream().noneMatch(n -> n > 7));
    }

    @Test
    public void iterate() {
        Tool.println("Stream.iterate() : ");
        Stream.iterate(0, n -> n + 3).limit(10).forEach(n -> Tool.print(n + " "));
        Tool.newLine();
    }

    @Test
    public void CollectorsGroupingBy() {
        Tool.println("CollectorsGroupingBy : ");
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            persons.add(new Person(i, "name" + i));
        }
        persons.get(2).setName("name1");
        persons.get(3).setName("name1");
        Map<String, List<Person>> personMap = persons.stream().collect(Collectors.groupingBy(Person::getName));
        personMap.forEach((k, v) -> {
            Tool.printInNewLine(k, "=");
            v.stream().map(Person::getName).forEach(name -> Tool.print(name, " "));
        });

        Tool.newLine();
        Map<String, Long> personGroupingCount = persons.stream().collect(Collectors.groupingBy(Person::getName, Collectors.counting()));
        personGroupingCount.forEach((k, v) -> {
            System.out.println(k + " = " + v);
        });
    }

    @Test
    public void compare() {
        Tool.println("Compare : 多条件组合排序");
        List<Person> persons = new ArrayList<Person>();
        persons.add(new Person(1, "name1"));
        persons.add(new Person(2, "name3"));
        persons.add(new Person(5, "name2"));
        persons.add(new Person(3, "name2"));
        persons.sort(Comparator.comparing(Person::getName).thenComparing(Person::getNum));
        persons.forEach(person -> Tool.println(person.getName() + ", " + person.getNum()));
    }
}
