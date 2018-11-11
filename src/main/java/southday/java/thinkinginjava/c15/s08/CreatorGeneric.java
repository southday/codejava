package southday.java.thinkinginjava.c15.s08;

abstract class GenericWithCreate<T> {
    final T element;
    GenericWithCreate() {
        // 模版方法，由子类决定创建什么类型的实例
        element = create();
    }
    abstract T create();
}

class Cat {}

class Creator extends GenericWithCreate<Cat> {
    Cat create() {
        return new Cat();
    }
    
    void f() {
        System.out.println(element.getClass().getName());
    }
}

/**
 * 使用模版方法设计模式来创建类型实例
 * @author southday
 * @date 2018年10月26日
 */
public class CreatorGeneric {
    public static void main(String[] args) {
        Creator c = new Creator();
        c.f();
    }
}
