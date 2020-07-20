package structure.beautifulofstructor.dynamic;

/**
 * 假设我们有几种不同币值的硬币v1，v2，......，vn（单位是元）。如果我们要支付w元，求最少需要多少个硬币。
 * 比如，我们有3种不同的硬币，1元、3元、5元，我们要支付9元，最少需要3个硬币（3个3元的硬币）
 * @author yanchao
 * @date 2020-07-10 14:50
 */
public class Coin {

    /**
     * TODO 修改返回值，因为现在涉及找零问题直接返回无解，但实际是可以计算的
     * @param coins 硬币的种类，如 1元，3元等
     * @param w     支付钱数
     * @return      支付次数，如果没有满足条件的情况（涉及到找零）返回 -1
     */
    public static int coin(int[] coins, int w) {
        // 二维数组：第一维表示支付次数；第二维表示已支付钱数；值为当前已支付钱数
        int[][] states = new int[w][w];

        // 初始状态，即第一次支付
        for (int i = 0; i < coins.length; i++) {
            // 如果是超过支付额度的硬币不考虑在内
            if (coins[i] <= w) {
                if (coins[i] == w) {
                    return 1;
                }
                states[0][coins[i]] = coins[i];
            }
        }

        for (int i = 1; i < w; i++) {
            for (int z = 0; z < w; z++) {
                for (int j = 0; j < coins.length; j++) {
                    if (coins[j] > w) {
                        continue;
                    }
                    if (states[i - 1][z] > 0) {
                        if (states[i - 1][z] + coins[j] == w) {
                            return i + 1;
                        } else if (states[i - 1][z] + coins[j] < w) {
                            states[i][z] = Math.max(states[i][z], states[i - 1][z] + coins[j]);
                        }
                    }
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] coins = {2,4,10};
        int w = 9;
        System.out.println(coin(coins, w));
    }

}
