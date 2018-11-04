package test.java.util;

import com.sun.org.apache.xpath.internal.operations.NotEquals;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author yanchao
 * @date 2017-12-22 10:30:40
 */
public class OptionalTest {

    private static final Logger logger = LoggerFactory.getLogger(OptionalTest.class);

    public User getUser() {
        return new User("newUser", "F");
    }

    /**
     * of(T) 方法是为非null值创建一个Optional，如果传入为null，则抛出NullPointerException
     */
    @Test
    public void of() {
        User user = getUser();
        Optional<User> userOptional = Optional.of(user);
        logger.info("userInfo : {}", userOptional.orElse(null));
        // 抛出NullPointerException
        // Optional<User> nullOptional = Optional.of(null);
    }

    /**
     * ofNullable(T) 是可以接受一个null作为参数的，不会抛出异常
     * 当不确定输入的参数是否为null的时候可以使用这个方法
     */
    @Test
    public void ofNullable() {
        Optional<User> nullOptional = Optional.ofNullable(null);
        logger.info("user : {}", nullOptional.orElse(null));
    }

    /**
     * isPresent() 方法用来判断Optional中的对象是否为null，如果为null则表示不存在，返回false，否则返回true
     * 该方法可以配合下边的get()一起使用，但由于依然要写一堆代码，所以一般不推荐这么用
     */
    @Test
    public void isPresent() {
        Optional<User> nullOptional = Optional.ofNullable(null);
        Optional<User> userOptional = Optional.of(getUser());
        logger.info("nullOptional isPresent : {}", nullOptional.isPresent());   //false
        logger.info("userOptional isPresent : {}", userOptional.isPresent());   //true
    }

    /**
     * get()方法用来获取Optional中的值，一般需要配合isPresent()来使用，否则在Optional中的值为null时会抛出NoSuchElementException异常
     * 但由于代码量的问题不推荐这么使用，可以使用orElse()来代替
     */
    @Test
    public void get() {
        Optional<User> userOptional = Optional.of(getUser());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            logger.info("userInfo : {}", user);
        }

        Optional<User> nullOptional = Optional.ofNullable(null);
        try {
            User nullUser = nullOptional.get();
            //上边语句会抛出异常，所以会执行catch中的语句
            logger.info("userInfo : {}", nullUser);
        } catch (NoSuchElementException e) {
            logger.warn("user not exist!!!");
        }
    }

    /**
     * ifPresent(Consumer)：如果Optional实例有值的话则调用Consumer，否则不做处理
     */
    @Test
    public void ifPresent() {
        Optional<User> userOptional = Optional.of(getUser());
        Optional<User> nullOptional = Optional.ofNullable(null);

        userOptional.ifPresent(user -> logger.info("userInfo : {}", user));
        // 由于是null，Consumer不会被执行，所以不会输出
        nullOptional.ifPresent(user -> logger.info("userInfo : {}", user));
    }

    /**
     * orElse(T)：如果Optional中有值就将其返回，否则就返回参数中指定的其他值
     */
    @Test
    public void orElse() {
        Optional<User> nullOptional = Optional.ofNullable(null);
        Optional<User> userOptional = Optional.of(getUser());
        logger.info("nullOptional orElse : {}", nullOptional.orElse(new User("Unknown", "Unknown")));
        logger.info("nullOptional orElse : {}", userOptional.orElse(new User("Unknown", "Unknown")));
    }

    /**
     * orElseGet(Supplier)：同orElse(T)方法类似，不同之处是参数类型：
     *      orElse(T)是直接指定返回值
     *      orElseGet(Supplier)是通过Supplier生成返回值
     */
    @Test
    public void orElseGet() {
        Optional<User> nullOptional = Optional.ofNullable(null);
        logger.info("nullOptional orElseGet : {}", nullOptional.orElseGet(User::new));
    }

    /**
     * orElseThrow(Supplier)：同orElseGet(Supplier)类似，不同之处是为空时的处理方式：
     *      orElseGet(Supplier)是通过Supplier生成返回值并返回
     *      orElseThrow(Supplier)是通过Supplier构造一个异常对象，抛出该异常
     */
    @Test
    public void orElseThrow() {
        Optional<User> nullOptional = Optional.ofNullable(null);
        try {
            nullOptional.orElseThrow(NullPointerException::new);
        } catch (NullPointerException e) {
            logger.warn("user not exist!!!");
        }
    }

    /**
     * map(Function)：如果有值，则对其执行调用mapping函数得到返回值。
     * 如果返回值不为null，则创建包含mapping返回值的Optional作为map方法返回值，否则返回空Optional
     */
    @Test
    public void map() {
        User user = getUser();
        Optional<User> optional = Optional.of(user);
        optional.map(User::getUsername)
                .map(name -> name.toUpperCase())
                .ifPresent(System.out::println);
    }

    /**
     * 如果有值，为其执行mapping函数返回Optional类型返回值，否则返回空Optional。
     * flatMap与map（Funtion）方法类似，区别在于flatMap中的mapper返回值必须是Optional。
     * 调用结束时，flatMap不会对结果用Optional封装。
     */
    @Test
    public void flatMap() {
        User user = getUser();
        Optional<User> optional = Optional.of(user);
        optional.flatMap(u -> Optional.of(u.getUsername()))
                .flatMap(name -> Optional.of(name.toUpperCase()))
                .ifPresent(nameOptional -> System.out.println(nameOptional));
    }

    /**
     * filter(Predicate)：如果有值并且满足断言条件返回包含该值的Optional，否则返回空Optional
     */
    @Test
    public void filter() {
        Optional<User> userOptional = Optional.of(getUser());
        try {
            userOptional.filter(user -> Objects.equals(user.getSex(), "M"))
                    .orElseThrow(Exception::new);
        } catch (Exception e) {
            logger.info("The user is not a male!");
        }
    }

    static class User {
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
}
