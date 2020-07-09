package structure.leetcode.linkedlist;

import lombok.Data;

/**
 * ListNode 的公共类
 * @author yanchao
 * @date 2020-06-25 16:24
 */
@Data
public class ListNode {

    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
    }

    /**
     * 将一个数组转换为链表
     * @param array 数组
     * @return      链表头，其值等于 array[0] 的值
     */
    public static ListNode getListNodeFromArray(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        ListNode head = null;
        ListNode current = null;
        int i = 0;
        while (i < array.length) {
            if (i == 0) {
                head = new ListNode(array[i]);
                current = head;
            } else {
                current.next = new ListNode(array[i]);
                current = current.next;
            }
            i++;
        }
        return head;
    }

    /**
     * 打印链表
     * @param listNode 链表头
     */
    public static void printNode(ListNode listNode) {
        ListNode iterator = listNode;
        while (iterator != null) {
            System.out.print(iterator.val + " -> ");
            iterator = iterator.next;
        }
        System.out.println();
    }

    /**
     * 打印链表，增加了前缀用于区分不同的链表，对同时打印多个链表时有用
     * @param prefix    前缀
     * @param listNode  链表头
     */
    public static void printNode(String prefix, ListNode listNode) {
        ListNode iterator = listNode;
        System.out.print(prefix + "   ");
        while (iterator != null) {
            System.out.print(iterator.val + " -> ");
            iterator = iterator.next;
        }
        System.out.println();
    }
}
