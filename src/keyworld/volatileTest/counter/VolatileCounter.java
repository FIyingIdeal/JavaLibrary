package keyworld.volatileTest.counter;

/**
 * Created by Administrator on 2017/2/7.
 */
public class VolatileCounter {

    public volatile static int count = 0;

    public static void inc() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;    //非原子操作
    }
}
