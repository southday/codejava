package southday.java.basic.innerclass;

/* [成员内部类]:
 * 1) 成员内部类是最常见的内部类，它是外围类的一个成员，所以它可以无限制地访问外围类
 * 的所有属性和方法（即使是private）修饰的
 * 2) 外围类要访问内部类的属性和方法时，则需要通过内部类实例（对象）来访问
 * 3) 成员内部类中不能存在任何static 的变量和方法
 * 4) 成员内部类类是依赖于外围类的，所以只有先创建了外围类对象才能创建内部类对象
 * 5) 成员内部类可以被abstract修饰
 * 
 * 6) 为什么成员内部类中不能有静态成员和方法？
 * [网上的解释]:
 * 非static的内部类，在外部类加载的时候，并不会加载它，所以它里面不能有静态变量或者静态方法。
    1、static类型的属性和方法，在类加载的时候就会存在于内存中。
    2、要使用某个类的static属性或者方法，那么这个类必须要加载到jvm中。
    基于以上两点，可以看出，如果一个非static的内部类如果具有static的属性或者方法，
    那么就会出现一种情况：内部类未加载，但是却试图在内存中创建static的属性和方法，
    这当然是错误的。原因：类还不存在，但却希望操作它的属性和方法。
 */

class OuterClass1 {
    private String str;
    
    public void outerDisplay() {
        System.out.println("OuterClass ...");
    }
    
    public class InnerClass1 {
        private String innerStr = "inner string";
        public void innerDisplay() {
            // 使用外围类属性
            str = "innerClass ...";
            System.out.println(str);
            System.out.println("内部类使用外围类的方法 outerDisplay()");
            outerDisplay();
        }
    }
    
    // 推荐使用getXXX()方法来获得内部类，特别是该内部类的构造函数无需参数的时候
    public InnerClass1 getInnerClass1() {
        return new InnerClass1();
    }
    
    public void printInnerString() {
        System.out.println("我是外围类，我通过创建内部类对象来访问内部类中的属性，并将其输出...");
        System.out.println(getInnerClass1().innerStr);
    }
    
}

public class MemberInnerClass {
    public static void main(String[] args) {
        OuterClass1 outer = new OuterClass1();
        OuterClass1.InnerClass1 inner = outer.getInnerClass1();
        inner.innerDisplay();
        System.out.println("-----------------");
        outer.printInnerString();
    }
}
