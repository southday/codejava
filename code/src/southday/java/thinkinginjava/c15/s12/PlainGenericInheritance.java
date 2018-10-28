package southday.java.thinkinginjava.c15.s12;

class GenericSetter<T> {
    void set(T arg) {
        System.out.println("GenericSetter.set(Base)");
    }
}

class DerivedGS extends GenericSetter<Base> {
    // 重载而不是重写
    void set(Derived d) {
        System.out.println("DerivedGS.set(derived)");
    }
}

/*
如果 DerivedGS 写为：
class DerivedGS<T> extends GenericSetter<Base> {
    void set(T arg) {
        System.out.println("DerivedGS.set(derived)");
    }
}

报错：因为 DerivedGS<T> 和 GenericSetter<T> 中都有 set(T arg) 方法，方法重定义了
泛型采用擦除的方式，所以两者中的set都是 set(Object arg){}，方法重定义
*/

/**
 * 普通泛型 (重载而不是重写)
 * @author southday
 * @date 2018年10月28日
 */
public class PlainGenericInheritance {
    public static void main(String[] args) {
        Base base = new Base();
        Derived d = new Derived();
        DerivedGS dgs = new DerivedGS();
        dgs.set(d);
        dgs.set(base); // 重载而不是重写
    }
}
