package southday.java.basic.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

/* 你在某些论坛上上传文件时会提示：上传文件的大小受限制，建议分卷上传
 * 这里的分卷，就是将一个大的文件分成几个小的文件碎片，当到达目的地后又可以进行合并
 * 比如接下来的例子中有个7.8MB的音频文件，设置个碎片的大小有2MB，则经过流分割后会得到4个碎片（最后一个大小应该为1.8MB）
 */

public class SplitFileDemo {
    public static void main(String[] args) throws IOException {
        // splitFile(); // 可以发现在 e:\\Gotta Have You-The
        // Weepies目录下出现了4个.mp3.part文件
        mergerFile();
    }

    // 切割流
    public static void splitFile() throws IOException {
        // 获取源
        FileInputStream fis = new FileInputStream(
                "e:\\Gotta Have You-The Weepies.mp3");

        // 目的为多个输出流，一个输出流得到一个文件碎片
        FileOutputStream fos = null;
        int len = 0;
        int count = 0;
        byte[] buf = new byte[2 * 1024 * 1024]; // 缓冲区大小定义为2MB
        while ((len = fis.read(buf)) != -1) {
            fos = new FileOutputStream("e:\\Gotta Have You-The Weepies\\"
                    + (count++) + ".mp3.part");
            fos.write(buf, 0, len);
            fos.close(); // 一个流写完之后需要关闭
        }

        fos.close();
        fis.close();
    }

    // 能切就能合--合并
    public static void mergerFile() throws IOException {
        // 获取源，使用SequenceInputStream来合并
        // 先创建Enumeration类
        Vector<FileInputStream> v = new Vector<FileInputStream>();
        v.add(new FileInputStream("e:\\Gotta Have You-The Weepies\\0.mp3.part"));
        v.add(new FileInputStream("e:\\Gotta Have You-The Weepies\\1.mp3.part"));
        v.add(new FileInputStream("e:\\Gotta Have You-The Weepies\\2.mp3.part"));
        v.add(new FileInputStream("e:\\Gotta Have You-The Weepies\\3.mp3.part"));

        Enumeration<FileInputStream> en = v.elements();
        // 得到碎片文件的有序序列
        SequenceInputStream sis = new SequenceInputStream(en);

        // 目的
        FileOutputStream fos = new FileOutputStream(
                "e:\\Gotta Have You-The Weepies\\Gotta Have You-The Weepies.mp3");

        byte[] buf = new byte[2 * 1024 * 1024]; // 写入缓冲区，和上面的对应
        int len = 0;
        while ((len = sis.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }

        fos.close();
        sis.close();
    }

    // 佛说 Vector 效率太低，还不如自己弄一个Enumeration类来解决
    public static void merger() throws IOException {
        // 获取源, 不用Vector了，用ArrayList
        ArrayList<FileInputStream> alf = new ArrayList<FileInputStream>();
        alf.add(new FileInputStream(
                "e:\\Gotta Have You-The Weepies\\0.mp3.part"));
        alf.add(new FileInputStream(
                "e:\\Gotta Have You-The Weepies\\1.mp3.part"));
        alf.add(new FileInputStream(
                "e:\\Gotta Have You-The Weepies\\2.mp3.part"));
        alf.add(new FileInputStream(
                "e:\\Gotta Have You-The Weepies\\3.mp3.part"));
        Iterator<FileInputStream> it = alf.iterator();
        // 使用匿名内部类的方式来获得一个 Enumeration 实例
        /* 
         * Enumeration<FileInputStream> en = new Enumeration<FileInputStream>() {...};
         * 这里不是对interface Enumeration的实例化，而是可以看成
         * 
         * Enumeration<FileInputStream> en = new xxx;  其中xxx是一个类的构造函数
         * 这里的  xxx 正是: Enumeration<FileInputStream>() {...}
         * 是一个匿名内部类的构造函数，这个匿名内部类实现了    Enumeration 这个接口
         * 
         * 所以 en 其实时  父类引用 指向了子类对象(或者说是实现类【该匿名内部类】对象)
         * 那么调用en.hasMoreElements() 和  en.nextElement()这两个方法时其实是在调用实现类中的方法
         */
        Enumeration<FileInputStream> en = new Enumeration<FileInputStream>() {
            public boolean hasMoreElements() {
                return it.hasNext();
            }

            public FileInputStream nextElement() {
                return it.next();
            }
        };

        // 得到碎片文件的有序序列
        SequenceInputStream sis = new SequenceInputStream(en);

        // 目的
        FileOutputStream fos = new FileOutputStream(
                "e:\\Gotta Have You-The Weepies\\Gotta Have You-The Weepies.mp3");

        byte[] buf = new byte[2 * 1024 * 1024]; // 写入缓冲区，和上面的对应
        int len = 0;
        while ((len = sis.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }

        fos.close();
        sis.close();
    }
}
