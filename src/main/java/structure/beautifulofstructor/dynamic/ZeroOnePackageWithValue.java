package structure.beautifulofstructor.dynamic;

import java.util.Arrays;

/**
 * 0-1背包问题，带物品价值，求最大价值的 -- 动态规划解决方案
 * @author yanchao
 * @date 2020-07-09 15:22
 */
public class ZeroOnePackageWithValue {


    /**
     * 背包问题：能装入背包的最大价值
     * @param weight    所有物品的重量
     * @param value     所有物品的价值
     * @param n         物品的数量
     * @param w         背包所能承受的最大重量
     * @return          能装入背包的最大价值
     */
    public int knapsack3(int[] weight, int[] value, int n, int w) {
        int[][] states = new int[n][w+1];
        for (int i = 0; i < states.length; i++) {
            for (int j = 0; j < states[i].length; j++) {
                states[i][j] = -1;
            }
        }

        states[0][0] = 0;
        if (weight[0] <= w) {
            states[0][weight[0]] = value[0];
        }

        for (int i = 1; i < n; i++) {
            // 物品从 0 开始计数，此时处理第 i 个物品不放入背包的情况，原样放入
            for (int j = 0; j <= w; j++) {
                if (states[i-1][j] >= 0) {
                    states[i][j] = states[i-1][j];
                }
            }

            // 物品从 0 开始计数，此时处理第 i 个物品放入背包的情况，需要考虑是否超过背包承受重量及物品价值是否最大
            for (int j = 0; j <= w - weight[i]; j++) {
                // 之前物品放置或不放置完有该重量才会计算，否则不计算
                if (states[i-1][j] >= 0) {
                    int v = states[i-1][j] + value[i];
                    // 如果将第 i 个物品放置完以后的价值比 "当前价值" 大，则记录最大价值，否则按之前价值计算（相当于该物品没有放置到背包中），保证价值最大化
                    // 当前价值：因为放置之前物品的时候这个重量下的价值已经存在，所以需要比较是否价值最大化，只有价值最大化的情况下才
                    if (v > states[i][j + weight[i]]) {
                        states[i][j + weight[i]] = v;
                        System.out.print(i + " ");
                    }
                }
            }
        }
        System.out.println();
        System.out.println(Arrays.toString(states[n - 1]));
        int maxValue = 0;
        for (int j = 0; j <= w; j++) {
            if (maxValue < states[n - 1][j]) {
                maxValue = states[n - 1][j];
                totalWeight = j;
            }
        }
        return maxValue;
    }


    public int knapsack4(int[] weight, int[] value, int n, int w) {
        // 记录重量与价值，其中数组下标为当前背包的重量，值为当前重量下最大价值
        int[] state = new int[w + 1];
        for (int i = 0; i < state.length; i++) {
            state[i] = -1;
        }
        // 第 0 个物品不放进背包，价值为0
        state[0] = 0;
        // 第 0 个物品放进背包，则重量为 weight[0]，价值为 value[0]。但前提是需要保证第 0 个物品的重量不能超过背包最大重量
        if (weight[0] <= w) {
            state[weight[0]] = value[0];
        }

        for (int i = 1; i < n; i++) {
            // 这里一定要从后往前计算，否则会出现重复计算的问题
            // 举例：当 j 的取值范围是 0 ~ 5 时，state[j] 的值也刚好落在 0 ~ 5 之间时，就可能会出现重复计算问题
            for (int j = w - weight[i]; j >= 0 ; j--) {
            // for (int j = 0; j <= w - weight[i]; j++) {
                if (state[j] < 0) {
                    state[j] = value[i];
                } else if (state[j + weight[i]] < state[j] + value[i]) {
                    state[j + weight[i]] = state[j] + value[i];
                }
            }
        }

        int maxValue = 0;
        System.out.println(Arrays.toString(state));
        for (int j = 0; j <= w; j++) {
            if (maxValue < state[j]) {
                maxValue = state[j];
                totalWeight = j;
            }
        }
        return maxValue;
    }

    private int knapsack20200814(int[] weight, int[] value, int w) {
        if (weight == null || weight.length == 0) {
            return 0;
        }
        int n = weight.length;
        int[][] dp = new int[n][w + 1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= w; j++) {
                dp[i][j] = -1;
            }
        }

        dp[0][0] = 0;
        if (weight[0] <= w) {
            dp[0][weight[0]] = value[0];
        }

        for (int i = 1; i < n; i++) {
            // 第 i 个物品不放入
            for (int j = 0; j <= w; j++) {
                if (dp[i-1][j] >= 0) {
                    dp[i][j] = dp[i-1][j];
                }
            }

            for (int j = 0; j <= w; j++) {
                if (dp[i-1][j] >= 0 && j + weight[i] <= w) {
                    if (dp[i-1][j] + value[i] > dp[i][j + weight[i]]) {
                        dp[i][j + weight[i]] = dp[i-1][j] + value[i];
                    }
                }
            }
        }

        int maxValue = 0;
        for (int j = 0; j <= w; j++) {
            if (dp[n - 1][j] > maxValue) {
                maxValue = dp[n-1][j];
                totalWeight = j;
            }
        }
        return maxValue;
    }

    private int totalWeight = 0;
    public static void main(String[] args) {
        int[] weight = {2,2,4,6,3};
        int[] value = {3,4,1,9,1};
        int n = weight.length;
        int w = 9;
        ZeroOnePackageWithValue z = new ZeroOnePackageWithValue();
        int maxValue = z.knapsack3(weight, value, n, w);
        System.out.println("max value is " + maxValue + ", total weight is " + z.totalWeight);

        maxValue = z.knapsack4(weight, value, n, w);
        System.out.println("max value is " + maxValue + ", total weight is " + z.totalWeight);

        maxValue = z.knapsack20200814(weight, value, w);
        System.out.println("max value is " + maxValue + ", total weight is " + z.totalWeight);
    }
}
