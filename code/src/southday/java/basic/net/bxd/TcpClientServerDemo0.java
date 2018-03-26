package southday.java.basic.net.bxd;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/* 本例讲解关于TCP简单的知识
 * 
 * 大二上学期学了《计算机网络（自顶向下）》，上面提到了TCP是面向连接的服务，
 * 所以呢，基于TCP的连接分为了 [客户端] 和 [服务端] （也可以叫做服务器）
 * 
 * [客户端] -- Socket
 *     由于TCP是面向连接的服务，所以客户端在建立套接字时，就需要指定
 *     [目标地址]及其通信端口，类似： new Socket(InetAddress address, int port)
 * 
 * [服务端] -- ServerSocket
 *     与客户端稍有不同的是，一个服务端可能（实际情况下是几乎）与多个客户端通信，
 *     所以 服务端 在建立 套接字时，是不指定 与某个 客户端进行通信的（也就是不指定客户端地址），
 *     但是，服务端 需要提供 通信端口，这样 服务端和客户端 才能通信
 * [补充]: TCP通信中，服务端可以获取 客户端的套接字对象 Socket，然后使用该对象与客户端进行通信，
 *     倘若有多个客户端要与某一个服务端通信，那么该服务端就会分别获取这些客户端的 Socket，并用这些Socket
 *     与之通信，从而确保 通信消息 不会被传递个 非目标对象
 * 
 * [需要注意]: 
 *     对于UDP协议来说，先开启客户端或服务端都可以，因为时面向无连接，
 *     而对于TCP协议来说，需要先开启服务端，这样 在建立客户端套接字时 才能连接服务端
 * 
 * @author LiChaoxi
 * @date 2016-04-11
 */

/* 客户端创建过程:
 *     1. 建立套接字对象，需要指定 服务端地址 和 通信端口
 *     2. 从 套接字对象 中获取 输出流 OutputStream
 *     3. 通过输出流 写入 数据，进而与客户端进行通信
 *     4. 关闭客户端，（这里就不需要关闭输出流了，因为这个输出流是来自客户端的，客户端关闭，意味着输出流也关闭）
 * 
 * 对于2,3步，如果是获取 读取流 InputStream，则可以通过该输入流对象 读取服务端传来的数据
 */
class TcpClientThread implements Runnable {
    
    @Override
    public void run() {
        try {
            /* 1. 建立套接字对象，需要指定 服务端地址 和 通信端口
             * 当然你也可以 直接 new Socket()，不过这样得到的对象是没有建立连接的，
             * 若要连接，需要使用 Socket类中的 connect(...)方法
             */
            InetAddress dst_address = InetAddress.getByName("127.0.0.1");
            int dst_port = 10002;
            Socket client = new Socket(dst_address, dst_port);
            
            // 2. 从 套接字对象中获取 输出流 OutputStream
            OutputStream outs = client.getOutputStream();
            
            // 3. 通过输出流 写入 数据，与服务端进行通信
            outs.write("Hi, I'm client".getBytes());
            
            // 4. 关闭客户端
            client.close();
        } catch (Exception e) {
            System.out.println("client catch");
            e.printStackTrace();
        }
    }
}

/* 服务端建立过程:
 *     1. 建立 服务端套接字对象，注意要绑定监听端口
 *     2. 通过 ServerSocket类中的 accept() 方法获取 客户端套接字对象
 *     3. 获取 客户端套接字对象的 读取/输出流 --> InputStream/OutputStream
 *     4. 使用 InputStream 对象来读取 客户端传来的数据
 *        使用 OutputStream 对象来写入 数据，从而与 客户端进行通信
 *     5. 关闭服务端 --> 该操作是可选的，一般来说，服务端是不关闭的，除非要维护或系统升级等
 *        服务端不关自己，但可以关闭 客户端
 */
class TcpServerThread implements Runnable {
    @Override
    public void run() {
        try {
            // 1. 建立 服务端套接字对象，注意要绑定监听端口
            int server_port = 10002;
            ServerSocket server = new ServerSocket(server_port);
            
            // 2. 通过 accept() 方法 获取 客户端套接字对象
            Socket client = server.accept(); // 阻塞方法
            
            // 3. 获取客户端套接字的 读取/输出流
            InputStream ins = client.getInputStream();
//            OutputStream outs = client.getOutputStream();
            
            /* 4. 通过 读取流读取客户端发来的消息
             *    通过 输出流写入数据，与客户端通信
             */
            // 接收数据，暂时不做写入数据这部分
            byte[] rece_bufr = new byte[1024];
            int message_size = ins.read(rece_bufr, 0, rece_bufr.length);
            String rece_message = new String(rece_bufr, 0, message_size);
            String client_ip = client.getInetAddress().getHostAddress();
            int client_port = client.getPort();
            System.out.println("from client[" + client_ip + ":" + client_port + "]: " + rece_message);
            
            /* 5. 关闭服务端 --> 可选操作，一般服务端不关闭 
             *    服务端 还可以关闭 客户端，即关闭连接，以免客户端一直占用资源，而不作动作
             */
            client.close();
            server.close();
        } catch (Exception e) {
            System.out.println("server catch");
            e.printStackTrace();
        }
    }
}

public class TcpClientServerDemo0 {
    public static void main(String[] args) {
        try {
            Thread server_thread = new Thread(new TcpServerThread());
            server_thread.start();
            Thread.sleep(1000);
            Thread client_thread = new Thread(new TcpClientThread());
            client_thread.start();
        } catch (InterruptedException e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
    }
}
