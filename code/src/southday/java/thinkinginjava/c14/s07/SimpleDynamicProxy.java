package southday.java.thinkinginjava.c14.s07;

import java.lang.reflect.Proxy;

/**
 * 简单动态代理
 * @author southday
 * @date 2018年10月24日
 */
public class SimpleDynamicProxy {
    public static void consumer(Interface iface) {
        iface.doSomething();
        iface.somethingElse("bonobo");
    }
    
    public static void main(String[] args) {
        RealObject real = new RealObject();
        consumer(real);
        // 使用代理，这里的DynamicProxyHandler相当于SimleProxy
        Interface proxy = (Interface)Proxy.newProxyInstance(
                Interface.class.getClassLoader(),
                new Class[] {Interface.class},
                new DynamicProxyHandler(real));
        consumer(proxy);
    }
}
