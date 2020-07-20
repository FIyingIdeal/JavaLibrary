package structure.beautifulofstructor.dynamic;

/**
 * 剪绳子
 * @author yanchao
 * @date 2020-07-10 22:48
 */
public class CuteRope {

    public int cuteRope(int n) {

        if (n < 2) {
            return 0;
        }
        if (n == 2) {
            return 1;
        }
        if (n == 3) {
            return 2;
        }

        int[] result = new int[n+1];
        result[1] = 1;
        result[2] = 2;
        result[3] = 3;

        int max = 0;
        for (int i = 4; i <= n; i++) {
            for (int j = 1; j <= i / 2; j++) {
                int temp = result[j] * result[i - j];
                if (temp > max) {
                    max = temp;
                }
            }
            result[i] = max;
            max = 0;
        }
        return result[n];
    }

    public static void main(String[] args) {
        int ropeLength = 8;
        System.out.println(new CuteRope().cuteRope(ropeLength));
    }
}
