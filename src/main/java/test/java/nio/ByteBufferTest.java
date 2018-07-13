package test.java.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author yanchao
 * @date 2018/7/12 10:30
 */
public class ByteBufferTest {

    @Test
    public void allocate() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put("Hello".getBytes());
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            System.out.print((char) byteBuffer.get());
        }
    }

    @Test
    public void slice() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put("Hello".getBytes());
        ByteBuffer sliceBuffer = byteBuffer.slice();
        sliceBuffer.put("World".getBytes());

        byteBuffer.put("!".getBytes());

        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            System.out.print((char) byteBuffer.get());  // Hello!
        }

        System.out.println();

        sliceBuffer.flip();
        while (sliceBuffer.hasRemaining()) {
            System.out.print((char) sliceBuffer.get());  // !orld
        }

    }

    /**
     * duplicate() 会将原来的 ByteBuffer 原样复制，且与原来的 ByteBuffer 操作同一个 byte[]
     * 区别是它们的四个指针是可以自己维护的，即赋值出来的 ByteBuffer 可以根据操作修改自己的指针而不影响被赋值的 ByteBuffer的指针
     * 但他们共享的 byte[] 是互相印象的
     *
     * 简单说： 同一个 byte[] 有两套指针对其操作，而维护指针的就是这两个ByteBuffer
     */
    @Test
    public void duplicate() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put("Hello".getBytes());
        ByteBuffer duplicateBuffer = byteBuffer.duplicate();
        duplicateBuffer.put("World".getBytes());

        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            System.out.print((char) byteBuffer.get());  // Hello
        }

        System.out.println();

        duplicateBuffer.flip();
        while (duplicateBuffer.hasRemaining()) {
            System.out.print((char) duplicateBuffer.get());  // HelloWorld
        }

        System.out.println();

        // duplicateBuffer更新了整个 byte[] ，byteBuffer也会跟着变，但它只能访问到自己指针指定的区域
        duplicateBuffer.clear();
        duplicateBuffer.put("0123456789".getBytes());
        byteBuffer.rewind();
        while (byteBuffer.hasRemaining()) {
            System.out.print((char) byteBuffer.get());  // 01234
        }

    }


    @Test
    public void substring() {
        String s = "012345";
    }

}
