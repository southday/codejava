package southday.java.basic.serializable;

import java.io.Serializable;

/* 本例主要介绍序列化中的 serialVersionUID 的作用
 * [serialVersionUID]: 序列化的版本号，凡是实现Serializable接口的类中都有一个表示序列化
 * 版本号的静态常量， private static final long serialVersionUID
 * 
 * 1) 若类中没有显示的定义serialVersionUID，则java运行时会根据类的内部细节自动生成UID 
 * 2) java自动生成的UID与类的内容是有关的，如果class文件（类名、方法名等）没有发现变化，
 *   （增加换行、空格、注释等）这些变化，一般是不会导致生成的UID变化的，
 * 3) 类serialVersionUID的默认值完全依赖于java编译器的实现，对于同一个类，使用不同的java编译器，
 *    也可能导致生成的UID不同
 * [参考网址]: http://swiftlet.net/archives/1268 金丝燕网 
 * 
 * 显示定义[serialVersionUID]的作用：
 *    1) 在某些场合，希望类的不同版本对序列化兼容，因此需要确保类的不同版本具有相同的serialVersionUID
 *    2) 在某些场合，不希望类的不同版本对序列化兼容，因此需要确保类的不同版本就有不同的serialVersionUID
 *
 * [实验过程]:
 * 1) 对于没有显示定义serialUIDPerson的类对象进序列化和反序列化，序列化保存文件 SerialUIDPerson.out
 * 2) 在进行(1)的序列化后，为类SerialUIDPerson添加一个成员变量 public int age = 21，再通过文件
 *    SerialUIDPerson.out 进行反序列化，观察反序列化结果
 * 3) 先为类SerialUIDPerson 显示定义一个serialVersionUID，然后再次进行(2)中的操作，观察反序列化结果
 * 
 * [实验结果]:
 * 1) 对象序列化和反序列化成功
 * 2) 反序列化失败,报出异常：java.io.InvalidClassException，serializable.SerialUIDPerson; 
 *      local class incompatible: 
 *      stream classdesc serialVersionUID = 287229581464828690, 
 *      local class serialVersionUID = -829821654020046301
 *    意思就是说，文件流中的class和classpath中的class，也就是修改后的class，不兼容了，处于安全机制考虑，
 *    程序抛出异常，拒绝载入。
 * 3) 反序列化成功，输出结果： name = LiChaoxi, age = 0，这里的age为什么时0,而不是默认的21呢？
 *    因为在序列化该对象时，对象中并没有age变量，而在反序列化前，增加了age成员变量，这时候进行反序列化，
 *    由于serialVersionUID相同，所以能反序列化成功，反序列过程中，由于文件中没有存有age变量的值，所以
 *    只能赋个这个age变量系统默认值，而不会使用类中定义的默认值。
 *   
 * @author southday
 * Time - 2016.4.6
 */

class SerialUIDPerson implements Serializable {
    // (3) 为该类显示定义 serialVersionUID，再进行实验过程(2)
    private static final long serialVersionUID = 1L;
    public String name;
    // (2) 添加一个成员变量，再通过文件 SerialUIDPerson.out 反序列化对象
    public int age = 21;
    
    public SerialUIDPerson(String name) {
        this.name = name;
        System.out.println("SerialUIDPerson constructor is called.");
    }
    
    @Override
    public String toString() {
        return "name = " + name + ", age = " + age;
    }
}

public class SerialVersionUIDWorking {
    public static void main(String[] args) {
        String filepath = "/home/coco/Desktop/Java/SerialUIDPerson.out";
//        SerialUIDPerson suidp = new SerialUIDPerson("LiChaoxi");
//        System.out.println("before serialize: " + suidp.toString());
//        // write
//        SerializeUtil.serializeObject(suidp, filepath);
        // read
        SerialUIDPerson suidp_copy = (SerialUIDPerson) SerializeUtil.deserializeObject(filepath);
        System.out.println("after deserialize: " + suidp_copy.toString());
    }
}
