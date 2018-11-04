package ThinkingInJava.Chapter15Generic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanchao
 * @date 2018/10/25 14:29
 */
public class GenericCase {

    class FixedSizeStack<T> {
        private int index = 0;
        private List<T> storage;

        public FixedSizeStack(int size) {
            storage = new ArrayList<>(size);
        }

        public void push(T item) {
            storage.add(item);
            index++;
        }

        public T pop() {
            return storage.get(--index);
        }
    }

    public static final int SIZE = 10;

    public static void main(String[] args) {
        FixedSizeStack<String> fixedSizeStack = new GenericCase().new FixedSizeStack<>(10);
        for (String s : "ABCDEFGHIJ".split("")) {
            fixedSizeStack.push(s);
        }
        for (int i = 0 ; i < SIZE; i++) {
            String s = fixedSizeStack.pop();
            System.out.println(s);
        }
    }

}
