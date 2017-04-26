package test.java.util;

import java.util.Scanner;

/**
 * Created by Administrator on 2017/1/10.
 */
public class ScannerTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line;
        while (true) {
            line = scanner.nextLine();
            if ("exit".equals(line))
                break;
            System.out.println(line);
        }
    }
}
