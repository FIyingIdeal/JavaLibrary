package ThinkingInJava.Chapter15Generic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanchao
 * @date 2018/10/23 10:25
 */
public class WarizedTest {

    @Test
    public void rawList() {
        List rawList = new ArrayList();
        rawList.add(11);
        rawList.add("123");
        rawList.forEach(item -> System.out.println(item.getClass()));
    }

    @Test
    public void unbound() {
        List<?> unboundList = new ArrayList<Integer>();
        unboundList.add(null);

        // unboundList.add(123); Error

        Object i = unboundList.get(0);
        System.out.println(i);
    }

    @Test
    public void bound() {
        List<Object> boundList = new ArrayList<>();
        boundList.add(123);
        boundList.add("456");
        boundList.forEach(System.out::println);
    }

    @Test
    public void bound1() {
        List<Integer> list = new ArrayList<>();
        list.add(123);
        list.add(456);
        List<? extends Number> numbers = list;
        numbers.forEach(num -> System.out.println(num.getClass()));
    }

    interface GenericGetter<T extends GenericGetter<T>> {
        T get();
    }

    interface Getter extends GenericGetter<Getter> {}

    class GenericsAndReturnTypes {
        void test(Getter getter) {
            Getter result = getter.get();
            GenericGetter gg = getter.get();
        }
    }

}
