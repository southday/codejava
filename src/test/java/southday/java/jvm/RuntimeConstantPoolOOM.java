package southday.java.jvm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 方法区，运行时常量池 OutOfMemoryError
 * vm args: -XX:PermSize=10M -XX:MaxPermSize=10M (jvm8后不支持)
 * @author southday
 * @date 2019/3/2
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        // 使用List保持着常量池引用，避免Full GC回收常量池行为
        List<String> list = new ArrayList<>();
        // 10MB 的PermSize在Integer范围内足够产生 OOM（OutOfMemory）
        int i = 0;
        while (true)
            list.add(String.valueOf(i++).intern());
    }

    /* String.intern()是一个Native方法；
     * 如果字符串常量池中已经包含一个等于此String对象的字符串，则返回代表池中这个字符串的String对象；
     * 否则，将此String对象包含的字符串添加到常量池中，并且返回此String对象的引用；
     */

    @Test
    public void internTest() {
        String str2 = new StringBuilder("ja2").append("va").toString();
        System.out.println(str2.intern() == str2); // true
        String str3 = new StringBuilder("ja2").append("va").toString();
        System.out.println(str3.intern() == str3); // false
        System.out.println(str3.intern() == str2); // true
    }
}
