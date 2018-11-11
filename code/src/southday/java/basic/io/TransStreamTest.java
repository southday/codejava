package southday.java.basic.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/* 1. 需求：将一个图片文件中的数据存储到另一个文件中
 * 分析：
 * (1) 源： 硬盘，图片文件    InputStream      Reader
 *     是否为纯文本文件？ 否！  InputStream
 *     
 * (2) 目的： 硬盘，图片文件  OutputStream    Writer
 *              是否为纯文本文件？ 否！   OutputStream
 * 
 * (3) 源：硬盘，图片文件
 *         InputStream中能操作文件的对象：FileInputStream
 *        是否需要提高效率？是！
 *         在InputStream中加入缓冲区体系：BufferedInputStream
 *      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(...));
 *    目的：硬盘，图片文件
 *        OutputStream中能操作文件的对象：FileOutputStream
 *   是否需要提高效率？是！
 *       在OutputStream中加入缓冲区体系：BufferedOutputStream
 *   BufferdOutputStream bos = new BufferedOutputStream(new FileOutputStream(...));
 */

/* 2. 需求：将键盘录入的数据保存在一个文件中
 * 分析：
 * (1) 源: InputStream  Reader
 *     是否为纯文本文件？ 是！（因为你在键盘输入的事abcdeg这些字符嘛）  Reader
 *设备：键盘录入,，对象是System.in
 *上面选择的不是 Reader吗？  System.in对应的不是字节流吗？
 *    为了操作键盘的文件数据方便，转成字符流按照字符串操作是最方便的！！
 *    所以既然明确了要是用Reader,那就把System.in转换成Reader    
 *用到Reader体系中转换流：InputStreamReader
 *  InputStreamReader isr = new InputStreamReader(System.in);
 *  是否需要提高效率？ 是！ 使用BufferedReader来包装 InputStreamReader
 *  BufferedReader br = new BufferedReader(isr);
 *  
 * (2) 目的： OutputStream  Writer
 *  是否为纯文本文件？ 是！ 使用 Writer
 *设备：硬盘， 文件，在Writer中操作文件的对象有：FileWriter
 *是否需要提高效率？ 是！ 使用BufferedWriter缓冲区机制
 *    BufferedWriter bw = new BufferedWriter(new FileWriter(...));
 *
 *    扩展：想要把录入的数据按照指定的编码表(utf-8)，将数据存到文件中
 *在存储时要加入指定的编码表，而指定的编码表只有转换流具备，那么我们要使用：
 *OutputStreamWriter,而转换流对象要接收一个字节输出流，而且是可以操作文件的字节输出流：FileOutputStream
 *但是FileWriter使用的是默认的编码表，我们存储时要使用指定编码表  utf-8
 *关键代码：
 *    OutputStreamWriter osw = new OutoutStreamWriter(new FileOutputStream(System.out), "utf-8");
 *    BufferedWriter bw = new BufferedWriter(osw);
 *
 * 【注意】：通常，涉及到字符编码转换时，需要用到转换流
 * 
 * 3. 需求：把硬盘上的文本数据打印在控制台上
 * 分析：
 *     (1) 源： InputStream  Reader
 *         是否是纯文本文件？是！ 使用  Reader
 *     设备：硬盘，文件，在Reader中操作文件的对象有：FileReader
 *     是否需要提高效率？是！使用BufferedReader缓冲区机制:
 *         BufferedReader br = new BufferedReader(new FileReader("E:\\TransStreamTest.java");
 *     
 *     (2) 目的：OutputStream  Writer
 *         是否是纯文本文件？是！ 使用 Writer
 *     设备：控制台，字节流 System.out，将字节流转为字符流
 *         OutputStreamWriter osw = new OutputStreamWriter(System.out);
 *     是否要提高效率？是！ 使用BufferedWriter来包装OutputStreamWriter
 *         BufferedWriter bw = new BufferedWriter(osw);
 */

public class TransStreamTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
//        test1();
//        test2();
        test3();
        long end = System.currentTimeMillis();
        
        System.out.println("用时 : " + (end - start) + "ms");
    }
    
    public static void test1() {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        
        try {
            bis = new BufferedInputStream(new FileInputStream("E:\\Li.jpg"));
            bos = new BufferedOutputStream(new FileOutputStream("E:\\haha.jpg"));
            
//            byte[] buf = new byte[1024];
//            int len = 0;
//            // 方法1，使用的是bis.read(buf)，一个buf一个buf得读取
//            while ((len = bis.read(buf)) != -1) {
//                bos.write(buf, 0, len);
//            }
            
            // 果然还是方法1 更快，毕竟我多开辟了一块内存空间给它用
            
            // 方法2，使用的是 bis.read()，一个字节一个字节得读取
            int ch = 0;
            while ((ch = bis.read()) != -1) {
                bos.write(ch); // 强制将 int  转为  byte， 只保留最低的8位
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            closeResource(bis);
            closeResource(bos);
        }
    }
    
    public static void test2() {
        BufferedReader br = null;
        BufferedWriter bw = null;
        
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            bw = new BufferedWriter(new FileWriter("E:\\keybordInput.txt"));
            String line = null;
            
            while ((line = br.readLine()) != null) {
                if ("over".equals(line)) {
                    break;
                }
                bw.write(line);
                bw.newLine(); // 这里是需要加  bw.newLine()的，不然的话写入的数据就没有回车换行
                bw.flush();
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            closeResource(br);
            closeResource(bw);
        }
    }
    
    public static void test3() {
        BufferedReader br = null;
        BufferedWriter bw = null;
        
        try {
            br = new BufferedReader(new FileReader("E:\\TransStreamDemo.java"));
            bw = new BufferedWriter(new OutputStreamWriter(System.out));
            
            String line = null;
            while((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            closeResource(br);
            closeResource(bw);
        }
    }
    
    public static void closeResource(Closeable t) {
        if (t != null) {
            try {
                t.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        } // if
    }
}
