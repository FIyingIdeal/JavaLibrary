package test.java.net.talk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author yanchao
 * @date 2017/9/14 16:43
 */
public class TalkClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 4700);
            BufferedReader userReader = new BufferedReader(
                    new InputStreamReader(System.in));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            BufferedReader serverReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            String line = userReader.readLine();
            while (!line.equals("bye")) {
                pw.println(line);
                pw.flush();
                System.out.println("Client: " + line);
                System.out.println("Server: " + serverReader.readLine());
                line = userReader.readLine();
            }
            serverReader.close();
            pw.close();
            userReader.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
