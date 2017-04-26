package test.java.lang.ThreadLocalTest;

import java.util.Map;

/**
 * Created by Administrator on 2016/7/25.
 */
public class ThreadLocalDefineTest extends Thread{

    private int time = 0;

    public ThreadLocalDefineTest(String name) {
        super(name);
    }

    @Override
    public void run() {

        while (++time < 10) {
            Map<String, Object> map = ThreadLocalDefine.getThreadLocal();
            if ("Thread1".equals(Thread.currentThread().getName())) {
                int maxExceptionCount = Integer.parseInt(map.get("maxExceptionCount").toString());
                map.put("maxExceptionCount", ++maxExceptionCount);
            }
            System.out.println(Thread.currentThread().getName() + "=======> maxExceptionCount = " + map.get("maxExceptionCount"));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadLocalDefineTest t1 = new ThreadLocalDefineTest("Thread1");
        ThreadLocalDefineTest t2 = new ThreadLocalDefineTest("Thread2");
        t1.start();t2.start();
    }
}
