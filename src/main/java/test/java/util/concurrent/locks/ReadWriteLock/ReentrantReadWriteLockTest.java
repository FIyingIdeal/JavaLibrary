package test.java.util.concurrent.locks.ReadWriteLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Administrator on 2017/7/13.
 */
public class ReentrantReadWriteLockTest {

    public static void main(String[] args) {
        for (int i = 0; i < 8; i++) {
            new MyThread(String.valueOf(i)).start();
            try {
                Thread.sleep(1000);
            } catch(Exception e) {}
        }
    }

    static class MyThread extends Thread {

        public MyThread(String threadName) {
            super(threadName);
        }

        @Override
        public void run() {
            if ("5".equals(this.getName())) {
                Job.put("1", "1");
            }
            //Job.get("1", this.getName());
            //*else {
            System.out.println("Thread " + this.getName() + " get value : " + Job.get("1", this.getName()));
            //}*/
        }
    }

    static class Job {
        private static Map<String, String> map = new HashMap<String, String>();
        static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        static Lock readLock = lock.readLock();
        static Lock writeLock = lock.writeLock();

        public static void put(String key ,String value) {
            System.out.println("before get writeLock : " + lock.getReadLockCount());
            writeLock.lock();
            //after每次都是0，因为读锁存在的话，写锁不能获取
            System.out.println("after get writeLock : " + lock.getReadLockCount());
            try {
                Thread.sleep(2000);
                map.put(key, value);
            } catch(Exception e) {
            } finally {
                writeLock.unlock();
            }
        }

        public static String get(String key, String threadName) {
            String value = null;
            readLock.lock();
            System.out.println(threadName + " comes in, readLock's num : " + lock.getReadLockCount());
            try {
                Thread.sleep(8000);
                value = map.get(key);
            } catch (Exception e) {

            } finally {
                System.out.println(threadName + " will release the readLock!");
                readLock.unlock();
            }
            return value;
        }

    }
}


