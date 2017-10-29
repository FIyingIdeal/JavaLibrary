package test.java.net;

import java.io.*;
import java.net.Socket;

/**
 * @author yanchao
 * @date 2017/9/11 14:34
 */
public class Client {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8000;
        Socket client = null;
        BufferedWriter writer = null;
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader socketInput;
        try {
            client = new Socket(host, port);
            String readLine;
            socketInput = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            while (!"exit".equals(readLine = userInput.readLine())) {
                System.out.println("Client first write : " + readLine);
                writer.write(readLine);
                writer.flush();
                System.out.println("Server : " + socketInput.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                client.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
