package structure.beautifulofstructor.dynamic;

/**
 * 0-1 背包问题的动态规划解法
 * @author yanchao
 * @date 2020-07-09 10:35
 */
public class ZeroOnePackage {

    /**
     * 使用二维数组记录每一个物品放入与不放入背包的状态：
     *      第一个维度为物品列表，长度为 n；
     *      第二个维度为当前背包重量，长度为 w + 1，其中 w 为背包所能承受的最大重量，w + 1 是因为当前背包重量可能为 0，即 0 ~ w 共 w+1 个数
     * @param weight    所有物品的重量，数组保存
     * @param n         物品总数量，理论上应该等于 weight.length
     * @param w         背包所能承受的最大重量
     * @return          将物品装入背包，背包承受能力范围内的最大重量
     */
    public int knapsack(int[] weight, int n, int w) {
        if (weight == null) {
            return 0;
        }
        // 这个其实没有必要，可以直接将参数 n 忽略，n 直接取 weight.length 即可
        if (weight.length != n) {
            throw new IllegalArgumentException("weight.length[" + weight.length + "]not equals n[" + n + "]");
        }
        // 一个二维数组，其中第一维是物品数量，所以长度为 n；第二维是背包承受重量，最大不能超过 w，另外还有一个为 0 的情况，所以长度为 w+1
        boolean[][] status = new boolean[n][w+1];
        // 初始化第一个物品（n == 0）的可能状态，要么放，要么不放，所以只需要设置两个值即可
        // 第一个物品不放置，则重量为 0 ，所以 status[0][0] = true;
        status[0][0] = true;
        // 第一个物品放置，则重量为 weight[0]，所以 status[0][weight[0]] = true; 但需要考虑第一个物品的重量是否超过了背包重量
        if (weight[0] <= w) {
            status[0][weight[0]] = true;
        }

        // 物品遍历，有一初始已经将第一个物品放置了，所以从第二个物品（i = 1）开始考虑
        for (int i = 1; i < n; i++) {
            // 第 i + 1 个物品不放置的情况
            for (int j = 0; j <= w; j++) {
                // status[i] 中保存了 第1个物品到第 i 个物品是否放入背包的所有情况，
                // 所以当某个物品不放入其中的时候，这时 status[i] 的状态与前一个物品装填后的重量状态 status[i-1] 是完全一样的
                if (status[i - 1][j]) {
                    status[i][j] = true;
                }
            }
            // 考虑完不放的情况就需要考虑放置的情况，放置的时候需要保证放置后重量不能超过背包承受重量 w
            for (int j = 0; j <= w - weight[i]; j++) {
                if (status[i - 1][j]) {
                    status[i][j + weight[i]] = true;
                }
            }
        }

        for (int j = w; j >= 0; j--) {
            if (status[n-1][j]) {
                return j;
            }
        }
        return 0;
    }

    /**
     * 如果理解了 {@link #knapsack(int[], int, int)} 方法的过程，会发现不放入的情况是被重复操作的过程，
     * 每次只需要考虑放入的情况即可，而保存状态的数组使用一维数组即可
     * @param weight    所有物品的重量，数组保存
     * @param n         物品总数量，理论上应该等于 weight.length
     * @param w         背包所能承受的最大重量
     * @return          将物品装入背包，背包承受能力范围内的最大重量
     */
    public int knapsack1(int[] weight, int n, int w) {
        if (weight == null) {
            return 0;
        }
        if (weight.length != n) {
            throw new IllegalArgumentException("weight.length[" + weight.length + "]not equals n[" + n + "]");
        }
        boolean[] status = new boolean[w + 1];
        status[0] = true;
        if (weight[0] <= w) {
            status[weight[0]] = true;
        }
        for (int i = 1; i < n; i++) {
            // for (int j = 0; j <= w - weight[i]; j++) {
            // 这里一定要从后往前计算，否则会出现重复计算的问题。对于这个可能不是很明显，但对于添加价值的会有导致错误的影响
            // 参考 ZeroOnePackageWithValue#knapsack4 方法
            for (int j = w - weight[i]; j >= 0 ; j--) {
                if (status[j]) {
                    status[j + weight[i]] = true;
                }
            }
        }
        for (int j = w; j >= 0; j--) {
            if (status[j]) {
                return j;
            }
        }
        return 0;
    }

    private int knapsack20200814(int[] weight, int w) {
        if (weight == null || weight.length == 0) {
            return 0;
        }
        int n = weight.length;
        // w + 1 是因为 0 - w 共 w+1 个数
        boolean[][] dp = new boolean[n][w + 1];
        dp[0][0] = true;
        if (weight[0] <= w) {
            dp[0][weight[0]] = true;
        }
        for (int i = 1; i < n; i++) {
            // 第 i 个物品不放入
            for (int j = 0; j <= w; j++) {
                if (dp[i-1][j]) {
                    dp[i][j] = true;
                }
            }
            // 第 i 个物品放入
            for (int j = 0; j <= w; j++) {
                // weight[i] + j <= w 可以作为 for 循环的判读条件( j <= w - weight[i])，提升效率，但写成这样方便理解
                if (dp[i-1][j] && weight[i] + j <= w) {
                    dp[i][j + weight[i]] = true;
                }
            }
        }

        for (int i = w; i >= 0; i--) {
            if (dp[n - 1][i]) {
                return i;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int[] weight = {2,2,4,6,10};
        int n = weight.length;
        int w = 9;
        ZeroOnePackage zeroOnePackage = new ZeroOnePackage();
        int maxWeight = zeroOnePackage.knapsack(weight, n, w);
        int maxWeight1 = zeroOnePackage.knapsack1(weight, n, w);
        int maxWeight2 = zeroOnePackage.knapsack20200814(weight, w);
        System.out.println("max weight is " + maxWeight);
        System.out.println("max weight is " + maxWeight1);
        System.out.println("max weight is " + maxWeight2);
    }

}
