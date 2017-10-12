package test.java.net.talk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author yanchao
 * @date 2017/9/14 16:41
 */
public class TalkServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(4700);
            } catch (Exception e) {
                System.out.println("Error : " + e);
            }
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (Exception e) {
                System.out.println("Error : " + e);
            }
            System.out.println("Client connect success!");
            BufferedReader clientReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            BufferedReader userReader = new BufferedReader(
                    new InputStreamReader(System.in));
            System.out.println("Client: " + clientReader.readLine());
            String line ;
            line = userReader.readLine();
            while (!line.equals("bye")) {
                pw.println(line);
                pw.flush();
                System.out.println("Server: " + line);
                System.out.println("Client: " + clientReader.readLine());
                line = userReader.readLine();
            }
            clientReader.close();
            userReader.close();
            pw.close();
            socket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
