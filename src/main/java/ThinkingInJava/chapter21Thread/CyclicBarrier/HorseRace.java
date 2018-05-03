package ThinkingInJava.chapter21Thread.CyclicBarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author yanchao
 * @date 2018/4/30 13:59
 */
public class HorseRace {

    static final int FINISH_LINE = 75;
    private List<Horse> horses = new ArrayList<>();
    // 注意这里使用的是CachedThreadPool，如果使用的不是CachedThreadPool，且允许的最大线程数量小于CyclicBarrier的值，则线程将会一直等待
    private ExecutorService executorService = Executors.newCachedThreadPool();
    // private ExecutorService executorService = Executors.newFixedThreadPool(7);
    private CyclicBarrier barrier;
    public HorseRace(int nHorses, final int pause) {
        barrier = new CyclicBarrier(nHorses, new Runnable() {
            @Override
            public void run() {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < FINISH_LINE; i++) {
                    sb.append("=");
                }
                System.out.println(sb);
                for (Horse horse : horses) {
                    System.out.println(horse.tracks());
                }
                for (Horse horse : horses) {
                    if (horse.getStrides() >= FINISH_LINE) {
                        System.out.println(horse + " won!");
                        executorService.shutdownNow();
                        return;
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(pause);
                } catch (InterruptedException e) {
                    System.out.println("barrier-action sleep interrupted");
                }
            }
        });

        for (int i = 0; i < nHorses; i++) {
            Horse horse = new Horse(barrier);
            horses.add(horse);
            executorService.execute(horse);
        }
    }

    public static void main(String[] args) {
        int nHorses = 7;
        int pause = 200;
        if (args.length > 0) {
            int n = new Integer(args[0]);
            nHorses = n > 0 ? n :nHorses;
        }
        if (args.length > 1) {
            int p = new Integer(args[1]);
            pause = p > -1 ? p : pause;
        }
        new HorseRace(nHorses, pause);
    }
}

class Horse implements Runnable {

    private static int counter = 0;
    private final int id = counter++;
    private int strides = 0;
    private static Random random = new Random(41);
    private static CyclicBarrier cyclicBarrier;

    public Horse(CyclicBarrier cb) {
        cyclicBarrier = cb;
    }

    public synchronized int getStrides() {
        return this.strides;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    strides += random.nextInt(3);
                }
                cyclicBarrier.await();
            }
        } catch (InterruptedException e) {

        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Horse " + id + " ";
    }

    public String tracks() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < getStrides(); i++) {
            s.append("*");
        }
        s.append(id);
        return s.toString();
    }
}
