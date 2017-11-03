package test.java.nio.channels.SocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author yanchao
 * @date 2017/10/31 9:58
 *
 * 案例来源：http://blog.csdn.net/anxpp/article/details/51512200
 */
public class ClientHandle implements Runnable {

    private String host;
    private int port;
    private volatile boolean started;
    private SocketChannel socketChannel;
    private Selector selector;

    public ClientHandle(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            started = true;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        this.started = false;
    }

    @Override
    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            System.out.println("服务器连接失败！");
            e.printStackTrace();
            System.exit(1);
        }

        while (started) {
            try {
                this.selector.select(1000);
                Set<SelectionKey> keys = this.selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(selectionKey);
                    } catch (IOException e) {
                        if (selectionKey != null) {
                            selectionKey.cancel();
                            if (selectionKey.channel() != null) {
                                selectionKey.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void doConnect() throws IOException {
        if (socketChannel.connect(new InetSocketAddress(host, port))) {
            System.out.println("Client ==> start connect!");
        } else {
            socketChannel.register(this.selector, SelectionKey.OP_CONNECT);
        }
    }

    private void handleInput(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isValid()) {
            SocketChannel channel = (SocketChannel) selectionKey.channel();
            if (selectionKey.isConnectable()) {
                if (channel.finishConnect()) {
                    System.out.println("Client ==> connect success!");
                } else {
                    System.out.println("Client ==> connect failure!");
                    System.exit(1);
                }
            }
            if (selectionKey.isReadable()) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                /*int length = channel.read(buffer);
                if (length > 0) {
                    buffer.flip();
                    byte[] readBytes = new byte[buffer.remaining()];
                    buffer.get(readBytes);
                    String result = new String(readBytes, "UTF-8");
                    System.out.println("计算结果：" + result);
                } else if (length < 0) {
                    selectionKey.cancel();
                    channel.close();
                }*/
                StringBuilder sb = new StringBuilder("计算结果：");
                //TODO -.-!!  The number of bytes read, possibly zero, or -1 if the channel has reached end-of-stream
                //XXXChannel#read(Buffer)到末尾的时候有可能返回的是0或-1...
                while (channel.read(buffer) > 0) {
                    buffer.flip();
                    sb.append(new String(buffer.array(), "UTF-8"));
                    buffer.clear();
                }
                System.out.println(sb.toString());
            }
        }
    }

    private void doWrite(SocketChannel channel, String message) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        channel.write(buffer);
        /*//直接使用wrap
        byte[] bytes = message.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        channel.write(buffer);*/
    }

    public void sendMessage(String message)  throws IOException {
        this.socketChannel.register(this.selector, SelectionKey.OP_READ);
        this.doWrite(this.socketChannel, message);
    }
}
