package southday.java.basic.net.bxd;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
/* 本例主要实现：从键盘录入数据，发送给接收方
 * 具体过程步骤这里就不再详细解说，如有需要，请看 UdpSendDemo0.java
 * 
 * @author southday
 * @date 2016-04-10
 */

public class UdpSendDemo1 {
    public static void main(String[] args) throws Exception {
        DatagramSocket ds = new DatagramSocket(8899);
        Scanner sc = new Scanner(System.in);
        String inputs = "";
        while (!inputs.equals("bye")) {
            inputs = sc.nextLine(); // 阻塞式方法
            byte[] data = inputs.getBytes();
            DatagramPacket dp = 
                    new DatagramPacket(data, data.length, InetAddress.getByName("127.0.0.1"), 9988);
            /* 这里我只是给本机发，若是使用 实际的Ip地址，然后把最后一个字段改成255，
             * 如：我目前的IP地址为 113.55.64.1，我把其改为： 113.55.64.255 (广播地址)
             * 则变成了广播地址，那么在同一个网段的所有主机都能接收到我发出去的数据，只要它们监听了9988端口
             */
            ds.send(dp);
        }
        ds.close();
        sc.close();
        System.out.println("communicate over");
    }
}
