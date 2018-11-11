package southday.java.thinkinginjava.c15.s08;

class Building {}

class House extends Building {}

/**
 * 类型擦除补偿</br>
 * 由于Java中的泛型是使用擦除来实现的，所以任何在运行时需要知道确切类型信息的操作都将无法工作，如：
 * {@code if (obj instanceof T)}，要实现该目的，我们可以通过引入类型标签来对擦除进行补偿
 * @author southday
 * @date 2018年10月26日
 */
public class ClassTypeCapture<T> {
    Class<T> kind;
    
    public ClassTypeCapture(Class<T> kind) {
        this.kind = kind;
    }
    
    public boolean f(Object arg) {
        return kind.isInstance(arg);
    }
    
    public static void main(String[] args) {
        ClassTypeCapture<Building> ctt1 = new ClassTypeCapture<Building>(Building.class);
        System.out.println(ctt1.f(new Building())); // true
        System.out.println(ctt1.f(new House())); // true
        
        ClassTypeCapture<House> ctt2 = new ClassTypeCapture<House>(House.class);
        System.out.println(ctt2.f(new Building())); // false
        System.out.println(ctt2.f(new House())); // true
    }
}
