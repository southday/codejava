package southday.java.basic.io;
import java.io.*;
/*
  字节流输入输出：   IntputStream    OutputStream
 字符流输入输出：     Reader          Writer
 先学习字符流的特点，在I/O中最常见的操作目标就是：文件
 java.io-> Writer -> OutputStreamWriter -> FileWriter
 *****其实JAVA并没有把字符写入文件的功能，它只是调用系统内部的相关功能。*****
 */
public class FileWriteDemo {
    public static void main(String[] args) throws IOException{
        //创建一个文件,如果该目录下已存在同名文件，则会被覆盖。
        FileWriter fw = new FileWriter("d:\\demo.txt");//这种格式的默认路径是：workpace\项目名称\
                                                    //.txt   .doc是生成文件的格式
        fw.write("lichaoxi");  //执行该步后，字符并没有直接写到demo.txt中，而是把字符写入流中（流的缓冲区中）                
        fw.flush();//刷新本流中缓冲区的数据，即把写入流中的字符写到指定文件里
        
        fw.write(" is a boy");  //再写入流
        fw.flush();//刷新之后，相当与对前面的内容进行添加
        
        fw.close();//关闭流（节约资源），关闭之前先刷新一次流，关闭之后就不可以再write something to file
    }
}
