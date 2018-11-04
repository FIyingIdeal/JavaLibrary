package netty.netty0_compare_with_jdk;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author yanchao
 * @date 2018/7/11 22:43
 */
public class OioServer {

    private final int port;
    private volatile boolean flag = true;

    public OioServer(int port) {
        this.port = port;
    }

    public void service() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (flag) {
                final Socket clientSocket = serverSocket.accept();
                System.out.println("Accept from client : " + clientSocket);

                new Thread() {
                    @Override
                    public void run() {
                        try (OutputStream outputStream = clientSocket.getOutputStream()) {
                            outputStream.write("Hi!".getBytes(Charset.forName("UTF-8")));
                            outputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                clientSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        } catch (IOException e) {

        }

    }
}
