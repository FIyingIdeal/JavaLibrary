package algorithm.sort;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author yanchao
 * @date 2018/11/18 10:06
 */
public class SimpleInsertSort {

    private static int[] sortArray = {9,2,3,6,1,7,8,5,4};

    public static int[] originalArray() {
        int[] originalArrayCopy = new int[sortArray.length];
        System.arraycopy(sortArray, 0, originalArrayCopy, 0, sortArray.length);
        return originalArrayCopy;
    }

    public static void printArray(int[] array) {
        System.out.println(ArrayUtils.toString(array));
    }

    /**
     * 直接插入排序：默认将第一个元素看做是有序的，然后将后续元素依次与前边的有序列表元素进行比较，直到插入有序列表中依然形成一个有序列表。如下所示：
     * 原始列表为   3,5,2,6
     * 第一次3位有序，用5与3比较后，5>3依然有序： 3,5,2,6
     * 第二次3,5有序，用2与3,5比较后，序列变为：  2,3,5,6
     * 第三次，用6比较，6>5，且6位最终元素，最终为：2,3,5,6
     * 时间复杂度：最好 O(n)，最差 O(n^2)，平均 O(n^2)
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


    @Test
    public void shellSort() {
        int[] sortArray = originalArray();
        printArray(sortArray);
        int size = sortArray.length;
        int j;
        for (int step = size / 2; step > 0; step /= 2) {
            for (int i = step; i < size; i++) {
                int temp = sortArray[i];
                for (j = i - step; j >= 0 && sortArray[j] > temp; j -= step) {
                    sortArray[j + step] = sortArray[j];
                }
                sortArray[j + step] = temp;
            }
        }
        printArray(sortArray);
    }
}
