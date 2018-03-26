package southday.java.basic.io;
import java.io.*;
/*
FileWriter(String fileName, boolean append) 
          根据给定的文件名以及指示是否附加写入数据的 boolean 值来构造 FileWriter 对象。
 */
public class FileWriterContinue {
    public static void main(String[] args) {
        FileWriter fw = null;
        try {
            //传递一个true参数，代表不覆盖已有的文件，并在已有文件的末尾处进行数据续写
            fw = new FileWriter("demo.txt",true);
            fw.write("lichaoxi");//windows 中的“回车”需要两个字符：\r\n，linux中用\n即可
            fw.write("\r\nlove java "); //所以要实现换行写入，则需要：←
            //如果只写\n，则在demo.txt中会出现黑色的小方块，因为windows系统不能识别！
            fw.flush();
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            try {
                if(fw!=null) {
                    fw.close();
                }
            } catch(IOException e) {
                System.out.println(e.toString());
            }
        }
    }
}
