package southday.java.basic.innerclass;

/* [匿名内部类]:
 * 1) 匿名内部类没有访问修饰符
 * 2) new 匿名内部类，该类首先是要存在的
 * 3) 当所在方法的形惨需要被匿名内部类使用时，那么这个形参就必须被final 修饰 
 * 4) 匿名内部类没有构造方法，想想，它连名字的都没有
 *
 * 5) 匿名内部类的创建方式：
 *     new 父类构造器(参数列表)| 实现接口() {
 *         匿名内部类的类体部分
 *     }
 * 
 * 6) 创建匿名内部类时，它会立即创建一个该类的实例，该类的定义就会立即消失，
 * 所以匿名内部类时不能够被重复使用的
 * 
 * 7) 使用匿名内部类时，我们必须时继承一个类或实现一个接口，但是两者不可兼得，
 * 同时也只能继承一个类或者实现一个接口
 * 
 * 8) 匿名内部类中不能存在任何静态成员和方法
 * 9) 匿名内部类属于局部内部类，所以局部内部类的所有限制对匿名内部类同样生效
 * 10) 匿名内部类可以被abstract修饰
 * 
 * 11) 在给匿名内部类传递参数时，若该形参在匿名内部类中需要被使用时，该形参必须要为final修饰
 * 如下面的: public Bird getAbird(int age, final String name) {...}
 */

abstract class Bird {
    private String name;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public abstract void fly(int age);
}

public class AnonymityInnerClass {
    public static void main(String[] args) {
        AnonymityInnerClass aic = new AnonymityInnerClass();
        aic.test(10, new Bird() {
            @Override
            public void fly(int age) {
                System.out.println(age + "岁的" + getName() + " can fly " + 100 + "m");
            }
            
            @Override
            public String getName() {
                return "大雁";
            }
        });
    }
    
    public void test(int age, Bird bird) {
        bird.fly(age);
    }
    
    public Bird getAbird(int age, String name) {
        /* 形参name被匿名内部类使用，所以其应被final修饰，
         * 虽然这里的形参没有被 final 修饰，即不是 final String name
         * 但其实早已隐藏地转换了，即在匿名内部类中的name是个字符串常量
         * 所以，如果方法中再对name进行值的修改，就会出现问题
         * 
         * 我觉得还是应该养成良好的编程习惯，getAbird()方法定义如下:
         * public Bird getAbird(int age, final String name) {...}
         */
        Bird bird = new Bird() {
            @Override
            public void fly(int age) {
                // 这里的name 其实时 final String name
                System.out.println(age + "岁的" + name + " can fly " + 100 + "m");
            }
        };
        age = 10; // 由于没有被匿名内部类使用，所以可以在方法中更改其值
//        name = "小燕子"; // 当这里取消注释时，fly()中的name会报错，原因请看-->LocalInnerClass.java
        return bird;
    }
}
