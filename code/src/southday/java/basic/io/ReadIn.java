package southday.java.basic.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/* 字符流
 * FileReader        FileWriter
 * BufferedReader    BufferedWriter
 * 
 * 字节流
 * FileInputStream        FileOutputStream
 * BufferedInputStream    BufferedOutputStream
 * 
 */

/* 读取键盘录入 
 * System.in -- > 返回 InputStream 的对象(其实是其实现类对象)
 * static InputStream in // "标准"输入流
 * static PrintStream err // "标准"错误输出流
 * static PrintStream out // "标准"输出流
 * 
 * java.lang.Object
 *  |--java.io.OutputStream
 *      |--java.io.FilterOutputStream
 *          |--java.io.PrintStream
 */
public class ReadIn {
    public static void main(String[] args) {
        testSystemIn();
//        KeybordInTest();
    }
    
    public static void testSystemIn() {
        InputStream in = System.in; // in 的真实身份是 BufferedInputStream 对象
        System.out.println("in 的真实身份: " + in.getClass().getName());
        try {
            int ch = in.read();
            System.out.println("ch = " + ch + ", " + (char)ch);
            /*
             * 这里的 in.read()只是一个字符一个字符的读，
             * 所以即使你键盘上输入的是：abcdefgh
             * ch = 97 ('a'的ASCII码)
             */
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            closeSource(in);
        }        
    }
    
    /* 需求：
     * 通过键盘录入数据
     * 当录入一行数据后，就将该行数据进行打印
     * 如果录入的数据是over，则停止录入
     */
    public static void KeybordInTest() {
        int ch = 0;
        InputStream in = System.in;
        StringBuilder sb = new StringBuilder();
        String str = null;
        
        try { // 这个写法感觉似曾相识，其实我们在练习BufferedReader的时候就写过
            while (true) { // 这样书写很繁琐，我们可以不可以一行一行的读取呢？-->请看 TransStreamDemo.java
                ch = in.read();
                if (ch == '\r')
                    continue;
                if (ch == '\n') {
                    str = sb.toString();
                    System.out.println(str);
                    sb.delete(0, sb.length()); // 输出后就清空缓冲区
                    if (str.equals("over"))
                        break;
                }
                else {
                    sb.append((char) ch);
                }
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            closeSource(in);
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
