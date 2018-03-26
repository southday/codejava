package southday.java.basic.collection;

import java.util.*;
/*
 * 集合框架工具类： Collections:
 * 
 * Collections其中有一个方法：sort
 * 1. public static <T extends Comparable<? super T>> void sort (List<T> list) {...}
 * 现在来解释一下 <T extends Comparable<? super T>>的含义：
 * （1） sort是排序的意思，要排序就需要比较，而List集合是可以比较的，但是要比较的对象事先不确定，所以这里定义了泛型 (List<T> list)
 * （2）假如sort方法是这样定义的： public static <T> void sort (List<T> list) {...}
 *         |-- 现在我定义一个类Student，然后调用sort方法，sort(List<Student> list);
 *         |-- 在编译的时候是没有错的，但是运行时候会出错，**** 因为我的Student类不具备比较性质 ****
 *         |-- 为了使错误发现于编译时期，这里就对这个泛型<T>实施了规定，如下：
 * （3）<T extends Comparable<? super T>> 表示：
 *         |-- 传入的对象类型<T>必须是Comparable的子类，即实现这个接口，让这个对象具备可比性
 *         |-- 至于Comparable<? super T> 是对方法进行了扩展，即我可以穿Person对象，也可以传Student对象
 * 
 * 2. public static <T> void sort (List<T> list, Comparator<? extends T> c) {...}
 *         |-- 与 第一个 sort不同的是：这里不用对static后的<T>做限定，因为后面的参数中要求传入比较器。
 *         |-- 即：存在了比较器，就不需要要求 对象 自身具备比较方法，也就是不要求实现Comparable接口
 * 
 * 这个类中涉及到的知识点：
 * (1) Collections.sort();
 * (2) Collections.max();
 * (3) Collections.binarySearch();
 */

public class CollectionsDemo1 {
    public static void main(String[] args) {
//        sortDemo();
//        showMax();
        BinarySearchDemo();
    }
    
    public static void sortDemo() {
        List<String> ls = new ArrayList<String>();
        ls.add("adk");
        ls.add("vfk");
        ls.add("ct");
        ls.add("tpp");
        
        sop(ls); // 怎么存进去就怎么输出的
        Collections.sort(ls); // 利用工具类排序
        sop(ls);
        
        // 现在我要求根据字符串的长度来排序，那么我就可以使用 sort(List<T> list, Copmarator<? super T> c)这个方法
        Collections.sort(ls, new StrLenComparator());
        sop(ls);
    }
    
    public static void showMax() {
        List<String> ls = new ArrayList<String>();
        ls.add("adkd");
        ls.add("vfk");
        ls.add("ct");
        ls.add("tpp");
        
        sop(ls);
        // public static <T extends Object & Comparable<? super T>> T max (Collection<? extends T> coll) {...}
        String max = Collections.max(ls);
        sop("max = " + max);
        // public static <T> T max (Collection<? extends T> coll, Comparator<? super T> comp) {...}
        max = Collections.max(ls, new StrLenComparator());
        sop("max = " + max);
    }
    
    
    /* ------- BinarySerach ------------ */
    /*
     * public static <T> int binarySearch(List<? extends Comparable<? super T>> list, T key)
     * （1）如果搜索键包含在列表中，则返回搜索键的索引；否则返回 (-(插入点) - 1)。
     *                【插入点】被定义为将键插入列表的那一点：即第一个大于此键的元素索引；
     *         比如： [aaa, abc, cde]， 对于"aab"的插入点为  1,即"aab"应该放在1角标这个位置-->[aaa,aab,abc,cde]
     * （2）如果列表中的所有元素都小于指定的键，则为 list.size()。注意，这保证了当且仅当此键被找到时，返回的值将 >= 0。
     */
    public static void BinarySearchDemo() {
        List<String> ls = new ArrayList<String>();
        ls.add("abcd");
        ls.add("aaa");
        ls.add("zz");
        ls.add("kkkkk");
        ls.add("qq");
        ls.add("z");
        
        Collections.sort((ls));
        sop(ls);
//        int index = Collections.binarySearch(ls, "aaaa");
        int index = HalfSearchDemo(ls, "aaa");
        sop(" index = " + index);
        
        // 使用比较器先排序 -- 按长度排序
        Collections.sort(ls, new StrLenComparator());
        sop(ls);
        index = HalfSearchDemo2(ls, "aaa", new StrLenComparator());
        sop(" index = " + index);
    }
    
    // 让元素自身具备比较性
    public static <T extends Comparable<? super T>> int HalfSearchDemo(List<T> ls, T key) {
        int max, min, mid, num;
        T str;
        max = ls.size() - 1;
        min = 0;
        
        while (min <= max) {
            mid = (max + min) >> 1; // (max + min) / 2
            str = ls.get(mid);
            sop("{ " + ls.get(min) + ", " + str + ", " + ls.get(max) + " }");
            num = str.compareTo(key);
            /*
             * 这个HalfSearchDemo()方法定义，强制对象具有比较性
             * 若对象不具备比较性或者说其具备的比较性不是我所需要的，可以定义一个比较器参数，如下：
             * public static <T> int HalfSearchDemo(List<T> ls, T key, Comparator<? super T> comp){...}
             * 这样一来， num = str.compareTo(key); 就要换成 -->  num = comp.compare(str, key);
             */
            if (num > 0)
                max = mid - 1;
            else if (num < 0)
                min = mid + 1;
            else
                return mid;
        }
        return -min-1; // 返回 -(插入点)-1 <=> -min-1
    }
    
    // 使用比较器
    public static <T> int HalfSearchDemo2(List<T> ls, T key, Comparator<? super T> comp) {
        int max, min, mid, num;
        T str;
        max = ls.size() - 1;
        min = 0;
        
        while (min <= max) {
            mid = (max + min) >> 1;
            str = ls.get(mid);
            sop("{ " + ls.get(min) + ", " + str + ", " + ls.get(max) + " }");
            // 使用比较器来比较，但是在比较前必须确保 ls 是有序的，按比较器方法排序的
            num = comp.compare(str, key); 
            if (num > 0)
                max = mid - 1;
            else if (num < 0)
                min = mid + 1;
            else
                return mid;
        }        
        return -min-1;
    }
    /* ------- BinarySerach ------------ */
    
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}


class StrLenComparator implements Comparator<String>{
    // 比较用来排序的两个参数。根据第一个参数小于、等于或大于第二个参数分别返回负整数、零或正整数
    public int compare(String s1, String s2) {
        if (s1.length() > s2.length())
            return 1; // 第一个参数 大于 第二个参数时返回正整数
        if (s1.length() < s2.length())
            return -1; // 第一个参数 小于 第二个参数时返回负整数
        return s1.compareTo(s2);
    }
}
