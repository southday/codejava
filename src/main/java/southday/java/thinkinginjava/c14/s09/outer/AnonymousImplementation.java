package southday.java.thinkinginjava.c14.s09.outer;

import java.lang.reflect.Method;

import southday.java.thinkinginjava.c14.s09.inner.A;
import southday.java.thinkinginjava.c14.s09.inner.AnonymousA;

/**
 * 在包外，通过反射机制调用类的成员，即使时匿名内部类中的包级私有、protected、private权限的成员
 * @author southday
 * @date 2018年10月25日
 */
public class AnonymousImplementation {
    public static void main(String[] args) {
        A a = AnonymousA.makeA();
        a.f();
        System.out.println(a.getClass().getName());
        callHiddenMethod(a, "g"); // public 
        callHiddenMethod(a, "u"); // package
        callHiddenMethod(a, "v"); // protected
        callHiddenMethod(a, "w"); // private
        callHiddenMethod(a, "z"); // not exists
    }
    
    public static void callHiddenMethod(Object obj, String methodName) {
        Class<?> clz = obj.getClass();
        Method method = null;
        try {
            method = clz.getDeclaredMethod(methodName);
        } catch (Exception e) {
            System.out.println("No such method [" + methodName + "] in class [" + clz.getName() + "]");
            return;
        }
        method.setAccessible(true);
        try {
            method.invoke(obj);
        } catch (Exception e) {
            System.out.println("call error: " + e.getMessage());
        }
    }
}

