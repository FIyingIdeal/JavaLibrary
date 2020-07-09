package structure.leetcode.linkedlist;

/**
 * @author yanchao
 * @date 2020-06-25 16:23
 */
public class Part92 {

    public ListNode reverseBetween(ListNode head, int m, int n) {
        int i = 1;
        ListNode current = head;
        // mPre 记录 m-1 节点，用于修改其next指针为 n
        // 若 m == 1 则，其前置节点应该不存在，则不需要修改 mPre.next
        ListNode mPre = null;

        while (i <= m - 1) {
            if (i == m - 1) {
                mPre = current;
            }
            current = current.next;
            i++;
        }
        // i == m
        ListNode revertedHead = reverseListNode(current, n - m + 1);

        // m != 1
        if (mPre != null) {
            mPre.next = revertedHead;
            return head;
        } else {
            return revertedHead;
        }

    }

    /**
     * 反转链表的前 n 个元素
     * @param head  原始链表的第 m 个位置的节点
     * @param n     反转链表的个数
     * @return      反转后的链表头
     */
    private ListNode reverseListNode(ListNode head, int n) {
        ListNode.printNode(head);
        if (n == 1) {
            return head;
        }
        int i = 1;
        ListNode pPre = null;
        ListNode newHead = null;
        ListNode current = head;
        while (i <= n) {
            ListNode pNext = current.next;
            if (i == n) {
                newHead = current;
                head.next = pNext;
            }
            current.next = pPre;
            pPre = current;
            current = pNext;
            i++;
        }
        ListNode.printNode(newHead);
        return newHead;
    }

    public static void main(String[] args) {
        Part92 part92 = new Part92();
        ListNode listNode = ListNode.getListNodeFromArray(new int[]{1,2,3});
        ListNode.printNode(listNode);
        ListNode result = part92.reverseBetween(listNode, 1, 1);
        ListNode.printNode(result);
    }
}
