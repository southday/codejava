package southday.java.basic.collection;

import java.util.*;

/*
 * 讲得方法：
 * (1) Collections.fill();
 * (2) Collections.reverse();
 * (3) Collections.reverseOrder();
 * (4) Collections.reverseOrder(Comparator<T> comp);
 * (5) Collections.shuffle();  //使用默认随机源对指定列表进行置换
 */


public class CollectionsDemo2 {
    public static void main(String[] args) {
//        fillListDemo();
//        orderDemo();
        shuffleDemo();
    }
    
    // 玩扑克牌的时候，调用这　shuffle() 方法就相当于洗牌了一次
    public static void shuffleDemo() {
        List<String> ls = new ArrayList<String>();
        ls.add("aaa");
        ls.add("dddd");
        ls.add("zz");
        ls.add("kkkkk");
        ls.add("eeee");
        
        sop(ls);
        Collections.shuffle(ls);
        sop(" 1 = > " + ls); // 两次输出的结果是不一样的
        Collections.shuffle(ls);
        sop(" 2 = > " + ls);
    }
    
    public static void fillListDemo() {
        List<String> ls = new ArrayList<String>();
        ls.add("aaa");
        ls.add("dddd");
        ls.add("zz");
        ls.add("kkkkk");
        ls.add("eeee");
        
        sop(ls);
        Collections.fill(ls, "pp"); // 填充--全部替换
        sop(ls);
        fillPart(ls, -5, 2, "hh");
        sop(ls);
        Collections.replaceAll(ls, "pp", "ooo"); // 替换
        sop(ls);
        Collections.reverse(ls); // 反转
        sop(ls);
    }
    
    // 【练习】： fill()方法有戈局限性，它可以将List集合中的所有元素都替换，现在要求只替换部分元素。
    public static <T> void fillPart(List<T> ls, int head, int tail, T key) {
        int size = ls.size();
        if (head < 0)
            head = 0;
        if (tail >= size)
            tail = size - 1;
        while (head <= tail) {
            ls.set(head, key);
            head++;
        }
    }
    
    
    // -------   orderDemo   ----------
    public static void orderDemo() {
//        TreeSet<String> tr = new TreeSet<String>();  【1】
//        TreeSet<String> tr = new TreeSet<String>(new StrComparator()); // 通过传入自定义比较器进行反转  【3】
        /* 【4】但是没必要那么麻烦，因为Collections类中已经提供了方法来进行反转
         * (1) public static <T> Comparator<T> reverseOrder() {...} 
         *        // 返回一个比较器，它强行逆转实现了 Comparable 接口的对象 collection 的自然顺序
         * (2) public static <T> Comparator<T> reverseOrder(Comparator<T> comp) {...}
         *        // 强行逆转指定比较器顺序的比较器, 如果指定比较器为null，则此方法等同于 reverseOrder()
         * 其实它们的实现与类   StrComparator 类似，只是把反转这个方法再封装了一下，就不必再去构造比较器了
         */
//        TreeSet<String> tr = new TreeSet<String>(Collections.reverseOrder()); // 【4】(1)
        TreeSet<String> tr = new TreeSet<String>(Collections.reverseOrder(new StrLenComparator())); // 【4】(2)
        tr.add("aaa");
        tr.add("x");
        tr.add("pp");
        tr.add("cccc");
        // Set集合在添加元素的时候就按自然顺序拍好序了
        
        Iterator<String> it = tr.iterator();
        while (it.hasNext()) {
            sop(it.next());
        }
        
    }
    // -------   orderDemo   ----------
    
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}

// 对于 orderDemo 这个例子中，现在要求反转 TreeSet集合中的默认排序  【2】
// 这样一来，我们就需要定义一个比较器，再将这个比较器传进去
class StrComparator implements Comparator<String> {
    public int compare(String s1, String s2) {
        return s2.compareTo(s1); // 原本是  return s1.compareTo(s2);
    }
}
