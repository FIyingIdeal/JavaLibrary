package structure.leetcode.linkedlist;

/**
 * @author yanchao
 * @date 2020-06-27 13:32
 */
public class Part25 {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k <= 1) {
            return head;
        }
        ListNode current = head;
        ListNode groupHead = head;
        ListNode nextGroupHead = null;
        ListNode newHead = null;
        ListNode groupTail = null;

        int i = 1;
        int j = 1;
        while (i <= k && current != null) {
            if (i == k) {
                nextGroupHead = current.next;
                ListNode newGroupHead = reverseKNode(groupHead, k);
                ListNode.printNode("After reverseKNode : ", newGroupHead);
                if (j == 1) {
                    newHead = newGroupHead;
                } else {
                    groupTail.next = newGroupHead;
                }
                groupTail = groupHead;
                groupHead = nextGroupHead;
                current = nextGroupHead;
                j++;
                i = 1;
            } else {
                current = current.next;
                i++;
            }
        }
        if (newHead == null) {
            newHead = head;
        }

        return newHead;
    }

    private ListNode reverseKNode(ListNode head, int k) {
        if (head == null || k <= 1) {
            return head;
        }
        int i = 1;
        ListNode current = head;
        ListNode pre = null;
        ListNode newHead = null;
        while (i <= k) {
            ListNode temp = current.next;
            if (i == k) {
                newHead = current;
            }
            i++;
            current.next = pre;
            pre = current;
            current = temp;
        }
        // 让原始的头节点（现在的尾节点指向 k 的后继）
        head.next = current;
        return newHead;
    }

    public static void main(String[] args) {
        ListNode listNode = ListNode.getListNodeFromArray(new int[] {1,2,3,4});
        ListNode.printNode(listNode);
        ListNode newHead = new Part25().reverseKGroup(listNode, 2);
        ListNode.printNode(newHead);
    }
}
