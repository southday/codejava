package southday.java.thinkinginjava.c15.s12;

interface OrdinaryGetter {
    Base get();
}

interface DerivedGetter extends OrdinaryGetter {
    // 重写（协变参数类型 - 方法参数类型(原来是Base)随着子类DerivedGetter而改变(变为Derived)）
    Derived get();
}

/**
 * 协变参数类型-方法参数类型会随子类而变化 (重写而不是重载)</br>
 * 对比普通参数类型：{@link OrdinaryArguments}
 * @author southday
 * @date 2018年10月28日
 */
public class CovariantReturnTypes {
    static void test(DerivedGetter d) {
        Derived d2 = d.get();
        // d只有 d.get()-Derived 方法，而没有 d.get()-Base 方法，说明get()方法被重写
    }
}
