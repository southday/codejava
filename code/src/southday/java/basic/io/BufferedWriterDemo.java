package southday.java.basic.io;
import java.io.*;
/*
***public class BufferedWriter extends Writer
————将文本写入字符输出流，缓冲各个字符，从而提供单个字符、数组和字符串的高效写入。 

缓冲区的出现是为了提高流的效率；
所以在创建缓冲区之前，必须有流对象——也就是为什么BufferedWriter的构造函数都有参数的原因
***该缓冲区提供了一个跨平台的换行符： newline();
（1）不同的平台下，换行的操作字符可能不同，如：windows下用\r\n，Linux下用\n
（2）在下载JDK时是分系统的，如windows下的JDK..
（3）newline()在不同的JDK下实现过程不同，但结果相同，所以其具有跨平台性。
 */
public class BufferedWriterDemo {
    public static void main(String[] args) throws IOException{
        //创建一个字符写入流对象
        FileWriter fw = new FileWriter("d:\\demo.txt");
        //为了提高字符写入流的效率，加入了缓冲技术；
        //只要将需要被提高效率的流对象作为参数传递给缓冲区的构造函数即可
        BufferedWriter buf = new BufferedWriter(fw);
        for(int x=0;x<5;x++) {
            buf.write("java_"+x);
            buf.newLine();//换行
            buf.flush();//记住：只要用到缓冲区，就要记得刷新
            //读 一次刷新一次，是为了防止意外发生而导致数据读写失败，如：突然停电，电脑死机等；
            //如果flush()是等到所有数据都存储到缓冲区后才进行的，遇到停电等突发状况，则在内存中的缓冲区数据会被释放
        }
        buf.close();//这样关闭缓冲区，就是在关闭对应的流对象，所以不用再写fw.close();
    }
}
