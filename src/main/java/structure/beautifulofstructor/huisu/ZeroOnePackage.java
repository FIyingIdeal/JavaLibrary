package structure.beautifulofstructor.huisu;

/**
 * 回溯算法 -- 0-1 背包问题
 * @author yanchao
 * @date 2020-07-07 14:41
 */
public class ZeroOnePackage {

    private int maxW = Integer.MIN_VALUE;

    public void f(int i, int cw, int[] items, int n, int w) {
        if (cw == w || i == n) {
            if (cw > maxW) {
                maxW = cw;
            }
            return;
        }
        // 不选择当前物品，直接考虑下一个（第 i+1 个），故 cw 不更新
        f(i+1, cw, items, n, w);
        if (cw + items[i] <= w) {
            System.out.print("   " + items[i]);
            // 选择了当前物品，故考虑下一个时，cw 通过入参更新为 cw + items[i]
            f(i+1, cw + items[i], items, n, w);
        }
    }

    public static void main(String[] a) {
        int[] items = new int[]{1,2,3};
        ZeroOnePackage zeroOnePackage = new ZeroOnePackage();
        zeroOnePackage.f(0, 0, items, 3, 5);
        System.out.println("result is " + zeroOnePackage.maxW);
    }
}
