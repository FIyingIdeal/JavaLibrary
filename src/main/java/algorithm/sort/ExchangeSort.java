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


    @Test
    public void bubbleSort20208080() {
        int[] sortArray = originalArray();
        printArray(sortArray);
        for (int i = 0; i < sortArray.length - 1; i++) {
            for (int j = 0; j < sortArray.length - i - 1; j++) {
                if (sortArray[j] > sortArray[j+1]) {
                    int temp = sortArray[j];
                    sortArray[j] = sortArray[j+1];
                    sortArray[j+1] = temp;
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



    @Test
    public void quickSort20200808() {
        int[] sortArray = originalArray();
        printArray(sortArray);
        quickSort20200808(sortArray);
        printArray(sortArray);
        int searchIndex = search(sortArray, 13);
        System.out.println("search index is " + searchIndex);
    }

    private void quickSort20200808(int[] array) {
        quickSort20200826(array, 0, array.length - 1);
    }

    private void quickSort20200826(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }
        int divide = divide20200826(array, left, right);
        quickSort20200826(array, left, divide - 1);
        quickSort20200826(array, divide + 1, right);
    }

    private int divide20200826(int[] array, int left, int right) {
        /*int temp = array[left];
        while (left != right) {
            while (array[right] > temp && left != right) {
                right--;
            }
            if (left != right) {
                array[left] = array[right];
                left++;
            }
            while (array[left] < temp && left != right) {
                left++;
            }
            if (left != right) {
                array[right] = array[left];
                right--;
            }
        }
        array[left] = temp;
        return left;*/

        int temp = array[left];
        while (left != right) {
            while (array[right] > temp && left != right) {
                right--;
            }
            if (left != right) {
                array[left] = array[right];
                left++;
            }
            while (array[left] < temp && left != right) {
                left++;
            }
            if (left != right) {
                array[right] = array[left];
                right--;
            }
        }
        array[left] = temp;
        return left;
    }



    private int search(int[] array, int num) {
        if (array == null || array.length == 0) {
            return -1;
        }
        int low = 0, high = array.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (array[mid] == num) {
                return mid;
            } else if (array[mid] > num) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

}




















