package southday.java.basic.concurrent.jmc.c06;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/* 本例主要描述：使用 序列化 和 反序列 化后导致对象实例有多例，违反了单例设计
 * 
 * 我们发现，当 实例 经过序列化后， 再经过反序列化取出时，其hashCode()变了，
 * 也就是说 其实 实例已经变了，这个问题是单例模式中需要注意的。
 * 
 * 至于 为什么实例在反序列化后会改变，这个问题我目前还说不清楚，
 * 经查阅资料，说是序列化、反序列化中用了一种特殊的“钩子”，即使类的构造器是private的，
 * 也可以创建该类对象。
 * 
 * 对于什么是序列化，反序列化，这个我将会在下一个阶段学习，等我把《java多线程编程核心技术》看完再系统研究。
 * 这里贴几个网址：
 *     http://blog.csdn.net/fg2006/article/details/6409423 序列化-理解readResolve()
 *  http://www.cnblogs.com/xdp-gacl/p/3777987.html    孤傲苍狼 java对象的序列化和反序列化
 *     http://www.cnblogs.com/rollenholt/archive/2012/11/26/2789445.html rollenholt java序列化与反序列化总结
 *     
 * 对于这个问题，其实也是可以解决的，这里就需要用到 readResolve()方法，
 * 只需要在 要实现单例的类中 实现自己的readResolve()方法，对于单例模式，一般是返回已经创建好的实例对象
 * 
 * private Object readResolve() {} 
 * 这个方法可以确保类的开发人员在序列化将会返回怎样的Object上具有发言权...
 * 该方法在 序列化 创建实例时会被引用...
 */

class SignletonObject implements Serializable {
    private static final long serialVersionUID = 777L;
    
    private SignletonObject() {}
    
    public static class SignletonObjectHandler {
        private static final SignletonObject so = new SignletonObject();
    }
    
    public static SignletonObject getInstance() {
        return SignletonObjectHandler.so;
    }
    
    // 加上这个方法后，经过反序列化得到的实例和序列化前的就一致了，这个方法会被自动调用！！
    private Object readResolve() {
        return SignletonObjectHandler.so;
    }
}

public class SerializedSignleton {
    public static void main(String[] args) {
        String filepath = "/home/coco/Desktop/Java/SignletonObject.txt";
        SignletonObject so = SignletonObject.getInstance();
        System.out.println(so.hashCode());
        serialObject(so, filepath);
        SignletonObject sso = noserialObject(filepath);
        System.out.println(sso.hashCode());
    }
    
    public static void serialObject(SignletonObject so, String filepath) {
        try {
            FileOutputStream fos = new FileOutputStream(filepath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(so);
            fos.close();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static SignletonObject noserialObject(String filepath) {
        SignletonObject so = null;
        try {
            FileInputStream fis = new FileInputStream(filepath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            so = (SignletonObject) ois.readObject();
            fis.close();
            ois.close();
        } catch (ClassNotFoundException fe) {
            fe.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return so;
    }
}
