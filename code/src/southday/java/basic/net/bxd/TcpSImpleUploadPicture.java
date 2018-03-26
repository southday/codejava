package southday.java.basic.net.bxd;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/* 本例是一个 简单的 基于TCP的 图片上传例子
 * 这里将使用： shutdownOutput()方法来标识结束
 * 
 * [需要注意]:
 *  1) 确定是字节流就用字节流的方式，读和取
 *  2) 流中不应 含有两种类型的数据 （字符，字节） // 这个是由该例字得到的体会，但不一定是正确的
 *  
 * 我客户端要先把图片名称传给服务端，之后再传 图片（字节流），
 * 一开始我传名称使用的是 字符流，在服务端中 接收也是用 字符流，然后再用 字节流 接收图片数据，
 * 然而，结果却有问题，最后生成的图片 字节数 小于 原始图片的字节数，
 * 我下意识就想到，会不会是 在使用 字节流 读取图片数据之前，我使用了 字符流 读取图片名称造成的，
 * 经过测试（我把服务端的图片名称硬编码了），果然确实是这样，所以应当注意上面提到的（2）问题
 * 
 *  3) 对于(2)中的问题，其实你也可以在客户端中 多加一条阻塞式语句，即只先把 图片名称写入缓冲区，
 *     再获得 服务端 “OK，已收到图片名称” 之后，再往里面写入 图片数据（字节），这样就OK
 * --> 然并卵，经过测试，这种方法也有问题，看来不深入内部研究研究，会被坑的很惨，代码都改来改去，醉了
 * 
 * [在此声明]: 这个例子执行结果是错的，等啥时候理解了I/O中那几个类的内部知识，可能再来修改，
 * 如果只是想实现一个简单的 图片上传demo，那你可直接 在服务端对 图片名称硬编码，省去图片名称获取这步，
 * 直接传递 图片数据（字节流）就ok.
 * | | | | | |
 * [问题已解决]:在 客户端中 获取 图片名称后，以字节流的形式传递给 服务端，而在此之前，要先获得 图片名称
 * 所占用的字节数，并且先将 图片名称所占用的字节数 传递给服务端。在服务端中，获得到 图片名称字节数后，通
 * 过 DataInputStream 中的 readByte()方法来一个字节一个字节地读取，然后重构 图片名称（字符串），这样
 * 就不会影响到 后面图片数据（字节流）的读取了 --> 2016-04-16
 * 
 * [参考]: net.bixiangdong.tcpuploadpicconcurrency.*;
 * @author southday
 * @date 2016-04-15
 */

class TcpUploadPictureClientThread implements Runnable {
    private Socket client = null;
    private String picpath = null;
    
    public TcpUploadPictureClientThread(Socket client) {
        this.client = client;
    }
    
    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }
    
    public String getPicpath() {
        return picpath;
    }
    
    @Override
    public void run() {
        try {
            // 1. 创建 读取流（字节流），源是 本地图片（文件）
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(picpath));
            // 获取 写入流（字节流），目的是 套接字的OutputStream 对象
            BufferedOutputStream bos = new BufferedOutputStream(client.getOutputStream());
            // 先将 图片名称 传给 服务端
            String picname = picpath.substring(picpath.lastIndexOf('/') + 1);
            /* 这里使用 DataOutputStream 来进行传递相关参数
             * 1) 传递给 服务端 图片名称 所占字节数
             * 2) 传递给 服务端 图片名称
             * @date 2016-04-16
             */
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            dos.writeInt(picname.getBytes().length); // 传递图片名称占用字节数
            dos.writeBytes(picname); // 传递 图片名称
            
            // 接收 服务端 传来的字符数据，判断是否已收到文件名称
            BufferedReader bfr =
                    new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println(bfr.readLine());
            
            /* 2. 从 读取流 中获取数据，然后通过写入流将数据写入
             * 这里其实我是很疑惑的：
             * --> 为什么 BufferedInputStream 和 BufferedOutputStream 中都定义了缓冲区
             * 却还要一个字节一个字节的操作，或者说要显示的定义  byte[] buf = new byte[xx];
             * 如果是这样的话，那和 使用 FileInputStream 和 FileOutputStream，然后在自己
             * 定义一个缓冲区，有什么区别吗？
             * [其实，我最后想问的是]: BufferedInputStream 和 BufferedOutputStream
             *     1) 是如何 提高效率的？
             *     2) 是如何 简化代码书写的？
             *     3) 在哪些场合下该使用他们？
             * @date 2016-04-15
             */
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = bis.read(buf)) != -1) {
                bos.write(buf, 0, len);
                bos.flush();
            }
            client.shutdownOutput(); // 标记 写入结束
            
            // 3. 获取 服务端的反馈信息，即 是否上传成功
            String upload_result = bfr.readLine();
            System.out.println("upload result: " + upload_result);
            
            // 4. 关闭资源
            bis.close();
            client.close();
        } catch (Exception e) {
            System.out.println("client catch");
            e.printStackTrace();
        }
    }
}

class TcpUploadPictureServerThread implements Runnable {
    private ServerSocket server = null;
    private String picdir = null;
    
    public TcpUploadPictureServerThread(ServerSocket server) {
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
            // 1. 获取 客户端套接字对象 IP prot
            Socket client = server.accept();
            String client_ip = client.getInetAddress().getHostAddress();
            int client_port = client.getPort();
            System.out.println("[" + client_ip + ":" + client_port + "] connected...");
            
            // 先读取一次，获取图片名称
            /* 这里使用 DataInputStream 来读取 客户端传来的 参数
             * 1) 接收 客户端传来的 图片名称占用字节数
             * 2) 接收 客户端传来的 图片名称
             */
            DataInputStream dis = new DataInputStream(client.getInputStream());
            int picname_bytes_len = dis.readInt(); // 获取 图片名称所占用的字节数
            System.out.println("picname_bytes_len = " + picname_bytes_len);
            byte[] picname_bytes = new byte[picname_bytes_len];
            for (int i = 0; i < picname_bytes_len; i++) {
                picname_bytes[i] = dis.readByte();
            } // 重构 图片名称
            String picname = new String(picname_bytes);
            
            // 反馈给 客户端，表示 已经收到图片名称
            PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
            pw.println("OK，已经收到图片名称:" + picname);
            
            // 获取 读取流
            BufferedInputStream bis = new BufferedInputStream(client.getInputStream());
            // 建立 写入流，目的是 本地图片（字节流形式）
            BufferedOutputStream bos =
                    new BufferedOutputStream(new FileOutputStream(picdir + picname));
            
            // 2. 从 读取流中获取数据，然后通过 写入流 将数据写入图片（文件）
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = bis.read(buf)) != -1) {
                bos.write(buf, 0, len);
                bos.flush();
            }
            
            // 3. 反馈给 客户端，上传是否成功
            pw.println(picname + "上传成功");
            
            // 4. 关闭资源
            bos.close();
            client.close();
            server.close();
        } catch (Exception e) {
            System.out.println("server client");
            e.printStackTrace();
        }
    }
}

public class TcpSImpleUploadPicture {
    static String dst_ip = "127.0.0.1";
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(10008);
            Socket client = new Socket(InetAddress.getByName(dst_ip), 10008);
            
            TcpUploadPictureServerThread server_thread = new TcpUploadPictureServerThread(server);
            server_thread.setPicdir("/home/coco/");
            new Thread(server_thread).start();
            Thread.sleep(1000);
            TcpUploadPictureClientThread client_thread = new TcpUploadPictureClientThread(client);
            client_thread.setPicpath("/home/coco/Pictures/hacker.png");
            new Thread(client_thread).start();
        } catch (Exception e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
    }
}
