package southday.java.basic.collection;
import java.util.*;
/*
Collection
     |--List:元素是有限的，元素可以重复，因为该集合体系有索引。
         |--ArrayList:底层的数据结构使用的都是数组数据结构，线程不同步。可变长度数组。
             ArrayList初始长度为10，但添加的数据超过10时，会自动再new一个空间，增长原空间的50%（即变成长度为15的数组），再进行数据拷贝。
         |--LinkedList:底层使用的是链表数据结构
         |--Vector:底层是数组数据结构，线程同步。在JDK 1.2后被ArrayList取代。可变长度数组。
             Vector长度也是可变的，当数据长度超过时额定空间时，做法与ArrayList类似，只是其增长的是原空间的100%（即变成长度为20的数组）。
     |--Set:元素是无序的，元素不可以重复，该集合体系中无索引。
 
List集合特有方法：
增
    add(index,Elemtype);  //在index位置，添加指定元素
    addAll(index,Collection);  //在index位置，添加一堆元素
删
    remove(index);        //删除指定位置的元素
改
    Set(index,Elemtype);    //更改index位置的元素
查
    get(index);        // 返回列表中指定位置的元素。
    listIterator(index); //返回列表中元素的列表迭代器（按适当顺序），从列表的指定位置开始。
    subList(from,to);    //返回列表中指定的 fromIndex（包括 ）和 toIndex（不包括）之间的部分视图。
    
List集合特有的迭代器：ListIterator是Iterator的子接口
---在迭代过程中，不可以通过集合对象的方法操作集合中的元素，因为会发生ConcurrentModificationException异常
       （即：对象的并发修改异常），所以在使用迭代器时，只能用迭代器操作集合元素，然而Iterator的操作是有限的，只能对元素
       进行判断，取出，删除操作。若想使用其他的操作，如：添加，更改等，则需要使用它的子接口：ListIterator。
---ListIterator只能通过List集合的ListIterator方法来获取。
       实现了在遍历过程中对集合元素的增、删、改、查等操作。
*/
public class ListDemo {
    public static void main(String[] args) {
        List_method();
        //Iterator_method();
    }
    
    public static void Iterator_method() {
        ArrayList al = new ArrayList();
        //先添加基本元素
        al.add("java-0");
        al.add("java-1");
        al.add("java-2");
        al.add("java-3");
        sop(al);
        
        ListIterator li = al.listIterator();
        
        while(li.hasNext()) {        //正向遍历
            Object obj = li.next();
            if(obj.equals("java-0")) {
                li.add("java-020");  //在"java-0"后面添加
                sop("li.add-->"+al);
            }
            if(obj.equals("java-2")) {
                li.remove();   //遍历到"java-2"(存在)则删除该元素
                sop("li.remove()-->"+al);
            }
            if(obj.equals("java-3")) {
                li.set("JAVA-3"); //遍历到"java-3"(存在)则更改该元素为"JAVA-1"
                sop("li.set()-->"+al);
            }
        }
        
        while(li.hasPrevious()) {
            Object obj = li.previous();
            sop("obj-->"+obj);
            //返回对 previous 的后续调用所返回元素的索引。
            sop("previousIndex:"+li.previousIndex());
        }
    }
    
    public static void List_method() {
        ArrayList al = new ArrayList();
        
        //先添加基本元素
        al.add("java-0");
        al.add("java-1");
        al.add("java-2");
        al.add("java-3");
        sop(al);
        
        //在指定位置添加元素
        al.add(1,"java-7"); //角标从0开始，在角标为1的位置添加元素"java-7"
        sop(al);
        
        //删除指定位置的元素
        al.remove(3);    //删除角标为3的元素
        sop(al);
        
        //更改指定元素
        al.set(0, "JAVA-00");   //更改角标为0的元素
        sop(al);
        
        //获取元素--查元素
        sop("al.get(2):"+al.get(2));  //获取角标为2的元素
        //al.listIterator(1)从角标为1的位置开始创建迭代器。
        for(ListIterator it = al.listIterator(0); it.hasNext();) {
            sop("next:"+it.next());
        }
        sop("al.subList(0,2):"+al.subList(0, 2)); //获取角标从0到1的元素
    }
    
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
