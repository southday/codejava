package southday.java.basic.io;
import java.io.*;
/*
已知BufferedReader中的readLine()是基于read()实现的，
————把读取的单个字符储存到缓冲区的数组中，读完一行后再取出来写入
所以可以自定义个readLine()方法，为了方便，这里使用StringBuilder类
————因为其中的append()方法返回的是该对象的一个引用
 */
class MyBuffereReadLine {
    private FileReader r;//需要操作  读取流对象
    private int linenum = 0;
    MyBuffereReadLine(FileReader r) {
        this.r = r;
    }
    public String Myreadline() throws IOException {
        StringBuilder sb = new StringBuilder();//为了方便，就直接throws了
        int ch = 0;
        linenum ++;
        while((ch=r.read())!=-1) {
            if(ch=='\r')//windows系统下是以'\r\n'做“回车”的，所以为了避免'\r'被读取，则在此做判断
                continue;
            if(ch=='\n') //读到'\n'则说明该行读完，读完一行则返回，read()中指针自动指向下一行的第一个字符
                return sb.toString();
            sb.append((char)ch);//没读完则继续添加字符
        }
        //System.out.println(sb.toString());
        //注意：如果最后一行的最后没有用“回车”(\r\n或者\n)结尾，这样最后一行就不会被返回，所以这这里加个if来判断，提高健壮性！
        if(sb.length()!=0)//应该是理解为：每返回一行，sb之前的数据就被删除，之后重新被添加字符
            return sb.toString();
        return null;//读到文件的最末尾，要返回一个null，与while((line=mybuf.Myreadline())!=null)对应
    }
    public void setLineNumber(int linenum) {
        this.linenum = linenum;
    }
    public int getLineNumber() {
        return linenum;
    }
    public void MyClose() throws IOException {
        r.close();        //其实BufferedReader类中的close()就是在调用FileReader类中的close()
    }
}
public class MyReadLineDemo {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("d:\\p.java");
        
        MyBuffereReadLine mybuf = new MyBuffereReadLine(fr);
        
        String line = null;
        while((line=mybuf.Myreadline())!=null)
            System.out.println(mybuf.getLineNumber()+":"+line);
        mybuf.MyClose();
    }
}

/*
int ch = 0;                 这里如果写成： char ch = 0;
while((ch=r.read())!=-1) {            while((ch=(char)r.reade())!=-1) {
    if(ch=='\r')                             if(ch=='\r')
        continue;                                continue;
    if(ch=='\n')                             if(ch=='\n')
        return sb.toString();                     return sb.toString();
    sb.append((char)ch);//没读完则继续添加字符                 sb.append(ch);
}                                        }
虽然右边的写法也可以正确输出，但是需要注意的是：  ch=(char)r.read()！=-1，这是永远为true的，因为char字符不会等于int数字；
所以程序只能手动停止，或者是等到read()完该文件所以位置，然后报出异常。
*/
