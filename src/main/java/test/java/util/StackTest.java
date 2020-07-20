package test.java.util;

import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.junit.Test;

import java.util.Stack;

/**
 * @author yanchao
 * @date 2020-07-20 12:05
 */
public class StackTest {

    @Test
    public void push() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        // forEach 是顺序遍历，且遍历完以后不会移除任何元素
        stack.forEach(System.out::println);
        // 2
        System.out.println(stack.size());

        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    @Test
    public void peek() {
        Stack<Character> stack = new Stack<>();
        stack.push('s');
        System.out.println(stack.peek());
    }

    static {
        i = 0;
    }
    static int i = 1;
    public static void main(String[] args) {
        System.out.println(i);
    }
}
