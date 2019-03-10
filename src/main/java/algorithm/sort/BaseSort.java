package algorithm.sort;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author yanchao
 * @date 2018/11/18 12:02
 */
abstract class BaseSort {

    private static int[] sortArray = {9,2,3,6,1,7,8,5,4};

    static int[] originalArray() {
        int[] originalArrayCopy = new int[sortArray.length];
        System.arraycopy(sortArray, 0, originalArrayCopy, 0, sortArray.length);
        return originalArrayCopy;
    }

    static void printArray(int[] array) {
        System.out.println(ArrayUtils.toString(array));
    }
}
