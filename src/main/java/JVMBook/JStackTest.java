package JVMBook;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 使用 jstack 对线程堆栈信息进行分析
 * @see <a href="http://www.hollischuang.com/archives/110">Java命令学习系列（二）——Jstack</a>
 *
 * 运行该测试类，会启动多个线程，这个多个线程会争抢同一个锁，只有一个线程会获取到锁，但这个线程会休眠很长时间。
 *
 * 1. 使用 jps 查看当前程序的 pid；
 * 2. 通过 jstack -l {pid} 查看当前线程的堆栈信息；
 *
 * @author yanchao
 * @date 2020-08-04 19:14
 */
public class JStackTest {

    private static final Object LOCK = new Object();

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(new JStackTask());
        service.submit(new JStackTask());

        service.awaitTermination(10, TimeUnit.SECONDS);
    }


    private static class JStackTask implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " enter ~ ");
            synchronized (LOCK) {
                System.out.println(Thread.currentThread().getName() + " get lock!");
                try {
                    TimeUnit.MINUTES.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
