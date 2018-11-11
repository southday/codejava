package southday.java.basic.io;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/* 
 * (1) public static File[] listRoots() {...}
 * 表示可用文件系统根的 File 对象数组；如果无法确定根集，则返回 null。如果没有文件系统，那么该数组将为空
 * 
 * (2) public String[] list() {...}
 * 返回字符串数组，这些字符串指定此抽象路径名表示的目录中的文件和目录。如果目录为空，那么数组也将为空。
 * 如果此抽象路径名不表示一个目录，或者发生 I/O 错误，则返回 null。 
 * 
 * (3) public File[] listFiles() {...}
 * 抽象路径名数组，这些路径名表示此抽象路径名表示的目录中的文件和目录。
 * 如果目录为空，那么数组也将为空。如果抽象路径名不表示一个目录，或者发生 I/O 错误，则返回 null
 */

public class FileDemo2 {
    public static void main(String[] args) throws IOException {
        method();
    }
    
    public static void method() throws IOException {
        // 【1】 listRoots()
        File[] arrFile = File.listRoots();
        for (File f : arrFile) {
            sop(f); // out : C:\  D:\  E:\  F:\  G:\ (当前机器下的有效盘符)
        }
        
        sop("-------------------------------");
        // 【2】 list()
        File f = new File("c:\\");
        String[] names = f.list();
        for (String name : names) {
            sop(name); // 输出的是C盘下的所有文件及其文件夹【包含隐藏文件】的名称
        }
        // listFiles() 返回一个抽象路径名数组，这些路径名表示此抽象路径名表示的目录中的文件
        File fdir = new File("e:\\");
        File[] farr = fdir.listFiles();
        for (File tf : farr) { // 因为listFiles()返回的是File[]，所以获取到其中的对象是可以有更多的操作
            sop(tf.getName() + " :: " + tf.length());
        }
        
        sop("-------------------------------");
        // 【3】list(FilenameFilter filter)
        File dir = new File("e:\\");
        String[] arr = dir.list(new FilenameFilter() { // 匿名内部类
            public boolean accept(File dir, String name) {
//                sop(dir + " --> " + name);
//                return true; 对于返回false的进行过滤
                return name.endsWith(".jpg"); // 返回所有以.jpg结尾的文件
            }
        });
        sop("length : " + arr.length);
        for (String name : arr) {
            sop(name);
        }
        
    }
    
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
