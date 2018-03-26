package southday.java.basic.collection;
import java.util.*;

public class MyCol {
    public static void main(String[] args) {
        Base_method();
    }
    
    public static void Base_method() {
        ArrayList<String> al1 = new ArrayList();
        ArrayList al2 = new ArrayList();
        //1.添加元素
        al1.add("Tro-1");
        al1.add("Tro-2");
        al1.add("Tro-3");
        al1.add("Tro-4");
        al1.add("Tro-6");
        
        al2.add("Tro-1");
        al2.add("Tro-5");
        al2.add("Tro-3");
        al2.add("Tro-4");
        sop("al1:"+al1);
        sop("al2:"+al2);
        //2.删除元素
        al1.remove(2);        //对于集合来说，元素角标从0开始
        sop("al1.remove(2):"+al1); //1 2 4 6 
        al1.remove("Tro-4"); 
        sop("al1.remove(\"Tro-4\")"+al1); //1 2 6
        
        //3.清空元素
        //al1.clear();
        
        //4.判断某个元素是否存在
    //    sop("al1.contains(\"Tro-1\")"+al1.contains("Tro-1"));
        //5.取交集--al1 ← al1 ∩ al2
        sop("al1.retainAll(\"al2\")"+al1.retainAll(al2));
        sop("al1:"+al1);        //1
        
        //6.取出去相同部分元素的剩余---al1 ← al1-(al1 ∩ al2)
        sop("al1.removeAll(\"al2\")"+al1.removeAll(al2));    
        sop("al1:"+al1);    //2 6
        
        //7.集合元素的个数
        sop("al2.size:"+al2.size());    //4
    }
    
    public static void sop(Object obj) {
        System.out.println(obj);
    }
    
}
