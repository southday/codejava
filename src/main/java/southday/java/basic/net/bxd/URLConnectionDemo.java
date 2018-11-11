package southday.java.basic.net.bxd;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/* 本例主要 简单描述下： URLConnection类（抽象类）
 * 看看 URLConnection类 是如何将 Sokect封装的，
 * 将我们的程序从 [传输层] 提到了 [应用层]
 * 
 * [传输层 & 应用层]:
 *     1) 传输层 得到的服务器数据，包含了 消息头和消息体
 *     2) 应用层 得到的服务器数据，是将 传输层的数据 进行拆包后的数据 --> 消息体
 * 区别可以参看：net.bixiangdong.tcpbrowserserver.TcpMyBrowser.java
 * 
 * [sure]: URLConnection类中也有好多方法，可以获取URL网址的相关信息
 * 
 * [结果]:
      sun.net.www.protocol.http.HttpURLConnection:http://113.55.51.7:8080/HelloWorld/test.html
    <html>
    <body>
        <font size='7' color='blue'>LiChaoxi</font>
    </body>
    </html>
 * 比起 TcpMyBrowser.java类中返回的消息，少了 消息头，因为被拆包了
 * 
 * @author LiChaoxi
 * @date 2016-04-17
 */

public class URLConnectionDemo {
    public static void main(String[] args) {
        try {
            /* 这里虽然写的是Tomcat开启的服务的网页地址，但其实是可以直接些互联网上的网页地址的
             * 获取到 .html文件后，通过一些正则表达式的使用，就可以制作一个简单的spider
             * 比如：http://www.xiami.com/
             */
            URL url = new URL("http://localhost:8080/HelloWorld/test.html");
//            URL url = new URL("http://www.xiami.com/");
            
            // URLConnection 封装了 Socket，让我们的程序处于 [应用层]
            URLConnection connection = url.openConnection();
            System.out.println(connection); // 可以看到，其实返回的是 HttpURLConnection 对象
            // URLConnection 中的 getInputStream() 其实是调用了 Socekt 中的 getInputStream()
            InputStream ins = connection.getInputStream(); 
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = ins.read(buf)) != -1) {
                System.out.print(new String(buf, 0, len));
            }
            // 看一下这返回结果，是不是 没有了 消息头
            // URLConnection 也不需要我们手动关闭 six six six
        } catch (MalformedURLException e) { // new URL(...);
            System.out.println("main catch");
            e.printStackTrace();
        } catch (IOException ie) { // url.openConnection();
            System.out.println("main catch");
            ie.printStackTrace();
        }
    }
}
