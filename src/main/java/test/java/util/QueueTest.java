package test.java.util;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

/**
 * @author yanchao
 * @date 2018/10/30 18:06
 */
public class QueueTest {

    private static final Logger logger = Logger.getLogger(QueueTest.class.getName());

    private Queue<Integer> queue = new LinkedList<>();

    /**
     * offer() 与 add() 方法功能一致，但在 Queue 中更推荐使用 offer()
     */
    public void offer() {
        queue.offer(1);
        queue.offer(2);
    }

    /**
     * poll() 方法会读取并移除列表头元素
     */
    public void poll() {
        logger.info("" + queue.poll());
        logger.info("" + queue.poll());
        logger.info("" + queue.poll());
    }

    /**
     * 与 poll() 不同的是，peek() 只会读取列表头元素，而不会移除该元素
     */
    public void peek() {
        logger.info("" + queue.peek());
        logger.info("" + queue.peek());
        logger.info("" + queue.peek());
    }

    @Test
    public void offerAndPoll() {
        offer();
        poll();
    }

    @Test
    public void offerAndPeek() {
        offer();
        peek();
    }


}
