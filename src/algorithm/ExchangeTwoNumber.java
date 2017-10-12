package algorithm;

/**
 * @author yanchao
 * @date 2017/10/12 15:14
 * 将两个数字交换，不引入第三个变量
 *
 * 使用异或运算：两个数异或的结果再与其中一个数异或的结果是另外一个数
 *
 * 离散数学中异或的性质：
 * 1. 交换律：A^B = B^A;
 * 2. 结合律：A^(B^C) = (A^B)^C;
 * 3. 恒等率：X^0 = X;
 * 4. 归零率：X^X = 0;
 * 5. 自反律：A^B^B = A^0 = A
 */
public class ExchangeTwoNumber {

    public static void main(String[] args) {
        int i1 = 8, i2 = 34;
        i1 ^= i2;
        i2 ^= i1;
        i1 ^= i2;
        System.out.println("i1 = " + i1 + ", i2 = " + i2);
    }
}
