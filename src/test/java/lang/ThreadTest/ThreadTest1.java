package test.java.lang.ThreadTest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/21.
 */
public class ThreadTest1 {
    ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public int getNextValue() {
        return threadLocal.get().intValue();
    }

    public void setValue() {
        threadLocal.set(threadLocal.get() + 1);
    }

    public static void main(String[] args) {
        ThreadTest1 test1 = new ThreadTest1();
        Map<String, ThreadTest1> map = new HashMap<>();
        map.put("message", test1);
        MyThread thread1 = new MyThread(map, "Thread1");
        MyThread thread2 = new MyThread(map, "Thread2");
        thread1.start();
        thread2.start();
    }

}

class MyThread extends Thread {

    private Map<String, ThreadTest1> map;
    private static int i = 1;

    public MyThread(Map<String, ThreadTest1> map, String name) {
        super(name);
        this.map = map;
    }

    @Override
    public void run() {
        while (i < 2) {
            if ("Thread1".equals(Thread.currentThread().getName())) {
                System.out.println("Thread1 update map info");
                map.get("message").setValue();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " get message : " + map.get("message").getNextValue());
        }
    }

}
