package test.java.lang.ThreadTest;

/**
 * Created by Administrator on 2017/2/10.
 * 让主线程睡眠5s两种实现方式：
 * 1.直接让主线程sleep 5s；
 * 2.定义一个子线程，子线程sleep 5s，并调用其join方法让主线程等待；
 */

public class MainStop5 {

    public static void main(String[] args) {
        System.out.println("Main Thread will sleep 5s!");
        //方法一：主线程sleep5s；
        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //方法二：定义一个子类，子类sleep5s，并调用其join方法让主线程等待
        MyThread myTHread = new MyThread();
        myTHread.start();
        try {
            myTHread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main Thread has slept 5s!");
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}