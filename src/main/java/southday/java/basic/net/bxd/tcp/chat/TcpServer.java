package southday.java.basic.net.bxd.tcp.chat;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/* 该类是 TCP 的服务端，主要实现了 服务端对数据的 接受 和 发送
 * 通过使用 接收线程 和 发送线程，实现了：
 *   1) 服务端可以 在客户端不回复的情况下 多次发送信息
 *   2) 服务端可以 在不回复客户端的情况下 多次接收到客户端的信息
 * 
 * @author southday
 * @date 2016-04-13
 */

class ServerReceThread implements Runnable {
    private Socket client = null ;
    private static final long DELTA_T = 10000; // 单位 ms
    
    public ServerReceThread(Socket client) {
        this.client = client;
    }
    
    @Override
    public void run() {
        try {
            // 1. 获取 客户端套接字对象，并通过该套接字对象 获取 读取流
            long beginTime = System.currentTimeMillis();
            InputStream ins = client.getInputStream();
            String client_ip = client.getInetAddress().getHostAddress();
            int client_port = client.getPort();
            while (true) {
                // 2. 从读取流中读取数据，并打印到控制台上
                // 获取客户端 IP 和 端口
                byte[] bufr = new byte[1024];
                int len = ins.read(bufr);
                String rece_message = new String(bufr, 0, len);
                long endTime = System.currentTimeMillis();
                if (ServerSendThread.isSend || endTime - beginTime > ServerReceThread.DELTA_T) {
                    System.out.println("[Client " + client_ip + ":" + client_port + "]:");
                    beginTime = System.currentTimeMillis();
                }
                System.out.println("\t" + rece_message);
                ServerSendThread.isSend = false;
                if (rece_message.equals("bye")) break;
            }
            client.close();
        } catch (Exception e) {
            System.out.println("server receive catch");
            e.printStackTrace();
        }
    }
}

class ServerSendThread implements Runnable {
    private Socket client = null;
    public static boolean isSend = true;
    
    public ServerSendThread(Socket client) {
        this.client = client;
    }
    
    @Override
    public void run() {
        try {
            // 1. 获取客户端的 套接字对象， 并通过该套接字对象 获取 写入流
            OutputStream outs = client.getOutputStream();
            Scanner sc = new Scanner(System.in);
            while (true) {
                // 2. 从键盘 读取数据，并写入到流中
                String send_message = sc.nextLine();
                outs.write(send_message.getBytes());
                ServerSendThread.isSend = true;
                if (send_message.equals("bye")) break;
            }
            sc.close();
            client.close();
        } catch (Exception e) {
            System.out.println("server send catch");
            e.printStackTrace();
        }
    }
}

public class TcpServer {
    public static void main(String[] args) {
        try {
            @SuppressWarnings("resource")
            ServerSocket server = new ServerSocket(10005);
            Socket client = server.accept();
            
            String client_ip = client.getInetAddress().getHostAddress();
            int client_port = client.getPort();
            System.out.println("[" + client_ip + ":" + client_port + "] connected...");
            
            Thread rece_thread = new Thread(new ServerReceThread(client));
            Thread send_thread = new Thread(new ServerSendThread(client));
            send_thread.start();
            Thread.sleep(1000);
            rece_thread.start();
        } catch (Exception e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
    }
}
