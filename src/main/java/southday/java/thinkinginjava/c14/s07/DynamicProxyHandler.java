package southday.java.thinkinginjava.c14.s07;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理处理者
 * @author southday
 * @date 2018年10月24日
 */
public class DynamicProxyHandler implements InvocationHandler {
    private Object proxied;
    
    public DynamicProxyHandler(Object proied) {
        this.proxied = proied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 当你想添加一些额外的操作，又不想改变原来的类方法时，就可以使用代理
        /************* 额外的操作 *****************/
        System.out.println("*** proxy: " + proxy.getClass() + 
                ", method: " + method + ", args: " + args);
        if (args != null)
            for (Object arg : args)
                System.out.println("  " + arg); 
        /************* 额外的操作 *****************/
        return method.invoke(proxied, args);
    }

}
