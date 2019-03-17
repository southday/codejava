package southday.java.jvm.classload;

/**
 * @author southday
 * @date 2019/3/17
 */
public class SubClass extends SuperClass {

    static {
        System.out.println("SubClass init!");
    }
}
