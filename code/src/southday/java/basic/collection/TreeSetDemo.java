package southday.java.basic.collection;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
/*
|——Set: 元素书无序的（存入和取出顺序不一定一致），元素是不重复的
    |--HashSet: 底层数据结构是哈希表
        HashSet是如何保证元素的唯一性呢？
        答：是通过两个方法：hashCode()和equals()来完成，如果元素的hashCode值相同，才会判断equals是否为true.
          如果元素的hashCode值不同，则不会调用equals。        //ArrayList依赖的是equals！！！
        注意：对于判断元素是否存在以及删除等操作，依赖的方法是元素的hashCode和equals方法。先判断hashCode，有了才判断equals
    |--TreeSet: 可以对Set集合中的元素进行排序--比如按字母的自然顺序(其ASCII码大小从小到大）排序
        底层数据结构是：二叉树
        保证元素唯一性依据：compareTo()方法来确定元素是否相同.return 0表示相同
        
    （1）TreeSet排序的第一种方式：  这种方式也称为元素的自然顺序（默认顺序）
        让元素自身具备比较性，元素所属类需要实现Comparable接口，覆盖compareTo方法
    （2）TreeSet排序的第二种方式：
        当元素自身不具备比较性时，或者具备的比较性不是所需要的，这时需要让【集合（容器）自身具备比较性】
        在集合初始化时就有了比较方式： 定义比较器，将比较器对象作为参数传递给TreeSet集合的构造函数
    TreeSet(Comparator<? super E> comparator)——构造一个新的空 TreeSet，它根据指定比较器进行排序。
           （3） 当两种排序方式都存在时，以比较器为主：
                       定义比较器的方法： 定义一个类，实现Comparator，覆盖public int compare(Object o1,Object o2)方法
*/

//关于TreeSet的测试
/* 需求：
 * 往TreeSet集合中存自定义对象，想要按学生的年龄进行排序
 * 排序时，当主要条件相同时，一定要判断以下次要条件.. java.lang中很多类都已经实现了compareTo()
 * */
class TreeSetDemo {
    public static void main(String[] args) {
        TreeSet ts = new TreeSet(new MyCompare());    //传自制比较器进去
        
        //往TreeSet中添加元素
//        ts.add("abc");        
//        ts.add("cda");
//        ts.add("bca");
//        ts.add("bda");
//        ts.add("Daa");
        
        ts.add(new TStudent("li01",15));    //排序的方法有点像二叉搜索树的排序，从已排序好的对象的树根对象入手比较
        ts.add(new TStudent("li02",19));    //如果二叉树中的元素过多的时候，可能还会自动取折中值，以减少比较次数
        ts.add(new TStudent("li03",18));
        ts.add(new TStudent("li04",12));
        ts.add(new TStudent("li05",20));
        
        //你会发现没有li06,因为此时的TStudent中的compareTo方法中只以年龄来判断对象是否相等
        //因为li03先存入Set且其年龄与li06的相同，所以compareTo方法判断为这两个对象相等
        //可以设置compareTo()方法，当年龄相等时，比较name属性，即: return this.name.compareTo(s.name);
        //String类中实现了compareTo方法,按字符串中字符的Unicode值升序排序
        ts.add(new TStudent("li06",18));  //结果是"li03"在"li06"的前面，如果名字和年龄都相同则该对象视为已存在
        
        Iterator it = ts.iterator();
        
        while(it.hasNext()) {   //输出结果： Daa abc bca bda cda  （按字母的自然顺序【字母ASCII码】排序）
            TStudent s = (TStudent)(it.next());
            System.out.println("s.name = "+s.getName()+" s.age = "+s.getAge());
        }
    }
    
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}

//自制比较器 Comparator
class MyCompare implements Comparator {

    public int compare(Object o1, Object o2) {
        // TODO Auto-generated method stub
        TStudent s1 = (TStudent)o1;
        TStudent s2 = (TStudent)o2;
        
        int num = s1.getName().compareTo(s2.getName());
        
        if(num==0)
            return new Integer(s1.getAge()).compareTo((new Integer(s2.getAge())));
        return num;
    }
    
}
