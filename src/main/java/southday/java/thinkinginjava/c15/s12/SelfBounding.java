package southday.java.thinkinginjava.c15.s12;

/* SelfBounded<T extends SelfBounded<T>>
 * 1) 强制泛型当作自己的边界参数来使用；
 * 2) 它可以保证类型参数必须与正在被定义的类相同；
 * 3) 自限定限制只能强制作用于继承关系
 * 如 SelfBouned<W>
 * 那么这个W类必须有定义：W extends SelfBounded<W>
 */
class SelfBounded<T extends SelfBounded<T>> {
    T element;
    
    SelfBounded<T> set(T arg) {
        element = arg;
        return this;
    }
    
    T get() {
        return element;
    }
}

class A extends SelfBounded<A> {}
// 可行，因为 A extends SelfBounded<A>
class B extends SelfBounded<A> {}

class C extends SelfBounded<C> {
    C setAndGet(C arg) {
        set(arg);
        return element;
    }
}

class D {}
/* class E extends SelfBounded<D> 不可行
 * 因为D类不满足要求：D extends SelfBounded<D>
 */

// 可行，因为自限定惯用语法不是可强制执行的
@SuppressWarnings("rawtypes")
class F extends SelfBounded {}

/**
 * 自限定类型
 * @author southday
 * @date 2018年10月28日
 */
public class SelfBounding {
    public static void main(String[] args) {
        A a = new A();
        a.set(new A());
        a = a.set(new A()).get();
        a = a.get();
        C c = new C();
        c = c.setAndGet(new C());
    }
    
    // 自限定也可用于泛型方法
    static <T extends SelfBounded<T>> T f(T arg) {
        return arg.set(arg).get();
    }
}
