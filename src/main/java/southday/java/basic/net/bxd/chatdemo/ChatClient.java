package southday.java.basic.net.bxd.chatdemo;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author southday
 * Date   2018/12/26
 */
public class ChatClient {
    private static final long DELTA_T = 10000; // 单位 ms
    private static boolean hasSend = false;

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.open();
    }

    public void open() {
        try {
            InetAddress dstIP = InetAddress.getByName("127.0.0.1");
            int dstPort = 10005;
            Socket socket = new Socket(dstIP,dstPort);
            Thread sender = new Thread(new Sender(socket));
            sender.start();
            Thread receiver  = new Thread(new Receiver(socket));
            receiver.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class Receiver implements Runnable {
        private Socket socket;

        public Receiver(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
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
                InputStream ins = socket.getInputStream();
                String serverIP = socket.getRemoteSocketAddress().toString();
                String printInfo = "[Server " + serverIP + "]:";
                while (true) {
                    byte[] buffer = new byte[1024];
                    int len = ins.read(buffer);
                    String message = new String(buffer, 0, len);
                    long endTime = System.currentTimeMillis();
                    if (hasSend || endTime - beginTime >DELTA_T) {
                        System.out.println(printInfo);
                        beginTime = System.currentTimeMillis();
                    }
                    System.out.println("\t" + message);
                    hasSend = false;
                    if ("bye".equals(message.trim()))
                        break;
                }
                socket.close();
            } catch (Exception e) {
                System.out.println("ClientReceiver: Connection closed.");
            }
        }
    }

    private class Sender implements Runnable {
        private Socket socket;

        public Sender(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                OutputStream outs = socket.getOutputStream();
                Scanner sc = new Scanner(System.in);
                while (true) {
                    String message = sc.nextLine();
                    outs.write(message.getBytes());
                    hasSend = true;
                    if ("bye".equals((message.trim())))
                        break;
                }
                socket.close();
                sc.close();
            } catch (Exception e) {
                System.out.println("ClientSender: Connection closed.");
            }
        }
    }
}
