package southday.java.thinkinginjava.c15.s17;

import java.lang.reflect.Method;
import java.util.ArrayList;

/* 潜在的类型机制
何为潜在的类型机制，如下面的python代码：
#: DogsAndRobots.py

class Dog:
    def speak(self):
        print "Arf!"
    def sit(self):
        print "Sitting"
    def reproduce(self):
        pass

class Robot:
    def speak(self):
        print "Click!"
    def sit(self):
        print "Click!"
    def oilChange(self):
        pass

def perform(anything):
    anything.speak()
    anything.sit()

a = Dog()
b = Robot()
perform(a)
perform(b)

上面的例子中，perform(anything)就运用了潜在类型机制：我不关心你什么类型，只要你可以speak()和sit()即可
anything只是一个标识符，它必须能够执行perform()期望它执行的操作，因此这里隐含着一个潜在的接口
*/

/* Java不支持上述的潜在类型机制，但是可以通过一些方法来模拟
1) 使用反射，本质就是通过 obj.class.getMethod(methodName)，看是否能获取到指定的方法，如果可以则执行，否则抛出运行时异常
2) 第2点就是本例描述的：将一个方法引用于序列 Apply.apply()
*/

class Apply {
    // apply() 方法可以接受任何实现了Iterable接口的事物
    public static <T, S extends Iterable<? extends T>>
    void apply(S seq, Method f, Object... args) {
        try {
            for (T t : seq)
                f.invoke(t, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

class Shape {
    public void rotate() { System.out.println(this + " rotate"); } 
    public void resize(int newSize) { System.out.println(this + " resize " + newSize); }
}

class Square extends Shape {}

class FilledList<T> extends ArrayList<T> {
    private static final long serialVersionUID = 1L;

    // 使用类型标记技术 type
    public FilledList(Class<? extends T> type, int size) {
        try {
            // 要求 type类型必须有默认构造器
            for (int i = 0; i < size; i++)
                add(type.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

/**
 * Apply - 将任何方法应用于某个序列中的所有对象
 * @author southday
 * @date 2018年10月29日
 */
public class ApplyTest {
    public static void main(String[] args) throws Exception {
        Apply.apply(new FilledList<Shape>(Shape.class, 5), Shape.class.getMethod("rotate"));
        Apply.apply(new FilledList<Shape>(Square.class, 2), Shape.class.getMethod("resize", int.class), 5);
        
        // 其他测试
        FilledList<Shape> l1 = new FilledList<Shape>(Shape.class, 5);
        @SuppressWarnings("unused")
        Square s1 = (Square)l1.get(0); // 报错 ClassCastException
        FilledList<Shape> l2 = new FilledList<Shape>(Square.class, 5);
        @SuppressWarnings("unused")
        Square s2 = (Square)l2.get(0); // 正常
    }
}
