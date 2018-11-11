package southday.java.thinkinginjava.c14.s06;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * 通过反射获取类的方法（命令行传入参数）
 * @author southday
 * @date 2018年10月24日
 */
public class ShowMethods {
    private static String usage =
            "usage: \n" +
            "ShowMethods qualified.class.name\n" +
            "To show all methods in class or:\n" +
            "ShowMethods qualified.class.name word\n" +
            "To search for methods involving 'word'";
    
    private static String regx = "\\w+\\.";
    // 练习 17: 去掉 native 和 final 关键字
//    private static String regx = "\\w+\\.| native| final";
    /* 也可以使用: (\\w+\\.| native| final)
     * 但是不能使用: [\\w+\\.| native| final]
     */
    private static Pattern p = Pattern.compile(regx);
    
    private static void print(Object o) {
        System.out.println(o);
    }
    
    /*
     * 右键 -> Run As -> Run Configurations...
     * Arguments: southday.java.thinkinginjava.c14.s6.ShowMethods
     */
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        if (args.length < 1) {
            print(usage);
            return;
        }
        int lines = 0;
        try {
            Class<?> clz = Class.forName(args[0]);
            Method[] methods = clz.getMethods();
            Constructor[] ctors = clz.getConstructors();
            if (args.length == 1) {
                for (Method m : methods)
                    print(p.matcher(m.toString()).replaceAll(""));
//                    print(m.toString()); // 完整的方法签名
                for (Constructor c : ctors)
                    print(p.matcher(c.toString()).replaceAll(""));
                lines = methods.length + ctors.length;
            } else {
                // 使用 args[1] 来找特定的方法签名（这些签名中包含字符串args[1]）
                for (Method m : methods)
                    if (m.toString().indexOf(args[1]) != -1) {
                        print(p.matcher(m.toString()).replaceAll(""));
                        lines++;
                    }
                for (Constructor c: ctors)
                    if (c.toString().indexOf(args[1]) != -1) {
                        print(p.matcher(c.toString()).replaceAll(""));
                        lines++;
                    }
            }
            print("There are " + lines + " methods in class" + args[0]);
        } catch(ClassNotFoundException e) {
            print("No such class: " + e);
        }
    }
}
