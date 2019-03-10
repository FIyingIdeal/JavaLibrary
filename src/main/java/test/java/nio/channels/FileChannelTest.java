package test.java.nio.channels;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author yanchao
 * @date 2017/10/27 18:55
 */
public class FileChannelTest {

    /**
     * FileChannel无法设置成非阻塞模式，且与SocketChannel不同的是，它没有无参的open()方法来获取FileChannel实例
     * 可以通过FileInputStream、FileOutputStream、RandomAccessFile对应的getChannel()方法来获取
     */
    @Test
    public void test() {
        try (RandomAccessFile file = new RandomAccessFile("filepath.txt", "rw")) {
            //获取Channel:可以通过FileInputStream、FileOutputStream、RandomAccessFile来获取FileChannel实例

            FileChannel channel = file.getChannel();

            //从FileChannel读取数据
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int bytesRead = channel.read(byteBuffer);
            while (bytesRead != -1) {
                //将buffer变为“读取模式”
                byteBuffer.flip();
                //从buffer中获取数据
                while (byteBuffer.hasRemaining()) {
                    System.out.println(byteBuffer.get());
                }
                //读取完毕以后，要“清空” buffer，可以使用clear()或compact()
                //两者的区别是，在buffer中的数据未完全处理的情况下，clear()直接忽略未处理的数据，而compact()方法会保留未处理数据，下次操作依然可以读取到
                byteBuffer.clear();     //byteBuffer.compact();
            }


            //向FileChannel中写数据
            String data = "testdata";
            /**
             * 向Buffer中填充数据可以使用静态方法wrap()或调用实例方法put()
             */
            //使用wrap()填充数据
            ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
            //使用put()填充数据
            buffer.put(data.getBytes());

            //使buffer变为读取模式
            buffer.flip();

            //注意：读取的时候是在while循环中，是因为无法保证write()方法一次能向FileChannel中写入多少字节
            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }

            //size()
            long channleSize = channel.size();

            //truncate(int)：截取FileChannel中指定的长度，剩余的部分将被删除
            FileChannel truncateFileChannel = channel.truncate(1024);

            //force(boolean)：将Channel中尚未写入磁盘的数据强制写到磁盘上，参数表示是否同时将文件元数据（权限信息等）写到磁盘上
            //出于性能考虑，OS会将数据缓存到内存中，所以无法保证写入到FileChannel里的数据一定会即时写入到磁盘上，要保证这一点，需要force()的帮助
            channel.force(true);

            //FileChannel使用完毕以后要关闭掉
            truncateFileChannel.close();
            channel.close();
        } catch (Exception e) {}
    }

    @Test
    public void FileChannelAndBufferTest() {
        CharBuffer charBuffer = CharBuffer.allocate(100);
        Path path = Paths.get("G:/IDEAWorkspace/JavaLibrary/src/main/java/test/java/nio/channels/FileChannelTest.java");
        int counter = 0;
        try (FileReader reader = new FileReader(path.toFile());
             PrintWriter writer = new PrintWriter(System.out)) {
            while (reader.read(charBuffer) != -1) {
                charBuffer.flip();
                while (charBuffer.hasRemaining()) {
                    writer.print(charBuffer.get());
                }
                charBuffer.clear();
                System.out.println("=====counter is " + counter++ + "=======");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
