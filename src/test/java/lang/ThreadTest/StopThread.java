package test.java.lang.ThreadTest;

/**
 * Created by Administrator on 2017/3/20.
 */
public class StopThread {

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myThread.setFlag(false);
    }

    //IDEA中一个java文件中定义两个类会报错，必须写成静态内部类
    static class MyThread extends Thread {
        private boolean flag = true;

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            while(flag) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("subThread running...");
            }
            System.out.println("SubThread stop!");
        }
    }
}