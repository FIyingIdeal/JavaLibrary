package structure.leetcode.linkedlist;

import static structure.leetcode.linkedlist.ListNode.getListNodeFromArray;
import static structure.leetcode.linkedlist.ListNode.printNode;

/**
 * @author yanchao
 * @date 2020-06-24 10:51
 */
public class Part83 {

    /*public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }
        ListNode newHead = null;
        ListNode current = head;
        ListNode newCurrent = null;
        ListNode node;
        while ((node = findNewHead(current)) != null) {
            System.out.println("====== node is ======" + node);
            if (newHead == null) {
                newHead = node;
                newCurrent = node;
            } else {
                newCurrent.next = node;
                newCurrent = newCurrent.next;
            }
            current = node.next;
            newCurrent.next = null;
        }
        return newHead;
    }

    private ListNode findNewHead(ListNode head) {
        if (head == null || head.next == null || head.next.val != head.val) {
            return head;
        }
        if (head.next.next == null) {
            return null;
        }

        ListNode current = head;
        while (current != null && current.next != null) {
            if (current == head && current.val != current.next.val) {
                return current;
            } else if (current.next.next == null && current.val != current.next.val) {
                return current.next;
            } else if (current.val != current.next.val && current.next.val != current.next.next.val) {
                return current.next;
            }
            current = current.next;
        }
        return null;
    }*/

    public ListNode deleteDuplicates(ListNode head) {
        ListNode newHead = new ListNode(-1);
        ListNode last = newHead;
        ListNode left = head;
        ListNode right = head == null ? null : head.next;
        while (right != null) {
            if (left.val == right.val) {
                while (right != null && left.val == right.val) {
                    right = right.next;
                }
                if (right != null) {
                    left = right;
                    right = right.next;
                    if (right == null) {
                        last.next = left;
                    }
                }
            } else {
                last.next = left;
                last = last.next;
                left = left.next;
                last.next = null;
                right = right.next;
                if (right == null) {
                    last.next = left;
                }
            }
        }
        return newHead.next;
    }

    public static void main(String[] args) {
        Part83 test = new Part83();
        ListNode head = getListNodeFromArray(new int[] {1,1,1,2,2});
        printNode(head);
        ListNode listNode = test.deleteDuplicates(head);
        printNode(listNode);
    }
}
