package test.java.util.concurrent.future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/3/2.
 */
public class CompletionServiceTest {
    public static void main(String[] args) {
        try {
            completionServiceCounter();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void completionServiceCounter()
            throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newCachedThreadPool();
        CompletionService<Integer> completionService
                = new ExecutorCompletionService<Integer>(service);
        final int threadNum = 5;
        for (int i = 0; i < threadNum; i++) {
            completionService.submit(getTask(i));
        }
        int sum = 0, temp = 0;
        for (int i = 0; i < threadNum; i++) {
            temp = completionService.take().get();
            sum += temp;
            System.out.println(temp + "\t");
        }
        System.out.println("sum = " + sum);
        service.shutdown();
    }

    public static Callable<Integer> getTask(int i) {
        final int time = new Random().nextInt(10) * 1000;
        return () -> {
            System.out.println("thread" + i + " sleep " + time);
            Thread.sleep(time);
            return i;
        };
    }
}
