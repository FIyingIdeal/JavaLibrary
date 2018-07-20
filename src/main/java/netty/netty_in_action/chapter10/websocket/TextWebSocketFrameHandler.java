package netty.netty_in_action.chapter10.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @author yanchao
 * @date 2018/7/20 15:01
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ChannelGroup group;

    public TextWebSocketFrameHandler(ChannelGroup group) {
        this.group = group;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 握手成功
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            // 握手成功的话，从 ChannelPipeline 中删除 HttpRequestHandler，因为接下来不会接收 Http 消息了
            ctx.pipeline().remove(HttpRequestHandler.class);
            // 写消息给所有已连接的 WebSocket 客户端
            group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + " joined"));
            // 将 channel 添加到 ChannelGroup 中
            group.add(ctx.channel());
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 这里使用 retain() 的原因是：所有操作都是异步的，writeAndFlush() 可能会在以后完成，不希望其访问到无效的引用
        // TODO 那什么时候会释放掉呢？
        group.writeAndFlush(msg.retain());
    }
}
