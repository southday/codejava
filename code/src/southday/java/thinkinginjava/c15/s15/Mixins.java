package southday.java.thinkinginjava.c15.s15;

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

/* 使用代理模式
把getSerialNumber()和 getStamp() 方法转交给内置的代理对象；
这种模式有个不足就是，被代理者(Mixin)要实现较多的接口，以提供外部对这些方法的一致访问
 */
class Mixin extends BasicImpl implements TimeStamped, SerialNumbered {
    private TimeStamped timeStamp = new TimeStampedImpl();
    private SerialNumbered serialNumber = new SerialNumberedImpl();
    
    @Override
    public long getSerialNumber() {
        return serialNumber.getSerialNumber();
    }

    @Override
    public long getStamp() {
        return timeStamp.getStamp();
    }
    
}

/**
 * 使用普通代理模式来模拟混型
 * @author southday
 * @date 2018年10月28日
 */
public class Mixins {
    public static void main(String[] args) {
        Mixin m = new Mixin();
        m.set("hahaha");
        System.out.println(m.get());
        System.out.println(m.getSerialNumber());
        System.out.println(m.getStamp()); 
    }
}
