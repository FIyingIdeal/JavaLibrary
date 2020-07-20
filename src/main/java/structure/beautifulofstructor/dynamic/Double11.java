package structure.beautifulofstructor.dynamic;

import java.util.Arrays;

/**
 * @author yanchao
 * @date 2020-07-09 18:23
 */
public class Double11 {

    public void double11adviance(int[] items, int n, int w) {
        boolean[][] states = new boolean[n][3 * w + 1];

        states[0][0] = true;
        states[0][items[0]] = true;
        for (int i = 1; i < n; i++) {

            for (int j = 0; j <= 3 * w; j++) {
                if (states[i - 1][j]) {
                    states[i][j] = true;
                }
            }

            for (int j = 0; j <= 3 * w - items[i]; j++) {
                if (states[i - 1][j]) {
                    states[i][j + items[i]] = true;
                }
            }
        }

        int j;
        for (j = w; j < 3 * w + 1; j++) {
            if (states[n - 1][j]) {
                break;
            }
        }
        if (-1 == j) {
            return;
        }
        for (int i = n - 1; i >= 1; i--) {
            if (j - items[i] >= 0 && states[i - 1][j - items[i]]) {
                System.out.print(items[i] + "  ");
                j = j - items[i];
            }
        }

        if (j != 0) {
            System.out.print(items[0]);
        }
    }

    public static void main(String[] args) {
        int[] items = {50, 10, 20, 30, 100, 30};
        // new Double11().double11adviance(items, items.length, 200);

        int[][] matrix = {{5}, {7,8}, {2,3,4}, {4,9,6,1}, {2,7,9,4,5}};
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "  ");
            }
            System.out.println();
        }
    }
}
