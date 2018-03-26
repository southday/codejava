package southday.java.basic.io;

import java.io.File;
import java.io.IOException;

/* File 常见方法
 * 1. 创建
 *     |-- createNewFile() -- boolean
 *         如果指定的文件不存在并成功地创建，则返回 true；如果指定的文件已经存在，则返回 false 
 *         和输出流不一样，输出流对象一建立创建文件，若文件已存在，会覆盖
 *     
 *     |-- mkdir() -- boolean  创建文件夹
 *  |-- mkdirs() -- boolean  创建多级文件夹
 * 
 * 2. 删除
 *     |-- delete() -- boolean
 *         删除此抽象路径名表示的文件或目录,当且仅当成功删除文件或目录时，返回 true；否则返回 false 
 *
 *  |-- deleteOnExit() -- void
 *      在虚拟机终止时，请求删除此抽象路径名表示的文件或目录,
 *  
 * 3. 判断
 *     |-- exists() -- boolean
 *          测试此抽象路径名表示的文件或目录是否存在, 当且仅当此抽象路径名表示的文件或目录存在时，返回 true；否则返回 false
 *     
 *     |-- isFile() -- boolean
 *  |-- isDirctory() -- boolean
 *  |-- isHidden() -- boolean
 *      仅当此抽象路径名表示的文件根据底层平台约定是隐藏文件时，返回 true
 *  |-- isAbsolute() -- boolean
 *      如果此抽象路径名是绝对路径名，则返回 true；否则返回 false
 *  
 * 4. 获取信息
 *     |-- getName() -- String
 *  |-- getPath() -- String
 *  |-- getAbsolutePath() -- String
 *  |-- getParent() -- String
 *  |-- lastModified() -- long
 *  |-- length() -- long
 * 
 */


public class FileDemo {
    public static void main(String[] args) throws IOException, InterruptedException {
//        consMethod();
//        method_1();
//        method_2();
//        method_3();
//        method_4();
        method_5();
    }
    
    public static void method_5() throws IOException {
        /* public boolean renameTo(File dest)
         *     重新命名此抽象路径名表示的文件
         *     当且仅当重命名成功时，返回 true；否则返回 false 
         */
        
        File f1 = new File("e:\\file.txt");
        File f2 = new File("e:\\wowowo.txt");
        sop("f1.renameTo(f2) : " + f1.renameTo(f2)); // 发现E盘下的file.txt变成了wowowo.txt
        
        File f3 = new File("f:\\ppoe.txt");
        sop("f2.renameTo(f3) : " + f2.renameTo(f3)); // 发现E盘下的wowowo.txt消失了，而F盘下出现pppoe.txt文件
    }
    
    public static void method_4() throws IOException {
        File f = new File("file.txt");
        
        sop("path ：" + f.getPath()); // 上面new File(str)中str是什么, f.getPath()就输出什么
        sop("absolutepath : " + f.getAbsolutePath()); //  D:\JDK\Java\java ee developers\workplease\JAVA\file.txt
        
        // 如果我写成 File f = new File("abc//file.txt");  则 f.getParent()返回的是 abc (即：上一层目录)
        sop("parent : " + f.getParent()); // 该方法返回的是绝对路径中的文件父目录，如果获取的是相对路径，则返回null
        
        
    }
    
    public static void method_3() throws IOException {
        File f = new File("file.txt");
        
        // 记住，在判断文件对象是否是文件或者目录时，必须先判断该文件对象封装的内容是否存在，exists()
        sop("isDirctory : " + f.isDirectory());
        sop("isFile :" + f.isFile());
    }
    
    public static void method_1() throws IOException, InterruptedException {
        File f1 = new File("e:\\file.txt"); // D:\file.txt 不存在
        f1.createNewFile();
        sop("f1 -> " + f1);
        Thread.sleep(2000);
        sop("delte f1 -> " + f1.delete()); 
    }
    
    public static void method_2() throws IOException {
        File f = new File("e:\\lskdfj.txt");
        sop("file exists : " + f.exists()); // false
        
        // 创建文件夹
        File dir = new File("e:\\abc");
        sop("file exists : " + dir.exists()); // false
        sop("mkdir : " + dir.mkdir()); // 创建单级目录
        
        File dirs = new File("e:\\abc\\def\\ijk");
        sop("mkdirs : " + dirs.mkdirs());
    }
    
    // 创建File对象
    public static void consMethod() {
        File f1 = new File("d:\\test.txt");
        File f2 = new File("d:", "test.txt");
        
        File f3 = new File("d:"); // 将目录封装成File对象
        File f4 = new File(f3, "test.txt");
        
        sop(f1); // 输出 d:\test.txt
        sop(f2); // d:\test.txt
        sop(f3 + " -> " + f4); // d: -> d:\test.txt
        
        // File.separator字段实现跨平台
        File f5 = new File("d:" + File.separator + "test.txt"); 
        sop(f5);
    }
    
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
