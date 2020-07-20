package structure.beautifulofstructor.huisu;

/**
 * 莱文斯坦距离 -- 回溯解法
 * @author yanchao
 * @date 2020-07-10 21:25
 */
public class LevenshteinDistance {

    private int minEdist = Integer.MAX_VALUE;

    public void lwstBT(String s1, String s2) {
        lwstBT(s1.toCharArray(), s2.toCharArray(), 0, 0, 0);
    }

    public void lwstBT(char[] s1, char[] s2, int i, int j, int edist) {
        // 有一个字符串已经到了末尾
        if (i == s1.length || j == s2.length) {
            // 字符串不相同，同样需要进行涉及到改动次数
            if (i < s1.length) {
                edist += s1.length - i;
            }
            if (j < s2.length) {
                edist += s2.length - j;
            }

            if (edist < minEdist) {
                minEdist = edist;
            }
            return;
        }

        if (s1[i] == s2[j]) {
            lwstBT(s1, s2, i + 1, j + 1, edist);
        } else {
            // 删除 s1[i] 或 在 s2[j] 前插入一个与 s1[i] 相同的字符
            lwstBT(s1, s2, i + 1, j, edist + 1);
            // 删除 s2[j] 或 在 s1[i] 前插入一个与 s2[j] 相同的字符
            lwstBT(s1, s2, i, j + 1, edist + 1);
            // 将 s1[i] 替换成 s2[j] 或 将 s2[j] 替换成 s1[i]
            lwstBT(s1, s2, i + 1, j + 1, edist + 1);
        }
    }

    public static void main(String[] args) {
        String s1 = "mitcmu";
        String s2 = "mtacnu";
        LevenshteinDistance l = new LevenshteinDistance();
        l.lwstBT(s1, s2);
        System.out.println(l.minEdist);

    }
}
