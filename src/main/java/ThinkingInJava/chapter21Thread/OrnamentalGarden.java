package ThinkingInJava.chapter21Thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author yanchao
 * @date 2018/5/10 23:21
 */
public class OrnamentalGarden {

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++)  {
            service.execute(new Entrance(i));
        }
        TimeUnit.SECONDS.sleep(10);
        Entrance.cancel();
        service.shutdown();
        if (!service.awaitTermination(250, TimeUnit.MILLISECONDS)) {
            System.out.println("Some tasks were not terminated!");
        }
        System.out.println("Total : " + Entrance.getTotalCount());
        System.out.println("Sum of Entrances : " + Entrance.getNumCount());
    }
}

class Counter {

    private int count = 0;
    private Random random = new Random(47);

    public synchronized int increment() {
        int temp = count;
        if (random.nextBoolean()) {
            Thread.yield();
        }
        return (count = ++temp);
    }

    public synchronized int value() {
        return count;
    }

}


class Entrance implements Runnable {

    private static Counter counter = new Counter();
    private static List<Entrance> list = new ArrayList<>();
    private final int id;
    private int num;
    private static volatile boolean canceled = false;

    public Entrance(int id) {
        this.id = id;
        list.add(this);
    }

    public static void cancel() {
        canceled = true;
    }

    @Override
    public void run() {
        while (!canceled) {
            //synchronized (this) {
                ++num;
            //}
            System.out.println(this + "   Total: " + counter.increment());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("sleep interrupted");
            }
        }
    }

    public synchronized int getNum() {
        return this.num;
    }

    @Override
    public String toString() {
        return "Entrance " + id + " : " + num;
    }

    public static int getTotalCount() {
        return counter.value();
    }

    public static int getNumCount() {
        int numCount = list.stream().map(Entrance::getNum).reduce(0, Integer::sum);
        int numCount1 = list.stream().collect(Collectors.summingInt(Entrance::getNum));
        return numCount;
    }
}
