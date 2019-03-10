package algorithm;

import java.util.Stack;

/**
 * @author yanchao
 * @date 2018/12/5 20:33
 */
public class StackPushAndPopTwoArray {
    public static boolean IsPopOrder(int[] pushA, int[] popA) {
        int pushIndex = 0, popIndex = 0;
        Stack<Integer> stack = new Stack<>();
        while (popIndex < popA.length) {
            if (!stack.isEmpty() && stack.peek() == popA[popIndex]) {
                stack.pop();
                popIndex++;
                continue;
            }
            while (pushIndex < pushA.length && pushA[pushIndex] != popA[popIndex]) {
                stack.push(pushA[pushIndex++]);
            }
            // 因为 pushIndex == pushA.length 退出循环
            if (pushIndex == pushA.length) {
                if (pushA[pushIndex - 1] != popA[popIndex]) {
                    return false;
                } else {
                    popIndex++;
                    continue;
                }
            }
            // 因为 pushA[pushIndex] == popA[popIndex] 退出循环
            else if (pushA[pushIndex] == popA[popIndex]) {
                popIndex++;
                pushIndex++;
                continue;
            }
        }
        //if (popIndex == popA.length)
        return true;
    }

    public static void main(String[] args) {
        int[] a = {1,2,3,4,5};
        int[] b = {4,5,3,2,1};
        System.out.println(IsPopOrder(a, b));
    }
}
