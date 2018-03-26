package southday.java.basic.thread.bxd;
/*
单例设计模式：一个类中只存在一个对象。
(1) 饿汉式：建立单例时推荐使用饿汉式。
(2) 懒汉式：延迟对象建立，CPU不能同时运行多个程序，当多个程序都进入getInstance()时，
CPU对这些程序不断进行切换运行，并可能产生多个对象（多线程访问getIstance()时），违反了单例，但也有解决办法。如下：
*/
class Student {
    private int age;
    
    //（1）饿汉式
    private Student(){}
    private static Student s = new Student();
    
    public static Student getInstance() {
        return s;
    }
    
    /*（2）懒汉式
    private Student(){}
    private static Student s = null;
    private static Student getInstance() {
         if(s == null)
            s = new Student();
         return s;
    }
    */
    
    /*(2)懒汉式出现问题的解决方法
    方案一：
    synchronized  是一个关键词（代表锁，就是程序A进来后为出去前，程序B无法进来）
    private static synchronized Student getInstance() {
         if(s == null)       
            s = new Student();
         return s;
    }
    方案一使用同步函数，由于每次其他线程进来时都要经过锁判断，这样会降低代码的运行效率，因此用方案二可以相对于方案一提高效率

    方案二：
    双重判断，涉及了 线程问题，以后细解。
    private static Student getInstance() {
         if(s == null) {
            synchronized(Student.class) { //静态方法里面是不能用this的，该类所属的字节码对象Student.class
               if(s == null)
                s = new Student();
            }
         }
         return s;
    }
    方案二中一旦s = new Student()执行后进来的其他线程就会在if(s==null)那被挡住，就不会进入锁的判断，提高了效率。
    */
    public void setAge(int age) {
        this.age = age;
    }
    
    public int getAge() {
        return age;
    }
}

    
class SingletonDemo {
    public static void main(String[] args) {
        Student m = Student.getInstance();
        m.setAge(5);
        Student n = Student.getInstance();
        //n.setAge(10);
        
        System.out.println("age = "+m.getAge());
        System.out.println("age = "+n.getAge());
        
    }
}
