package structure;

import utils.PrintUtil;

import java.util.Objects;
import java.util.Scanner;

/**
 * @author yanchao
 * @date 2018/11/1 14:01
 * 数据结构 -- 链表
 */
public class MyLinkedList {

    static class SingleListNode {
        String data;
        SingleListNode next;
        SingleListNode(String data) {
            this.data = data;
        }
    }

    /**
     * 创建链表
     * @return 链表头节点
     */
    public static SingleListNode createSingleList() {
        PrintUtil.print("请输入链表单节点，以逗号分隔（空节点将会自动被忽略）：");
        Scanner scanner = new Scanner(System.in);
        String nodes = scanner.nextLine();
        if (Objects.equals("", nodes.trim())) {
            return null;
        }
        String[] nodeArray = nodes.split(",");
        SingleListNode head = null, tail = null;
        for (int i = 0; i < nodeArray.length; i++) {
            SingleListNode next = new SingleListNode(nodeArray[i]);
            if (i == 0) {
                head = next;
                tail = head;
            } else {
                tail.next = next;
                tail = next;
            }
        }
        return head;
    }

    /**
     * 遍历链表，链表所有节点内容按序拼接成的字符串
     * @param singleList    链表头节点
     * @return              链表所有节点内容按序拼接成的字符串
     */
    public static String getSingleListString(SingleListNode singleList) {
        StringBuilder sb = new StringBuilder();
        SingleListNode temp = singleList;
        while (temp != null) {
            sb.append(temp.data);
            temp = temp.next;
        }
        return sb.toString();
    }

    /**
     * 反转链表（不太好的实现）
     * @param singleListNode    原始链表头节点
     * @return                  反转后链表头节点
     * @see this#reverseSingleList1(SingleListNode)
     */
    public static SingleListNode reverseSingleList(SingleListNode singleListNode) {
        if (singleListNode == null) {
            return null;
        }
        SingleListNode current = singleListNode;
        SingleListNode next = current.next;
        current.next = null;    // 断掉头节点的next指针，否则形成了一个循环链表了
        while (next != null) {
            SingleListNode anotherNext = next.next;
            next.next = current;
            current = next;
            next = anotherNext;
        }
        return current;
    }

    /**
     * 反转链表
     * @param singleListNode    原始链表头节点
     * @return                  反转后链表头节点
     * @see this#reverseSingleList(SingleListNode)
     */
    public static SingleListNode reverseSingleList1(SingleListNode singleListNode) {
        SingleListNode reversedHead = null;
        SingleListNode node = singleListNode;
        SingleListNode prev = null;
        while (node != null) {
            SingleListNode next = node.next;
            if (next == null) {
                reversedHead = node;
            }
            node.next = prev;
            prev = node;
            node = next;
        }
        return reversedHead;
    }

    /**
     * 从一个给定的链表中删除一个给定的节点（这个节点一定在这个链表里边）。
     * 最简单的方法是遍历链表，直到找到给定的节点删除掉，然后结束，但这个样子的时间复杂度为 O(n);
     * 更简单的方式是将这个被删节点（deleteNode）的后续节点（nextNode）的值复制到deleteNode中，然后修改deleteNode的next为nextNode的next
     *
     * *****************************************************************************************************************
     * * 特别注意：java里边的引用问题：被删除节点的引用必须是链表中某一节点的真实引用，而不是后期new出来的“一样模样”的其他对象引用~，否则删不掉 *
     * *****************************************************************************************************************
     *
     * 但有几种情况需要考虑：
     *      1.被删链表只有这一个节点；
     *      2.被删链表有多个节点，而被删节点刚好在链表尾部；
     *      3.被删链表有多个节点，而被删节点刚好在链表中部（头部与这种情况相同）；
     * @param singleList    链表头节点
     * @param deleteNode    被删节点
     * @return              被删除后链表头部节点
     */
    public static SingleListNode deleteOneNode(SingleListNode singleList, SingleListNode deleteNode) {
        // 如果被删列表或被删节点未指定的话，直接返回就好
        if (singleList == null || deleteNode == null) {
            return singleList;
        }

        // 如果链表只有一个节点，那么直接返回 null
        if (singleList.next == null && Objects.equals(singleList.data, deleteNode.data)) {
            singleList.data = null; // help GC
            singleList = null;
        } else if (deleteNode.next != null) {
            SingleListNode next = deleteNode.next;
            deleteNode.data = next.data;
            deleteNode.next = next.next;
            next.data = null;
            next.next = null;
        } else {
            // 被删的是多节点链表尾节点
            SingleListNode temp = singleList;
            while(temp != null) {
                if (Objects.equals(temp.next.data, deleteNode.data)) {
                    temp.next = null;
                    deleteNode.data = null;
                    break;
                }
                temp = temp.next;
            }
        }
        return singleList;
    }

    public static void main(String[] args) {
        SingleListNode node = createSingleList();
        String singleListString = getSingleListString(node);
        PrintUtil.println(singleListString);

        // 反转链表1
//        String badReverseSingleList = getSingleListString(reverseSingleList(node));
//        PrintUtil.println(badReverseSingleList);
        // 反转链表2 两个反转不要同时使用，否则会出现异常输出
//        String goodReverseSingleList = getSingleListString(reverseSingleList1(node));
//        PrintUtil.println(goodReverseSingleList);

        // 删除链表中指定的节点（O(1)）
        // START 获取链表中要被删除的节点
        PrintUtil.print("请输入被删节点在链表中的index（从0开始计算）：");
        int deleteNodeIndex = new Scanner(System.in).nextInt();
        SingleListNode deleteNode = node;
        for (int i = 0; i < deleteNodeIndex; i++) {
            deleteNode = deleteNode.next;
        }
        // END 获取链表中要被删除的节点
        String afterDeleteSingleList = getSingleListString(deleteOneNode(node, deleteNode));
        PrintUtil.println(afterDeleteSingleList);
    }
}
