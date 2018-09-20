package algorithm.leetcode;

import java.util.Arrays;
import java.util.List;

/**
 * @author yanchao
 * @date 2018/8/26 20:12
 * 看 leetcode 第二题！！
 */
public class TwoListNodeSum {

    class ListNode {
        public int val;
        public ListNode next;
        public ListNode(int x) {
            val = x;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int sum = listNodeToNumber(l1) + listNodeToNumber(l2);
        return numberToListNode(sum);
    }

    private int listNodeToNumber(ListNode listNode) {
        int num = 0;
        int counter = 0;
        while (listNode != null) {
            num += listNode.val * Math.pow(10, counter);
            counter++;
            listNode = listNode.next;
        }
        return num;
    }

    private ListNode numberToListNode(int number) {
        ListNode listNode = new ListNode(number % 10);
        ListNode copy = listNode;
        while ((number = number / 10) != 0) {
            copy.next = new ListNode(number % 10);
            copy = copy.next;
        }
        return listNode;
    }

    public static void main(String[] args) {
        TwoListNodeSum object = new TwoListNodeSum();
        object.numberToListNode(1000000000);

        StringBuffer sb = new StringBuffer();
        sb.reverse();
        System.out.println("".equals(sb.toString()));
    }
}
