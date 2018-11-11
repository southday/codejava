package southday.java.basic.serializable;

import java.io.Serializable;

/* 单例模式，在不参与序列化机制下，该类是可行的（暂不考虑反射）
 * 然而当该类实现了Serializable接口后，若不经过其他处理，则在反序列时会产生一个新的实例，破坏了单例机制
 */
final class MySingletonNoReadResolve implements Serializable {
    private static final long serialVersionUID = 2L;
    private MySingletonNoReadResolve() {}
    private static final MySingletonNoReadResolve INSTANCE = new MySingletonNoReadResolve();
    
    public static MySingletonNoReadResolve getInstance() {
        return INSTANCE;
    }
}

/* 使用readResolve方法来确保单例
 * 序列化操作提供了一个特别的钩子（hook）：在类中包含一个私有的被实例化的方法readResolve()，
 * 这个方法可以确保类的开发人员在序列化将会返回怎样的Object上具有发言权，
 * readResolve在序列化创建实例的时候被引用
 */
final class MySingletonWithReadResolve implements Serializable {
    private static final long serialVersionUID = 1L;
    private MySingletonWithReadResolve() {}
    private static final MySingletonWithReadResolve INSTANCE = new MySingletonWithReadResolve();
    
    public static MySingletonWithReadResolve getInstance() {
        return INSTANCE;
    }
    
    /* 1) readResolve的最主要应用场合就是单例、枚举类型的保护性恢复
     * 2) Java5之后的版本都实现了enum类型的自动保护性恢复
     * 3) 如果依赖readResolve进行实例控制，则带有对象引用类型的所有实例域都必须声明为transient，否则可能遭到攻击（Effective Java 2 第77条）
     */
    private Object readResolve() {
        return INSTANCE;
    }
}

/**
 * {@code readResolve}方法讲解
 * @author southday
 * @date 2018年5月15日
 */
public class ReadResolveMethod {
    
    public static void main(String[] args) {
        testNoReadResolve(); // false
        testWithReadResolve(); // true
    }
    
    // 测试1：MySingletonNoReadResolve
    public static void testNoReadResolve() {
        MySingletonNoReadResolve instance = MySingletonNoReadResolve.getInstance();
        SerializeUtil.serializeObject(instance, "instance.out");
        MySingletonNoReadResolve obj = (MySingletonNoReadResolve) SerializeUtil.deserializeObject("instance.out");
        System.out.println("instance == obj : " + (instance == obj));
    }
    
    // 测试2：MySingletonWithReadResolve
    public static void testWithReadResolve() {
        MySingletonWithReadResolve instance = MySingletonWithReadResolve.getInstance();
        SerializeUtil.serializeObject(instance, "instance.out");
        MySingletonWithReadResolve obj = (MySingletonWithReadResolve) SerializeUtil.deserializeObject("instance.out");
        System.out.println("instance == obj : " + (instance == obj));
    }
}