package southday.java.thinkinginjava.c14.s08;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * 使用动态代理来为每一种Robot类型都创建一个空对象
 * @author southday
 * @date 2018年10月25日
 */
public class NullRobotProxyHandler implements InvocationHandler {
    private String nullName;
    private Robot proxied = new NRobot();

    public NullRobotProxyHandler(Class<? extends Robot> type) {
        nullName = type.getSimpleName() + " NullRobot";
    }
    
    private class NRobot implements Robot {
        public String name() { return nullName; }
        public String model() { return nullName; }
        public List<Operation> operations() {
            return Collections.emptyList();
        }
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 使用 NRobot来代理 构造器中传入的 type
        return method.invoke(proxied, args);
    }
}
