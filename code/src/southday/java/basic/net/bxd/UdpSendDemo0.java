package southday.java.basic.net.bxd;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;

/* 本例是一个简单的基于UDP发送数据的例子
 * [过程]:
 *     1) 建立套接字 socket
 *     2) 创建数据包，用来封装要发送的数据
 *     3) 根据 DatagramSocket 中的方法 发送数据包
 *     4) 关闭资源，因为网络传输是占用了网卡资源
 * 
 * @author southday
 * @Date 2016-04-10
 */

public class UdpSendDemo0 {
    public static void main(String[] args) throws Exception {
        // 1) 建立套接字 socket，8899 是发送端 的端口号（数字标识）
        DatagramSocket ds = new DatagramSocket(8899); // 因为是在申请资源，所以可能会出现异常
        
        byte[] data = "Hi, I'm LiChaoxi".getBytes();
        // 2) 创建数据包，用来封装数据，这里为假设是发到本机的9988端口上，9988是 接收端的端口号（数字标识）
        DatagramPacket dp = 
                new DatagramPacket(data, 0, data.length, InetAddress.getByName("127.0.0.1"), 9988);
        
        // 3) 使用 DatagramSocket 中的方法 发送数据
        ds.send(dp);
        
        // 4) 关闭资源，因为网络传输占用了网卡资源
        ds.close();
        System.out.println("udp send over");
    }
}
