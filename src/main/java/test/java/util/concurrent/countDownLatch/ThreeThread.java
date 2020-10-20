package test.java.util.concurrent.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author yanchao
 * @date 2020-09-03 12:56
 */
public class ThreeThread {

    public static void main(String[] args) {
        CountDownLatch latchA = new CountDownLatch(1);
        CountDownLatch latchB = new CountDownLatch(1);
        CountDownLatch latchC = new CountDownLatch(1);

        Thread A = new Thread(new Task(latchA, latchB, "A"));
        Thread B = new Thread(new Task(latchB, latchC, "B"));
        Thread C = new Thread(new Task(latchC, latchC, "C"));

        A.start();
        B.start();
        C.start();
        latchA.countDown();
    }


    private static class Task implements Runnable {

        private CountDownLatch perLatch;
        private CountDownLatch afterLatch;
        private String name;

        public Task(CountDownLatch perLatch, CountDownLatch afterLatch, String name) {
            this.perLatch = perLatch;
            this.afterLatch = afterLatch;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                perLatch.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(name);
            // do something
            afterLatch.countDown();
        }
    }
}
