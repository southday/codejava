package southday.java.basic.io;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/* 字符流:
 * FileReader
 * FileWriter
 * 
 * BufferedReader
 * BufferedWriter
 * 
 * 字节流：
 * InputStream
 * OutputStream
 * 
 * 要求：想要操作图片数据，就需要用到字节流
 * 
 */


public class FileStreamDemo {
    public static void main(String[] args) {
//        writeFile();
//        readFile_1();
//        readFile_2();
        readFile_3();
    }
    
    // 一个字节地读
    public static void readFile_1() {
        FileInputStream fis = null;
        int ch = 0;
        try {
            fis = new FileInputStream("E:\\fos.txt");
            while ((ch = fis.read()) != -1) { 
                System.out.print((char) ch);
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            closeSource(fis);
        } // finally
    }
    
    // 将字节读入制定大小的字节数组(缓冲区)
    public static void readFile_2() {
        FileInputStream fis = null;
        byte[] by = new byte[1024];
        int len = 0;
        try {
            fis = new FileInputStream("E:\\fos.txt");
            while ((len = fis.read(by)) != -1) { 
                System.out.println(new String(by, 0, len));
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            closeSource(fis);
        } // finally
    }
    
    // public int available() throws IOExcpetion {...}的使用
    // 返回：可以不受阻塞地从此输入流中读取（或跳过）的估计剩余字节数
    public static void readFile_3() {
        FileInputStream fis = null;
        /*
         * 使用 available()的好处，因为会返回文件中总字节数，
         * 那么我们在定义字节缓冲区的时候就可以定义刚刚好大小的字节数组，
         * 这样也不会造成空间的浪费！
         * 如： byte[] by = new byte[fis.available()];
         * 
         * 【注意】：使用这种方式一定要小心
         * 如果你操作的是一个DVD影像，那么available()方法会返回很大的值
         * 这时候你在内存中new 一个1G多的缓冲区是不合适的，如果你的内存刚好只有1G呢？是不是就爆了！
         * 所以较为保守和优化的方法还是:
         * byte[] by = new byte[SIZE]; SIZE 为 1024 的整数倍
         */
        int num = 0;
        try {
            fis = new FileInputStream("E:\\fos.txt");
            num = fis.available(); // 返回总字节数，若有换行的，占两个字节 '\r\n'(windows下)
            System.out.println("num = " + num);
            
            byte[] by = new byte[fis.available()]; // 当心内存不够!!!
            fis.read(by);
            System.out.println(new String(by)); // 把字节数组变成字符串
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            closeSource(fis);
        } // finally
    }
    
    
    public static void writeFile() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("E:\\fos.txt");        
            
            fos.write("abcdefg".getBytes()); // 传字节流对象
            /*
             * 这里不用 flush()
             * 因为只是针对字节流对象的，直接一个字节就可以写入
             * 而对于字符流对象，比如中文，需要两个字节才可以写入，所以需要刷新
             */
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            closeSource(fos);
        } // finally
    }
    
    public static <T extends Closeable> void closeSource(T t) {
        if (t != null) {
            try {
                t.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        } // if
    }
}
