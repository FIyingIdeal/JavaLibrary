package test.java.nio.channels;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

/**
 * @author yanchao
 * @date 2017/10/26 16:06
 */
public class ChannelTest {

    public static void main(String[] args) throws Exception {
        Charset charset = Charset.forName("UTF-8");
        //会根据不同的编码集调用对应的newDecoder()
        CharsetDecoder decoder = charset.newDecoder();
        try (FileChannel fileChannel = new FileInputStream("README.md").getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            CharBuffer charBuffer = CharBuffer.allocate(1024);
            int i = 0;
            while (fileChannel.read(buffer) != -1) {
                buffer.flip();

            /*
             * decode(ByteBuffer)方法内部已经进行了flip()操作，不需要自己再进行flip()操作
             * 但这样操作有一个问题，中文占用了两个字节，但ByteBuffer因容量不足，致使分两次进行读取，那有可能第一次只读到了汉字的第一个字节，
             * 第二次读到了汉字的第二个字节，那这个字的输出就是一个乱码了
             */
            /*charBuffer = charset.decode(buffer);
            System.out.print(charBuffer);
            //这里要进行清空
            buffer.clear();
            charBuffer.clear();*/

                /**  2017年10月26日20:27:08 燕
                 * 如果出现了中文被读了一半的情况，那在第二次读取的时候，buffer里的第一个byte就是“错误”的，即出现了 malformed(畸形)-input error，
                 * 这个时候，可以通过onMalformedInput()方法来设置解决策略，包括IGNORE、REPLACE、REPORT（默认行为，啥都不做，且decode异常）
                 * IGNORE：会将错误的byte忽略掉
                 * REPLACE：默认会使用"\uFFFD"进行替换，可以调用replaceWith(String)方法进行指定要替换成的字符串，但字符串的长度有限制，具体根据不同的编码集来定
                 * {@link CharsetDecoder#onMalformedInput(CodingErrorAction)}
                 * {@link CharsetDecoder#replaceWith(String)}
                 * {@link sun.nio.cs.UTF_8#newDecoder()} 如果使用REPLACE的话，此处可查看替换的字符串的长度，这里只引用了UTF_8的，其他编码请查阅sun.nio.cs包
                 */
                //TODO 如何解决多次读取只读到中文字符的一个byte的问题
                //TODO CharsetDecoder的reset问题
                CoderResult result = decoder.onMalformedInput(CodingErrorAction.REPLACE).replaceWith("错").decode(buffer, charBuffer, false);
                //System.out.println(result.toString());
                charBuffer.flip();
                while (charBuffer.hasRemaining()) {
                    System.out.print(charBuffer.get());
                }
                buffer.clear();
                charBuffer.compact();
                i++;
            }
        }
    }
}
