package test.java.nio.channels.SocketChannel;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author yanchao
 * @date 2017/10/31 14:24
 *
 * 案例来源：http://blog.csdn.net/anxpp/article/details/51512200
 */
public class Client {

    public static final String HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 8080;
    private static ClientHandle clientHandle;

    public static void start() {
        start(HOST, DEFAULT_PORT);
    }

    public static void start(String host, int port) {
        if (clientHandle != null) {
            clientHandle.stop();
        }
        clientHandle = new ClientHandle(host, port);
        new Thread(clientHandle, "Client").start();
    }

    public static boolean sendMessage(String message) throws IOException {
        if ("q".equals(message)) {
            return false;
        }
        clientHandle.sendMessage(message);
        return true;
    }

    public static void main(String[] args) throws IOException {
        start();
        Scanner scanner = new Scanner(System.in);
        while (sendMessage(scanner.nextLine()));

    }
}
