package structure.offer;

import java.util.HashMap;
import java.util.Map;

/**
 * 《剑指 offer》 题目3：给定一个长度为 n 的数组，数组中的数字全部在 0 ~ (n-1) 范围内，但数字可能重复，找出一个重复的数字(可能有多个，但只需要找到一个就可以)
 * @author yanchao
 * @date 2020-03-01 11:48
 */
public class Interview3 {

    public static void main(String[] args) {
        int[] arr = {2,3,1,0,4,5,3};
        int dup = duplicateNumber(arr);
        System.out.println(dup);
        System.out.println(duplicateNumberWithAnotherArray(arr));
        System.out.println(duplicateNumberWithMap(arr));
    }

    /**
     * 最好的解决方法，在原数组中查找，空间复杂度为 O(1)
     *
     * 相当于对数组排序，但排序并不使用我们使用的常规方法，而是从头开始遍历数组，
     * 0. 判断数组下标 index(最初 index = 0) 与该下标对应位置的数字 arr[index] 是否相等，
     *      - 1. 如果不相等的话就将该数字 arr[index] 与 index = arr[index] 位置的数字 (arr[arr[index]]) 进行比较：
     *          - 1.1 如果数字相等，则找到了重复数字；
     *          - 1.2 如果不相等，则交换这两个位置的数字，这样的话 index = arr[index] 位置的数字与 index 就相等了，继续步骤 0；
     *      - 2. 如果相等的话，则 index = index + 1，回到步骤 0，开始下一轮循环；
     */
    private static int duplicateNumber(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int count = arr.length;
        for (int i = 0; i < count; i++) {
            // 当下标与该下标处的数字不相等的时候一直交换
            while (arr[i] != i) {
                // 判断交换位置是否相等，如果相等即为重复数字，直接返回
                if (arr[arr[i]] == arr[i]) {
                    return arr[i];
                }
                // 交换两个数字
                int temp = arr[i];
                // 这里可以是 arr[i] = arr[arr[i]]，但容易混淆
                arr[i] = arr[temp];
                // 注意这里不应该是 arr[arr[i]] = temp，因为 arr[i] 已经被上一步修改了
                arr[temp] = temp;
            }
        }
        return -1;
    }

    private static int duplicateNumberWithAnotherArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int length = arr.length;
        Integer[] arr1 = new Integer[length];
        for (int value : arr) {
            if (arr1[value] != null) {
                return value;
            }
            arr1[value] = value;
        }
        return -1;
    }

    /**
     * 使用 Map 的空间复杂度更高，比使用另一个数组的空间复杂度还要高
     */
    private static int duplicateNumberWithMap(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        Map<Integer, Integer> map = new HashMap<>(16);
        for (int value : arr) {
            if (map.put(value, value) != null) {
                return value;
            }
        }
        return -1;
    }
}
