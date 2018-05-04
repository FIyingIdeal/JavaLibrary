package ThinkingInJava.chapter21Thread.DelayQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author yanchao
 * @date 2018/4/30 16:05
 * DelayQueue是一个无界的BlockingQueue，用于放置实现了Delayed接口的对象，其中的对象只能在其到期时才能从队列中取走。
 * 这种队列是有序的，即队头对象的延迟到期时间最长（？？？）。如果没有任何延迟到期，那么就不会有任何头元素，并且poll将返回null（正因为这样，不能将null放置到这种队列中）
 *
 * DelayQueue中包含的任务的创建顺序对其取出顺序没有任何影响，任务是按照所期望的延迟顺序取出的。
 */
public class DelayQueueDemo {

    public static void main(String[] args) {
        Random random = new Random(47);
        ExecutorService service = Executors.newCachedThreadPool();
        DelayQueue<DelayedTask> queue = new DelayQueue<>();
        for (int i = 0; i < 20; i++) {
            queue.put(new DelayedTask(random.nextInt(5000)));
        }
        queue.add(new DelayedTask.EndSentinel(5000, service));
        service.execute(new DelayedTaskConsumer(queue));
    }

}

class DelayedTask implements Runnable, Delayed {

    private static int counter = 0;
    private final int id = counter++;
    private final int delta;
    private final long trigger;
    protected static List<DelayedTask> sequence = new ArrayList<>();
    public DelayedTask(int delayInMilliseconds) {
        delta = delayInMilliseconds;
        trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delta, TimeUnit.MILLISECONDS);
        sequence.add(this);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        DelayedTask that = (DelayedTask) o;
        if (trigger < that.trigger) return -1;
        if (trigger > that.trigger) return 1;
        return 0;
    }

    @Override
    public void run() {
        System.out.println(this + " ");
    }

    @Override
    public String toString() {
        return String.format(Thread.currentThread().getName() + "[%1$-4d]", delta) + " Task " + id;
    }

    public String summary() {
        return "( " + id + ":" + delta + ")";
    }

    public static class EndSentinel extends DelayedTask {
        private ExecutorService service;
        public EndSentinel(int delay, ExecutorService service) {
            super(delay);
            this.service = service;
        }

        @Override
        public void run() {
            for (DelayedTask pt : sequence) {
                System.out.println(pt.summary() + " ");
            }
            System.out.println();
            System.out.println(this + " Calling shutdownNow");
            service.shutdownNow();
        }
    }
}

class DelayedTaskConsumer implements Runnable {

    private DelayQueue<DelayedTask> queue;
    public DelayedTaskConsumer(DelayQueue<DelayedTask> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                queue.take().run();
            }
        } catch (InterruptedException e) {

        }
        System.out.println("Finished DelayedTaskConsumer");
    }
}
