package southday.java.basic.io;
import java.io.*;
/*
abstract  void close() 
          关闭该流并释放与之关联的所有资源。 
int read() throws FileNotFoundException
          读取单个字符。 
 */
public class FileReaderDemo {
    public static void main(String[] args) {
        FileReader fr = null;
        try {
            //创建一个文件读取流对象，和指定名称的文件相关联；
            //要保证该文件是已经存在的，如果不存在，会发生异常：FileNotFoundException
             fr = new FileReader("demo.txt");
             int ch = 0;
             //调用读取流对象的read()方法；
             //read()方法：一次读取一个字符，而且会自动往下读
             while((ch=fr.read())!=-1) //作为整数读取的字符，范围在 0 到 65535 之间 (0x00-0xffff)，如果已到达流的末尾，则返回 -1
                 System.out.println("ch = "+(char)ch+";");//这样写的话只会返回来字符的int形式，可在前面加(char)进行强制转化
             //如果输出出现：ch = (换行);ch = (换行);的情况，应该是demo.txt里虽然没有输入字符，但是第2行已经开启（允许输入字符）
        } catch(IOException e) {
            System.out.println(e.toString());
        }
        finally {
            try {
                if(fr!=null)
                    fr.close();//FileReader中的close()在关闭流前***不刷新
            } catch(IOException e) {
                System.out.println(e.toString());
            }
        }
    }
}
