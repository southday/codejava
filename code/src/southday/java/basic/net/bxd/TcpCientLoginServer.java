package southday.java.basic.net.bxd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/* 本例是一个 简单的 基于TCP的 客户端 登录 服务端 的例子，没有使用数据库
 * 
 * [内容]:
 *     1) 在服务器端，新建一个 文件(User.txt)来表示 已注册的用户信息
 *     2) 客户端 通过键盘录入 用户名来登录 服务器
 *     3) 当用户名 客户端 登录的用户名不存在时，服务端显示 xxx用户尝试登录
 *        当用户名存在时，服务器 反馈给 客户端 “登录成功”，并欢迎 客户端 登录
 *     4) 客户端只有 3次 登录的机会，如果3次登录不成功，则服务器会断开与该客户端的连接
 * 
 * @author southday
 * @date 2016-04-16
 */

class TcpClientLoginThread implements Runnable {
    private Socket client = null;
    
    public TcpClientLoginThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            Scanner sc = new Scanner(System.in);
            // 客户端只有3次登录机会
            for (int i = 0; i < 3; i++) {
                String username = sc.nextLine(); 
                // 键盘录入时， 按ctrl+c，则结束程序，这时username = null;
                if (username == null) break;
                
                // 将 用户名 传递给 服务端
                PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
                pw.println(username);
                
                // 获取 服务端的反馈信息
                BufferedReader bfr =
                        new BufferedReader(new InputStreamReader(client.getInputStream()));
                String message_from_server = bfr.readLine();
                System.out.println(message_from_server);
                if (message_from_server.contains("welcome")) {
                    System.out.println("Hi! [" + username + "], welcome you!");
                }
            }
            sc.close();
            client.close();
        } catch (Exception e) {
            System.out.println("client catch");
            e.printStackTrace();
        }
    }
}

class TcpServerProcLoginThread implements Runnable {
    private ServerSocket server = null;
    
    public TcpServerProcLoginThread(ServerSocket server) {
        this.server = server;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                Socket client = server.accept();
                String client_ip = client.getInetAddress().getHostAddress();
                int client_port = client.getPort();
                System.out.println("[" + client_ip + ":" + client_port + "] connected...");
                
                // 服务端 只给 客户端 3次登录机会，如果3次登录都失败，则断开与 客户端的连接，防止暴力登录
                for (int i = 0; i < 3; i++) {
                    BufferedReader bfrIn =
                            new BufferedReader(new InputStreamReader(client.getInputStream()));
                    // 读取 用户名
                    String username = bfrIn.readLine();
                    if (username == null) break;
                    
                    // 创建 读取流（字符流，读取UserTable），用于 接下来的 username 匹配
                    BufferedReader bfr = new BufferedReader(
                            new FileReader("/home/coco/Documents/JavaTest/TcpClientLoginServer-UserTable.txt"));
                    String user_in_table = null;
                    while ((user_in_table = bfr.readLine()) != null) {
                        if (username.equals(user_in_table)) break;
                    }
                    PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
                    if (user_in_table == null) { // 说明 username 不在用户表里面
                        // 这时要反馈给 客户端信息 --> 登录失败，用户名username不存在
                        pw.println("login failure, the user name [" + username + "] is not exists.");
                        // 并且在服务端显示 username尝试登录
                        System.out.println("[" + username + "] try to login.");
                    } else { // username 在 用户表中，即：登录成功
                        pw.println("login successfully, welcome to you!");
                        // 在服务端显示 username以登录
                        System.out.println("[" + username + "] logined.");
                        break; // 登录成功后 即跳出 for 循环
                    }
                    bfr.close();
                } // end of for
                /* 这里按理来说不应该 直接 client.close()的，因为之前可能 客户端已成功登录
                 * 只是，我们只演示这个登录过程，而不在登录后继续 某些操作，所以在这里统一关闭
                 */
                client.close();
            }
        } catch (Exception e) {
            System.out.println("server catch");
            e.printStackTrace();
        }
    }
}

public class TcpCientLoginServer {
    static String server_ip = "127.0.0.1";
    static int server_port = 10010;
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(server_port);
            Socket client = new Socket(InetAddress.getByName(server_ip), server_port);
            
            Thread server_thread = new Thread(new TcpServerProcLoginThread(server));
            server_thread.start();
            Thread.sleep(1000);
            Thread client_thread = new Thread(new TcpClientLoginThread(client));
            client_thread.start();
        } catch (Exception e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
    }
}
