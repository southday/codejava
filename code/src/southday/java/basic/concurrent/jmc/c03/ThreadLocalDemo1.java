package southday.java.basic.concurrent.jmc.c03;

/* 第一次调用ThreadLocal类的get()时，get()返回值时null，
 * 怎么实现第一次调用的返回值不是null呢？ 即设置一个默认值，该值 != null
 * 
 * ThreadLocal类中的initialValue()方法：
 * protected T initialValue() {
        return null;
    }
    默认返回null;
    
 []: 还应注意一点，由于每个线程在ThreadLocal中有个map，而在set(value)时，
   map.set(this, value);说明了是用ThreadLocal实例作为key，也就是说，对于
   同一个ThreadLocal实例，同一个线程第二次set(value)时，会把第一次的值覆盖，
   而这个 value 可以是任意对象，如：基本数据类型（应该是内部转型了）、ArrayList等，
   这些对象中又可以存多个变量
 */

class ThreadLocalSub extends ThreadLocal<Object> {
    @Override
    protected Object initialValue() {
        return "I'm != null";
    }
}

public class ThreadLocalDemo1 {
    public static void main(String[] args) {
        ThreadLocal<Object> Thread_local = new ThreadLocalSub();
        System.out.println(Thread_local.get());
        Thread_local.set(Thread.currentThread().getName());
        System.out.println(Thread_local.get());
    }
}
