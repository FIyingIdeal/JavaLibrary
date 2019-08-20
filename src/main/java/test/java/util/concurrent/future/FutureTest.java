package test.java.util.concurrent.future;

import java.util.concurrent.*;

/**
 * @author yanchao
 * @date 2019-08-16 18:07
 */
public class FutureTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        Future<String> future = service.submit(new MyCallable());
        try {
            String result = future.get();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();
    }

    private static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            TimeUnit.SECONDS.sleep(10);
            return "abc";
        }
    }

}
