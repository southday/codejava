package southday.java.basic.innerclass;

/* 本例主要讲解：
 * 1) 如何定义内部类？
 * 2) 如何创建内部类对象？
 * 3) 内部类对外围类属性的访问
 */

class OuterClass0 {
    private String name;
    private int age;

    public OuterClass0(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public class InnerClass0 {
        public InnerClass0() {
            // 即使 name 在外围类中是private修饰，但在内部类中可以无缝地访问
            name = "LiChaoxi";
        }
        
        public void display() {
            System.out.println("name = " + getName());
        }
        
        public OuterClass0 getOuterClass0Object() {
            // 内部类通过 外围类类名.this 来获得外围类对象的引用
            return OuterClass0.this;
        }
    }
    
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
}

public class InnerClassDemo0 {
    public static void main(String[] args) {
        /* 内部类对象的创建需要以  外部类对象.new 的方式创建
         * 并且在声明时的格式是： 外围类类名.内部类类名
         */
        OuterClass0.InnerClass0 inner = new OuterClass0("Java", 10).new InnerClass0();
        inner.display();
        
        /* 在内部类中，要引用外围类对象，可以使用OuterClass0.this
         */
        int age = inner.getOuterClass0Object().getAge();
        System.out.println("我通过内部类返回了外围类对象，然后获取了相关属性值");
        System.out.println("外围类对象的属性 age = " + age);
    }
}
