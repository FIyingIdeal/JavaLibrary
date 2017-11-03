package test.java.nio.channels.SocketChannel;

/**
 * @author yanchao
 * @date 2017/10/31 13:55
 *
 * 案例来源：http://blog.csdn.net/anxpp/article/details/5151220
 */
public class Server {

    public static final int DEFAULT_PORT = 8080;
    public static ServerHandle serverHandle;

    public static void start() {
        start(DEFAULT_PORT);
    }

    public static void start(int port) {
        if (serverHandle != null) {
            serverHandle.stop();
        }
        serverHandle = new ServerHandle(DEFAULT_PORT);
        new Thread(serverHandle, "Server").start();
    }

    public static void main(String[] args) {
        start();
    }
}
