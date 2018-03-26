package southday.java.basic.collection;
import java.util.*;

/*
  将自己定义对象作为元素存到ArrayList中，并去除重复。
  
 比如： 存人对象，同姓名同年龄视为同一人
 // List 集合判断元素是否相同，依据的是元素的equals方法。在Person的equals语句中加入一个输出语句：
     System.out.println(this.name+"..."p.name);就可以看出
*/

class Person {
    private String name;
    private int age;
    
    Person(String name, int age) {    // 在这里偷点懒，按理来说，应该是用SetName()和SetAge()来实现
        this.name = name;                // 然后可以用GetName()和GetAge()来访问
        this.age = age;
    }
    
    public String GetName() {
        return this.name;
    }
    
    public int GetAge() {
        return this.age;
    }
    
    /*
     * 如果是Object中的equals方法，对于 new Person("lisi01",22)和 new Person("lisi01",22)是不同的，因为对象不是一个
     * 所以，如果要按我们对于人相同定义来操作，则需要重写equals方法，以其姓名和年龄来判断相同与否
     */
    public boolean equals(Object obj) {
        if(!(obj instanceof Person))
            return false;
        Person p = (Person)obj;
        /*
         * System.out.println(this.name+"..."+p.name);
         * 当写入这条输出语句时，你会发现你根本而没有调用equals方法，而是调用了contains方法，却依旧会执行该语句
         * 每获取一个对象【contains(C)】C，都默认会和原有对象A,B进行比较。
         * 假如当前al中有对象A B C，执行al.contains(D)时，默认D会与A B C都会比较【执行equals方法】一次。
         * 为什么说是默认呢？因为如果D和B比较时二者相等，则就不会再与A比较。
         */
        return (this.name.equals(p.name) && this.age==p.age);
    }
}

public class ArrayListExercise {
    public static void main(String[] args) {
        ArrayList al = new ArrayList();
        
        al.add(new Person("lisi01",31)); // add(Object obj); 相当于 Object obj = new Person("lisi01",31);类型提升了
        al.add(new Person("lisi02",32));
        al.add(new Person("lisi03",33));
        al.add(new Person("lisi04",34));
        printPerson(al);    // 输出人对象【姓名...年龄】
        
        System.out.println("------------------------");
        al.add(new Person("lisi04",34));
        al.add(new Person("lisi02",32));
        al = signalElemet(al);    // 去除重复元素
        printPerson(al);
    }
    
    public static ArrayList signalElemet(ArrayList al) {
        ArrayList newAl = new ArrayList();
        Iterator it = al.iterator();
        
        while(it.hasNext()) {
            // Person p = (Person)it.next();
            Object obj = it.next();
            if(!newAl.contains(obj))    // contains的底层原理就是equals方法
                newAl.add(obj);
        }
        return newAl;
    }
    
    
    public static void printPerson(ArrayList al) {
        Iterator it = al.iterator();
        
        while(it.hasNext()) {
            // sop(it.next().getName()+"...."+it.next().getAge());
            /*
            上面这种写法存在两个错误：
            （1）每执行it.next()后，指针向后移，所以说对于奇数个元素时，会出现NoSuchElementException异常。
            （2）add(Object obj)中存在隐式的类型提升，而next()函数返回的是Object类型，然而Object类中没有GetName和GetAge这两个函数。
            */
            Person p = (Person)it.next();    // 所以需要向下转型
            sop(p.GetName()+"..."+p.GetAge());
        }
    }
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
