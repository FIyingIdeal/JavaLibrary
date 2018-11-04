package ThinkingInJava.chapter21Thread.CyclicBarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author yanchao
 * @date 2018/10/25 9:46
 */
public class MyHorseRace {

    static class Horse implements Runnable {

        private static int count = 0;
        private final int id = count++;
        private CyclicBarrier cyclicBarrier;
        private static Random random = new Random(47);
        private int step = 0;

        public Horse(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                step += random.nextInt(3);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public String toString() {
            return "Horse " + id + " ";
        }

        public int getStep() {
            return this.step;
        }

        public String trace() {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < step; i++) {
                stringBuilder.append("*");
            }
            stringBuilder.append(id);
            return stringBuilder.toString();
        }
    }

    private static final int FINISH_LINE = 75;
    private List<Horse> horses;
    private ExecutorService executorService;
    private CyclicBarrier cyclicBarrier;

    public MyHorseRace(int nHorse) {
        this.horses = new ArrayList<>(nHorse);
        this.executorService = Executors.newFixedThreadPool(nHorse);
        this.cyclicBarrier = new CyclicBarrier(nHorse, () -> {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < FINISH_LINE; i++) {
                s.append("=");
            }
            System.out.println(s.toString());

            for (int n = 0; n < nHorse; n++) {
                Horse horse = horses.get(n);
                System.out.println(horse.trace());
            }

            for (int j = 0; j < nHorse; j++) {
                Horse horse = horses.get(j);
                if (horse.getStep() >= FINISH_LINE) {
                    System.out.println(horse + " wins!");
                    executorService.shutdownNow();
                    return;
                }
            }

            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        for (int num = 0; num < nHorse; num++) {
            Horse horse = new Horse(this.cyclicBarrier);
            horses.add(horse);
            this.executorService.submit(horse);
        }
    }

    public static void main(String[] args) {
        MyHorseRace myHorseRace = new MyHorseRace(7);
    }
}
