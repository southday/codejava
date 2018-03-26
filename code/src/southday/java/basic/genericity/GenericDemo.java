package southday.java.basic.genericity;

import java.util.ArrayList;
/*
 * 泛型： JDK1.5后出现的新特性，用于解决安全问题，是一个类型安全机制
 * 泛型格式： 通过<>来定义要操作的引用数据类型
 * 
 * 好处：（1）将运行时期出现的问题ClassCastException，转移到了编译时期，方便于程序员解决问题，让运行时期问题减少。
 *     （2）避免了强制转换的麻烦
 * 
 * 我们在使用JAVA提供的对象时，什么时候写泛型呢？
 * 【答】：通常在集合框架中很常见，只要见到<>就要定义泛型。
 * 其实，<>就是用来接收类型的，当使用集合时，将集合中要存储的数据类型作为参数传递到<>即可。
 */
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class GenericDemo {

    public static void main(String[] args) {
        //ArrayListDemo();
        TreeSetDemo();
    }
    
    public static void ArrayListDemo() {
        ArrayList<String> al = new ArrayList<String>();
        
        al.add("aa-0");
        al.add("aa-1");
        al.add("aa-2");
        al.add("aa-2");
        
        Iterator<String> it = al.iterator();
        
        while(it.hasNext()) {
            String str = it.next();    //因为上面的Iterator<String>，所以这里可以不用强制转换
            System.out.println(str);
        }
    }
    
    public static void TreeSetDemo() {
        TreeSet<String> ts = new TreeSet<String>(new LenTreeSetComparator());
        
        ts.add("a");
        ts.add("cbg");
        ts.add("beef");
        ts.add("ab");
        
        Iterator<String> it = ts.iterator();
        while(it.hasNext()) {
            String str = it.next();
            System.out.println(str);
        }
    }
}

//比较器，实现TreeSet中的元素按字符串长度（从小到大排列），如果长度相同则按默认排列方式（字符编码顺序）
class LenTreeSetComparator implements Comparator<String> {    //比较器中使用了泛型
    public int compare(String o1, String o2) {    //参数类型也由Object改为String
        int num = new Integer(o1.length()).compareTo(new Integer(o2.length()));
        if(num==0)
            return o1.compareTo(o2);
        return num;
    }
}
