package test.java.util.concurrent.exchanger;

import java.util.Objects;
import java.util.concurrent.Exchanger;

/**
 * Created by Administrator on 2017/3/2.
 */
public class ExchangerRunnable implements Runnable {

    private Exchanger exchanger;
    private Object object;

    public ExchangerRunnable(Exchanger exchanger, Object object) {
        this.exchanger = exchanger;
        this.object = object;
    }

    @Override
    public void run() {
        Object previous = this.object;
        try {
            if (Objects.equals(Thread.currentThread().getName(), "ThreadB")) {
                Thread.sleep(1000);
            }
            this.object = this.exchanger.exchange(this.object);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()
            + " previous object " + previous + " has changed to " + this.object);
    }
}
