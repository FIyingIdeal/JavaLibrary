package test.java.util.concurrent.cyclicBarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yanchao
 * @date 2018/10/25 10:27
 *
 * 有 5 个线程，每个线程依次打印 1 - 10，当每一个线程都打印了同样的数字后才能开始打印之后的数字。
 * 两种写法：一个Task自己维护number，一个是读取公共number
 */
public class PrintNumber {

    class PrintNumberTask implements Runnable {

        private CyclicBarrier cyclicBarrier;

        private int number = 1;
        public void addNumber() {
            number++;
        }

        public PrintNumberTask(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }
        @Override
        public void run() {
            while (!Thread.interrupted() && PrintNumber.printNumber <= 10) {
                // System.out.println(Thread.currentThread().getName() + " : " + number);
                System.out.println(Thread.currentThread().getName() + " : " + PrintNumber.printNumber);
                try {
                    this.cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<PrintNumberTask> tasks = new ArrayList<>();
    private ExecutorService service = Executors.newCachedThreadPool();
    private int taskNum = 5;
    private CyclicBarrier cyclicBarrier;

    private static int printNumber = 1;

    public PrintNumber() {
        cyclicBarrier = new CyclicBarrier(taskNum, () -> {
            /*for (int i = 0; i < taskNum; i++) {
                tasks.get(i).addNumber();
            }*/
            printNumber++;
        });
        for (int j = 0; j < taskNum; j++) {
            PrintNumberTask task = new PrintNumberTask(cyclicBarrier);
            tasks.add(task);
            service.submit(task);
        }
    }

    public static void main(String[] args) {
        PrintNumber printNumber = new PrintNumber();
        printNumber.service.shutdown();
    }

}
