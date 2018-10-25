package southday.java.thinkinginjava.c14.s09.inner;

/**
 * 使用匿名内部类的方式来构建A的对象
 * @author southday
 * @date 2018年10月25日
 */
public class AnonymousA {

    public static A makeA() {
        return new A() {
            public void f() {
                System.out.println("public C.f()");
            }
            public void g() {
                System.out.println("public C.g()");
            }
            // 包权限
            void u() {
                System.out.println("package C.u()");
            }
            protected void v() {
                System.out.println("protected C.v()");
            }
            private void w() {
                System.out.println("private C.w()");
            }
        };
    }
}
