package netty.netty_in_action.chapter1.component;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yanchao
 * @date 2018/7/12 14:19
 */
public class ByteBufTest {

    private static final Logger logger = LoggerFactory.getLogger(ByteBufTest.class);

    @Test
    public void array() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8);
        if (byteBuf.hasArray()) {
            byte[] array = byteBuf.array();
            // 15 ?
            logger.info("array length is {}", array.length);
            int offset = byteBuf.arrayOffset() + byteBuf.readerIndex(); // 0
            int length = byteBuf.readableBytes();                       // 5
            logger.info("offset is {}, length is {}", offset, length);
        }
    }

    @Test
    public void readerIndex() {
        ByteBuf buf = Unpooled.copiedBuffer("Netty in action", CharsetUtil.UTF_8);
        logger.info("buf.capacity() = {}", buf.capacity());
        logger.info("buf.getByte(0) = {}", (char) buf.getByte(0));
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        // readerIndex = 0, writerIndex = 15
        logger.info("readerIndex = {}, writerIndex = {}", readerIndex, writerIndex);

        buf.writeInt(3);
        writerIndex = buf.writerIndex();
        // writerIndex = 19，表明int占用了4个byte
        logger.info("writerIndex = {}", writerIndex);
        buf.writeByte('c');
        writerIndex = buf.writerIndex();
        // writerIndex = 20
        logger.info("writerIndex = {}", writerIndex);
    }

    @Test
    public void indexOf() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello World!", CharsetUtil.UTF_8);
        int readerIndex = byteBuf.readerIndex();
        int writerIndex = byteBuf.writerIndex();
        int index = byteBuf.indexOf(readerIndex, writerIndex, (byte)' ');
        logger.info("readerIndex = {}, writerIndex = {}, ' 'Index = {}", readerIndex, writerIndex, index);
    }

    @Test
    public void slice() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello World!", CharsetUtil.UTF_8);
        int readerIndex = byteBuf.readerIndex();
        int writerIndex = byteBuf.writerIndex();
        int index = byteBuf.indexOf(readerIndex, writerIndex, (byte)' ');
        String slice1 = byteBuf.slice(readerIndex, index).toString(CharsetUtil.UTF_8);
        String slice2 = byteBuf.slice(index +1,  writerIndex).toString(CharsetUtil.UTF_8);
        logger.info("slice1 is {}, slice2 is {}", slice1, slice2);
    }

}
