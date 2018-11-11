package southday.java.basic.other;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

/*
 * System 类,类中的方法和属性都是静态的
 *     |-- out ： 代表标准输出，默认是控制台
 *     |-- in  ： 默认是键盘
 * 描述系统的一些信息
 *     |-- 获取系统属性信息：public static Properties getProperties()
 */

public class SystemDemo {
    public static void main(String[] args) throws IOException {
        // 【1】因为Properties是Hashtable的子类，也是Map集合的一个子类对象，
        // 那么可以通过Map的方法取出集合中的元素。
        // 该集合中存储的都是 字符串 String 没有泛型定义！！
        Properties prop = System.getProperties();
        String value = null;
        for (Object obj : prop.keySet()) { // 会打印出很多系统属性！！
            value = (String) prop.get(obj);
            System.out.println(obj + " --> " + value);
        }
        
        /* java.lang.Object
         *     |--java.util.Dictionary<K,V>
         *         |--java.util.Hashtable<Object,Object>
         *             |--java.util.Properties
         * 
         * Properties 类中有一个方法:
         * void list(PrintStream out) -- 将属性列表输出到指定的输出流
         * 我们在将IO的时候提到过PrintStream,它是OutputStream的间接子类
         * 而PrintStream中的一个构造方法：
         *         PrintStream(String fileName)
         *         创建具有指定文件名称且不带自动行刷新的新打印流
         * 所以我们可以这样写：
         *          prop.list(new PrintStream("E:\\jvm.txt"));
         * 从而将jvm启动时候所加载的东西用列表的形式写入到E盘jvm.txt文件中
         */
        prop.list(new PrintStream("E:\\jvm.txt")); // 这个是要抛出异常的！！
        
        // 【2】如何在系统中自定义一些特有信息呢？
        /* public static String setProperty(String key, String value) {...}
         * key -- 系统属性的名称
         * value -- 系统属性的值
         * 返回：系统属性以前的值，如果没有以前的值，则返回null
         */
        
        // 【3】获取指定属性信息
        /* public static String getProperty(String key) {...}
         * key - 系统属性的名称
         * 系统属性的字符串值，如果没有带有此键的属性，则返回 null。 
         */
        String osName = System.getProperty("os.name");
        System.out.println("os.name = " + osName);
        
        // 【4】可不可以在jvm启动时，动态地加载一些属性信息呢？
        /* jvm启动的时候，在命令行写： >java 文件名
         * 而jvm中有一个 -D参数， -D<name>=<value>，以键值对的形式存在
         * 在命令输入 >java -Dhaha=qqqq 文件名，这就让jvm在启动时动态加载了haha这个参数
         * 这样就可以通过：
         * System.getProperty("haha")来获取了值"qqqq"了
         */
    }
}
