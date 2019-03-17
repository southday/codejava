package southday.java.jvm.classload;

/**
 * @author southday
 * @date 2019/3/16
 */
public class SuperClass {

    static {
        System.out.println("SuperClass init");
    }

    public static int value = 123;
}
