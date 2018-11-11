package southday.java.basic.serializable;

import java.io.Serializable;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;

/* 本例主要讲解：序列化的存储规则
 * 内容包括：
 *  1) 序列化同一个对象多次，检测文件大小，再序列化同一类的不同对象，检测文件大小
 *  2) 序列化同一个对象两次（第二次序列化前，对象属性被改变），然后读取对象，输出信息
 *     序列化同一类的不同对象（对象属性相同），然后读取对象，输出信息
 * 判断 object1 == object2 是否成立
 * 
 * 实验现象：
 * (1) [实验结果描述]:
 * A) 序列同一个对象多次，检测文件大小
 *  第一次写入： 文件大小 = 87
 *  第二次写入： 文件大小 = 92
 *  第三次写入： 文件大小 = 97
 * -------------------------------------
 * B) 再次写入同一类的不同对象，检测文件大小
 *  第四次写入： 文件大小 = 108
 * [发现]:前三次写入，文件大小之差为5,而第四次写入时，文件大小之差为11   
 * [分析]: java 序列化机制为了节省磁盘空间，当写入文件的为同一对象时，并不会再将对象的内容进行存储，
 * 而只是再次存储一份引用，上面增加5字节的存储空间就是新增引用的空间和一些控制信息的空间。反序列化时，
 * 恢复引用关系，使得 obj1、obj2、obj3指向同一对象，所以三者相等，即使在写入前修改了对象的属性，只
 * 要是同一对象，就不会被重新写入，而最后取出的对象也只是第一次写入的对象。而当new一个新对象写入时，
 * 文件大小增加了11字节，这11字节就是新增对象的存储空间。
 * [更深入的细节，我还没有了解，目前只能解释道这个程度]
 * [参考网址]: http://blog.csdn.net/jiangwei0910410003/article/details/18989711/ 
 * 
 * (2) [实验结果描述]:
 * A) 序列化同一对象两次，反序列化，判断obj1 == obj2
 *  第一次写入对象obj: name = LiChaoxi
 *  修改对象obj的属性name --> name = coco
 *  第二次写入对象obj: name = coco
 *  反序列化obj1: name = LiChaoxi
 *  反序列化obj2: name = LiChaoxi
 *  判断obj1 == obj2 --> true
 * -------------------------------------
 * B) 序列化同一类的不同对象，反序列化，判断obj1 == obj2
 *  第一次写入对象obj1: name = LiChaoxi
 *  第二次写入对象obj2: name = LiChaoxi
 *  反序列化obj1: name = LiChaoxi
 *  反序列化obj2: name = LiChaoxi
 *  判断obj1 == obj2 --> false
 *  [发现]: 对于同一个对象序列化（写入文件）多次，即使期间修改了对象属性，反序列化时得到的对象引用都只
 *  指向第一次写入的该类对象，这时候这些对象是相等的; 而对于同一类的不同对象序列化到同一文件中，即使对
 *  象的属性相同，反序列化时得到的对象引用是指向不同的地址的，这时候两个对象不相等
 *  [分析]: 参看(1)中的[分析]
 */

class SerialWMPerson implements Serializable {
    private static final long serialVersionUID = 1L;
    public String name;
    
    public SerialWMPerson(String name) {
        this.name = name;
        System.out.println("SerialWMPerson is called.");
    }
    
    @Override
    public String toString() {
        return "name = " + name;
    }
}

public class WriteManyTimesSerial {
    public static void main(String[] args) {
        test1();
//        test2();
    }
    
    /* (1) 测试序列化同一个对象一次、两次、三次时，文件的大小
     * 创建一个新的对象，属性与之前的对象属性完全一样，写入文件，再次获取文件大小
     */
    public static void test1() {
        SerialWMPerson swmp = new SerialWMPerson("LiChaoxi");
        String filepath = "/home/coco/Desktop/Java/SerialWMPersonManyObject.out";
        System.out.println("before serialize: " + swmp.toString());
        try {
            FileOutputStream fos = new FileOutputStream(filepath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // 写入一次时，文件的大小
            oos.writeObject(swmp);
            oos.flush();
            System.out.println("write once, file size: " + (new File(filepath)).length());
            // 写入两次时，文件的大小
            oos.writeObject(swmp);
            oos.flush();
            System.out.println("write twice, file size: " + (new File(filepath)).length());
            // 写入三次时，文件的大小
            oos.writeObject(swmp);
            oos.flush();
            System.out.println("write third, file size: " + (new File(filepath)).length());
            // new 一个新对象，属性与swmp对象属性完全一样，写入文件
            SerialWMPerson swmp_copy = new SerialWMPerson("LiChaoxi");
            oos.writeObject(swmp_copy);
            oos.flush();
            System.out.println("write a new object, file size: " + (new File(filepath)).length());
            fos.close();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /* (2) 序列化同一个对象两次，然后读取两次，判断 obj1 == obj2
     * 序列化同一个类的不同对象（对象的属性相同），然后读取两次，判断 obj1 == obj2
     */
    public static void test2() {
        SerialWMPerson swmp = new SerialWMPerson("LiChaoxi");
        String filepath = "/home/coco/Desktop/Java/SerialWMPersonSameObject.out";
        // write
        try {
            FileOutputStream fos = new FileOutputStream(filepath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // 第一次写入
            System.out.println("before serialize, first write: " + swmp.toString());
            oos.writeObject(swmp);
//            // 第二次写入前修改对象属性
//            swmp.name = "coco";
//            System.out.println("before serialize, second write: " + swmp.toString());
//            oos.writeObject(swmp);
            
            // 第二次写入 同一类的不同对象，对象的属性相同
            SerialWMPerson another_swmp = new SerialWMPerson("LiChaoxi");
            System.out.println("before serialize, second write, a new object: " + another_swmp.toString());
            oos.writeObject(another_swmp);
            fos.close();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // read
        try {
            FileInputStream fis = new FileInputStream(filepath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            // 第一次读取
            SerialWMPerson obj1 = (SerialWMPerson) ois.readObject();
            // 第二次读取
            SerialWMPerson obj2 = (SerialWMPerson) ois.readObject();
            System.out.println("obj1 --> " + obj1.toString());
            System.out.println("obj2 --> " + obj2.toString());
            // 判断 obj1 是否等于 obj2
            System.out.println("obj1 == obj2 ? --> " + (obj1 == obj2));
            fis.close();
            ois.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        }
    }
}
