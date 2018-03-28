package keyword.synchronizedTest;

import java.util.concurrent.TimeUnit;

/**
 * 内置锁测试
 */
public class IntrinsicLockTest {

    public static void main(String[] args) throws Exception {
        IntrinsicLockClass ilc = new IntrinsicLockClass();
        IntrinsicLockClass ilc1 = new IntrinsicLockClass();
        new Thread(() -> ilc.methodA(), "A").start();   // 其次会输出它，毫无疑问会先于线程C输出，因为它优先启动了，且占据了内置锁
        new Thread(() -> ilc1.methodB(), "B").start();  // 首先会输出它，因为ilc与ilc1是两个不同的对象，那么内置锁（当前对象）不一样，不会受线程A和C的影响
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> ilc.methodB(), "C").start();   // 最后输出，受线程A的影响，无法得到内置锁，只有当线程A执行完成以后才会执行
    }
}


class IntrinsicLockClass {

    public synchronized void methodA() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("methodA");
    }

    public synchronized void methodB() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("methodB");
    }
}
