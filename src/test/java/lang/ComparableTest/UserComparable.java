package test.java.lang.ComparableTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 * List元素排序：java中对List进行排序有两种方式：
 *     1. Collections.sort(List<T>) : T必须实现Comparable接口(java.lang.Comparable)并重写了compare()方法;
 *     2. Collections.sort(List<T>, Comparator> : 自己提供一个Comparator接口(java.util.Comparator)的实现，并重写compare()方法
 *
 * 该测试使用Comparable进行了两个字段的排序，先以username正序排序，如果username相同，按age正序排序。
 */
public class UserComparable implements Comparable {

    private String username;
    private int age;

    public UserComparable() {}

    public UserComparable(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserComparable{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        UserComparable user = (UserComparable)o;
        //只根据username排序
        //return this.username.compareTo(user.username);

        //先根据username正序排，然后根据age正序排
        int compResult = this.username.compareTo(user.username);
        if (compResult == 0) {
            return this.age > user.age ? 1 : this.age == user.age ? 0 : -1;
        }
        return compResult;
    }

    public static void main(String[] args) {
        List<UserComparable> userList = new ArrayList<>();
        UserComparable user5 = new UserComparable("c", 29);
        UserComparable user1 = new UserComparable("aaa", 23);
        UserComparable user2 = new UserComparable("aaa", 21);
        UserComparable user3 = new UserComparable("aaa", 24);
        UserComparable user4 = new UserComparable("bbb", 28);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        userList.forEach(user -> System.out.println(user));
        Collections.sort(userList);
        System.out.println("--------------After sort---------------");
        userList.forEach(user -> System.out.println(user));
    }
}
