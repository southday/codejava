package southday.java.basic.innerclass;

/* 《Think in java》：
 * 1) 使用内部类最吸引人的原因：每个内部类都能独立的继承一个（接口）实现，
 * 所以无论外围类是否已经继承了某个（接口的）实现，对于内部类无影响。
 * 
 * 2) 使用内部类最大的优点在于它能够非常好的解决多重继承问题。
 *  a) 内部类可以有多个实例，每个实例都有自己的状态信息，并且与其他外围对象的信息相互独立
 *  b) 在单个的外围类中，可以让多个内部类以不同的方式实现同一接口，或者继承同一个类
 *  c) 创建内部类对象的时刻，并不依赖于外围对象的创建 （即：不是创建了外围对象就创建了内部类对象，二者是独立的）
 *  d) 内部类并没有令人疑惑的"is-a"关系，他就是一个独立的实体
 *  e) 内部类提供了更好的封装，除了该外围类，其他类都不能访问
 * 
 * 3) 内部类是编译时的概念，一旦编译成功后，它就与外围类属于两个不同的类（当然他们之间有一定联系）
 *     一个名为OuterClass的外围类中有一个明文InnerClass的内部类，
 *  在编译成功后，会出现两个class文件：OuterClass.class 和 OuterClass$InnerClass.class
 * 
 * 4) java中的内部类主要分为4种：
 *  a) 成员内部类
 *  b) 局部内部类
 *  c) 匿名内部类
 *  d) 静态内部类
 */
public class InnerClass {
    public static void main(String[] args) {
        System.out.println("studing inner class ...");
    }
}
