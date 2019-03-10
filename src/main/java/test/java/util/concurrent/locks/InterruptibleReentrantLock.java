package test.java.util.concurrent.locks;

import java.util.Collection;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yanchao
 * @date 2019/3/9 12:52
 */
public class InterruptibleReentrantLock extends ReentrantLock {

    /**
     * {@link ReentrantLock#getWaitingThreads(Condition)} 是一个 protected 方法
     * 通过此方法将其暴露出来
     * @return
     */
    public Collection<Thread> pubGetWaitingThreads(final Condition condition) {
        return getWaitingThreads(condition);
    }

    /**
     * 中断所有等待指定 Condition 的线程
     * @param condition
     */
    public void interruptWaiters(final Condition condition) {
        final Collection<Thread> waitingThreads = getWaitingThreads(condition);
        waitingThreads.forEach(Thread::interrupt);
    }
}
