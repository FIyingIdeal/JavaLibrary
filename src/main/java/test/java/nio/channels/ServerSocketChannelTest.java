package test.java.nio.channels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author yanchao
 * @date 2017/10/28 17:38
 */
public class ServerSocketChannelTest {

    private final static int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        selector();
    }

    private static void selector() {
        ServerSocketChannel serverSocketChannel = null;
        Selector selector = null;
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(8080));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                if (selector.select() == 0) {
                    System.out.println("==");
                    continue;
                }

                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey selectionKey = keyIterator.next();
                    if (selectionKey.isAcceptable()) {
                        handleAccept(selectionKey);
                    } else if (selectionKey.isWritable()) {
                        handleWrite(selectionKey);
                    } else if (selectionKey.isReadable()) {
                        handleRead(selectionKey);
                    } else if (selectionKey.isConnectable()) {
                        System.out.println("Server is ready to connect!!");
                    }
                    keyIterator.remove();
                }

            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    private static void handleAccept(SelectionKey selectionKey) throws IOException {
        System.out.println("Server is ready to accept!");
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(BUFFER_SIZE));
    }

    private static void handleWrite(SelectionKey selectionKey) throws IOException {
        System.out.println("Server is ready to write!");
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        String message = "I'm Server handleWrite method";
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
        channel.register(selectionKey.selector(), SelectionKey.OP_READ);
    }

    private static void handleRead(SelectionKey selectionKey) throws IOException {
        System.out.println("Server is ready to read!");
        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        int bytesRead = channel.read(buffer);
        while (bytesRead != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
            //System.out.println();
            buffer.clear();
            bytesRead = channel.read(buffer);
        }
        /*if (bytesRead == -1) {
            channel.close();
        }*/
        buffer.clear();

        channel.register(selectionKey.selector(), SelectionKey.OP_WRITE);
    }
}
