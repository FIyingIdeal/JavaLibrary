package test.java.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * unity3d 服务端
 * @author lm
 *
 */
public class U3dServer implements Runnable {

    public void run() {

        ServerSocket u3dServerSocket = null;

        while (true) {

            try {

                u3dServerSocket = new ServerSocket(8000);
                System.out.println("u3d服务已经启动,监听8000端口");
                while (true) {
                    Socket socket = u3dServerSocket.accept();
                    System.out.println("客户端接入");
                    new RequestReceiver(socket).start();
                }
            } catch (IOException e) {
                System.err.println("服务器接入失败");
                if (u3dServerSocket != null) {
                    try {
                        u3dServerSocket.close();
                    } catch (IOException ioe) {
                    }
                    u3dServerSocket = null;
                }
            }

            // 服务延时重启
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {

            }

        }


    }

    /**
     * 客户端请求接收线程
     *
     * @author lm
     */
    class RequestReceiver extends Thread {

        private Socket socket;

        /**
         * socket输入处理流
         */
        private BufferedReader bis = null;

        public RequestReceiver(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                //获取socket中的数据
               bis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
               String line;
                /**
                 * 在Socket报文传输过程中,应该明确报文的域
                 */
                while (!(line = bis.readLine()).equals("exit")) {

					/*
					 * 这种业务处理方式是根据不同的报文域,开启线程,采用不同的业务逻辑进行处理
					 * 依据业务需求而定
					 */
                    //读取字节数组中的内容，BufferedInputStream#read()方法是阻塞式的
                    //bis.read(buf);
                    //输出
                    System.out.println(line);
                    OutputStream out = socket.getOutputStream();
                    //向客户端传输数据的字节数组
                    out.write(new String("i am server").getBytes());
                    //line = br.readLine();
                }

            } catch (IOException e) {
                System.err.println("读取报文出错");
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                    }
                    socket = null;
                }
            }

        }
    }
}
