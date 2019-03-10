package test.java.util.concurrent.executor;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/2/28.
 * ExecutorService用完之后需要关闭（shutdown()），如果应用是通过一个main()方法启动的，之后main方法退出应用，
 * 如果应用中还有一个活动的ExecutorService它将还会保持运行，ExecutorService里的活动线程阻止了JVM的关闭
 * 调用shutdown()方法以后，ExecutorService并不会立即关闭，但它不会继续接受新的任务，
 * 而且一旦所有线程都执行完毕，ExecutorService将会关闭。所有在shutdown()之前提交的任务都会被执行。
 *
 * 如果想要立即关闭ExecutorService可以调用shutdownNow()方法，这样会立即尝试停止所有执行中的任务，
 * 并且会忽略掉那些已提交但尚未开始处理的任务，无法保证执行任务的正确执行。
 */
public class ExecutorTest {

    static class MyThread implements Runnable {

        @Override
        public void run() {
            //while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "正在执行！");
            //}
        }
    }

    public static void main(String[] args) {
        ExecutorService singleThread = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            singleThread.execute(new MyThread());
        }
        singleThread.shutdown();

        try {
            /**
             * awaitTermination() 在以下三种事件之一发生时，停止阻塞：
             * 1. 如果所有线程执行完毕以后，即使未到指定的时间也停止阻塞；
             * 2. 线程未执行完毕，到达指定的时间；
             * 3. 线程被中断；
             */
            singleThread.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程结束");
    }

    /**
     * 单线程的线程池
     * 打印出来的结果只能看到同一个ThreadName
     */
    @Test
    public void singleThreadExecutor() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            pool.execute(new MyThread());
        }
        pool.shutdown();

        try {
            pool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建固定大小的线程池
     * 当任务足够多的时候，最多启用指定数量的线程进行处理
     */
    @Test
    public void fixedThreadPool() {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            pool.execute(new MyThread());
        }
        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建一个可缓存的线程池
     * 创建的线程数量受限于系统环境（JVM）指定最大线程数量
     */
    @Test
    public void cachedThreadPool() {
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            pool.execute(new MyThread());
        }
        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 定时执行者服务
     */
    @Test
    public void scheduledThreadPool() {
        // 注意，此处的类型为ScheduledExecutorService，是一个interface
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);
        System.out.println("It will execute 2s later");
        for (int i = 0; i < 5; i++) {
            // 延迟两秒钟在执行
            //pool.schedule(new MyThread(), 2, TimeUnit.SECONDS);
            // 延迟1s后每秒执行一次
            pool.scheduleAtFixedRate(new MyThread(), 1, 1, TimeUnit.SECONDS);
        }

        try {
            pool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }

    @Test
    public void scheduledThreadPoolExecutor() {
        // 线程池之间的多个线程之间互不影响
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(2);
        for (int i = 0; i < 5; i++) {
            pool.scheduleAtFixedRate(new MyThread(), 1000, 1000, TimeUnit.MILLISECONDS);
        }

        try {
            pool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }

    /**
     * Callable与Runnable类似，但它可以在线程执行完毕以后获取结果，而Runnable不可以
     * 与实现Runnable接口的类需要重写run()方法一样，实现Callable接口的类需要重写call()方法
     */
    @Test
    public void callable() {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        Future<String> future = pool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "123";
            }
        });
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void RejectedExecutionHandler() {
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        // 如果不指定 RejectedExecutionHandler 的话，默认是 AbortPolicy，会丢弃任务并抛出异常
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(3), handler);

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    System.out.println("======" + Thread.currentThread().getName());
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
