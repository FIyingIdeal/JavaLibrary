package structure.leetcode.linkedlist;

/**
 * @author yanchao
 * @date 2020-06-27 18:20
 */
public class Part24 {

    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pairHead = head;
        ListNode newPairTail = null;
        ListNode nextPairHead = null;
        ListNode newHead = null;

        while (pairHead != null && pairHead.next != null) {
            nextPairHead = pairHead.next.next;
            pairHead.next.next = pairHead;
            if (newHead == null) {
                newHead = pairHead.next;
            }
            if (newPairTail == null) {
                newPairTail = head;
            } else {
                newPairTail.next = pairHead.next;
            }
            pairHead = nextPairHead;
        }
        return newHead;
    }

    public static void main(String[] args) {
        ListNode head = ListNode.getListNodeFromArray(new int[] {2,5,3,4,6,2,2});
        ListNode.printNode(head);
        ListNode newHead = new Part24().swapPairs(head);
        ListNode.printNode(newHead);
    }
}
