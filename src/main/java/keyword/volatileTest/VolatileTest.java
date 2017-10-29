package keyword.volatileTest;

import keyword.volatileTest.counter.NormalCounter;
import keyword.volatileTest.counter.VolatileCounter;
import org.junit.Test;

/**
 * Created by Administrator on 2017/2/7.
 */

/**
 * 锁(synchronized)提供了两种主要特性：互斥性(mutual exclusion)和可见性(visibility):
 * 互斥（原子）性：一次只允许一个线程持有某个特定的锁，实现对共享数据的协调访问，即一次只有一个线程能够使用该共享数据
 * 可见性：一个线程释放锁之前对共享数据做出的更改对于随后获得该锁的另一个线程是可见的
 *
 * volatile变量只具有synchronized的可见性，但不具备原子性
 * 正确使用volatile变量的条件：
 * 1.对变量的写操作不依赖于当前值；
 * 2.该变量没有包含在具有其他变量的不变式中。
 * 这表明可以被写入volatile变量的这些有效值独立于任何程序的状态，包括变量的当前状态
 */

public class VolatileTest {

    /**
     * 调用到的NormalCounter没有进行任何的线程同步的操作（volatile/synchronized）
     */
    @Test
    public void noSynchronizedTest() {
        for (int i = 0; i < 1000; i++) {
            new Thread() {
                @Override
                public void run() {
                    NormalCounter.inc();
                }
            }.start();
        }
        System.out.println(NormalCounter.count);
        //运行结果不确定 960、981...也可能是1000
        //原因：
    }

    /**
     * 调用到的VolatileCounter使用了volatile
     * 但由于使用方式的错误，致使无法达到效果
     */
    @Test
    public void unusedVolatileTest() {
        for (int i = 0; i < 1000; i++) {
            new Thread() {
                @Override
                public void run() {
                    VolatileCounter.inc();
                }
            }.start();
        }
        System.out.println(VolatileCounter.count);
        //运行结果不正确 960、938...
        //原因：volatile只能保证可见性，但仍然可能存在并发问题，如线程5和线程6同时读到变量值相同
    }
}
