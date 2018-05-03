package ThinkingInJava.chapter21Thread.CountDownLatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author yanchao
 * @date 2018/4/30 13:11
 */
public class CountDownLatchDemo {
    static final int SIZE = 100;
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(100);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new WaitingTask(latch));
        }
        for (int i = 0; i < SIZE; i++) {
            executorService.execute(new TaskPortion(latch));
        }
        System.out.println("Latched all tasks!");
        executorService.shutdown();
    }
}

class TaskPortion implements Runnable {

    private static int counter = 0;
    private final int id = counter++;
    private final CountDownLatch latch;
    private final Random random = new Random(41);

    public TaskPortion(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            doWork();
            latch.countDown();
        } catch (InterruptedException e) {}
    }

    private void doWork() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(2000));
        System.out.println(this + "completed");
    }

    @Override
    public String toString() {
        return String.format("%1$-3d ", id);
    }
}

class WaitingTask implements Runnable {

    private static int counter = 0;
    private final int id = counter++;
    private final CountDownLatch latch;

    public WaitingTask(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            latch.await();
            System.out.println("Latch barrier passed for " + this);
        } catch (InterruptedException e) {}
    }

    @Override
    public String toString() {
        return String.format("WaitingTask %1$-3d ", id);
    }
}
