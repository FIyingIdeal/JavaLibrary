package keyword.synchronizedTest;

/**
 * Created by Administrator on 2017/7/13.
 */
/**
 * 参考：http://blog.csdn.net/xiao__gui/article/details/8188833
 */
public class SynchronizedTest {

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new MyThread(String.valueOf(i)).start();
        }
    }
}

class Job {

    private String jobName;
    private static Object obj = new Object();

    public Job(String jobName) {
        this.jobName = jobName;
    }

    //将synchronized加载方法声明上时(public synchronized void print())是相当于对当前对象加锁
    public void print() {
        synchronized (obj) {
            System.out.println("start job " + this.jobName);
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("end job " + this.jobName);
        }
    }
}

class MyThread extends Thread {

    private String jobName;

    public MyThread(String jobName) {
        this.jobName = jobName;
    }

    @Override
    public void run() {
        Job job = new Job(jobName);
        job.print();
    }
}
