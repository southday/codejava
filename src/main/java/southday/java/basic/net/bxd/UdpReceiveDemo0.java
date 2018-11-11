package southday.java.basic.net.bxd;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

/* 本例是与UdpReceive对应的，是一个简单的基于UDP的数据接收例子
 * [过程]:
 *     1) 建立套接字 DatagramSocket，接收端通常都会监听一个端口，其实就是给这个网络应用程序定义数字标识
 *        方便明确 哪些数据过来，该应用程序可以处理
 *     2) 创建数据包 DatagramPacket，用来承载接收到的数据包对象
 *     3) 使用 DatagramSocket 中的方法 接受数据
 *     4) 使用 DatagramPacket 中的方法，来获取相应信息，并进行操作
 *     5) 关闭资源，因为建立的套接字占用者网卡资源
 * 
 * @author southday
 * @date 2016-04-10
 */

public class UdpReceiveDemo0 {
    public static void main(String[] args) throws Exception {
        // 1) 建立套接字 DatagramSocket，这里和UdpSendDemo.java中的 数据包地址、端口 相对应
        DatagramSocket ds = new DatagramSocket(9988, InetAddress.getByName("127.0.0.1"));
        
        byte[] data = new byte[1024];
        // 2) 建立数据包 DatagramPacket，用来接收发送端传来的数据包
        DatagramPacket dp = new DatagramPacket(data, data.length);
        
        // 3) 使用 DatagramSocket 中的方法 接受数据
        ds.receive(dp); // This method blocks(阻塞) until a datagram is received.
        
        /* DatagramPacket 类中 提供了一些获取数据包中数据信息的方法，我们可以用这些方法做相应处理
         * 这里，因为从 UdpSendDemo.java 中发送过来的初始数据是 字符串形式，所以这里接收到的数据我们也用字符串形式 
         */
        // 4) 使用 DatagramPacket 中的方法，来获取相应信息，并进行操作
        String ip_hostname = dp.getAddress().getHostName();
        String receive_data = new String(dp.getData(), 0, dp.getLength());
        int port = dp.getPort();
        System.out.println( receive_data + " " + ip_hostname + " " + port);
        
        // 5) 关闭资源，因为建立的套接字占用着网卡资源
        ds.close();
        System.out.println("udp receive over");
    }
}
