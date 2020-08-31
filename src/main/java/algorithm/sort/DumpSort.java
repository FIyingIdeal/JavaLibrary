package algorithm.sort;

/**
 * 堆排序
 * @author yanchao
 * @date 2020-08-30 11:57
 */
public class DumpSort extends BaseSort {


    public static void dumpSort(int[] a) {
        if (a == null || a.length == 0) {
            return;
        }

        int hasChildIndex = (a.length - 1) / 2;
        while (hasChildIndex >= 0) {
            dumpSortBuildHeap(a, hasChildIndex, a.length);
            hasChildIndex--;
        }

        dumpSortRemove(a);

    }

    public static void dumpSortBuildHeap(int[] a, int hole, int length) {

        int temp = a[hole];
        while (hole * 2 + 1 < length) {
            int child = hole * 2 + 1;
            if (child != length - 1 && a[child + 1] > a[child]) {
                child++;
            }
            // 必须是 > temp，不能是 > a[hole]，因为 hole 是一直变化的
            if (a[child] > temp) {
                a[hole] = a[child];
                hole = child;
            } else {
                // 不满足直接退出，下边满足条件是通过 dumpSort(int[] a) 方法保证的
                break;
            }

        }
        a[hole] = temp;
    }

    public static void dumpSortRemove(int[] a) {
        int length = a.length;
        // 等于 0 不用处理
        while (length > 0) {
            int temp = a[length-1];
            a[length - 1] = a[0];
            a[0] = temp;
            length--;
            dumpSortBuildHeap(a, 0, length);
        }
    }


    public static void main(String[] args) {
        int[] a = new int[] {1,7,2,9,4,0};
        printArray(a);
        dumpSort(a);
        printArray(a);
    }

}
