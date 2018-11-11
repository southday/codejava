package southday.java.thinkinginjava.c14.s09;

import java.lang.reflect.Field;

/**
 * 通过反射机制修改类的私有域，包括带有 fianl 修饰的域（final修饰的字段不会被更改）
 * @author southday
 * @date 2018年10月25日
 */
public class ModifyingPrivateFields {
    public static void main(String[] args) throws Exception {
        WithPrivateFinalField pf = new WithPrivateFinalField();
        System.out.println(pf);
        Field f = pf.getClass().getDeclaredField("i");
        f.setAccessible(true);
        System.out.println("f.getInt(pf): " + f.getInt(pf));
        f.setInt(pf, 47);
        System.out.println(pf);
        
        /* 如果 i 是int型，那就用f.getInt(pf)，使用错误类型会报错
         * Exception in thread "main" java.lang.IllegalArgumentException: 
         * Attempt to get int field "southday.java.thinkinginjava.c14.s09.WithPrivateFinalField.i" 
         * with illegal data type conversion to boolean
         */
//        System.out.println(f.getBoolean(pf));
        
        f = pf.getClass().getDeclaredField("s"); // final
        f.setAccessible(true);
        System.out.println("f.get(pf): " + f.get(pf));
        f.set(pf, "No, you're not!");
        System.out.println(pf);
        
        f = pf.getClass().getDeclaredField("s2");
        f.setAccessible(true);
        System.out.println("f.get(pf): " + f.get(pf));
        f.set(pf, "No, you're not!");
        System.out.println(pf);
    }
}
