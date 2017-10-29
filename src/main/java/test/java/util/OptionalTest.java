package test.java.util;

import org.junit.Test;

import java.util.*;

/**
 * Created by Administrator on 2017/1/23.
 */
public class OptionalTest {

    public static void main(String[] args) {
        Optional<User> user = Optional.of(new User("test", "F"));
        user.ifPresent(System.out :: println);

        Optional<User> user1 = Optional.ofNullable(null);
        User user2 = user1.orElseGet(() -> getUser());
        System.out.println(user2);
    }

    public static User getUser() {
        return new User("newUser", "F");
    }

    @Test
    public void optionalMapTest() {
        User user = getUser();
        Optional<User> optional = Optional.of(user);
        optional.map(u -> u.getUsername())
                .map(name -> name.toUpperCase())
                .ifPresent(System.out::println);
    }

    @Test
    public void lambdaTest() {
        List<Integer> list = Arrays.asList(1, 4, 6, 2, 5, 9, 34, 21);
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        Collections.sort(names, (String a, String b) -> b.compareTo(a));
        Collections.sort(list, (a, b) -> a-b);
        System.out.println(names);
        System.out.println(list);
    }

    @Test
    public void mapTest() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        map.forEach((k, v) -> System.out.println(k + "," + v));
    }
}

class User {
    private String username;
    private String sex;

    public User() {
    }

    public User(String username, String sex) {
        this.username = username;
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
