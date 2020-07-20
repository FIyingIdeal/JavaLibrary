package structure.beautifulofstructor.dynamic;

import java.util.Arrays;

/**
 * @author yanchao
 * @date 2020-07-11 10:05
 */
public class LevenshteinDistance {

    public static int levenshteisDistance(String s1, String s2) {
        return levenshteisDistance(s1.toCharArray(), s1.length(), s2.toCharArray(), s2.length());
    }

    private static int levenshteisDistance(char[] a, int n, char[] b, int m) {
        int[][] result = new int[n][m];

        for (int j = 0; j < m; j++) {
            if (b[j] == a[0]) {
                result[0][j] = j;
            } else if (j != 0) {
                result[0][j] = result[0][j - 1] + 1;
            } else {
                result[0][j] = 1;
            }
        }

        for (int i = 0; i < n; i++) {
            if (a[i] == b[0]) {
                result[i][0] = i;
            } else if (i != 0) {
                result[i][0] = result[i - 1][0] + 1;
            } else {
                result[i][0] = 1;
            }
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (a[i] != b[j]) {
                    result[i][j] = Math.min(Math.min(result[i - 1][j] + 1, result[i][j-1] + 1), result[i-1][j-1] + 1);
                } else {
                    result[i][j] = Math.min(Math.min(result[i - 1][j] + 1, result[i][j - 1] + 1), result[i - 1][j - 1]);
                }
            }
        }

        return result[n - 1][m - 1];
    }

    public static void main(String[] args) {
        String s1 = "mtacnu";
        String s2 = "mitcmu";
        System.out.println(LevenshteinDistance.levenshteisDistance(s2, s1));
    }
}
