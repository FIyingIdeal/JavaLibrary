package algorithm.sort;

import org.junit.Test;

/**
 * @author yanchao
 * @date 2018/11/18 10:06
 * 插入排序
 */
public class InsertSort extends BaseSort {

    /**
     * 直接插入排序：默认将第一个元素看做是有序的，然后将后续元素依次与前边的有序列表元素进行比较，直到插入有序列表中依然形成一个有序列表。如下所示：
     * 原始列表为   3,5,2,6
     * 第一次3位有序，用5与3比较后，5>3依然有序： 3,5,2,6
     * 第二次3,5有序，用2与3,5比较后，序列变为：  2,3,5,6
     * 第三次，用6比较，6>5，且6位最终元素，最终为：2,3,5,6
     *
     * 时间复杂度：最好 O(n)，最差 O(n^2)，平均 O(n^2)
     *
     * 稳定排序
     */
    @Test
    public void simpleInsertSort() {
        int[] sortArray = originalArray();
        int j;
        for (int i = 1; i < sortArray.length; ++i) {
            int temp = sortArray[i];
            for (j = i - 1; j >= 0 && sortArray[j] > temp; j--) {
                sortArray[j + 1] = sortArray[j];
            }
            sortArray[j + 1] = temp;
        }
        printArray(sortArray);
    }

    /**
     * 希尔排序：直接插入排序的一种优化。指定步长，将指定步长的所有元素进行排序，这样的话绝大多数元素有序，最后进行一次步长为1的排序，
     * 因为之前的步长排序中，大多数元素有序，所以在步长为1的排序是，比较和移动的次数相对较少
     *
     * 时间复杂度不确定，因为选定的步长是不确定的。
     *
     * 不稳定排序
     */
    @Test
    public void shellSort() {
        int[] sortArray = originalArray();
        printArray(sortArray);
        int size = sortArray.length;
        int j;
        for (int step = size / 2; step > 0; step /= 2) {
            for (int i = step; i < size; i++) {         // 对相距step的元素序列采用直接插入排序
                int temp = sortArray[i];
                for (j = i - step; j >= 0 && sortArray[j] > temp; j -= step) {
                    sortArray[j + step] = sortArray[j];
                }
                sortArray[j + step] = temp;
            }
        }
        printArray(sortArray);
    }

    /**
     * 二分插入排序：在查找元素时使用二分法查找
     *
     * 时间复杂度：O(n^2)
     */
}
