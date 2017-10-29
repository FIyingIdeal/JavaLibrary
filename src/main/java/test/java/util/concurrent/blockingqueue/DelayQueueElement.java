package test.java.util.concurrent.blockingqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/2/27.
 */
public class DelayQueueElement implements Delayed {

    @Override
    public long getDelay(TimeUnit unit) {
        return -1;
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
