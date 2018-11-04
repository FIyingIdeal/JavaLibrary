package netty.netty0_compare_with_jdk;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author yanchao
 * @date 2018/7/11 22:56
 */
public class NioServer {

    private final int port;
    private volatile boolean flag = true;
    private static final int TIMEOUT = 3000;
    private static final int BUF_SIZE = 256;

    public NioServer(int port) {
        this.port = port;
    }

    public void service() {

        try (ServerSocketChannel channel = ServerSocketChannel.open();
             Selector selector = Selector.open();
             ServerSocket serverSocket = channel.socket();) {

            channel.configureBlocking(false);
            serverSocket.bind(new InetSocketAddress(port));
            channel.register(selector, SelectionKey.OP_ACCEPT);
            while (flag) {
                if (selector.select(TIMEOUT) == 0) {
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();

                    if (selectionKey.isAcceptable()) {
                        SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(BUF_SIZE));
                    }

                    if (selectionKey.isReadable()) {
                        SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                        long length = clientChannel.read(byteBuffer);
                        // TODo
                    }

                    if (selectionKey.isWritable()) {

                    }
                }
            }
        } catch (IOException e) {}
    }
}
