package southday.java.basic.io;
import java.io.*;
/*
**public class BufferedReader extends Reader
————从字符输入流中读取文本，缓冲各个字符，从而实现字符、数组和行的高效读取。
**public String readLine()
————读取一个文本行。通过下列字符之一即可认为某行已终止：换行 ('\n')、回车 ('\r') 或回车后直接跟着换行。
返回——包含该行内容的字符串，不包含任何行终止符，如果已到达流末尾，则返回 null 
 */
public class BufferedReaderDemo {
    public static void main(String[] args) throws IOException {
        //创建一个读取流对象和文件相关联
        FileReader fr = new FileReader("d:\\p.java");
        
        //为了提高效率，加入缓冲技术，将字符读取流对象作为参数传递给缓冲区的构造函数
        BufferedReader buf = new BufferedReader(fr);    //默认大小的输入缓冲区
        
        String line = null;
        /*while((line=buf.readLine())!=null)//读取文本中的行，读取完毕自动跳到下一行读取
            System.out.println("line = "+line);*/
        for(int i=1; ((line=buf.readLine())!=null); i++)
            System.out.println(i+" "+line);
        
        buf.close();
    }
}
