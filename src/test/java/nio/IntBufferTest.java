package test.java.nio;

import org.junit.Test;

import java.nio.IntBuffer;
import java.util.stream.IntStream;

/**
 * @author yanchao
 * @date 2017/10/27 10:45
 */
public class IntBufferTest {

    /**
     * IntBuffer#put()与get(int[], int, int)测试
     * get(int[] destination, int offset, int length)方法是将Buffer中position位置起的共length个元素写到目标数组destination的index为offset起的位置中。
     */
    @Test
    public void putAndGet() {
        IntBuffer buffer = IntBuffer.allocate(40);
        buffer.put(new int[] {1,2,3,4,5,6,7,8,9,0});
        buffer.flip();
        int[] nums = new int[5];
        buffer.get(nums, 2, 2);
        //注意，这里要使用IntStream.of，而不是Stream.of
        IntStream.of(nums).forEach(num -> {System.out.println(num);});
    }
}
