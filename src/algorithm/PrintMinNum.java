package algorithm;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * @author yanchao
 * @date 2017/10/19 10:04
 */
public class PrintMinNum {

    public static void main(String[] args) throws Exception {
        /*Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 4; i++) {
            int num = scanner.nextInt();
            System.out.println(num);
        }*/
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int length = 0;
        System.out.print("请输入数组长度：");
        length = Integer.valueOf(br.readLine());
        String[] nums = new String[length];
        for (int i = 0; i < length; i++) {
            System.out.print("请输入第" + i + "个数字：");
            nums[i] = br.readLine();
        }
        Arrays.sort(nums);
        StringBuilder result = new StringBuilder();
        Stream.of(nums).forEach(num -> {result.append(num);});
        System.out.println("最小的数字为：" + result.toString());
    }
}
