package southday.java.basic.io;
import java.io.*;
//通过缓冲区复制一个.java文件
public class BuffereCopyDemo {
    public static void main(String[] args) {
        BufferedWriter buw = null;
        BufferedReader bur = null;
        try {            //使用匿名对象，因为关闭  buw/bur 即关闭 流对象
            buw = new BufferedWriter(new FileWriter("f:\\lcx.txt"));
            bur = new BufferedReader(new FileReader("d:\\p.java"));
            
            String line = null;
            while((line=bur.readLine())!=null) {
                buw.write(line);//不包含任何行终止符，即：“回车”不会被读取
                buw.newLine();//则需要换行
                buw.flush();
            }
        }
        catch(IOException e) {
            throw new RuntimeException("读写失败");
        }
        finally {        //分开try{} 分开判断
            try {
                if(buw!=null)
                    buw.close(); // 这里可能会抛出异常，关闭异常
            } catch(IOException e) {
                throw new RuntimeException("写入流关闭失败");
            }
            try {
                if(bur!=null)
                    bur.close();
            } catch(IOException e) {
                throw new RuntimeException("读取流关闭失败");
            }
        }
    }
}
