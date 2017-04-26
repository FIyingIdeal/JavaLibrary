package test.java.util.concurrent.future;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/3/2.
 * 通过Future的get()方法获取线程的执行结果
 */
public class GetFutureResult {
    public static void main(String[] args) {
        GetFutureResult futureResult = new GetFutureResult();
        List<Future<String>> futureList = new ArrayList<Future<String>>();
        futureResult.createThreadPool(3, futureList);
        futureResult.mainSleep();
        futureResult.handleResult(futureList);
    }

    public void createThreadPool(int threadNum, List<Future<String>> futureList) {
        ExecutorService pool = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < 5; i++) {
            Future<String> future = pool.submit(getJob(i));
            futureList.add(future);
        }
        //需要关闭
        pool.shutdown();
    }

    public Callable<String> getJob(int i) {
        final int time = new Random().nextInt(10);
        return () -> {
            System.out.println(i + "---" + time);
            Thread.sleep(time * 1000);
            if (i == 3) {
                Thread.sleep(5000);
            }
            return "thread-" + i;
        };
        /*return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(time * 1000);
                return "thread-" + i;
            }
        };*/
    }

    public void mainSleep() {
        try {
            System.out.println("do other things...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void handleResult(List<Future<String>> futureList) {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.execute(getCollectionJob(futureList));
        pool.shutdown();
    }

    public Runnable getCollectionJob(List<Future<String>> futureList) {
        return () -> {
            for (Future<String> future : futureList) {
                while (true) {
                    try {
                        if (future.isDone() && !future.isCancelled()) {
                            System.out.println("future:" + future + ",result :" + future.get());
                            break;
                        } else {
                            System.out.println("waiting " + future);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
