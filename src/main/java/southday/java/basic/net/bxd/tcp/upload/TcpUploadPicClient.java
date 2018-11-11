package southday.java.basic.net.bxd.tcp.upload;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/* 本例是一个 简单的 基于TCP的 并行的 图片上传 客户端 
 * [需要注意的点]:
 * 1) 在提供给 客户 （上传图片程序）的时候，需要在客户上传图片时 对图片进行检测
 *    a) 文件是否存在
 *    b) 文件格式
 *    c) 文件大小
 *    ...
 * 这就像在开发 Web 应用一样，在浏览器设置javascript检测（前端检测），而不是在服务器端检测
 * 
 * [注意]:
 *     这里使用了 DataOutputStream，判断 图片文件 是否合法，传递给 服务端一个 Boolean 值
 *     而服务端中，使用 DataInputStram 来读取，确保了读取的 字节数（一个Boolean类型的字节数），
 *     使得 其不影响 后面读取 图片数据时
 * [所以]: 
 *     对于 net.bixiangdong.TcpSimpleUploadPicture.java 中的问题，可以通过 在服务端中 读取
 *     特定的字节数来获得 图片名称，这样就不会影响到后面读取 图片数据了 
 * 
 * @author LiChaoxi
 * @date 2016-04-16
 */

class TcpUploadPicClientThread implements Runnable {
    private static final long FILE_MAX_SIZE = 1024 * 1024 * 5; // 5MB
    private Socket client = null;
    private String picpath = null;
    
    public TcpUploadPicClientThread(Socket client) {
        this.client = client;
    }
    
    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }
    
    public String getPicpath() {
        return picpath;
    }
    
    // 判断 要上传的图片（文件） 是否合法
    public boolean isFileLegitimacy(File file) {
        // a) 判断文件是否存在，并且是 文件 （而不是目录等其他的内容）
        if (!(file.exists() && file.isFile())) {
            System.out.println("请选择一个文件");
            return false;
        }
        // b) 判断文件名是否合法
        if (!(file.getName().endsWith(".png"))) {
            System.out.println("请选择一个png格式的图片");
            return false;
        }
        // c) 判断文件大小是否合法，客户可能会把一个视频文件.avi，改成了.png
        if (file.length() > FILE_MAX_SIZE) {
            System.out.println("请选择一个小于5MB的png图片");
            return false;
        }
        return true;
    }
    
    @Override
    public void run() {
        try {
            File file = new File(picpath);
            // 1. 先判断 要上传的图片（文件） 是否合法
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            if (isFileLegitimacy(file)) {
                dos.writeBoolean(true); // 传递给 服务端作标记，如果文件名合法才执行后续方法
            } else {
                dos.writeBoolean(false);
                return;
            }
            
            /* 2. 创建 图片 读取流（字节流）。用于获取 本地图片数据
             *    获取 套接字对象 写入流，将 图片数据写入到流中
             */
            FileInputStream fis = new FileInputStream(file);
            OutputStream outs = client.getOutputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = fis.read(buf)) != -1) {
                outs.write(buf, 0, len);
                outs.flush();
            }
            client.shutdownOutput(); // 标记 写入 结束
            
            // 3. 接收 服务端 反馈的信息，确定图片是否上传成功
            BufferedReader bfr =
                    new BufferedReader(new InputStreamReader(client.getInputStream()));
            String upload_result = bfr.readLine();
            System.out.println("upload result: " + upload_result);
            
            // 4. 关闭资源
            fis.close();
            client.close();
        } catch (Exception e) {
            System.out.println("client catch");
            e.printStackTrace();
        }
    }
}

public class TcpUploadPicClient {
    static String dst_ip = "127.0.0.1";
    static int dst_port = 10009;
    public static void main(String[] args) {
        try {
            Socket client = new Socket(InetAddress.getByName(dst_ip), dst_port);
            TcpUploadPicClientThread client_thread = new TcpUploadPicClientThread(client);
            client_thread.setPicpath("/home/coco/Pictures/hacker.png");
            new Thread(client_thread).start();
        } catch (Exception e) {
            System.out.println("client main catch");
            e.printStackTrace();
        }
    }
}
