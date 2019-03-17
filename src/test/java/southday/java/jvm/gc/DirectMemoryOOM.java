package southday.java.jvm.gc;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 直接内存溢出测试，直接内存可以理解为你内存条上标记的大小
 * vm args: -Xmx20m -XX:MaxDirectMemorySize=10m
 * @author southday
 * @date 2019/3/2
 */
public class DirectMemoryOOM {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws Exception {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        for (int i = 1; true; i++) {
            unsafe.allocateMemory(_1MB);
            System.out.println("allocate 1MB, 总分配量：" + i + "MB");
        }
    }
    // allocate 1MB, 总分配量：12712MB
}
