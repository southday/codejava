package southday.java.basic.innerclass;

/* [局部内部类]:
 * 1) 局部内部类嵌套在方法中，并且只作用于该方法，出了该方法就会失效
 * 2) 使用局部内部类，一般是用来解决比较复杂的问题，想创建一个类来辅助我们的解决方案，
 * 而又不希望这个类时公共可见的
 * 3) 局部内部类就像是一个局部变量一样，是不能有public private protected 以及static修饰的
 * 
 * 4) 局部内部类中可以访问  局部常量(final修饰), 不能访问局部变量
 * []:这和JVM以及编译器有关，因为内部类的实例和局部变量的生命周期不同，内部类的实例可能在局部变量被
 * 销毁后依旧存在，为了防止此情况发生，局部内部类对象在创建时会拷贝一份局部变量，由于局部变量是在局部
 * 内部类对象创建时拷贝的，为了保证局部变量和拷贝数据的一致性，就需要用final来修饰，以保证何时都不变
 * 
 * []:更深入一点，其实JVM会对方法中的局部变量进行拷贝，并将其作为局部内部类的成员变量，所以事实上局部
 * 内部类变不是访问了方法中的变量，而是在访问自己的成员变量，只是这个成员变量与方法中相应的变量值是一致的。
 * 如下：
 *  外观上：    public void method() {
 *             int var = 0;
 *             class LocalInner {}
 *        }
 *  而实际上：public void method() {
 *            int var = 0; // final int var = 0;
 *            class LocalInner {
 *                int var = 0; // final int var = 0;
 *            }
 *         }
 *  上面也说了，局部内部类的生命周期和局部变量的不一样，所以为了避免当变量var消亡时，局部内部类对其访问造成错误，
 *  JVM只能维持变量var的生命周期，这时就会使用final修饰符来对变量var进行修饰，从而维持它的生命周期，让其可以
 *  让局部内部类继续调用，直到结束。
 * 
 * 5) 局部内部类可以访问外围类中的属性和方法，因为它与一般内部类一样，持有了外围类对象的引用OuterClass.this
 * 6) 局部内部类可以被abstract修饰
 */

interface Destionation {
    public abstract String whereTo();
}

public class LocalInnerClass {
    final int localconst = 1024;
    int localvar = 10;
    
    public Destionation destionation(String str) {
        final int spendDays = 30; // 总共花30天
        int overMonth = 5; // 在今年5月结束
        
        class MyDestionation implements Destionation {
            private String destionation;
            public MyDestionation(String destionation) {
                this.destionation = destionation;
            }
            
            @Override
            public String whereTo() {
                return destionation;
            }
            
            public void access() {
                System.out.println("我是局部内部类，我可以通过OuterClass.this来访问外围类的属性和方法");
                System.out.println("我可以访问 外围类中的常量localconst, localconst = " + LocalInnerClass.this.localconst);
                System.out.println("我可以访问 外围类中的变量localvar, localvar = " + LocalInnerClass.this.localvar);
                System.out.println("----------------------------------");
                
                System.out.println("我可以访问 本方法中的局部常量spendDays, spendDays = " + spendDays);
                System.out.println("我可以访问 本方法中的局部变量overMonth, overMonth = " + overMonth);
                System.out.println("然而，说是局部变量overMonth, 但我不能改变它的值，也就是说， 它现在是一个常量");
                System.out.println("当我在局部内部类中引用overMonth后，若本方法（局部内部类之外）中再对overMonth进行修改，这里的引用就会报错");
            }
        } // local inner class
        
        MyDestionation mds = new MyDestionation(str);
        mds.access();
//        overMonth = 6;
//        System.out.println("而在本方法中（局部内部类之外），overMonth 的值可以被改变，现在 overMonth = " + overMonth);
        /*
         * 当我去掉上面两行代码的注释时，access()方法中的overMonth就会报错，
         * 可能是由于，刚开始方法中定义的overMonth是一个局部变量，
         * 而我在局部内部类中引用了它，这时候它被变成了局部常量，可以在局部内部类中被引用，
         * 而当我在方法中（局部内部类之外）想要修改overMonth的值时，与之前overMonth被局部内部类引用冲突了，
         * 因为局部内部类只能引用局部常量，这样一来access()方法中的overMonth就会报错。 
         */
        return mds;
    }
    
    public static void main(String[] args) {
        LocalInnerClass localic = new LocalInnerClass();
        Destionation myd = localic.destionation("Mars");
        String destionation = myd.whereTo();
        System.out.println("I will go to " + destionation);
    }
}
