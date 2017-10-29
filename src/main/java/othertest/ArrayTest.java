package othertest;

import org.junit.Test;

/**
 * Created by Administrator on 2017/6/1.
 */
public class ArrayTest {

    @Test
    public void arrayTest() {
        int [][] arrs = {{1,2,3}, {4,5,6}, {7,8,9}};
        for (int i = 0; i < arrs.length; i++) {
            for (int j = 0; j < arrs[i].length; j++) {
                System.out.print(arrs[i][j] + " ");
            }
            System.out.println();
        }
    }
}
