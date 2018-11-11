package southday.java.thinkinginjava.c15.s08;

class ClassAsFactory<T> {
    T x;
    
    public ClassAsFactory(Class<T> kind) {
        try {
            // 前提是kind类型具有默认构造器，如果没有，编译期间也不会报错
            x = kind.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

class Employee {
    // private Employee() {} // 关闭Employee的默认构造器
}

/**
 * Java中无法通过泛型创建类型实例，如：{@code T a = new T();} 一部分原因是以为擦除，另一部分是无法确定T有默认构造函数。</br>
 * 我们可以通过传递一个工厂对象来实现上述效果。
 * @author southday
 * @date 2018年10月26日
 */
public class InstantiateGenericType {
    public static void main(String[] args) {
        ClassAsFactory<Employee> fe = new ClassAsFactory<Employee>(Employee.class);
        System.out.println("ClassAsFactory<Employee> succeeded");
        try {
            // 以为Integer类没有默认构造器
            ClassAsFactory<Integer> fi = new ClassAsFactory<Integer>(Integer.class);
        } catch (Exception e) {
            System.out.println("ClassAsFactory<Integer> failed");
        }
    }
}
