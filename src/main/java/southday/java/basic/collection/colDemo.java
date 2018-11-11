package southday.java.basic.collection;
import java.util.*;
/*
1.add()方法的参数类型是 Object，以便于接受任意类型的对象
2.集合中存储的都是对象的引用（地址）

*/
public class colDemo {
    public static void main(String[] args) {
        Base_method();
        //method_2();
        //method_get();
    }
    // 什么是迭代器呢？ ————其实就是为了取出集合中的元素
    private static void method_get() {
        ArrayList al = new ArrayList();
        al.add("JAVA-1");
        al.add("JAVA-2");
        al.add("JAVA-3");
        /*第一种写法
         Iterator itor = al.iterator();//获取迭代器，用于取出集合中的元素
         while(itor.hasNext())//或者写成 for(int x=0;x<al.size();x++)
            sop(itor.next());
         */
        //第二种写法
        for(Iterator itor = al.iterator();itor.hasNext();)
            sop(itor.next());
        //区别在于：第二种写法中的itor对象存在于for中，循环一结束则会被清除。节约了存储空间。但第一种比较直观。
        sop("size:"+al.size());        //返回集合元素个数
        sop(al);        //打印集合,以数组的形式出现[JAVA-1,JAVA-2,JAVA-3]
    }
    private static void method_2() {
        ArrayList al1 = new ArrayList();
        al1.add("JAVA-1");
        al1.add("JAVA-2");
        al1.add("JAVA-3");
        
        ArrayList al2 = new ArrayList();
        al2.add("JAVA-1");
        al2.add("JAVA-5");
        al2.add("JAVA-4");
        
        //al1.retainAll(al2);//取al1和al2的交集赋予al1.  al1 ← al1 ∩ al2
        al1.removeAll(al2);//去掉al1和al2相同的元素后再赋予al1.   al1 ← al1-(al1 ∩ al2)
        sop("al1 = "+al1);
        sop("al2 = "+al2);
    }
    private static void Base_method() {
        //创建一个集合容器，使用collection接口的子类。ArrayList
        ArrayList al = new ArrayList();
        
        //1.添加元素
        al.add("JAVA-1");
        al.add("JAVA-2");
        al.add("JAVA-3");
        //打印集合
        sop("al(原) = "+al);
        
        //3.删除元素
        al.remove(2);//等价于al.remove("JAVA-3");
        sop("al (remove)="+al);
        sop("size:"+al.size());
        al.clear();//清空集合
        sop("al (clear)= "+al);
        
        //4.判断某个元素是否存在
        sop("JAVA-2 是否存在："+al.contains("JAVA-2"));
        sop("集合是否为空："+al.isEmpty());
        
        //2.获取集合长度/
        sop("size :"+al.size());
    }
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
