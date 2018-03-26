package southday.java.basic.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/* 复制图片
 * 思路：
 * 1. 用字节流读取对象和图片关联
 * 2. 用字节流写入对象创建一个图片文件，用于存储获取到的文件资源
 * 3. 通过循环读写，完成数据的存储
 * 4. 关闭资源
 * 
 */

public class CopyPicDemo {
    public static void main(String[] args) {
        copyPic();
    }
    
    public static void copyPic() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        byte[] buff = new byte[1024];
        int len = 0;
        
        try {
            fis = new FileInputStream("E:\\Tup\\IMG_20150920_123556.jpg");
            fos = new FileOutputStream("E:\\Li.jpg");
            while((len = fis.read(buff)) != -1) {
                fos.write(buff, 0, len);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            closeSource(fis);
            closeSource(fos);
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
