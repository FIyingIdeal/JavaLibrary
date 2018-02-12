package test.java.net;

import org.junit.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * @author yanchao
 * @date 2018/1/26 18:00
 */
public class NetworkInterfaceTest {

    @Test
    public void getByInetAddress() throws Exception {
        /*InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println(inetAddress);*/
        InetAddress inetAddress1 = InetAddress.getByName("192.168.3.20");
        System.out.println(inetAddress1);
        byte[] mac = NetworkInterface.getByInetAddress(inetAddress1).getHardwareAddress();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length() == 1 ? 0 + s : s);
        }
        System.out.println("MAC : " + sb.toString().toUpperCase());
    }
}
