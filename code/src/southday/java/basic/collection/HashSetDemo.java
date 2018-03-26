package southday.java.basic.collection;

import java.util.HashSet;
import java.util.Iterator;
/*
|——Set: 元素书无序的（存入和取出顺序不一定一致），元素是不重复的
    |--HashSet: 底层数据结构是哈希表
        HashSet是如何保证元素的唯一性呢？
        答：是通过两个方法：hashCode()和equals()来完成，如果元素的hashCode值相同，才会判断equals是否为true.
          如果元素的hashCode值不同，则不会调用equals。        //ArrayList依赖的是equals！！！
        注意：对于判断元素是否存在以及删除等操作，依赖的方法是元素的hashCode和equals方法。先判断hashCode，有了才判断equals
    |--TreeSet: 可以对Set集合中的元素进行排序--比如按字母的自然顺序(其ASCII码大小从小到大）排序
*/

//关于HashSet的测试
class HashSetDemo {
    public static void main(String[] args) {
        HashSet hs = new HashSet();
        /*
        //HashSet添加元素是无序的，是按哈希地址来添加的。
        sop(hs.add("java-01"));    // add方法返回true，因为该元素之前不存在
        hs.add("java-02");
        hs.add("java-03");
        hs.add("java-04");
        sop("HashSet = "+hs);
        
        //HashSet读取元素只有一种方法，就是迭代器
        Iterator it = hs.iterator();
        sop(hs.add("java-01"));    // add方法返回false，因为该元素已经存在。要保证Set的唯一性
        
        while(it.hasNext()) {    // 可以很明显的观察到输出的元素具有无序性（当然也可能按添加顺序输出）
            sop(it.next());
        }
        */
        
        //往HashSet中添加对象元素，测试HashSet如何确保元素的唯一性
        hs.add(new TPerson("li3",1));
        hs.add(new TPerson("li4",2));
        hs.add(new TPerson("li5",3));
        hs.add(new TPerson("li4",2));
    //    sop(hs);
        
        sop("---------------------");
        // 判断某元素是否存在，先判断hashCode，如果hashCode已经存在（碰撞了），则再执行equals判断
        sop("li3 exist? "+hs.contains(new TPerson("li3",1)));
        
        sop("---------------------");
        // 删除某元素，同样的道理
        sop("remove li4 "+hs.remove(new TPerson("li4",2)));
        
        sop("---------------------");
        Iterator it = hs.iterator();    // 如果在未添加元素前创建迭代器，则it.hasNext()返回false
        while(it.hasNext()) {
            TPerson p = (TPerson)it.next();
            sop(p.getName()+"..."+p.getAge());
        }
    }
    
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
