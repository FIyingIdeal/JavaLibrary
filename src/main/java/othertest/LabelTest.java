package othertest;

import org.junit.Test;

/**
 * @author yanchao
 * @date 2018/4/8 14:57
 */
public class LabelTest {

    public static void main(String[] args) {
        int i = 0;
        outer:
        for (;true;){
            inner:
            for (; i < 10 ; i++){
                System.out.println("i = " + i);
                if (i == 2){
                    System.out.println("continue");
                    continue;
                }
                if (i == 3){
                    System.out.println("Break");
                    i++;
                    break;  //break会中断for循环，在末尾之前 i++不会执行，所以要手动i++
                }
                if (i == 7){
                    System.out.println("continue outer");
                    i++;
                    continue outer;  //跳出循环顶部，也不会执行i++
                }
                if (i == 8){
                    System.out.println("break outer");
                    break outer;
                }
                for(int k = 0 ; k < 5 ; k++){
                    if (k == 3){
                        System.out.println("continue inner");
                        continue inner;
                    }
                }
            }
        }

        System.out.println("break outer后for循环不再执行！");
    }

    @Test
    public void test() {
        int i = 1;
        label:
        for (; i < 10; i++) {
            System.out.println("i = " + i);
            try {
                if (i % 2 == 0) {
                    throw new NullPointerException();
                }
            } catch (Exception e) {
                // i++;
                System.out.println("NPE" + i);
                continue label;
            }
        }
    }
}
