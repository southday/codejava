package southday.java.basic.net.bxd.chatdemo;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author southday
 * Date   2018/12/26
 */
public class ChatServer {
    private static final long DELTA_T = 10000; // 单位 ms
    private static boolean hasSend = false;

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.open();
    }

    public void open() {
        try {
            @SuppressWarnings("resource")
            ServerSocket server = new ServerSocket(10005);
            Socket client = server.accept();

            String clientIP = client.getInetAddress().getHostAddress();
            int clientPort = client.getPort();
            System.out.println("[" + clientIP + ":" + clientPort + "] Connected...");

            Thread receiver = new Thread(new Receiver(client));
            Thread sender = new Thread(new Sender(client));
            receiver.start();
            Thread.sleep(1000);
            sender.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class Receiver implements Runnable {
        private Socket client;

        public Receiver(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                long beginTime = System.currentTimeMillis();
                InputStream ins = client.getInputStream();
                String clientIP = client.getInetAddress().getHostAddress();
                int clientPort = client.getPort();
                String printInfo = "[Client /" + clientIP + ":" + clientPort + "]:";
                while (true) {
                    byte[] buffer = new byte[1024];
                    int len = ins.read(buffer);
                    String message = new String(buffer, 0, len);
                    long endTime = System.currentTimeMillis();
                    if (hasSend || endTime - beginTime > DELTA_T) {
                        System.out.println(printInfo);
                        beginTime = System.currentTimeMillis();
                    }
                    System.out.println("\t" + message);
                    hasSend = false;
                    if ("bye".equals(message.trim()))
                        break;
                }
                client.close();
            } catch (Exception e) {
                System.out.println("ServerReceiver: Connection closed.");
            }
        }
    }

    private class Sender implements Runnable {
        private Socket client;

        public Sender(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                OutputStream outs = client.getOutputStream();
                Scanner sc = new Scanner(System.in);
                while (true) {
                    String message = sc.nextLine();
                    outs.write(message.getBytes());
                    hasSend = true;
                    if ("bye".equals(message.trim()))
                        break;
                }
                sc.close();
                client.close();
            } catch (Exception e) {
                System.out.println("ServerSender: Connection closed.");
            }
        }
    }
}
