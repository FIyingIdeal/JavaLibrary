package test.java.nio.channels.aio;

import test.java.nio.channels.aio.client.Client;
import test.java.nio.channels.aio.server.Server;

import java.util.Scanner;

/**
 * @author yanchao
 * @date 2017/10/31 17:47
 */
public class Test {
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception{
        //运行服务器
        Server.start();
        //避免客户端先于服务器启动前执行代码
        Thread.sleep(100);
        //运行客户端
        Client.start();
        System.out.println("请输入请求消息：");
        Scanner scanner = new Scanner(System.in);
        while(Client.sendMsg(scanner.nextLine()));
    }
}
