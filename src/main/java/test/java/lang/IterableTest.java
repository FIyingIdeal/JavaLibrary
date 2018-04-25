package test.java.lang;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Iterator;

public class IterableTest {

    @Test
    public void forEach() {
        int length = 10;
        IterableTestClass<String> test = new IterableTestClass<>(length);
        for (int i = 0; i < length; i++) {
            test.add(Integer.toString(i));
        }
        for (String s : test) {
            System.out.println(s);
        }
    }
}

class IterableTestClass<T> implements Iterable<T> {

    private T[] characters;
    private int i = 0;

    public IterableTestClass( int length) {
        // 创建泛型数组，不能使用new T[length];
        characters = (T[]) new Object[length];
    }

    public IterableTestClass(Class<T> clazz, int length) {
        // Array.newInstance(Class, int)用来创建一个数组对象，但这个方法的返回值依然是Object，需要进行强制类型转换。
        // 既然已经指定了元素类型，为什么还需要进行强制类型转换？？
        characters = (T[]) Array.newInstance(clazz, length);
    }

    public void add(T t) {
        characters[i] = t;
        i++;
    }

    @Override
    public Iterator<T> iterator() {

        return new Iterator<T>() {
            int j = 0;
            @Override
            public boolean hasNext() {
                return j < characters.length;
            }

            @Override
            public T next() {
                return characters[j++];
            }
        };
    }
}
