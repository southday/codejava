package southday.java.basic.net.bxd.tcp.upload;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/* 本例是一个 简单的 基于TCP的 并行的 图片上传 服务端
 * [需要注意的点]:
 *   1) 明确 服务端中 （上传图片的代码），然后将其封装成 （上传图片线程）
 *   2) 要使 客户上传的图片 不被新图片覆盖，就需要对图片名进行标识，可以使用（时间戳、IP地址+count等）方式
 * 
 * @author southday
 * @date 2016-04-16
 */

class UploadPicThread implements Runnable {
    private Socket client = null;
    private String picdir = null;
    
    public UploadPicThread(Socket client, String picdir) {
        this.client = client;
        this.picdir = picdir;
    }
    
    @Override
    public void run() {
        String client_ip = client.getInetAddress().getHostAddress();
        try {
            // 1. 获取 客户端IP 和 时间戳，作为图片名
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh时:mm分:ss秒");
            String date_time = sdf.format(date);
            DataInputStream dis = new DataInputStream(client.getInputStream());
            if (!dis.readBoolean()) return; // 如果哦客户端传的文件不合法的话，这里就不再继续 
            File file = new File(picdir + client_ip + "(" + date_time + ").png");
            
            /* 2. 获取 客户端套接字 的 读取流，用来读取 客户端传来的 图片数据
             *    创建 写入流（字节流），用于 将图片数据 写入到本地文件中
             */
            InputStream ins = client.getInputStream();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] bufr = new byte[1024];
            int len = 0;
            while ((len = ins.read(bufr)) != -1) {
                fos.write(bufr, 0, len);
                fos.flush();
            }
            client.shutdownInput(); // 其实这步可以不要的，因为 我的客户端中在上传图片后就关了
            
            // 3. 反馈给 客户端 上传结果信息，成功与否
            PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
            pw.println("图片上传成功!");
            
            System.out.println(file.getName() + " 上传成功!");
            // 4. 关闭资源
            fos.close();
            client.close();
        } catch(Exception e) {
            System.out.println("UploadPicThread catch");
            throw new RuntimeException(client_ip + " 上传失败!");
        }
    }
}

class TcpUploadPicServerThread implements Runnable {
    private ServerSocket server = null;
    private String picdir = null;
    
    public TcpUploadPicServerThread(ServerSocket server) {
        this.server = server;
    }
    
    public void setPicdir(String picdir) {
        this.picdir = picdir;
    }
    
    public String getPicdir() {
        return picdir;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                Socket client = server.accept();
                String client_ip = client.getInetAddress().getHostAddress();
                int client_port = client.getPort();
                System.out.println("[" + client_ip + ":" + client_port + "] connected...");
                // 为每一个 上传图片的 请求（客户端）创建一个新的线程来实现并行上传
                new Thread(new UploadPicThread(client, picdir)).start();
            }
        } catch (Exception e) {
            System.out.println("server catch");
            e.printStackTrace();
        }
    }
}

public class TcpUploadPicServer {
    static int port = 10009;
    static String picdir = "/home/coco/Pictures/";
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(port);
            TcpUploadPicServerThread server_thread = new TcpUploadPicServerThread(server);
            server_thread.setPicdir(picdir);
            new Thread(server_thread).start();
        } catch (Exception e) {
            System.out.println("server main catch");
            e.printStackTrace();
        }
    }
}
