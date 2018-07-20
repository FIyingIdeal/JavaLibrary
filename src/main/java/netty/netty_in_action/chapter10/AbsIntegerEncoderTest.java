package netty.netty_in_action.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author yanchao
 * @date 2018/7/20 10:24
 */
public class AbsIntegerEncoderTest {

    @Test
    public void test() {
        ByteBuf byteBuf = Unpooled.buffer();
        for (int i = 0; i < 10; i++) {
            byteBuf.writeInt(i * -1);
        }

        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        channel.writeOutbound(byteBuf);
        channel.finish();

        for (int i = 0; i < 10; i++) {
            int num = (int) channel.readOutbound();
            System.out.println("num is " + num);
            Assert.assertEquals(num, i);
        }
        Assert.assertNull(channel.readOutbound());
    }
}
