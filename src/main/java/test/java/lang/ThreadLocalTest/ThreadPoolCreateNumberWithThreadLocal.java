package test.java.lang.ThreadLocalTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yanchao
 * @date 2018/4/16 9:50
 */
public class ThreadPoolCreateNumberWithThreadLocal {

    private static final ThreadLocal<Integer> numberThreadLocal = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        // 向线程池提交三个任务，这样就会使用上边指定好的线程池中的三个线程来执行

        // 如果指定超过三个任务的话，三个线程各领一个任务执行，其余的任务会放置到队列中等待，当线程执行完当前任务后就会从队列中领取任务
        for (int i = 0; i < 4; i++) {
            executorService.execute(new NumberGenerator(numberThreadLocal));
        }
        // 记得要shutdown()调用，否则即使执行完成java进程也无法停止
        executorService.shutdown();
    }
}

class NumberGenerator implements Runnable {

    ThreadLocal<Integer> threadLocal;

    public NumberGenerator(ThreadLocal<Integer> threadLocal) {
        this.threadLocal = threadLocal;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Integer number = threadLocal.get();
            System.out.println(Thread.currentThread().getName() + " : " + number);
            threadLocal.set(++number);
        }
        // threadLocal.set(0);
    }
}
