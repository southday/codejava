package southday.java.basic.serializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/* 本例主要讲一下 序列化中提供的 "hook" 机制，即两个方法：
 * private writeObject(java.io.ObjectOutputStream stream) throw IOException {...}
 * private readObject(java.io.ObjectInputStream stream) throw IOException, ClassNotFoundException {...}
 * 通过在类中实现这两个方法，可以让我们自定义 对象 序列化与反序列化的过程
 * []:这两个方法是通过 反射机制 被自动调用的
 * 
 * 本例将包含以下内容：
 *    1) 一个简单的自定义序列化与反序列操作的demo
 *    2) 探讨我们在什么情况下需要使用到自定义的序列化与反序列化操作
 *       a) 确保序列化数据的安全信，对一些敏感信息加密
 *       b) 确保对象的成员变量符合正确的约束条件 --> 美国总统的话：信任，也要验证
 *       c) 优化序列化的性能... --> 这里由于我暂时还没有阅读默认序列化、反序列化源码，所以谈不上优化
 * 在writeObject()和readObject()中的(1)、(2)、(3)、(4)分别有对这些问题的演示，除了 c)没有 
 * 
 * 参考网址：
 *    http://www.cnblogs.com/rollenholt/archive/2012/11/26/2789445.html    java序列化和反序列化总结 Rollen Holt
 *    http://www.cnblogs.com/mengdd/archive/2013/02/13/2911094.html    javaIO序列化与反序列化 圣骑士-wind
 * 
 * @author southday
 * Time - 2016.4.6
 */

class SerialWRPerson implements Serializable {
    private static final long serialVersionUID = 1L;
    // 为了操作方便，这里我直接写public
    public String name = "coco";
    public int age = 0;
    
    SerialWRPerson(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("SerialWRPerson constructor is called.");
    }
    
    private void writeObject(ObjectOutputStream outs) throws IOException {
        /* (1) do nothing, just output something 
         * 对象的成员变量并没有被序列化到本地保存，因为writeObject中没有做任何事情
         */
//        System.out.println("writeObject is called, just this.");
        
        /* (2) 序列化name变量，而不序列化age变量
         * 只序列化name变量的值，那么文件中也只保存了该值
         */
//        outs.writeObject(name);
        
        /* (3) 这里我们假设 age 是敏感信息，那么在序列化时就需要进行加密，
         * 为了操作简便，就只进行简单“加密”, 我们用移位操作来表示加密和解密
         */
//        age <<= 2; // 加密操作
//        outs.defaultWriteObject();
        
        /* (4) 序列化时，写入“恶意数据”，即在进行readObject()时，可能会获得与
         * 成员变量类型不符合的数据，这里使用”类型不匹配的数据“来作”恶意数据“，
         * 一是为了简便，二是我想不出有哪些”恶意数据“可以用在这些地方
         */
        outs.writeObject(name); // 正常数据，序列化name 
        outs.writeObject("abcde"); // 恶意数据， 本来应该序列化一个整数类型的值 
    }
    
    private void readObject(ObjectInputStream ins) throws IOException, ClassNotFoundException {
        /* (1) do nothing, just output something
         * 由于writeObject没有序列化对象的成员变量，并且readObject也没做任何事情，
         * 所以得到如下输出结果:
         * 序列化前： name = LiChaoxi, age = 21
         * 反序列化后： name = null, age = 0
         * null, 0分别时系统对String型变量和int型变量默认值
         */
//        System.out.println("readObject is called, just this.");
        
        /* (2) 读取name变量的值
         * 得到如下输出结果：
         * 序列化前： name = LiChaoxi, age = 21
         * 反序列化后： name = LiChaoxi, age = 0
         * 如果这里我再加一条语句： age = (int) ins.readObject();
         * 该方法就会抛出 OptionalDataException，因为对应的writeObject方法中没有写入age这个变量的值
         */
//        name = (String) ins.readObject();
        
        /* (3) 对于读取到的age，需要进行“解密”
         * 输出结果:
         * 序列化前： name = LiChaoxi, age = 21
         * 反序列化后： name = LiChaoxi, age = 21
         * 若不进行解密操作，即没有执行 age >>= 2;
         * 则反序列化后的结果是： name = LiChaoxi, age = 84;
         */
//        ins.defaultReadObject();
//        age >>= 2; // 解密操作
        
        /* (4) 读取”恶意数据“，并作数据类型判断
         * 输出结果：
         * 序列化前： name = LiChaoxi, age = 21
         * 反序列化过程中：捕获到类型转换异常，已处理!
         * 反序列化后：    name = LiChaoxi, age = 0
         */
        try {
            name = (String) ins.readObject();
            age = (int) ins.readObject();
        } catch (Exception e) {
            System.out.println("捕获到类型转换异常，已处理!");
        }
    }
    
    @Override
    public String toString() {
        return "name = " + name + ", age = " + age;
    }
}

public class WriteAndReadObjectMethod {
    public static void main(String[] args) {
        String filepath = "/home/coco/Desktop/Java/SerialWRPerson.out";
        SerialWRPerson swrp = new SerialWRPerson("LiChaoxi", 21);
        System.out.println("befor serialize: " + swrp.toString());
        // write
        SerializeUtil.serializeObject(swrp, filepath);
        // read
        SerialWRPerson swrp_desr = (SerialWRPerson) SerializeUtil.deserializeObject(filepath);
        System.out.println("after deserialize: " + swrp_desr.toString());
    }
}
