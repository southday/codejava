package southday.java.basic.net.bxd.tcp.bs;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

/* 本例是一个 简单的 基于TCP的 自定义浏览器发送请求的 例子
 * [本例主要目的]:
 *     1) 根据 TcpMyServer.java 中获取到的 浏览器请求消息，来模拟浏览器发送消息请求
 *     2) 以 Tomcat 作为服务器，接受 服务器 返回的消息
 * 
 * [步骤]:
 *     1) 模拟 浏览器 发送请求消息
 *     2) 接收 服务器 返回的消息
 * 
 * [结果]:
    HTTP/1.1 200 OK
    Server: Apache-Coyote/1.1
    Accept-Ranges: bytes
    ETag: W/"75-1460876285000"
    Last-Modified: Sun, 17 Apr 2016 06:58:05 GMT
    Content-Type: text/html
    Content-Length: 75
    Date: Sun, 17 Apr 2016 07:10:21 GMT
    Connection: close
    
    <html>
    <body>
        <font size='7' color='blue'>LiChaoxi</font>
    </body>
    </html>
    browser receive message over.
 * 
 * @author southday
 * @date 2016-04-17
 */

public class TcpMyBrowser {
    public static void main(String[] args) {
        try {
            // tomcat 服务器默认端口是 8080
            Socket client = new Socket("127.0.0.1", 8080);
            // 获取 写入流（字符流），模拟浏览器 发送请求
            PrintWriter pw = new PrintWriter(client.getOutputStream());
            // 这里，我并没有把 所有请求消息都写上，好多参数是可选的
            pw.println("GET /HelloWorld/test.html HTTP/1.1");
            pw.println("Host: localhost:10010");
            pw.println("Accept: */*"); // 表示支持全部
            pw.println("Accept-Language: en-US,en;q=0.5");
//            pw.println("Connection: keep-alive");
            pw.println("Connection: closed");
            pw.println(); // HTTP协议规定，消息头和消息体之间必须空一行
            pw.flush();
            
            // 获取 读取流（字节流），读取 tomcat服务器返回的消息
            InputStream ins = client.getInputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = ins.read(buf)) != -1) {
                System.out.print(new String(buf, 0, len));
            }
            System.out.println("browser receive message over.");
            client.close();
        } catch (Exception e) {
            System.out.println("my browser catch");
            e.printStackTrace();
        }
    }
}

/*
 * GET /test.html HTTP/1.1
 *  Host: localhost:10010
 *  User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:42.0) Gecko/20100101 Firefox/42.0
 *  Accept: text/html,application/xhtml+xml,application/xml;q=0.9;q=0.8
 *  Accept-Language: en-US,en;q=0.5
 *  Accept-Encoding: gzip, deflate
 *  Connection: keep-alive
 */ 
