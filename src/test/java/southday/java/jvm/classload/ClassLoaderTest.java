package southday.java.jvm.classload;

import java.io.IOException;
import java.io.InputStream;

/**
 * 类加载器与instanceof关键字演示<br/>
 * 由不同类加载器加载的类必定不相等
 * @author southday
 * @date 2019/3/17
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = myLoader.loadClass("southday.java.jvm.classload.ConstClass").getDeclaredConstructor().newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof southday.java.jvm.classload.ConstClass);

        System.out.println(obj.getClass().getClassLoader());
        System.out.println(southday.java.jvm.classload.ConstClass.class.getClassLoader());
    }

    /*
    # 输出
    class southday.java.jvm.classload.ConstClass
    false
    southday.java.jvm.classload.ClassLoaderTest$1@34b7bfc0
    jdk.internal.loader.ClassLoaders$AppClassLoader@4629104a
     */

    /*
    虚拟机中存在两个不同的ConstClass类：
    1）一个是由系统应用程序类加载器加载的；jdk.internal.loader.ClassLoaders$AppClassLoader@4629104a
    2）另一个是由我们自定义的类加载器加载的；southday.java.jvm.classload.ClassLoaderTest$1@34b7bfc0
    虽然二者均来自同一个Class文件，但依然是两个独立的类，所以 obj instanceof 检查结果为 false；
     */
}
