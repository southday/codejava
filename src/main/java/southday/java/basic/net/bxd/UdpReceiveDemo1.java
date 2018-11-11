package southday.java.basic.net.bxd;

import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;

/* 本例与 UdpSendDemo1.java 相对应，主要是使用while循环接受数据
 * 具体过程步骤，这里也不给出，如有需要，请看 UdpReceiveDemo0.java
 * 
 * @author southday
 * @date 2016-04-10
 */

public class UdpReceiveDemo1 {
    public static void main(String[] args) throws Exception {
        DatagramSocket ds = new DatagramSocket(9988, InetAddress.getByName("127.0.0.1"));
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket dp = new DatagramPacket(data, data.length);
            ds.receive(dp); // 阻塞式方法
            String ip = dp.getAddress().getHostAddress();
            int port = dp.getPort();
            String rec_string = new String(dp.getData(), 0, dp.getLength());
            System.out.println(rec_string + " " + ip + ":" + port);
            if (rec_string.equals("bye")) break;
        }
        ds.close();
        System.out.println("communicate over");
    }
}
