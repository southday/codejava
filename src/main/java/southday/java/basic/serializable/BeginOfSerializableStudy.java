package southday.java.basic.serializable;

import java.io.Serializable;

/* 本例是我了解 “序列化与反序列化” 的第一个例子。在这里，我会根据我的印象，实现一个简单的
 * 序列化demo。此外，我还会写出一些关于序列化的基本知识，算是开个头...
 * 
 * 1. 什么是 序列化？
 * []:序列化并非时Java领域的专有名词，而是计算机界一个常见的概念。
 * 序列化就有以下特征：
 *     1) 序列化之前的对象通常是瞬时的，动态的，可变的;
 *        序列化之后的结果通常时持久的，静态的，不可变的;
 *     2) 序列化的目的通常是使得对象得以保持，或者传输
 *     3) 序列化过程通常有反序列化过程与之对应
 * 
 * 2. 什么情况下需要 序列化？
 * []: 1) 当你想把内存中的对象保存到文本中或者数据库中的时候
 *     2) 当你想用套接字在网络上传输对象时
 *     3) 当你想通过RMI传输对象时 -- (RMI: Remote Method Invocation 远程方法调用)
 *     [RMI是一种机制，能够让某个Java虚拟机上的对象调用另一个Java虚拟机上的对象]
 * 
 * 3. 如何实现序列化？
 * []: 让 YourClass implements java.io.Serializable 即可，这里需要一个serialVersionUID，
 * 详细内容到另一个.java中再讲 
 * 
 * @author southday
 * Time - 2016.3.31
 */

class SerialPerson implements Serializable {
    private static final long serialVersionUID = 111L;
    public String name;
    public int age;
    
    public SerialPerson() {}
    
    public SerialPerson(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

public class BeginOfSerializableStudy {
    public static void main(String[] args) {
        String filepath = "/home/coco/Desktop/Java/SerialPerson.txt";
        SerialPerson sp = new SerialPerson("LiChaoxi", 21);
        System.out.println("Name: " + sp.name + " | Age: " + sp.age);
        SerializeUtil.serializeObject(sp, filepath);
        
        SerialPerson desp = (SerialPerson) SerializeUtil.deserializeObject(filepath);
        System.out.println("Name: " + desp.name + " | Age: " + desp.age);
    }
}
