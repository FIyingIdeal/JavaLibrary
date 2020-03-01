package test.java.util.concurrent.delayqueue;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author yanchao
 * @date 2019-08-13 20:32
 * Java 延迟队列共三部分：
 *     1. 实现了 {@link Delayed} 接口的消息体；
 *     2. 消费消息的消费者；
 *     3. 存放消息的延迟队列；
 *
 * 其中第一条实现了 {@link Delayed} 接口的消息体需要重写两个方法：
 * {@link Delayed#compareTo(Object)}    决定插入延迟队列消息体在队列中的位置，这个一定要按照延迟时间来排序（更准确说应该是执行时间点），否则可能造成到期的消息没有被立即消费的可能
 * {@link Delayed#getDelay(TimeUnit)}   决定消息的延迟时间。需要注意 unit.convert(sourceWaitTime, sourceWaitTimeUnit) 的时间单位，否则可能造成 CPU 的空转
 */
public class DelayQueueTest {

    /**
     * 实现了 {@link Delayed} 接口的消息体
     */
    @Data
    private static class DelayedElement implements Delayed {

        private String delayedElementName;
        /**
         * 延迟时间，单位毫秒
         * 在构造方法中将其转换为执行的具体时间点
         */
        private long delayMillis;
        /**
         * 执行的具体时间点（一旦构造以后，其执行时间就固定了）。而在 getDelay(TimeUnit) 方法中需要计算延迟时间
         * delayMillis 与 executeTimeMillis 其实只需要一个就行，这里分开是为了更清晰明了
         */
        private long executeTimeMillis;

        public DelayedElement(String name, long delayMillis) {
            this.delayedElementName = name;
            this.delayMillis = delayMillis;
            this.executeTimeMillis = System.currentTimeMillis() + delayMillis;
        }

        /**
         * 由于 {@link DelayQueue} 内部使用的是一个优先级队列 {@link java.util.PriorityQueue} 来保存元素的，
         * 且这个优先级队列并没有指定 {@link java.util.Comparator}，
         * 所以保存到优先级队列中的元素必须是实现了 {@link Comparable} 接口的类对象。
         *
         * 在向 {@link DelayQueue} 中插入数据时，相当于是向一个 {@code PriorityQueue} 优先级队列中插入数据，
         * 在插入的时候就会构造一个最大化堆或最小化堆
         * @param o     {@link DelayedElement}
         * @return      比较执行时间 {@link DelayedElement#executeTimeMillis}，返回值可为 1  0  -1
         */
        @Override
        public int compareTo(Delayed o) {
            DelayedElement element = (DelayedElement) o;
            return Long.compare(this.executeTimeMillis, element.executeTimeMillis);
        }

        /**
         * {@link DelayQueue#take()} 方法会根据这个方法返回值来判断是否将队列中的 {@link Delayed} 对象返回，如果返回值大于0，则睡眠；
         * 如果返回值小于等于0，则返回。
         * @param unit  在 {@link DelayQueue#take()} 方法中，传入到方法中的参数值是 {@link TimeUnit#NANOSECONDS}
         * @return      如果返回值大于0，表示延迟时间还没到，需要继续等待；否则准备返回执行。
         */
        @Override
        public long getDelay(TimeUnit unit) {
            // 这里需要注意，this.executeTimeMillis 是一个固定的执行时间，而 System.currentTimeMillis() 是一直变化的
            return unit.convert(this.executeTimeMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 这个类的作用是为了测试 {@link Delayed#compareTo(Object)} 方法对延迟队列的影响
     * 详细测试请参考下边的测试方法 {@link this#unusualDelayElementQueueTest()}
     *
     * 注意这个 {@link Delayed} 实现类（继承自 {@link DelayedElement}，{@link DelayedElement} 实现了 {@link Delayed} 接口）
     * 重写了 {@link DelayedElement#compareTo(Delayed)} 方法。通过 {@link DelayedElement#delayedElementName} 来进行排序
     */
    private static class UnusualDelayedElement extends DelayedElement {

        public UnusualDelayedElement(String name, long delayMillis) {
            super(name, delayMillis);
        }

        @Override
        public int compareTo(Delayed o) {
            DelayedElement delayedElement = (DelayedElement)o;
            return this.getDelayedElementName().compareTo(delayedElement.delayedElementName);
        }
    }

    /**
     * 延迟队列消费者
     */
    @Slf4j
    private static class DelayedConsumer implements Runnable {

        private DelayQueue<? extends DelayedElement> delayQueue;

        public DelayedConsumer(DelayQueue<? extends DelayedElement> delayQueue) {
            this.delayQueue = delayQueue;
        }

        @Override
        public void run() {
            // while (true) {
            // just for test
            while (this.delayQueue.size() > 0) {
                try {
                    DelayedElement delayedElement = this.delayQueue.take();
                    // 干该干的事情，这里执行了简单的打印操作
                    log.info("Thread's name is {}, DelayedElement's name is {}, DelayedElement's delayTime is {}",
                            Thread.currentThread().getName(), delayedElement.delayedElementName, delayedElement.delayMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // do other things!
                }
            }
        }
    }

    /**
     * 使用 {@link DelayedElement} 对 {@link DelayQueue} 进行测试
     */
    @Test
    public void delayElementQueueTest() {
        ExecutorService service = Executors.newFixedThreadPool(3);
        // 存放消息的延迟队列
        DelayQueue<DelayedElement> queue = new DelayQueue<>();
        Random random = new Random(47);
        for (int i = 0 ; i < 10; i++) {
            queue.offer(new DelayedElement(String.valueOf(i),
                    TimeUnit.MILLISECONDS.convert(random.nextInt(10), TimeUnit.SECONDS)));
        }

        for (int threadNum = 0; threadNum < 3; threadNum++) {
            service.execute(new DelayedConsumer(queue));
        }

        try {
            service.awaitTermination(15, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            // do other thing!
        }
        service.shutdown();
    }


    /**
     * 测试 {@link Delayed#compareTo(Object)} 对延迟队列的影响。
     * 通过结果观察，发现 {@link Delayed#compareTo(Object)} 直接影响了队列中元素的位置，而取元素的是从队头来获取，未到执行时间直接await
     * 即如果 {@link Delayed#compareTo(Object)} 方法的排序规则并非是按照延迟时间来排序的，那延迟队列会出现一定的误差：
     *      当队头元素的延迟时间大于其他元素的延迟时间的话，即使其他元素已经可执行，也要等到队头到延迟时间后才被执行
     */
    @Test
    public void unusualDelayElementQueueTest() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        DelayQueue<UnusualDelayedElement> queue = new DelayQueue<>();

        /** 为了测试 compareTo 对延迟队列的影响，这里只向队列中插入两个 Delayed 对象（记为 d1 和 d2），两者的特征如下：
         *     1. d1 的延迟时间 {@link UnusualDelayedElement#delayMillis} 要小于 d2 的延迟时间，即 d1 理论上来说要优先于 d2 执行，
         *          为什么说理论上呢？因为其还会受 compareTo() 影响；
         *     2. d1 的 {@link UnusualDelayedElement#delayedElementName} 要大于 d2 的 name，即 d1 在队列中的位置是在 d2 之后；
         *          参见 {@link UnusualDelayedElement#compareTo(Delayed)}
         */
        UnusualDelayedElement d1 = new UnusualDelayedElement("zzz", 1000);
        UnusualDelayedElement d2 = new UnusualDelayedElement("aaa", 5000);
        queue.offer(d1);
        queue.offer(d2);
        service.execute(new DelayedConsumer(queue));

        try {
            service.awaitTermination(8, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            // do other thing!
        }
        service.shutdown();
    }
}
