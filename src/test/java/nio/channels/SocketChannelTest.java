package test.java.nio.channels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author yanchao
 * @date 2017/10/28 17:39
 */
public class SocketChannelTest {

    public static void main(String[] args) {
        client();
    }


    private static void client() {
        SocketChannel socketChannel = null;
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("192.168.3.20", 8080));

            if (socketChannel.finishConnect()) {
                Scanner scanner = new Scanner(System.in);
                scanner.useDelimiter("\\n");
                //String input = null;//!"exit".equals(input = scanner.next())
                int i = 0;
                while (true) {
                    TimeUnit.SECONDS.sleep(3);
                    String input = "I'm "+ i++ +"-th information from client";
                    buffer.clear();
                    buffer.put(input.getBytes());
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        socketChannel.write(buffer);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socketChannel != null) {
                    socketChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
