package structure.beautifulofstructor.huisu;

/**
 * 正方形从左上到右下最短路径 -- 回溯解法
 * 假设我们有一个n乘以n的矩阵w[n][n]。矩阵存储的都是正整数。棋子起始位置在左上⻆，终止位置在右下⻆。
 * 我们将棋子从左上⻆移动到右下⻆。每次只能向右或者向下移动一位。从左上⻆到右下⻆，会有很多不同的路径可以走。
 * 我们把每条路径经过的数字加起来看作路径的⻓度。那从左上⻆移动到右下⻆的最短路径⻓度是多少呢？
 * @author yanchao
 * @date 2020-07-10 10:08
 */
public class SquareFindWay {

    private int minDist = Integer.MAX_VALUE;

    private void minDistBT(int i, int j, int dist, int[][] w, int n) {
        if (i == n && j == n) {
            if (dist < minDist) {
                minDist = dist;
            }
            return;
        }

        if (i < n) {
            minDistBT(i + 1, j, dist + w[i][j], w, n);
        }

        if (j < n) {
            minDistBT(i, j + 1, dist + w[i][j], w, n);
        }
    }

    public static void main(String[] args) {
        int [][] w = {{1,3,5,9}, {2,1,3,4}, {5,2,6,7}, {6,8,4,3}};
        SquareFindWay sfw = new SquareFindWay();
        sfw.minDistBT(0,0,0,w, 4);
        System.out.println("min dist by minDistBT is " + sfw.minDist);
    }
}
