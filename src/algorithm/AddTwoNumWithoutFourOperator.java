package algorithm;

import java.util.Scanner;

/**
 * @author yanchao
 * @date 2017/10/26 9:39
 * @function 写一个函数，求两个整数之和，要求在函数体内不得使用+、-、*、/四则运算符号
 *
 * 思路：
 * 1、先将各bit位相加，不计进位，这一步可以用m^n实现
 * 2、加上进位，进位如何来，用m&n可以得到m和n中都为1的bit位，而不全为1的位则全部变为了0，该位相加会发生进位，使得左边一位加1，因此(m&n)<<1边可得到进位后要加的1的位置；
 * 3、将前面两步的结果相加，相加的时候还有可能再产生进位，因此二者相加的过程可以再次重复循环步骤1和2，直到(m&n)<<1变为了0，这时候不会再产生进位，退出循环即可。
 */
public class AddTwoNumWithoutFourOperator {

    public static int add(int num1, int num2) {
        int sum = 0;    //不含进位的和
        int carry = 0;  //进位

        while (num2 != 0) {
            sum = num1 ^ num2;
            //此处需要注意运算符的优先级， <<优先级要高于 &
            carry = (num1 & num2) << 1;
            num1 = sum;
            num2 = carry;
        }
        return num1;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        //Scanner默认使用whitespace（包括空格，回车\n，制表符\t）作为分隔，这里通过useDelimiter()指定只有回车为分隔符
        scanner.useDelimiter("\\n");
        int[] nums = new int[2];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }
        System.out.println(add(nums[0], nums[1]));
    }
}
