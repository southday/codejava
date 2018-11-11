package southday.java.basic.innerclass;

/* [静态内部类]:
 * 1) 非静态内部类在编译完后会隐含着保存一个引用，该引用指向它的外围类，而静态内部类却没有，
 * 这就说明：
 *  a) 静态内部类的创建不依赖与外围类
 *  b) 它不能使用任何外围类非static的成员变量和方法
 * 
 * 2) 在静态内部类中可以存在静态成员
 * 3) 静态内部类对象的创建， OuterClass.StaticInnerClass xx = new OuterClass.StaticInnerClass();
 * 4) 静态内部类可以被：public protected private等修饰
 * 5) 使用静态内部类，多个外围类对象可以共享同一个内部类对象; 而非静态内部类不能了
 * 6) 只有静态内部类才能拥有静态成员
 * 7) 只有内部类才能够声明为static，就是静态内部类
 */

public class StaticInnerClass {
    private String sex = "man";
    public static String name = "LiChaoxi";
    
    /*
     * 静态内部类
     */
    static class staticInnerClass {
        // 在静态内部类中可以存在静态成员
        public static String staticName = "LiChaoxi_staticInnerClass";
        
        public void display() {
            /*
             * 静态内部类只能访问外围类的静态成员和方法，
             * 不能访问外围类的非静态成员和方法
             */
            System.out.println("OuterClass.name = " + name);
        }
    }
    
    /*
     * 非静态内部类
     */
    class InnerClass {
        // 非静态内部类中不能存在静态成员
        public String innerName = "LiChaoxi_InnerClass";
        
        // 非静态内部类可以调用外围类的任何成员，不管是静态还是非静态
        public void display() {
            System.out.println("OuterClass.name = " + name);
            System.out.println("OuterClass.sex = " + sex);
        }
    }
    
    /*
     * 外围类方法
     */
    public void display() {
        // 外围类访问静态内部类
        System.out.println(staticInnerClass.staticName);
        // 静态内部类可以直接创建实例，而不需要依赖外围类
        new staticInnerClass().display(); // 真是醉了，静态的东西也需要创建实例
        
        // 非静态内部类的创建需要依赖外围类
        StaticInnerClass.InnerClass innerc = new StaticInnerClass().new InnerClass();
        // 访问非静态内部类的成员，需要非静态内部类的实例
        System.out.println(innerc.innerName);
        innerc.display();
    }
    
    public static void main(String[] args) {
        StaticInnerClass sic = new StaticInnerClass();
        sic.display();
        
        // 静态内部类对象的创建
        StaticInnerClass.staticInnerClass ssic = new StaticInnerClass.staticInnerClass();
        ssic.display();
    }
}
