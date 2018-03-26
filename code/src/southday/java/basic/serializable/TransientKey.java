package southday.java.basic.serializable;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/* 本例主要介绍 transient 关键字
 * [内容包括]:
 *     1) 什么是 transient 关键字？ 其有何作用？
 *     2) 被 transient 关键字修饰的非静态成员变量真的不能被序列化吗？
 * 
 * [解析]:
 *  1) a) transient 是java中的一个关键字，只能用于修饰变量
 *     b) 当一个变量被transient 修饰时，就表示该变量不再是对象持久化的一部分，该变量内容在序列化后无法获得访问
 *     c) 局部变量不能被 transient 修饰，如果用 transient 修饰自定义类变量，则该类需要实现 Serializable 接口
 *  
 *  实验中，SerialTPerson 的 成员变量 age 被 transient 修饰，得到如下输出结果：
 *     序列化前： name = LiChaoxi, age = 21
 *     反序列化后: name = LiChaoxi, age = 0
 *     发现变量 age 的值是系统默认值，而不是创建对象是赋值的21,说明变量age没有被序列化 
 *  
 *  2) 被 transient 修饰的成员变量真的不能被序列化吗？
 *   在java规范中，对 transient 关键字的解释是：被transient 关键字修饰的变量不应该被当作对象持久化的一部分
 *   注意，上面用到的词是 “不应该”，这么说来，我们应该是可以对 被transient修饰的变量进行序列化的，只是需要自己
 *   手动实现。不过这样没多大意义，因为transient本意为“瞬时的”，对于经常改变的变量我们是没必要对其进行持久化的，
 *   为了一探究竟，还是写一写吧...
 *  
 *  经过重写 writeObject()和readObject() 方法，手动对 被transient修饰的变量age进行了序列化和反序列化
 *  实验结果输出：
 *   序列化前： name = LiChaoxi, age = 21
 *   反序列化后： name = LiChaoxi, age = 21
 *  证明了：我们是可以在手动的情况下实现对 被transient关键字修饰的变量 进行序列化和反序列化的
 * [参考网址]: http://www.cnblogs.com/lanxuezaipiao/p/3369962.html    Alexia
 * 
 * @author  southday
 * @date    2016-04-06
 */

class SerialTPerson implements Serializable {
    private static final long serialVersionUID = 1L;
    public String name;
    public transient int age;
    
    public SerialTPerson(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("SerialTPerson constructor is called.");
    }
    
    private void writeObject(ObjectOutputStream outs) throws IOException {
        // 对 被transient修饰的变量age 进行手动序列化
        outs.defaultWriteObject();
        outs.writeObject(age); // 看我序列化一个被transient修饰的变量
    }
    
    private void readObject(ObjectInputStream ins) throws IOException, ClassNotFoundException {
        ins.defaultReadObject();
        age = (int) ins.readObject(); // 手动反序列化 被transient修饰的变量age
    }
    
    @Override
    public String toString() {
        return "name = " + name + ", age = " + age;
    }
}

public class TransientKey {
    public static void main(String[] args) {
        String filepath = "/home/coco/Desktop/Java/TransientKey.out";
        SerialTPerson stp = new SerialTPerson("LiChaoxi", 21);
        System.out.println("before serialize: " + stp.toString());
        // write
        SerializeUtil.serializeObject(stp, filepath);
        // read
        SerialTPerson stp_copy = (SerialTPerson) SerializeUtil.deserializeObject(filepath);
        System.out.println("after deserialize: " + stp_copy.toString());
    }
}
