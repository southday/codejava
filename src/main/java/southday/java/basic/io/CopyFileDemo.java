package southday.java.basic.io;
import java.io.*;
/*将D盘中的p.java文件copy到F盘的p.java中
abstract void write(char[] cbuf, int off, int len) 
 写入字符数组的某一部分。cbuf - 字符数组;off - 开始写入字符处的偏移量;len - 要写入的字符数 
方法：（1）读一个字符写一个字符**繁琐且效率低，这里不写出**；（2）读一堆储存在字符数组中，再写出去；
*/
public class CopyFileDemo {        
    public static void main(String[] args) {
        FileWriter fw = null;//定义局部变量时，通常要初始化
        FileReader fr = null;
        try {
            fw = new FileWriter("f:\\lcx.txt");
            fr = new FileReader("d:\\p.java");
            char[] buf = new char[1024];
            int len = 0;
            while((len=fr.read(buf))!=-1)
                fw.write(buf,0,len);
        } catch(IOException e) {
            throw new RuntimeException("读写失败");
            //System.out.println(e.toString());
        }
        finally {
            try {
                if(fw!=null)
                    fw.close();
                if(fr!=null)
                    fr.close();
            } catch(IOException e) {
                System.out.println(e.toString());
            }
        }
    }
}
