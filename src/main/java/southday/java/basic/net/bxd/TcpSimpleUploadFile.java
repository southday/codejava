package southday.java.basic.net.bxd;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/* 本例是一个 简单的 基于TCP 的文件上传demo
 * [重要的点]:
 *     1) I/O 流的使用
 *         BufferedReader        BUfferedWriter
 *         InputStreamReader    OutputStreamWriter
 *         PrintStream            PrintWriter
 *         InputStream            OutputStream
 *         DataInputStream        DataOutputStream    
 * 
 *     2) 结束标签
 *      a)    使用 特定字符串 来作为写入结束标签，比如"over"
 *         这是不好的做法，因为标签可能和 文件内容重复
 *      b) 使用 时间戳 来作为写入结束标签，比如"System.currentTimeMillis()"
 *         这种方法道也可取，只不过在服务端读取时还要增加 判断条件，决定是否跳出循环，时间戳是唯一的
 *      c)    使用 Socket类中带有的方法 --> shutdownOutput() / shutdownInput 来进行标记
 *         这种方法倒是挺方便的，帮你省了一些内部操作，只不过在一些实际应用中，还是需要自己定义结束标签的
 * 
 * 在下面的代码中，我使用的是 时间戳来标记结束 的方式，可以看出--> 好麻烦 - -||
 * 使用 shutdownOutput() 则可以这样：
 *         String line = null;
 *        while ((line = bufr.readLine()) != null) {
 *            outs.println(line);
 *        }
 *        client.shutdownOutput();
 * 使用该方法，在服务端中都不需要添加其他判断代码，因为 Socket 对象中内部执行了这些操作
 * 
 * @author southday
 * @date 2016-4-15
 */

class TcpUploadFileClientThread implements Runnable {
    private Socket client = null;
    private String filepath = null;
    
    public TcpUploadFileClientThread(Socket client) {
        this.client = client;
    }
    
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
    
    public String getFilepath() {
        return filepath;
    }
    
    @Override
    public void run() {
        try {
            // 1. 创建 读取流，源是一个本地文件
            BufferedReader bufr =
                    new BufferedReader(new FileReader(filepath));
            // 创建 写入流（这里因为是写入字符，所以可以使用PrintWriter），目的是 套接字 的 OuptStream
            PrintWriter outs = new PrintWriter(client.getOutputStream(), true); // true 标识 自动刷新
            // 先将文件名传给 服务端
            outs.println(filepath.substring(filepath.lastIndexOf('/') + 1));
            
            // 使用时间戳来标记 写入结束
            long timestamp = System.currentTimeMillis();
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            dos.writeLong(timestamp); // 得先写入一次，将时间戳（结束标记）发送给服务端
            
            // 2. 将文件 写入到 outs中
            String line = null;
            while ((line = bufr.readLine()) != null) {
                outs.println(line);
            }
            outs.println(timestamp); // 再写如一次，标志着 文件写入结束
            
            // 3. 等待服务端给出回复，确定是否上传成功
            BufferedReader bufrIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String upload_result = bufrIn.readLine();
            System.out.println("upload result: " + upload_result);
            
            // 4. 关闭资源
            bufr.close();
            outs.close();
            dos.close();
            client.close();
        } catch (Exception e) {
            System.out.println("client catch");
            e.printStackTrace();
        }
    }
}

class TcpUploadFileServerThread implements Runnable {
    private ServerSocket server = null;
    private String filedir = null;
    
    public TcpUploadFileServerThread(ServerSocket server) {
        this.server = server;
    }
    
    public void setFiledir(String filedir) {
        this.filedir = filedir;
    }
    
    public String getFiledir() {
        return filedir;
    }
    
    @Override
    public void run() {
        try {
            // 1. 获取 客户端 套接字对象
            Socket client = server.accept();
            String client_ip = client.getInetAddress().getHostAddress();
            int client_port = client.getPort();
            System.out.println("[" + client_ip + ":" + client_port + "] connected...");
            
            // 获取 读取流，读取客户端传来的数据
            BufferedReader bufr = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String filename = bufr.readLine();     // 先读取 文件名
            // 再读取一次，获取 客户端传来的 结束标记 --> 时间戳
            DataInputStream dis = new DataInputStream(client.getInputStream());
            long end_flag = dis.readLong(); // 时间戳 timestamp
            
            // 2. 创建 写入流（字符流），源是 读取流bufr，目的是 本地文件
            PrintWriter pw = new PrintWriter(filedir + filename);
            String line = null;
            while ((line = bufr.readLine()) != null) {
                try {
                    long timestamp = Long.parseLong(line);
                    if (end_flag == timestamp) break;
                } catch (NumberFormatException e) {}
                pw.println(line);
            }
            
            // 3. 反馈给 客户端，上传 成功与否
            PrintWriter outs = new PrintWriter(client.getOutputStream());
            outs.println(filename + "上传成功");
            // 如果你的构造函数中没有 指明 autoFlush 为true的话，那么默认是 false的
            outs.flush(); // 就需要手动flush()
            
            // 4. 关闭资源
            dis.close();
            pw.close();
            outs.close();
            client.close();
            server.close();
        } catch (Exception e) {
            System.out.println("server catch");
            e.printStackTrace();
        }
    }
}

public class TcpSimpleUploadFile {
    static String dst_ip = "127.0.0.1";
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(10007);
            Socket client = new Socket(InetAddress.getByName(dst_ip), 10007);
            
            // 先开启服务端
            TcpUploadFileServerThread server_thread = new TcpUploadFileServerThread(server);
            server_thread.setFiledir("/home/coco/");
            new Thread(server_thread).start();
            Thread.sleep(1000);
            // 后开启客户端
            TcpUploadFileClientThread client_thread = new TcpUploadFileClientThread(client);
            client_thread.setFilepath("/home/coco/Documents/Haa.txt");
            new Thread(client_thread).start();
        } catch (Exception e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
    }
}
