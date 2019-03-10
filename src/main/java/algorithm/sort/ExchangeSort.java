package algorithm.sort;

import org.junit.Test;

/**
 * @author yanchao
 * @date 2018/11/18 15:26
 * 交换排序
 */
public class ExchangeSort extends BaseSort {


    /**
     * 冒泡排序：每次比较相邻两个元素的大小，如果前一个数大于后一个数的话交换这两个元素的位置，否则不交换继续比较后续的元素。
     *
     * 时间复杂度：O(n^2)
     *
     * 稳定排序
     */
    @Test
    public void bubbleSort() {
        int[] sortArray = originalArray();
        printArray(sortArray);
        int size = sortArray.length;
        int temp;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (sortArray[j] > sortArray[j + 1]) {
                    temp = sortArray[j];
                    sortArray[j] = sortArray[j + 1];
                    sortArray[j + 1] = temp;
                }
            }
        }
        printArray(sortArray);
    }

    /**
     * 快速排序
     *
     * 时间复杂度：最优O(NlogN)，最差O(N^2)，平均O(NlogN)
     */
    @Test
    public void quickSort() {
        int[] sortArray = originalArray();
        printArray(sortArray);
        int size = sortArray.length;
        quickSort(sortArray, 0, size - 1);
        printArray(sortArray);
    }

    public void quickSort(int[] sortArray, int low, int high) {
        if (low >= high) {
            return;
        }
        int midIndex = divide(sortArray, low, high);
        quickSort(sortArray, low, midIndex - 1);
        quickSort(sortArray, midIndex + 1, high);
    }

    public int divide(int[] sortArray, int low, int high) {
        int temp = sortArray[low];
        do {
            while (sortArray[high] >= temp && low < high) {
                high--;
            }
            if (low < high) {
                sortArray[low] = sortArray[high];
                low++;
            }
            while (sortArray[low] <= temp && low < high) {
                low++;
            }
            if (low < high) {
                sortArray[high] = sortArray[low];
                high--;
            }
        } while (low != high);
        sortArray[low] = temp;
        return low;
    }
}




















