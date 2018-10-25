package southday.java.thinkinginjava.c14.s09;

/**
 * 通过反射机制修改类的私有域，包括带有 fianl 修饰的域（final修饰的字段不会被更改）
 * @author southday
 * @date 2018年10月25日
 */
public class WithPrivateFinalField {
    private int i = 1;
    private final String s = "I'm totally save";
    private String s2 = "Am I safe?";
    
    public String toString() {
        return "i = " + i + ", " + s + ", " + s2;
    }
}
