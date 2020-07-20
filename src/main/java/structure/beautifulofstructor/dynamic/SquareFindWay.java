package structure.beautifulofstructor.dynamic;

/**
 * @author yanchao
 * @date 2020-07-10 10:45
 */
public class SquareFindWay {

    public int minDistDP(int[][] matrix, int n) {
        int[][] state = new int[n][n];
        // state[0][0] = matrix[0][0];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    if (i == 0 && j == 0) {
                        state[i][j] = matrix[i][j];
                    } else if (i == 0) {
                        state[i][j] = state[i][j - 1] + matrix[i][j];
                    } else if (j == 0) {
                        state[i][j] = state[i - 1][j] + matrix[i][j];
                    }
                } else {
                    state[i][j] = Math.min(state[i-1][j], state[i][j - 1]) + matrix[i][j];
                }
            }
        }
        return state[n -1][n - 1];
    }



    private int[][] matrix1 = {{1,3,5,9}, {2,1,3,4}, {5,2,6,7}, {6,8,4,3}};
    private final static int SQUARE_NUMBER = 4;
    private int[][] result = new int[SQUARE_NUMBER][SQUARE_NUMBER];
    private int minDistDP(int i, int j) {

        if (i == 0 && j == 0) {
            return matrix1[0][0];
        }

        if (result[i][j] > 0) {
            return result[i][j];
        }

        int top = Integer.MAX_VALUE;
        int left = Integer.MAX_VALUE;
        if (i - 1 >= 0) {
            top = minDistDP(i - 1, j);
        }
        if (j - 1 >= 0) {
            left = minDistDP(i, j - 1);
        }

        int minDist = matrix1[i][j] + Math.min(top, left);
        result[i][j] = minDist;
        return result[SQUARE_NUMBER][SQUARE_NUMBER];
    }

    public static void main(String[] args) {
        int [][] w = {{1,3,5,9}, {2,1,3,4}, {5,2,6,7}, {6,8,4,3}};
        SquareFindWay sfw = new SquareFindWay();
        int minDist = sfw.minDistDP(w, 4);
        System.out.println("min dist by minDistBT is " + minDist);
    }
}
