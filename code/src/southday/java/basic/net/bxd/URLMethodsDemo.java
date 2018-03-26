package southday.java.basic.net.bxd;

import java.net.MalformedURLException;
import java.net.URL;

/* 本例主要 介绍了 URL 的在一些方法
 * [方法]:
 *     1) String getProtocol() --> 获取此URL的 协议
 *     2) String getHost()     --> 获取此URL的 主机名
 *     3) int getPort()        --> 获取此URL的 端口
 *     4) String getFile()     --> 获取此URL访问的 文件
 *     5) String getPath()     --> 获取此URL访问的 路径
 *     6) String getQuery()    --> 获取此URL的 查询信息
 *     ...
 * 还有好多其他的方法也经常会用到，这里只是表达：
 * URL类中 封装了 对URL网址解析等各种操作，我们可以拿来直接使用
 * 
 * @author LiChaoxi
 * @date 2016-04-17
 */

public class URLMethodsDemo {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://113.55.51.7:8080/HelloWorld/test.html?name=LiChaoxi&age=21");
            
            // 1) 获取协议
            System.out.println("url.getProtocol() --> " + url.getProtocol());
            // 2) 获取主机名
            System.out.println("url.getHost() --> " + url.getHost());
            // 3) 获取端口
            System.out.println("url.getPort() --> " + url.getPort());
            // 4) 获取 要访问的文件
            System.out.println("url.getFile() --> " + url.getFile());
            // 5) 获取 访问路径
            System.out.println("url.getPath() --> " + url.getPath());
            // 6) 获取 查询信息
            System.out.println("url.getQuery() --> " + url.getQuery());
            
            /*
             * 这里需要注意，当我们的URL中没有指定端口时，
             * url.getPort() 返回 -1，
             * 一般而言，服务器程序会先解析 URL网址，然后根据其所使用的 协议 http/https等再进一步解析
             * 当解析到 port时：
             *     int port = url.getPort();
             *     if (port == -1) {
             *         port = 80; // 80是 Web的默认端口
             *     }
             * 也就是当 URL中没有指定port时，服务器程序会为其指定默认端口80,然后继续解析，
             * 最后封装成 Socket 对象
             */
        } catch (MalformedURLException e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
    }
}
