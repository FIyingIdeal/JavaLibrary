package algorithm.leetcode;

/**
 * @author yanchao
 * @date 2020-07-18 11:50
 */
public class Part97 {
        // 动态规划
        public boolean isInterleave(String s1, String s2, String s3) {
            if ("".equals(s1) && "".equals(s2) && "".equals(s3)) {
                return true;
            }

            if (s3.length() != (s1.length() + s2.length())) {
                return false;
            }
            if (("".equals(s1) || "".equals(s2)) && !"".equals(s3)) {
                if (s3.equals(s1) || s3.equals(s2)) {
                    return true;
                } else {
                    return false;
                }
            }

            String[][] status = new String[s1.length()][s2.length()];

            /*status[0][0] = s3.startsWith("" + s1.charAt(0) + s2.charAt(0)) ? "" + s1.charAt(0) + s2.charAt(0)
                    : s3.startsWith("" + s2.charAt(0) + s1.charAt(0)) ? "" + s2.charAt(0) + s1.charAt(0) : null ;*/

            // 初始化第一列，其中 status[i][0] 可能为 {s1.substring(0, i + 1) + s2.charAt(0), s[i-1][0] + s1.charAt(i)}
            // 只需要留下 s3.startsWith(status[i][0]) == true 的即可
            for (int i = 0; i < s1.length(); i++) {
                String s = s1.substring(0, i + 1) + s2.charAt(0);
                if (s3.startsWith(s)) {
                    status[i][0] = s;
                } else if (i == 0) {
                    s = "" + s2.charAt(0) + s1.charAt(0);
                    if (s3.startsWith(s)) {
                        status[i][0] = s;
                    }
                }
                // 这个可以用 else if 如果上边满足则可以不用判断了，即使满足也是重复的
                if (i != 0 && status[i - 1][0] != null) {
                    s = status[i - 1][0] + s1.charAt(i);
                    if (s3.startsWith(s)) {
                        status[i][0] = s;
                    }
                }
            }

            // 同理，初始化第一行
            for (int j = 0; j < s2.length(); j++) {
                String s = s2.substring(0, j + 1) + s1.charAt(0);
                if (s3.startsWith(s)) {
                    status[0][j] = s;
                }
                if (j != 0 && status[0][j - 1] != null) {
                    s = status[0][j - 1] + s2.charAt(j);
                    if (s3.startsWith(s)) {
                        status[0][j] = s;
                    }
                }
            }

            for (int i = 1; i < s1.length(); i++) {
                for (int j = 1; j < s2.length(); j++) {
                    /*if (status[i-1][j] == null && status[i][j-1] == null) {
                        return false;
                    }*/
                    if (status[i-1][j] != null) {
                        String s = status[i-1][j] + s1.charAt(i);
                        if (s3.startsWith(s)) {
                            status[i][j] = s;
                        }
                    }
                    else if (status[i][j-1] != null) {
                        String s = status[i][j-1] + s2.charAt(j);
                        if (s3.startsWith(s)) {
                            status[i][j] = s;
                        }
                    }
                }
            }
            return status[s1.length()-1][s2.length()-1] != null;
        }

    public static void main(String[] arg) {
        System.out.println(new Part97().isInterleave("aabd", "abdc", "aabdabcd"));
    }
}
