package southday.java.basic.collection;
import java.util.*;

/*
枚举是Vector特有的取出方式，发现枚举和迭代很像，而其实枚举和迭代是一样的。
因为枚举的名称和方法的名称都过长，所以被迭代器取代了，所以其抑郁而终了。
 */
public class VectorDemo {
    public static void main(String[] args) {
    //Vector<String> vc = new Vector<String>();
        Vector vc = new Vector();
        vc.add("mak1");
        vc.add("mak2");
        vc.add("mak3");
        vc.add("mak4");
        vc.add(124);    //如果写成Vector<String> vc = new Vector<String>();则这里会报错
        sop(vc);
        
        Enumeration en = vc.elements(); //Enumeration<String> en = vc.elements(); <E>参数化
        while(en.hasMoreElements()) {
            System.out.println(en.nextElement());
        }
        
    }
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
