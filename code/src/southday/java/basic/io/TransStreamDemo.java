package southday.java.basic.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/* java.io.Reader
 *     |--java.io.InputStreamReader
 * 是字节流通向字符流的桥梁：它使用指定的 charset 读取字节并将其解码为字符。它使用的字符集可以由名称指定或显式给定，或者可以接受平台默认的字符集。
 * 
 * BufferedReader(Reader r) {...}
 * InputStreamReader(InputStream in) {...}
 * ↓↓↓↓ 那么就可以写成：
 * BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 * 
 * java.io.Writer
 *  |--java.io.OutputStreamWriter
 * OutputStreamWriter 是字符流通向字节流的桥梁：可使用指定的 charset 将要写入流中的字符编码成字节。
 * 它使用的字符集可以由名称指定或显式给定，否则将接受平台默认的字符集。 
 * 
 * 关于System.的小插曲
 * (1) static void setIn(InputStream in) {...}  重新分配“标准”输入流。
 * eg: System.setIn(new FileInputStream("E:\\test.txt"))  输入流为文本文件输入
 * 
 * (2) static void setOut(PrintStream out) {...} 重新分配“标准”输出流。
 * eg: System.setOut(new PringStream("E:\\test1.txt"))  输出流为文本文件输出
 */

/* 流操作的基本规律：
 * 我们最痛苦的就是流对象有很多，不知道该用哪一个？
 * 【答】：
 * 通过两个明确来完成：
 * 1. 明确  源 和  目的
 *  源:输入流  --> InputStream   Reader
 *  目的:输出流 --> OutputStream  Writer
 * 
 * 2. 操作的数据是否为纯文本
 *  是: 使用字符流   Reader  Writer
 *  不是: 使用字节流  InputStream  OutputStream
 *   
 * 3. 当体系明确后，再明确要使用哪个具体的对象？？怎么明确？？
 * 【答】：通过设备来进行区分
 *  源设备: 内存，硬盘，键盘
 *  目的设备: 内存，硬盘，控制台
 */

public class TransStreamDemo {
    public static void main(String[] args) {
        // transReadStream();
        transWriteStream();
    }

    public static void transReadStream() {
        // (1) 创建读入 字节流
        InputStream in = System.in; // 其实 in 是BufferedInputStream 对象
        System.out.println("in 的真实身份是：" + in.getClass().getName());

        // (2) 将读入 字节流 转换成 读入 字符流
        InputStreamReader isr = new InputStreamReader(in); // 将 in(读字节流对象)转换成
                                                            // (读字符流对象)

        // (3) 为了提高效率，使用BufferedReader来包装InputStreamReader实例
        BufferedReader br = new BufferedReader(isr);

        // (4) 现在转变成我们熟悉的 BufferedReader了，开始进行读取操作
        String str = null;
        try {
            while ((str = br.readLine()) != null) {
                if ("over".equals(str))
                    break;
                System.out.println(str);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            closeSource(br);
        }

    }

    /*
     * 1. 源：键盘录入     目的：控制台
     * BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
     * 
     * 2. 源：键盘录入     目的：文件 
     * BufferedWriter bw = new BufferedWriter(
     *             new OutputStreamWriter(new FileOutputStream("E:\\transStreamDemo.txt"));
     * 
     * 3. 源：文件        目的：控制台
     * BufferedReader br = new BufferedReader(
     *             new InputStreamReader(new FileInputStream("E:\\tansStreamDemo.txt"));
     */

    public static void transWriteStream() {
        // (1) 键盘录入，由 读字节流 转换为 读字符流    ① 源为键盘录入 
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br = null;
//        BufferedWriter bw = null;
        // (2) 将 写字符流 转换成 写字节流    ① 目的为控制台
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        // (3) 开始输出
        String str = null;
        try {
            // (2) ② 目的为 文件
//            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("E:\\transStreamDemo.txt")));
            br = new BufferedReader(new InputStreamReader(new FileInputStream("E:\\transStreamDemo.txt")));
            while ((str = br.readLine()) != null) {
                if ("over".equals(str))
                    break;
                bw.write(str); // 这里会出现问题，因为write()方法只是将数据存入缓冲区，要想输出还需要flush()

                /*
                 * newLine(); 通过BufferedWriter 包装 OutputStreamWriter 来使用
                 * 这是BufferedWriter 特有的方法，因为上面的write()中输出是不包括"回车"的 而如果你写成：
                 * bw.write(str + '\r\n'), 请记住Linux中的回车只有'\n'（不跨平台了）
                 * 所以，为了保证跨平台，还是使用 bw.newLine();
                 */
                bw.newLine();
                bw.flush(); // 别忘记刷新，不然是不会输出东西的
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            closeSource(br);
            closeSource(bw);
        }
    }

    public static void closeSource(Closeable t) {
        if (t != null) {
            try {
                t.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        } // if
    }
}
