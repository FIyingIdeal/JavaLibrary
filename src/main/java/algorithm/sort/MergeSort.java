package algorithm.sort;

import org.junit.Test;

/**
 * @author yanchao
 * @date 2018/11/18 16:34
 * 归并排序：两个有序列表合并成一个有序列表
 */
public class MergeSort extends BaseSort {

    /**
     * 归并排序
     *
     * 时间复杂度：O(NlogN)
     */
    @Test
    public void mergeSort() {
        int[] sortedArray1 = {1,3,5,7,9};
        int[] sortedArray2 = {2,4,5,6,8};
        int length1 = sortedArray1.length, length2 = sortedArray2.length;
        int[] mergeArray = new int[length1 + length2];
        int index1 = 0, index2 = 0, index = 0;
        while (index1 < length1 && index2 < length2) {
            if (sortedArray1[index1] <= sortedArray2[index2]) {
                mergeArray[index++] = sortedArray1[index1++];
            } else {
                mergeArray[index++] = sortedArray2[index2++];
            }
        }
        while (index1 < length1) {
            mergeArray[index++] = sortedArray1[index1++];
        }
        while (index2 < length2) {
            mergeArray[index++] = sortedArray2[index2++];
        }
        printArray(mergeArray);
    }
}
