package algorithm.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author yanchao
 * @date 2018/9/18 17:08
 */
public class LongestSubstring {

    public static void main(String[] args) {
        String str = "abba";
        System.out.println(getLongestSubstringLength(str));
        System.out.println(getLongestSubstringLength1(str));
    }

    private static int getLongestSubstringLength(String str) {
        int length = str.length();
        int ans = 0, i = 0, j = 0;
        Set<Character> set = new HashSet<>();
        while (i < length && j < length) {
            // 当charAt(j) 存在时，将一直执行 else 语句块直到删除掉与 charAt(j) 一样的字符及其之前的字符
            if (!set.contains(str.charAt(j))) {
                set.add(str.charAt(j++));
                ans = Math.max(ans, j - i);
            }
            else {
                set.remove(str.charAt(i++));
            }
        }
        return ans;
    }

    private static int getLongestSubstringLength1(String str) {
        int length = str.length();
        int ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0, j = 0; j < length; j++) {
            if (map.keySet().contains(str.charAt(j))) {
                // 如"abba"，当 b 重复时，i = 2；而当 a 重复是，i = 1。所以需要通过 max 取最大值
                i = Math.max(map.get(str.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(str.charAt(j), j + 1);
        }
        return ans;
    }
}
