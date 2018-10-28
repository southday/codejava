package southday.java.thinkinginjava.c15.s15;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/* 混型
在C++中使用多重继承的最大理由，就是为了使用混型，所以 混型 在一定程度上等价于 多重继承的效果
在C++中，可以实现如下：
int main() {
    // C++ 中的泛型 参数化类型 - 混型实现
    TimeStampedImpl<SerialNumberedImpl<BasicImpl>> mixin;
    mixin.set("hahah"); // 来自 BasicImpl的方法
    mixin.getSerialNumber(); // 来自 SerialNumberedImpl的方法
    mixin.getStamp(); // 来自 TimeStampedImpl的方法
}

然而Java不提供如此机制，所以只能通过其他方法来尽量的实现这种功能：
第1种方法，使用类似代理的方法来实现，如：Mixins.java
第2种方法，使用装饰器模式来实现，未展示
第3中方法，与动态代理结合，该方法是Java中实现混型最贴近的方法，如：DynamicProxyMixin.java
*/

class TwoTuple<A, B> {
    public A first;
    public B second;
    public TwoTuple(A a, B b) {
        first = a;
        second = b;
    }
}

class MixinProxy implements InvocationHandler {
    Map<String, Object> delegatesByMethod;
    
    public MixinProxy(@SuppressWarnings("unchecked") TwoTuple<Object, Class<?>>... pairs) {
        delegatesByMethod = new HashMap<String, Object>();
        // 将混型的实际对象和方法注册到 delegatesByMethod中
        for (TwoTuple<Object, Class<?>> pair : pairs) {
            for (Method method : pair.second.getMethods()) {
                String methodName = method.getName();
                if (!delegatesByMethod.containsKey(methodName))
                    delegatesByMethod.put(methodName, pair.first);
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Object delegate = delegatesByMethod.get(methodName);
        // 取出实际参与的类对象
        return method.invoke(delegate, args);
    }
    
    public static Object newInstance(@SuppressWarnings("unchecked") TwoTuple<Object, Class<?>>... pairs) {
        Class<?>[] interfaces = new Class[pairs.length];
        for (int i = 0; i < pairs.length; i++)
            interfaces[i] = (Class<?>)pairs[i].second;
        ClassLoader cl = pairs[0].first.getClass().getClassLoader();
        return Proxy.newProxyInstance(cl, interfaces, new MixinProxy(pairs));
    }
}


/**
 * 结合动态代理来实现混型
 * @author southday
 * @date 2018年10月28日
 */
public class DynamicProxyMixin {
    public static void main(String[] args) {
        @SuppressWarnings("unchecked")
        Object mixin = MixinProxy.newInstance(
                tuple(new BasicImpl(), Basic.class),
                tuple(new TimeStampedImpl(), TimeStamped.class),
                tuple(new SerialNumberedImpl(), SerialNumbered.class));
        // 有个不足就是在使用时必须强制转型
        Basic b = (Basic)mixin;
        TimeStamped t = (TimeStamped)mixin;
        SerialNumbered s = (SerialNumbered)mixin;
        b.set("hahaha");
        System.out.println(b.get());
        System.out.println(s.getSerialNumber());
        System.out.println(t.getStamp());
    }
    
    public static <A, B> TwoTuple<A, B> tuple(A a, B b) {
        return new TwoTuple<A, B>(a, b);
    }
}
