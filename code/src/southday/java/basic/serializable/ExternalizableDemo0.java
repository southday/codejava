package southday.java.basic.serializable;

import java.io.Externalizable;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.io.IOException;

/* 本例主要介绍 Externalizable 接口相关知识
 * 
 * 1) [Externalizable] 实现了 Serializable 接口
 *     a) 当你想指定自己的序列化机制时，就可以实现该接口来进行，
 *  b) 当你实现该接口时，你就需要重写以下两个方法，来实现自定义序列化和反序列化机制：
 *      public void readExternal(ObjectInput ins) throws IOException, ClassNotFoundException {...}
 *      public void writeExternal(ObjectOutput outs) throws IOException {...} 
 *  c) 当你实现该接口时，你就需要为类提供一个默认的（无参的）构造函数
 * 
 * 2) 自定义序列化机制，使用readObject()和writeObject()不是也可以实现吗？
 *    为什么还要使用 Externalizable 呢？
 *  a) 当使用Serializable 进行序列化时，除了所属字段，属于这个对象map的和成员变量能接触到的所有对象都会被序列化
 *   --> 比如 Student类 有个父类 Person类，那么会序列化Student的所有父类，直到 Object类
 *   --> 同样，如果 Student类中有一个 指向其他对象的 引用，那么该引用所指向的对象的整个集合也会被序列化
 *  b) 当使用Serializable时，jvm会使用反射来进行序列化，这样会很慢... 这里我没实验过
 *  c) 当使用Serializable时，类的描述信息也会储存到流里，包括父类的描述信息，和类关联的成员变量的信息，可能会使性能下降
 *  d) 如果我们只是想序列化 Student 类中的几个成员变量，是没必要增加这么多开销的
 * 
 * 3) 使用 Serializable 和 Externalizable 的区别
 *  a) 通过 Serializable接口对对象进行序列化的支持是内建于核心API的，
 *     通过 Externalizable接口对对象进行序列化、反序列化的操作是需要程序员自己实现的
 *  b) 使用 Serializable时，有关类的信息（比如：它的成员变量和成员变量的类型信息等）都会与实例数据一起被存储起来
 *     使用 Externalizable时，其只会存储每个被存储类型的非常少的信息 --> 这非常少到底是有多少呢？ - -
 *  总结一下
 *  [Serializable]
 *     优点: 内建支持    易于实现
 *     缺点: 占用空间过大    由于额外开销导致速度较慢
 *  [Externalizable]
 *     优点: 开销较少（因为是由程序员决定存储什么东西）    可能的速度提升
 *     缺点: 虚拟机不提供任何帮助，所有的工作都需要程序员自行完成
 *     
 * [参考网址]:
 *  http://www.deepinmind.com/java/2014/03/05/Java%E7%9A%84Externalizable%E6%8E%A5%E5%8F%A3.html
 *  http://blog.csdn.net/namecyf/article/details/8057820
 * 
 * @author southday
 * @Date 2016-04-08
 */

class ExternalPerson implements Externalizable {
    private static final long serialVersionUID = 1L;
    public String name;
    public int age;

    // 默认的（无参的）构造函数必须提供，不然就会报InvalidClassException异常
    public ExternalPerson() {
        System.out.println("ExternalPerson() constructor is called.");
    }
    
    public ExternalPerson(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("ExternalPerson(String name, int age) constructor is called.");
    }
    
    @Override
    public void writeExternal(ObjectOutput outs) throws IOException {
        // 自定义写操作
        outs.writeUTF(name);
    }
    
    @Override
    public void readExternal(ObjectInput ins) throws IOException, ClassNotFoundException {
        // 自定义读操作
        name = ins.readUTF();
    }
    
    @Override
    public String toString() {
        return "name = " + name + ", age = " + age;
    }
}

public class ExternalizableDemo0 {
    public static void main(String[] args) {
        String filepath = "/home/coco/Desktop/Java/ExternalPerson.out";
        ExternalPerson ep = new ExternalPerson("LiChaoxi", 21);
        System.out.println("before serialize: " + ep.toString());
        // write
        SerializeUtil.serializeObject(ep, filepath);
        // read
        ExternalPerson ep_copy = (ExternalPerson) SerializeUtil.deserializeObject(filepath);
        System.out.println("after deserialize: " + ep_copy.toString()); 
    }
}
