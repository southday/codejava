package southday.java.basic.io;
import java.io.*;

public class ReadeJavaFile {
    public static void main(String[] args) throws IOException{
        String str = "D:\\JDK\\Java\\java eclipse Chinese\\Workpace\\JAVA\\src\\";
        FileReader fr = new FileReader(str+"Tronado.java");
        char[] buf = new char[1024];
        int num = 0;//将p.java文件里的字符读入buf[]中，然后返回（读取的字符数）→ num, num相当于计数器
        while((num=fr.read(buf))!=-1)//read(char[] ch)返回的是：读取的字符数，如果已到达流的末尾，则返回 -1
            System.out.print(new String(buf,0,num));
        //这里不用println的原因是：如果p.java文件中的字符大于1024个，则**会换行**，再接着读取下面的字符。
        fr.close();
    }
}
