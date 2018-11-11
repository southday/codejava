package southday.java.basic.net.bxd;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/* 本例旨在通过多线程技术，实现一个简单的聊天程序
 * [群聊]:
 *     由于窗口只有一个，所以需要对 “在窗口上显示聊天信息" 这个操作进行同步
 *     此外，还需要在接受端设置消息缓冲区，不然在同步显示的时候，可能造成数据丢失
 * 所以，为了简化聊天过程，这里只实现了简单的 1对1 聊天
 * 
 * [问题]:
 *     本来为想使用舍友电脑进行测试的，但是不知为什么，ubuntu 14.04下，
 *     我使用 ifconfig -a ， ip addr 下看本机IP地址，总是在不停变化，
 *     可能就是因为这个原因，导致我的接收端在创建时就报错：
 *         java.net.BindException: Cannot assign requested address
 *     在网上查了查资料，说ubuntu 拨号上网频繁掉线...
 *     然而，即使我照着网页上所讲的，把/etc/ppp/options文件中的
 *         lcp-echo-interval 30
 *         lcp-echo-failure 4 --> 我改成了30
 *     修改后，还是出现问题，目前不知道怎么解决，等会换Windows跑一下这个程序看看
 * 
 *     [2014-04-11]--> 该程序，我在Windows下面跑，是正确的，当然一开始由于舍友的防火墙问题，
 *     我没能把信息穿过去，后来让他把防火墙关了，我们两就实现了聊天。
 * 
 *     []:本来想实现多人聊天的，使用消息缓冲队列我都想好了（虽然不知道对不对），但啥广播地址的我不懂，
 *    查了些资料，发现子网掩码为255.255.255.255的广播地址就是ip，也就是无法广播给其他朋友，只能指定ip
 *
 * @author southday
 * @date 2016-04-10
 */

/* 啊哈哈，IP终于没在变了，虽然我也不知道内部原因是怎么搞的...
 * [参考网址]: http://jingyan.baidu.com/article/ceb9fb10d858c78cad2ba0bd.html
 * 1) 可能是我 配置了 dnsmasq，这个过程中影响到了 网络连接
 * 2) 可能是我用：>sudo pppoeconf 的方式连接，而 pppoeconf 和 networkmanage有冲突
 * 不管哪种解释，都有点牵强，因为我没有深入研究... -O -
 * 
 * 
 * [但是，我照者网上的方法，修改了某些配置文件，一切就搞定了 :-)]
 *     1) sudo vim /etc/NetworkManager/NetworkManager.conf
 *        将 managed=false 改成 managed=true
 *     2) sudo vim /etc/network/interfaces
 *        删掉其他的内容，只保留:
 *         auto lo
 *         iface lo inet loophack
 *     3) 本来第三部要删除 /etc/resolve.conf 文件，但我没删除，重启
 * 然后就OK了，我就可以在Ubuntu下愉快的测试我的 网络编程demo了
 * @date 2016-04-14
 */
class SendMessageThread implements Runnable {
    private DatagramSocket ds = null;
    private String dest_ip = null;
    private int dest_port = 0;
    
    public SendMessageThread(DatagramSocket ds, String dest_ip, int dest_port) {
        this.ds = ds;
        this.dest_ip = dest_ip;
        this.dest_port = dest_port;
    }
    
    @Override
    public void run() {
        try {
            Scanner sc = new Scanner(System.in);
            String message = "";
            while (!message.equals("bye")) {
                message = sc.nextLine();
                byte[] data = message.getBytes();
                DatagramPacket dp = 
                        new DatagramPacket(data, 0, data.length, InetAddress.getByName(dest_ip), dest_port);
                ds.send(dp);
            }
            ds.close();
            sc.close();
        } catch (Exception e) {
            System.out.println("send catch");
            e.printStackTrace();
        }
    } // end or run()
}

class ReceiveMessageThread implements Runnable {
    private static final Object displayer = new Object();
    private DatagramSocket ds = null;
    
    public ReceiveMessageThread(DatagramSocket ds) {
        this.ds = ds;
    }
    
    @Override
    public void run() {
        synchronized (displayer) {
            try {
                // 一般来说，服务器总是处于接受状态的，所以这里我没有进行关闭资源
                while (true) {
                    byte[] buf = new byte[1024];
                    DatagramPacket dp = new DatagramPacket(buf, buf.length);
                    ds.receive(dp);
                    String ip = dp.getAddress().getHostAddress();
                    int port = dp.getPort();
                    String message = new String(dp.getData(), 0, dp.getLength());
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
                    String time = sdf.format(date);
                    System.out.println(message + " [" + ip + ":" + port + " " + time + "]");
                }
            } catch (Exception e) {
                System.out.println("receive catch");
                e.printStackTrace();
            }
        }
    } // end of run()
}

public class UdpSimpleChat {
    // ip老是变化，只能用127.0.0.0，不然就报错
    static String src_ip = "127.0.0.0";
    static String dst_ip = "113.55.53.189";
    public static void main(String[] args) {
        try {
            DatagramSocket sendsocket = new DatagramSocket(8899);
            DatagramSocket recesocket = new DatagramSocket(9988, InetAddress.getByName(src_ip));
            Thread send_thread = new Thread(new SendMessageThread(sendsocket, dst_ip, 9988));
            send_thread.start();
            Thread.sleep(1000);
            Thread rece_thread = new Thread(new ReceiveMessageThread(recesocket));
            rece_thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
