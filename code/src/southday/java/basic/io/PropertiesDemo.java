package southday.java.basic.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;


/* java.lang.Object
 *  |--java.util.Dictoinary<K,V>
 *      |--java.util.Hashtable<Object,Object>
 *          |--java.util.Properties
 *  
 * Properties 是 Hashtable 的子类
 * 也就是说它具备map集合的特点，而且它里面存储的键值对都是字符串（不需要泛型）
 * 
 * 是集合中和IO技术想结合的集合容器，
 * 该对象的特点，可以用于键值对形式的配置文件
 * 
 * void load(InputStream inStream)  JDK1.6+ 支持穿字符流读对象Reader
 * void store(Writer writer, String comments) 
 * 
 */

public class PropertiesDemo {
    public static void main(String[] args) throws IOException {
//        setAndGet();
//        method_1();
//        loadDemo();
        storeDemo();
    }
    
    // 如何将流中的数据存储到集合中
    // 想要将info.txt中键值数据存到集合中进行操作
    public static void method_1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("e:\\info.txt"));
//        HashMap<String, String> hm = new HashMap<String, String>();
        Properties prop = new Properties();
        String line = null;
        String[] sarr = null;
        while ((line = br.readLine()) != null) {
            sarr = line.split("=");
            prop.setProperty(sarr[0], sarr[1]);
//            hm.put(sarr[0], sarr[1]);
        }
        br.close();
        sop(prop);
//        for (HashMap.Entry<String, String> hme : hm.entrySet()) {
//            sop(hme.getKey() + "=" + hme.getValue());
//        }
    }
    
    public static void loadDemo() throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("e:\\info.txt");
        
        prop.load(fis);
        sop(prop);  // 和 method_1是一样的
        // void list(PrintStream out) 
        prop.list(System.out); // 在控制台输出  --listing properties--
    }
    
    public static void storeDemo() throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("e:\\info.txt");
//        FileOutputStream fos = new FileOutputStream("e:\\info.txt"); 【1】
        
        /* 【问题】：
         * 如果将 fos 定义在这，那么即在打开读取流(还没修改前)就打开了写入流
         * 执行下面的修改操作，然后prop.store()，其他的键值对就消失，或者说被新一轮写入覆盖
         */
        prop.load(fis); // 从指定文件加载properties
        prop.setProperty("Color", "Red"); // 修改 value
        sop(prop); // fos在【1】位置定义时，只剩下一个Color=Red输出，而fos在【2】位置定义时，其他键值对不会消失!!!!!
        
        FileOutputStream fos = new FileOutputStream("e:\\info.txt"); // 【2】
        // 而如果 fos 定义在这的话，其他的键值对就不会消失----why？？？
//        prop.store(fos, "前面带有#号的是注释，不会被Properties所加载"); // 打开info.txt会看见一堆Unicode(如:\u524D->前)
        prop.store(fos, "This is Comment"); // 还会自动跟上修改的时间
        
        fis.close();
        fos.close();
    }
    
    // 设置和获取元素
    public static void setAndGet() {
        Properties pro = new Properties();
        // 存键值对
        pro.setProperty("zhangsan", "39");
        pro.setProperty("lisi", "41");
        sop(pro);
        
        // 由 key 获取  value
        String value = pro.getProperty("zhangsan");
        sop("zhangsan -> " + value);
        
        // 取 key
        Set<String> names = pro.stringPropertyNames();
        for (String name : names) {
            sop(name + "=" + pro.getProperty(name));
        }
        
        // 修改 value
        pro.setProperty("zhangsan", 10 + "");
        sop("zhangsan ->" + pro.get("zhangsan").toString());
    }
    
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
