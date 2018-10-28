package southday.java.thinkinginjava.c15.s12;

class OrdinarySetter {
    void set(Base base) {
        System.out.println("ordinarySetter.set(base)");
    }
}

class DerivedSetter extends OrdinarySetter {
    // 重载 set(Base base){} 依然存在
    void set(Derived d) {
        System.out.println("derivedSetter.set(derived)");
    }
}

/**
 * 普通参数类型 - 重载而不是重写</br>
 * 对比协变参数类型：{@link CovariantReturnTypes}，
 * {@link SelfBoundingAndCovariantArguments}
 * @author southday
 * @date 2018年10月28日
 */
public class OrdinaryArguments {
    public static void main(String[] args) {
        Base base = new Base();
        Derived d = new Derived();
        DerivedSetter ds = new DerivedSetter();
        ds.set(base);
        ds.set(d);
    }
}

