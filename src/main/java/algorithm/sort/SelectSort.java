package algorithm.sort;

import org.junit.Test;

/**
 * @author yanchao
 * @date 2018/11/18 12:01
 * 选择排序
 */
public class SelectSort extends BaseSort {

    /**
     * 直接选择排序
     *
     * 时间复杂度：O(n^2) = (n - 1) + (n - 2) + ... + 2 + 1
     *
     * 不稳定排序
     */
    @Test
    public void simpleSelectSort() {
        int[] sortArray = originalArray();
        int size = sortArray.length;
        printArray(sortArray);
        // 最有一个元素不用比较，所以条件是 i < size - 1
        for (int i = 0; i < size - 1; i++) {
            int k = i;

            // 查找最小的元素
            for (int j = i + 1; j < size; j++) {
                if (sortArray[k] > sortArray[j]) {
                    k = j;
                }
            }

            // 不引入第三个变量交换元素，前提是 k != i
            if (k != i) {
                sortArray[k] = sortArray[k] + sortArray[i];
                sortArray[i] = sortArray[k] - sortArray[i];
                sortArray[k] = sortArray[k] - sortArray[i];
            }
        }
        printArray(sortArray);
    }
}
