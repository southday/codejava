package southday.java.basic.net.bxd;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Scanner;

/* 本例基于 TcpClientServerDemo0.java，实现了客户端和服务端的 读数据/写数据
 * [实现内容]:
 *     1) 服务端开启，等待客户端发送数据
 *     2) 客户端开启，发送给服务端数据，然后执行到read()方法，被阻塞(即等待服务端发送数据)
 *     3) 服务端接收到客户端的数据，输出
 *     4) 服务端发送给客户端数据，然后执行到read()方法，被阻塞
 *     5) 客户端接收到服务端的数据，输出
 *     ...
 * 
 * 详细解释，这里就不再给出，如有需要，请看TcpClientServerDemo0.java
 * @author southday
 * @date 2016-04-12
 */

class TcpClientThread1 implements Runnable {
    private Socket client = null;
    
    public TcpClientThread1(Socket client) {
        this.client = client;
    }
    
    @Override
    public void run() {
        try {
            OutputStream outs = client.getOutputStream();
            InputStream ins = client.getInputStream();
            Scanner sc = new Scanner(System.in);
            while (true) {
                // write data
                String send_message = sc.nextLine();
                outs.write(send_message.getBytes());
                if (send_message.equals("bye")) break;
                
                // read data
                byte[] bufr = new byte[1024];
                int message_size = ins.read(bufr);
                String rece_message = new String(bufr, 0, message_size);
                System.out.println("[Server]: " + rece_message);
                if (rece_message.equals("bye")) break;
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("client catch");
            e.printStackTrace();
        }
    }
}

class TcpServerThread1 implements Runnable {
    private ServerSocket server = null;
    
    public TcpServerThread1(ServerSocket server) {
        this.server = server;
    }
    
    @Override
    public void run() {
        try {
            Socket client = server.accept();
            InputStream ins = client.getInputStream();
            OutputStream outs = client.getOutputStream();
            Scanner sc = new Scanner(System.in);
            while (true) {
                // read data
                byte[] bufr = new byte[1024];
                int message_size = ins.read(bufr);
                String rece_message = new String(bufr, 0, message_size);
                String client_ip = client.getInetAddress().getHostAddress();
                int client_port = client.getPort();
                System.out.println("[client " + client_ip + ":" + client_port+ "]: " + rece_message);
                if (rece_message.equals("bye")) break;
                
                // write data
                String send_message = sc.nextLine();
                outs.write(send_message.getBytes());
                if (send_message.equals("bye")) break;
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("server catch");
            e.printStackTrace();
        }
    }
}

public class TcpClientServerDemo1 {
    static String server_ip = "127.0.0.1";
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(10002);
            Thread server_thread = new Thread(new TcpServerThread1(server));
            server_thread.start();
            Thread.sleep(1000);
            // 这里要注意，在创建 客户端套接字对象 之前，要把服务端 开启
            Socket client = new Socket(InetAddress.getByName(server_ip), 10002);
            Thread client_thread = new Thread(new TcpClientThread1(client));
            client_thread.start();
        } catch (Exception e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
    }
}
