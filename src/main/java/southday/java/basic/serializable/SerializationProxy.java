package southday.java.basic.serializable;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

/*
 * Effective Java 2 第76条
 * 不变性条件：start <= end
 */
final class Period implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date start;
    private Date end;
    
    public Period(Date start, Date end) {
        /* 做保护性拷贝，防止外部引用修改Period的start和end对象内容
         * 先拷贝再检验（不变性条件）
         */
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
        if (this.start.compareTo(this.end) > 0) {
            throw new IllegalArgumentException(start + " after " + end);
        }
    }
    
    /* 序列化代理模式：
     * 为可序列化的类设计一个私有的静态嵌套类，精确地表示外围类实例的逻辑状态
     * 通过序列化代理，序列化系统永远不会产生外围类的序列化实例
     */
    private static class SProxy implements Serializable {
        private static final long serialVersionUID = 2L;
        private final Date start;
        private final Date end;
        
        SProxy(Period p) {
            this.start = p.start;
            this.end = p.end;
        }
        
        // 反序列化时返回 Period对象实例
        private Object readResolve() {
            // 通过构造函数创建实例，函数内部以包含保护性拷贝、约束条件检验
            return new Period(start, end);
        }
    }
    
    // 返回 代理实例，即实际序列化的是代理实例，而不是Period类对象
    private Object writeReplace() {
        return new SProxy(this);
    }
    
    // 在使用了 proxy后，以以下方法实现readObject，避免攻击者伪造字节流，创造出违反该类约束条件的实例
    private void readObject(ObjectInputStream s) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }
    
    /* 使用readObject是为了防止攻击者通过修改序列化文件（字节流）发起的攻击
     * 在未使用：proxy时，我们需要如下实现readObject来组织序列化攻击破坏Period的不变性条件
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        // 对于final域，保护性拷贝是不可能的，所以要使用readObject就必须将start和end前的final修饰去掉
        start = new Date(start.getTime());
        end = new Date(end.getTime());
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + " after " + end);
        }
    }
     */
    
    public Date start() {
        return new Date(start.getTime());
    }
    
    public Date end() {
        return new Date(end.getTime());
    }
    
    public String toString() {
        return start + " - " + end;
    }
}

/**
 * 序列化代理模式（学自：Effective Java 2 第78条）
 * @author southday
 * @date 2018年5月15日
 */
public class SerializationProxy {
    public static void main(String[] args) {
        Period p = new Period(new Date(1), new Date(2));
        // p: Thu Jan 01 08:00:00 CST 1970 - Thu Jan 01 08:00:00 CST 1970
        System.out.println("p: " + p.toString());
        SerializeUtil.serializeObject(p, "instance.out");
        Period obj = (Period) SerializeUtil.deserializeObject("instance.out");
        // obj: Thu Jan 01 08:00:00 CST 1970 - Thu Jan 01 08:00:00 CST 1970
        System.out.println("obj: " + obj.toString());
    }
}
