package test.java.util.concurrent.countDownLatch;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/3/1.
 * CountDownLatch是一个并发构造，它允许一个或多个线程等待一系列执行操作的完成。
 * 初始化的时候指定一个初始量n，每调用一次countDown()就将n减1
 * 通过调用await()方法之一，线程可以阻塞  直到 n == 0
 */
public class CountDownLatchTest {
    @Test
    public void test() {
        CountDownLatch latch = new CountDownLatch(3);
        new Thread(new Waiter(latch)).start();
        new Thread(new Decrementer(latch)).start();

        //让当前线程等待子线程执行完毕
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 让 10 个线程同时打印 自己的数字
    @Test
    public void printNumber() throws InterruptedException {
        int threadCount = 10;
        ExecutorService service = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);

        class PrintNumberTask implements Runnable {
            private int number;
            public PrintNumberTask(int number) {
                this.number = number;
            }
            @Override
            public void run() {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " : " + this.number);
            }
        }

        for (int i = 0; i < threadCount; i++) {
            service.submit(new PrintNumberTask(i));
        }
        latch.countDown();
        service.shutdown();
        TimeUnit.SECONDS.sleep(3);
    }

    // 模拟并发，但 CountDownLatch 只能使用一次
    @Test
    public void http() throws IOException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        HttpGet get = new HttpGet("https://www.baidu.com");
        CloseableHttpClient client = HttpClientBuilder.create().build();

        class HttpRequestTask implements Runnable {
            CloseableHttpResponse response = null;
            @Override
            public void run() {
                try {
                    latch.await();
                    response = client.execute(get);
                    System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
                } catch (IOException | InterruptedException e) {
                } finally {
                    if (response != null) {
                        try {
                            response.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            service.submit(new HttpRequestTask());
        }
        latch.countDown();
        service.shutdown();
        TimeUnit.SECONDS.sleep(10);
    }

}
