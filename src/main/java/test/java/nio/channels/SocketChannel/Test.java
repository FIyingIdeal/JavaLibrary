package test.java.nio.channels.SocketChannel;

/**
 * @author yanchao
 * @date 2017/10/31 14:39
 *
 * 案例来源：http://blog.csdn.net/anxpp/article/details/5151220
 */
import java.util.Scanner;
public class Test {
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception{
        Server.start();
        Thread.sleep(100);
        Client.start();
        /*Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        System.out.println(message);
        Client.sendMessage(message);*/
        while(Client.sendMessage(new Scanner(System.in).nextLine()));

    }
}
