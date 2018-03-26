package southday.java.basic.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/* 打印流
 * 该流提供了打印方法，可以将各种数据类型的数据都原样打印
 * 
 * 字节打印流: PrintStream
 * 构造函数可以接收的参数类型
 * 1. file对象  File
 * 2. 字符串路径  String
 * 3. 字节输出流  OuputStream
 * 
 * 字符打印流: PrintWriter
 * 构造函数可以接收的参数类型
 * 1. file对象  File
 * 2. 字符串路径  String
 * 3. 字节输出流  OuputStream
 * 4. 字符输出流  Writer
 */


public class PrintStreamDemo {
    public static void main(String[] args) throws IOException {
        // 键盘接收
        BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
        
//        PrintWriter out = new PrintWriter(System.out);
        /* public PrintWriter(OutputStream out, boolean autoFlush)
         * out - 输出流
         * autoFlush - boolean 变量；如果为 true，则 println、printf 或 format 方法将刷新输出缓冲区
         * 
         * 这个构造函数中的out 只针对 字节流
         * 虽然有构造函数为 public PrintWriter(String fileNmae) 可以直接输入文件路径，
         * 但这个构造函数中是不带自动刷新的，如果想要让程序写入该文件时能够自动刷新，
         * 可以将 这个文件封装成 字节流对象--- FileWriter, 如下：
         *         PrintWriter out = new PrintWriter(new FileWriter(String fileName), true);
         * 若还想提高效率，可以使用缓冲区技术：
         *         PrintWriter out = new PrintWriter(
         *             new BufferedWriter(new FileWriter(String fileName), true));
         * java.lang.Object
         *     |--java.io.Writer
         *         |--java.io.OutputStreamWriter
         *             |--java.io.FileWriter
         */
        PrintWriter out1 = new PrintWriter(System.out, true);
        
        String line = null;
        while ((line = bufr.readLine()) != null) {
            if ("over".equals(line))
                break;
//            out.write(line); // 这里没有带回车
            out1.println(line); // 而打印流中可以直接使用println()来添加回车
//            out.flush(); // 如果这里不flush的话，则要等到结束后才会讲键盘中输入的所有字符输出来
        }
        
//        out.close(); // close() 中 自带一个 flush()
        bufr.close();
    }
}
