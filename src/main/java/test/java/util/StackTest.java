package test.java.util;

import org.junit.Test;

import java.util.Stack;

/**
 * @author yanchao
 * @date 2020-07-20 12:05
 */
public class StackTest {

    /**
     * {@link Stack#push(Object)} 元素入栈
     */
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

    /**
     * {@link Stack#pop()} 元素出栈
     */
    @Test
    public void pop() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.isEmpty());
    }

    /**
     * {@link Stack#peek()} 从栈顶获取一个元素，但不移除，与 pop() 不一样
     */
    @Test
    public void peek() {
        Stack<Character> stack = new Stack<>();
        stack.push('s');
        System.out.println(stack.peek());
    }
}
