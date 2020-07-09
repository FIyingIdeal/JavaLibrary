package structure.beautifulofstructor.huisu;

import java.util.Arrays;

/**
 * 回溯算法 -- 8 皇后问题
 * @author yanchao
 * @date 2020-07-07 10:14
 */
public class EightQueens {

    /**
     * Queen 数量，也是棋盘行列数，修改该值就变为了 n 皇后问题
     */
    private static final int QUEEN_NUMBER = 4;
    /**
     * 数组 result 用来记录每一行上 Queen 的位置
     * 其中 数组下标 i 表示的是行号，而该下标的值 - result[i] 标志该行上 Queen 所在的列
     */
    private int[] result = new int[QUEEN_NUMBER];
    /**
     * 排列方式总数
     */
    private static int count = 0;

    private void cal8queens(int row) {
        if (row == QUEEN_NUMBER) {
            System.out.println(Arrays.toString(result));
            printQueens(result);
            count++;
            return;
        }
        for (int column = 0; column < QUEEN_NUMBER; column++) {
            System.out.println(Arrays.toString(result) + "   " + row + "   " + column);
            if (isOk(row, column)) {
                result[row] = column;
                cal8queens(row + 1);
            }
        }
    }

    /**
     * 判断 (row, column) 格对应的列及对角线上是否存在 Queen，如果存在则不符合要求，返回 false；否则返回 true；
     * 由于设置 Queen 时是按行从上往下一行一行设置的，所以只需要考虑 row 之前的行即可，无需考虑下标的行
     * @param row       行
     * @param column    列
     * @return          当 (row, column) 格所在列及对角线上不存在 Queen 时，返回true；否则返回 false
     */
    private boolean isOk(int row, int column) {
        // leftup 与 rightup 用于判断 (row, column) 格对角线上是否存在 Queen，由于是从上往下一行一行设置值，所以只需要考虑 row 之前的行
        int leftup = column - 1, rightup = column + 1;
        // 由于是从上往下一行一行设置值，所以之前的行 (0 ~ row-1) 一定是有值的
        // 那重点就是判断 0 ~ row-1 每一行上的值是否在 (row, column) 这个格上的列或对角线上，这里使用 for 循环一行一行判断
        for (int i = row - 1; i >= 0; i--) {
            // 判断该行对应的 column 列是否有 Queen。这里比较难理解点：数组 result 用来记录每一行上 Queen 的位置
            // 其中 数组下标 i 表示的是行号，而该下标的值 - result[i] 标志该行上 Queen 所在的列。所以 ：
            //      result[i] != column 时表示不在 column 列上，需要继续判断 (row, column) "上对角线"上的是否有 Queen；
            //      result[i] == column 时表示在这个列上，则直接返回false；
            if (result[i] == column) {
                return false;
            }
            // 判断 (row, column) 格左上对角线的格子上是否有 Queen 存在，如果存在的话则直接返回 false
            // 外层 if(leftup >= 0) 用来控制边界，当 column < 4 (0 ~ 3) 时，leftup 会先到达边界，而 rightup 要晚一些到达
            if (leftup >= 0) {
                if (result[i] == leftup) {
                    return false;
                }
            }
            // 判断 (row, column) 格右上对角线的格子上是否有 Queen 存在，如果存在的话直接返回 false
            // if(rightup < 8) 同样用来控制边界
            if (rightup < QUEEN_NUMBER) {
                if (result[i] == rightup) {
                    return false;
                }
            }
            leftup--;
            rightup++;
        }
        return true;
    }

    private void printQueens(int[] result) {
        for (int row = 0; row < QUEEN_NUMBER; row++) {
            for (int column = 0; column < QUEEN_NUMBER; column++) {
                if (result[row] == column) {
                    System.out.print("Q ");
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        new EightQueens().cal8queens(0);
        System.out.println(count);
    }
}
