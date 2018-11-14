package southday.java.thinkinginjava.c15.s12.s03;

interface SelfBoundSetter<T extends SelfBoundSetter<T>> {
    void set(T arg);
}

interface Setter extends SelfBoundSetter<Setter> {
    // 重写了 set(T arg)，使用导出类型 Setter 作为参数类型：set(Setter arg)
}

/**
 * 自限定泛型协变参数类型-导出类型为参数 (重写而不是重载)</br>
 * 对比：{@link CovariantReturnTypes}，
 * {@link OrdinaryArguments}
 * @author southday
 * @date 2018年10月28日
 */
public class SelfBoundingAndCovariantArguments {
    static void test(Setter s1, Setter s2, @SuppressWarnings("rawtypes") SelfBoundSetter sbs) {
        s1.set(s2); // 可行，因为Setter重写了 Set(T arg)
        /* s1.set(sbs); 不可行
         * 因为 Setter中的set()方法已被重写为 set(Setter arg)，而 sbs 是 SelfBoundSetter类型
         */
    }
}
