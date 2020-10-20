package algorithm.leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 从超过十个数字中找出最小的n个数 -- 堆排序
 * @author yanchao
 * @date 2020-08-30 18:12
 */
public class MinTenNumber {

    private static final int find = 5;
    private int[] a = new int[find];
    private int size = 0;

    public boolean add(int num) {
        int i = size;
        siftUp(i, num);
        this.size++;
        return true;
    }

    private void siftUp(int index, int num) {

        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (a[parentIndex] > num) {
                break;
            }
            a[index] = a[parentIndex];
            index = parentIndex;
        }
        a[index] = num;
    }

    public int poll() {
        int num = a[0];
        siftDown(0, a[size - 1]);
        size--;
        return num;
    }

    public int peek() {
        if (size == 0) {
            throw new IllegalStateException("没有元素");
        }
        return a[0];
    }

    private void siftDown(int index, int num) {
        while (index * 2 + 1 < size) {
            int childIndex = index * 2 + 1;
            if (childIndex != size - 1 && a[childIndex + 1] > a[childIndex]) {
                childIndex++;
            }
            if (a[childIndex] > num) {
                a[index] = a[childIndex];
                index = childIndex;
            } else {
                break;
            }
        }
        a[index] = num;
    }



    public static void priorityMethod(int[] a) {
        Comparator<Integer> comparator = (i1, i2) -> i2 - i1;
        PriorityQueue<Integer> queue = new PriorityQueue<>(comparator);

        for (int i = 0; i < find; i++) {
            queue.offer(a[i]);
        }

        for (int i = find; i < a.length; i++) {
            if (a[i] >= queue.peek()) {
                continue;
            }
            queue.poll();
            queue.offer(a[i]);
        }

        int[] a1 = new int[find];
        for (int i = find - 1; i >= 0; i--) {
            a1[i] = queue.poll();
        }
        Arrays.stream(a1).forEach(System.out::print);
    }

    public static void main(String[] args) {

        int[] a = new int[]{6,2,1,4,5,6,7,8,10,33,66,2};
        /*
        // 找前5个最大的数

        MinTenNumber minTenNumber = new MinTenNumber();
        // 先插入 5 个数
        for (int i = 0; i < find; i++) {
            minTenNumber.add(a[i]);
        }
        Arrays.stream(minTenNumber.a).forEach(num -> System.out.print(num + ", "));
        for (int i = find; i < a.length; i++) {
            if (a[i] >= minTenNumber.peek()) {
                System.out.println("skip " + a[i]);
                continue;
            }
            int pollNumber = minTenNumber.poll();
            System.out.println("===== pollNumber is " + pollNumber);
            minTenNumber.add(a[i]);
        }

        Arrays.stream(minTenNumber.a).forEach(num -> System.out.print(num + ", "));
        */

        priorityMethod(a);
    }
}
