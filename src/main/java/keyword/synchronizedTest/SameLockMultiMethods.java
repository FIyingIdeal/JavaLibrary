package keyword.synchronizedTest;

/**
 * 同一个线程调用不同方法，对通过一个对象进行加锁
 * @author yanchao
 * @date 2020-08-02 15:58
 */
public class SameLockMultiMethods {

    private final Object lockObject = new Object();

    private void lockMethod1() {
        System.out.println("Will lock : method1");
        synchronized (lockObject) {
            System.out.println("Locked : method1");
            lockMethod2();
        }
    }

    private void lockMethod2() {
        System.out.println("Will lock : method2");
        synchronized (lockObject) {
            System.out.println("Locked : method2");
        }
    }

    public static void main(String[] args) {
        SameLockMultiMethods sameLockMultiMethods = new SameLockMultiMethods();

        Thread thread = new Thread(
                () -> {
                    sameLockMultiMethods.lockMethod1();
                });
        thread.start();
    }
}
