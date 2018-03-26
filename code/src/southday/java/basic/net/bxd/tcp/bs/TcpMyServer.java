package southday.java.basic.net.bxd.tcp.bs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/* 本例是一个 简单的 基于TCP的 自定义服务器，而特别之处是 客户端为浏览器
 * [本例主要目的]:
 *     1) 通过浏览器访问 自定义的服务器，然后输出 浏览器发出的请求消息
 *     2) 通过 自定服务器 返回给 浏览器（客户端）一个.html文件，然浏览器解析
 * 
 * [步骤]:
 *     1) 开启 自定义服务器
 *     2) 使用 浏览器 访问： http://localhost:10010/test.html
 *     3) 查看 Eclipse 控制台输出结果
 * 
 * [结果]:
 *     GET /test.html HTTP/1.1
 *  Host: localhost:10010
 *  User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:42.0) Gecko/20100101 Firefox/42.0
 *  Accept: text/html,application/xhtml+xml,application/xml;q=0.9;q=0.8
 *  Accept-Language: en-US,en;q=0.5
 *  Accept-Encoding: gzip, deflate
 *  Connection: keep-alive
 *  
 *  read meesage header over.
 *  
 * [问题]: 不知道为什么，为浏览器firefox中显示的直接是 test.html的源码，而不是被解析后的文件,
 *     我感觉是浏览器的问题。然而我用舍友的浏览器访问，直接被墙，是被我的墙还是被他的墙？？
 *  
 * [需要注意]：使用Eclipse开发，FileReader()之类的读取类，默认目录为 [项目根目录]
 * @author southday
 * @date 2016-04-17
 */
public class TcpMyServer {
    public static void main(String[] args) {
        // 为了简便，为就不写 什么循环，多线程的了
        try {
            ServerSocket server = new ServerSocket(10010);
            // 获取 客户端套接字
            Socket client = server.accept();
            // 获取 读取流（字节流），来读取 浏览器发出的信息
            InputStream ins = client.getInputStream();
            byte[] buf = new byte[1024]; // 使用 缓冲区加快读取速度
            int len = ins.read(buf);
            System.out.print(new String(buf, 0, len));
            
            System.out.println("read meesage head over.");
            /* 这里 本来是需要 解析消息头后，看看浏览器需要获取什么资源，再进行响应的
             * 但是为了简便，我直接把响应小心 硬编码了
             */
            // 需要注意：使用Eclipse开发，FileReader()之类的读取类，默认目录为 [项目根目录]
            BufferedReader bfr = new BufferedReader(
                    new FileReader("src/southday/java/net/bxd/tcp/bs/test.html"));
            PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
            String line = null;
            while ((line = bfr.readLine()) != null) {
                pw.println(line);
            }
            client.shutdownOutput();
            bfr.close();
            server.close();
        } catch (Exception e) {
            System.out.println("my server catch");
            e.printStackTrace();
        }
    }
}
