package southday.java.jvm.classload;

/**
 * @author southday
 * @date 2019/3/17
 */
public class ConstClass {
    static {
        System.out.println("ConstClass init");
    }
    public static final String HELLOWORLD = "Hello world!";
}
