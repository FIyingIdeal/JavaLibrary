package test.java.util.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author yanchao
 * @date 2018/5/1 18:32
 */
public class CallableTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        // List<Future<String>> futureList = new ArrayList<>();
        for (int i = 10; i > 0; i--) {
            TaskWithResult callable = new TaskWithResult(i);
            // futureList.add(service.submit(callable));
            service.execute(new PrintResultTask(service.submit(callable)));
        }
        service.shutdown();
        /*try {
            for (Future<String> future : futureList) {
                System.out.println(future.get());
            }
        } catch(InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }*/
    }

}

class TaskWithResult implements Callable<String> {

    private int sleepTime;

    public TaskWithResult(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(this.sleepTime);
        String result = Thread.currentThread().getName() + " in call()";
        // System.out.println(result);
        return result;
    }
}

class PrintResultTask implements Runnable {

    private Future<String> future;

    public PrintResultTask(Future<String> future) {
        this.future = future;
    }

    @Override
    public void run() {
        while (!future.isDone()) {
            try {
                System.out.println(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}