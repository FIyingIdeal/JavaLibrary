package algorithm.leetcode;

/**
 * @author yanchao
 * @date 2020-08-24 23:43
 */
public class Part459 {

    public static void main(String[] args) {
        System.out.println(repeatedSubstringPattern("abab"));
    }

    private static boolean repeatedSubstringPattern(String s) {
        // 题目给定，非空字符串，所以不考虑该情况
        int l = s.length();
        if (l == 1) {
            return true;
        }

        int mid = (l - 1) / 2;

        for (int i = mid; i >= 0; i--) {
            int currentLength = i + 1;
            int rightLength = l - currentLength;
            // 剩余长度如果不是现在长度的整数倍，则直接跳过
            if (rightLength % currentLength != 0) {
                continue;
            }
            // 剩余分组数
            int groupNumber = rightLength / currentLength;
            // 用于匹配的子串
            String s1 = s.substring(0, currentLength);
            int n = 1;
            while (n <= groupNumber) {
                String s2 = s.substring(currentLength * n, currentLength * (n + 1));
                if (s1.equals(s2)) {
                    if (n == groupNumber) {
                        return true;
                    } else {
                        n++;
                    }
                } else {
                    break;
                }
            }
        }
        return false;
    }
}
