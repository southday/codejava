package southday.java.basic.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 * (1) 列出指定目录下的文件或者文件夹，包括子目录下的内容
 * 也就是列出指定目录下的所有内容
 * 
 * (2) 删除指定目录下的所有内容（包括子目录下的内容）
 * 
 * (3) 将一个指定目录下的java文件的绝对路径，存到一个文本文件中，建立一个java文件列表文件
 * 思路：
 *         1. 对指定的目录进行递归
 *         2. 获取递归过程所有的java文件的路径
 *         3. 将这些路径存储到集合中
 *         4. 将集合中的数据写入到一个文件中
 */

public class FileDemo3 {
    public static void main(String[] args) throws IOException {
//        File dir = new File("F:\\验证码识别");
//        showDir_Recursive(dir);
        
        File dir = new File("D:\\JDK\\Java\\java ee developers\\workplease\\JAVA");
        createJavaFileList(dir, "f:\\JavaList.txt");
    }
    
    // (1) 列出指定目录下的所有文件
    public static void showDir_Recursive(File dir) throws IOException {
        File[] farr = dir.listFiles();
        for (File f : farr) {
            if (f.isDirectory())
                showDir_Recursive(f);
            else
                sop(f.getName() + " :: " + f.length());
        }
    }
    
    // (2) 删除指定目录下的所有文件
    public static void removeDir_Recursive(File dir) throws IOException {
        File[] farr = dir.listFiles();
        for (File f : farr) {
            if (f.isDirectory())
                removeDir_Recursive(f);
            else
                sop(f.getName() + " :: " + f.delete()); // 干掉它
        }
        sop(dir.getName() + " :: " + dir.delete()); // dir中的文件干掉后再来干掉dir
    }
    
    // (3) 将一个指定目录下的java文件的绝对路径，存到一个文本文件中，建立一个java文件列表文件
    public static void createJavaFileList(File dir, String JavaListPath) throws IOException {
        ArrayList<String> al = new ArrayList<String>();
        
        // 获取指定目录下所有(包括子目录中)的java文件的绝对路径
        getJFAbsolutePath(dir, al);
        
        // 将包含java文件绝对路径的ArrayList<>中的内入写入JavaList.txt中
        FileWriter fw = new FileWriter(JavaListPath);
        BufferedWriter bw = new BufferedWriter(fw);
        for (String path : al) {
            bw.write(path);
            bw.newLine();
            bw.flush();
        }
        bw.close();
    }
    public static void getJFAbsolutePath(File dir, ArrayList<String> al) {
        File[] farr = dir.listFiles();
        for (File f : farr) {
            if (f.isDirectory())
                getJFAbsolutePath(f, al);
            else if (f.getName().endsWith(".java")){
                al.add(f.getAbsolutePath());
            }
        }
    }
    
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
