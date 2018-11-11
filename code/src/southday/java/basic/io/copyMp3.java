package southday.java.basic.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.Closeable;

/* 演示MP3的复制，通过缓冲区
 * BufferedInputStream;
 * BufferedOutputStream;
 * 
 */

public class copyMp3 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
//        copymp3_1();
        copymp3_2();
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
    }
    
    public static void copymp3_1() {
//        FileOutputStream fos = null;
//        FileInputStream fis = null;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
//        byte[] buff = new byte[1024*2];
        int ch = 0;
        
        try {
//            fis = new FileInputStream("F:\\Music\\Gotta Have You-The Weepies.mp3");
//            fos = new FileOutputStream("E:\\Gotta Have You-The Weepies.mp3");
            
            bis = new BufferedInputStream(new FileInputStream("F:\\Music\\Gotta Have You-The Weepies.mp3"));
            bos = new BufferedOutputStream(new FileOutputStream("E:\\Gotta Have You-The Weepies.mp3"));
            
            // 【1】  使用byte[] 字节数组来当缓冲区，所用时间为 23ms ~ 28ms
//            while((len = bis.read(buff)) != -1) {
//                bos.write(buff, 0, len);
//            }
            // 【2】使用BufferedInputStream和BufferedOutputStream来实现，用时 328ms+
            // 造成【2】比【1】耗时多的原因是否是：函数调用的时间，还是其他的？ 其底层是否就为【1】这种方法实现
            while((ch = bis.read()) != -1) {
                bos.write(ch);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            closeSource(bis);
            closeSource(bos);
        }
    }
    
    public static void copymp3_2() {
        MyBufferedInputStream mbis = null; // 使用自制的字节流缓冲区
        BufferedOutputStream bos = null;
        int len = 0;
        
        try {
            mbis = new MyBufferedInputStream(new FileInputStream("F:\\Music\\Gotta Have You-The Weepies.mp3"));
            bos = new BufferedOutputStream(new FileOutputStream("E:\\Gotta Have You-The Weepies.mp3"));
            
            while ((len = mbis.MyRead()) != -1) {
                bos.write(len);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            closeSource(mbis);
            closeSource(bos);
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
