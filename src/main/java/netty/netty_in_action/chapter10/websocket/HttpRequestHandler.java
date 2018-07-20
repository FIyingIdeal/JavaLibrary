package netty.netty_in_action.chapter10.websocket;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.RandomAccessFile;

/**
 * @author yanchao
 * @date 2018/7/20 14:01
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static String webSocketUri;
    private static final String INDEX;

    static {
        INDEX = System.getProperty("user.dir") + "/index.html";
    }

    public HttpRequestHandler(String webSocketUri) {
        this.webSocketUri = webSocketUri;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (webSocketUri.equalsIgnoreCase(request.getUri())) {
            // retain() 禁止消息被释放
            ctx.fireChannelRead(request.retain());
        } else {
            if (HttpHeaders.is100ContinueExpected(request)) {
                send100Continue(ctx);
            }

            try (RandomAccessFile file = new RandomAccessFile(INDEX, "r")) {

                HttpResponse response = new DefaultHttpResponse(
                        request.getProtocolVersion(),
                        HttpResponseStatus.OK);
                response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html;charset=UTF-8");

                boolean keepAlive = HttpHeaders.isKeepAlive(request);
                if (keepAlive) {
                    response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());
                    response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
                }
                ctx.write(response);

                if (ctx.pipeline().get(SslHandler.class) == null) {
                    ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
                } else {
                    ctx.write(new ChunkedNioFile(file.getChannel()));
                }
                ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
                if (!keepAlive) {
                    future.addListener(ChannelFutureListener.CLOSE);
                }
            }
        }
    }

    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
