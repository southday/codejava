package southday.java.basic.enumeration;

/**
 * 枚举类型(enum type)是指由一组固定的常量组成合法值的类型<p>
 * 学自：Effective Java - chapter 6
 * @author southday
 * @date 2018年5月7日
 */
public class EnumType {
    
    /* 在没有引入枚举类型之前，表示枚举类型的常用模式是声明一组具名的int常量，每个类型成员一个常量，如下：
     * (当然也有String枚举模式)
     * 
     * 1.int枚举模式存在诸多不足，它在类型安全性和使用方便性方面没有任何帮助：
     * 1) 如果你将apple传到想要orange的方法中，编译器不会出现警告，还会用==操作符将apple与orange进行比对
     * 2) int枚举类型是编译时常量，被编译到使用它们的客户端中，如果与枚举常量关联的int发生了变化，客户端就必须重新编译
     * 3) 将int枚举常量翻译成可打印的字符串，并没有很便利的方法，如果将这种常量打印出来，或从调试器中将它显示出来，并没有太大用处
     */
    public static final int APPLE_FUJI = 0;
    public static final int APPLE_PIPPIN = 1;
    public static final int APPLE_GRANNY_SMITH = 2;
    public static final int ORANGE_NAVEL = 0;
    public static final int ORANGE_TEMPLE = 1;
    public static final int ORANGE_BLOOD = 2;
    
    public static void main(String[] args) {
        for (Apple ap : Apple.values()) {
            System.out.println(ap.toString() + " -- " + ap.ordinal());
        }
        System.out.println("----------------------");
        for (Orange or : Orange.values()) {
            System.out.println(or.toString() + " -- " + or.ordinal());
        }
    }
}

/* 从Java1.5发行版开始，提供了枚举类型
 * 本来公有枚举类型：public enum 应该和类一样定义在自己的文件中，
 * 这里我为了方便说明，直接定义到了 EnumType.java 这个文件中，相对的，取消了 public
 * 
 * 1.Java的枚举本质上是int值
 * 
 * 2.Java枚举类型，是通过公有的静态final域为每个枚举常量导出实例的类
 * 1) 因为没有可以访问的构造器，枚举类型是真正的final
 * 2) 因为客户端既不能创造枚举类型的实例，也不能对它进行扩展，因此枚举类型是实例受控的
 * 
 * 3.枚举提供了编译时的类型安全，如果参数类型为Apple，你传入Orange的对象，编译时就会报错
 * 
 * 4.包含同名常量的多个枚举类型可以在一个系统中和平共处，因为每个类型都有自己的命名空间
 * 
 * 5.你可以增加或者重新排列枚举类型中的常量，而无需重新编译它的客户端代码，
 *   因为导出常量的域在枚举类型和它的客户端之间提供了一个隔离层：常量值并没有被编译到客户端代码中
 * 
 * 6.可以通过调用toString方法，将枚举转换成可打印的字符串
 * 
 * 7.除此之外，枚举类型还允许添加任意的方法和域，并实现任意的接口：  它们提供了所有Object方法的高级实现，
 *   实现了Comparable和Serializable接口，并针对枚举类型的可任意改变性设计了序列化方式
 * -> 第7点，请参看 Planet.java 文件
 */ 
enum Apple {
    FUJI, PIPPIN, GRANNY_SMITH
}

enum Orange {
    NAVEL, TEMPLE, BLOOD
}