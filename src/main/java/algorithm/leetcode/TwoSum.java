package algorithm.leetcode;

/**
 * @date 2017年10月29日23:19:10
 * @function
 */
public class TwoSum {

    public static void main(String[] args) {
        int[] nums = new int[]{2,11,13,7};
        int[] result = twoSum(nums, 9);
        System.out.println(result[0] + " " + result[1]);
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }
}
