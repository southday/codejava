package southday.java.thinkinginjava.c14.s07;

/**
 * 简单代理模式测试
 * @author southday
 * @date 2018年10月24日
 */
public class SimpleProxyDemo {
    
    public static void consumer(Interface iface) {
        iface.doSomething();
        iface.somethingElse("bonobo");
    }
    
    public static void main(String[] args) {
        consumer(new RealObject());
        consumer(new SimpleProxy(new RealObject()));
    }
    
}
