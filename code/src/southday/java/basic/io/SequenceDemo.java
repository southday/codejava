package southday.java.basic.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.Enumeration;
import java.util.Vector;

/* SequenceInputStream 表示其他输入流的逻辑串联。它从输入流的有序集合开始，并从第一个输入流开始读取，
 * 直到到达文件末尾，接着从第二个输入流读取，依次类推，直到到达包含的最后一个输入流的文件末尾为止。 
 * 
 *  (1) public SequenceInputStream(Enumeration<? extends InputStream> e)
 *       e - 输入流的一个枚举
 * 
 * (2) public SequenceInputStream(InputStream s1, InputStream s2);
 *     s1 - 要读取的第一个输入流。
 *     s2 - 要读取的第二个输入流。
 */

public class SequenceDemo {
    public static void main(String[] args) throws IOException {
        
        Vector<FileInputStream> v = new Vector<FileInputStream>();
        v.add(new FileInputStream("e:\\1.txt"));
        v.add(new FileInputStream("e:\\2.txt"));
        
        // 实现 Enumeration 接口的对象，它生成一系列元素，一次生成一个。连续调用 nextElement 方法将返回一系列的连续元素
        Enumeration<FileInputStream> en = v.elements();
        SequenceInputStream sis = new SequenceInputStream(en);
        
        FileOutputStream fos = new FileOutputStream("e:\\4.txt");
        
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = sis.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }
        fos.close();
        sis.close();
    }
}
