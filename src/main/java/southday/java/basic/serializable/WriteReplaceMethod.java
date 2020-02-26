package southday.java.basic.serializable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Cat implements Serializable {
    private static final long serialVersionUID = 1L;

    /* 1) 如果一个序列化类中含有Object writeReplace()方法，
     *    那么实际序列化的对象将是作为writeReplace方法返回值的对象，
     *    而且序列化过程的依据是实际被序列化对象的序列化实现
     *
     * 2) 注意：writeReplace方法中所返回的对象也必须可序列化
     * 3) 用writeReplace和readResolve方法结合，可以用来实现：序列化代理模式（参看：SerializationProxy.java）
     * 4) 使用writeReplace()方法，就不用writeObject()方法，但如果同时实现了二者，则调用顺序是：writeReplace -> writeObject
     */
    private Object writeReplace() {
        System.out.println("writeReplace");
        // 1.在反序列化，强制转换时将报异常
//        return new Dog(); // 测试输出：southday.java.basic.serializable.Dog

        // 2.在反序列化，强制转换时不报异常
        return new BlueCat(); // 测试输出：southday.java.basic.serializable.BlueCat
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        System.out.println("writeObject");
        oos.defaultWriteObject();
    }
}

class BlueCat extends Cat {
    private static final long serialVersionUID = 2L;
}

class Dog implements Serializable {
    private static final long serialVersionUID = 3L;
}

/**
 * {@code writeReplace}方法讲解
 * @author southday
 * @date 2018年5月15日
 */
public class WriteReplaceMethod {
    public static void main(String[] args) {
        Cat c = new Cat();
        SerializeUtil.serializeObject(c, "instance.out");
        Object obj = SerializeUtil.deserializeObject("instance.out");
        System.out.println(obj.getClass().getName());
        try {
            Cat cc = (Cat) obj;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
