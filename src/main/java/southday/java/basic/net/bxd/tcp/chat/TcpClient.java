package southday.java.basic.net.bxd.tcp.chat;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/* 该类是 TCP 的客户端类，主要实现了 客户端 对数据的 接收 和 发送
 * 通过使用 接收线程 和 发送线程，实现了：
 *   1) 客户端可以 在服务端不回复的情况下 多次发送信息
 *   2) 客户端可以 在不回复服务端的情况下 多次接收到服务端的信息
 * 这样才像真实的聊天场景
 * 
 * @author southday
 * @date 2016-04-13
 */

class ClientReceThread implements Runnable {
    private Socket client = null;
    private static final long DELTA_T = 10000; // 单位 ms
    
    public ClientReceThread(Socket client) {
        this.client = client;
    }
    
    @Override
    public void run() {
        // 因为只有一个线程占用着 console，所以这里有不同步了
        try {
            /* 加一个时间限定，用于控制输出格式
             * 如，在5秒内，如果 服务端持续发送消息，并且在这期间 客户端没有发送消息，则可以显示如下：
             * [Server]:
             *   消息1...
             *   消息2...
             *   ...
             * 当过了5秒后，则会再重新输出 [Server]:
             */
            long beginTime = System.currentTimeMillis();
            // 1. 获取 读取流
            InputStream ins = client.getInputStream();
            while (true) {
                // 2. 读取数据并打印到控制台
                byte[] bufr = new byte[1024];
                int len = ins.read(bufr);
                String rece_message = new String(bufr, 0, len);
                long endTime = System.currentTimeMillis();
                if (ClientSendThread.isSend || endTime - beginTime > ClientReceThread.DELTA_T) {
                    System.out.println("[Server]:");
                    beginTime = System.currentTimeMillis();
                }
                System.out.println("\t" + rece_message);
                ClientSendThread.isSend = false;
                if (rece_message.equals("bye")) break;
            }
            client.close();
        } catch (Exception e) {
            System.out.println("client receive catch");
            e.printStackTrace();
        }
    }
}

class ClientSendThread implements Runnable {
    private Socket client = null;
    // 用于控制 输出形式，见 ClientReceThread
    public static boolean isSend = true;
    
    public ClientSendThread(Socket client) {
        this.client = client;
    }
    
    @Override
    public void run() {
        // 同样，这里也只有一个线程操作 写入流，所以就不同步了
        try {
            // 1. 获取 写入流，获取 从键盘读取数据 的读取流
            OutputStream outs = client.getOutputStream();
            Scanner sc = new Scanner(System.in);
            while (true) {
                // 2. 从键盘中获取数据，并将数据 写入到流中
                String send_message = sc.nextLine();
                outs.write(send_message.getBytes());
                ClientSendThread.isSend = true;
                if (send_message.equals("bye")) break;
            }
            client.close();
            sc.close();
        } catch (Exception e) {
            System.out.println("client send catch");
            e.printStackTrace();
        }
    }
}

public class TcpClient {
    public static void main(String[] args) {
        try {
//            InetAddress dst_ip = InetAddress.getByName("113.55.84.249");
            InetAddress dst_ip = InetAddress.getByName("127.0.0.1");
            int dst_port = 10005;
            Socket client = new Socket(dst_ip, dst_port);
            Thread send_thread = new Thread(new ClientSendThread(client));
            send_thread.start();
            Thread rece_thread = new Thread(new ClientReceThread(client));
            rece_thread.start();
        } catch (Exception e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
    }
}
