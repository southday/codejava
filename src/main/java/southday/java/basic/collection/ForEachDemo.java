package southday.java.basic.collection;

import java.util.*;

/* 高级for循环
 * 格式：for (数据类型  变量名  ：被遍历的集合Collection 或  数组) {...}
 * (1) 对集合进行遍历：只能获取，不能对集合进行过多的操作！！
 * (2) 迭代器，除了遍历，还可以进行remove集合中元素的动作（会使原集合改变）
 * (3) 如果使用ListIterator，还可以在遍历过程中对集合进行 增、删、改、查的动作
 * 
 * 传统for循环与高级for循环有何区别？
 * 【答】：高级for有一个局限性，必须有被遍历的目标，如 for (int i : arr)， arr就是被遍历的目标
 * 建议： 在遍历数组的时候还是希望使用传统for，因为传统for可以定义角标，我们可以对角标进行操作。
 * 
 * 比如我要打印 "Hello world!" 100 次：
 *         |-- 高级for怎么写？  for ( : )，难道要定义一个长度为100的数组，然后每读取一次就输出 "Hello world!" ？？麻烦！
 *         |-- 传统for： for (int x = 0; x < 100; x++) { "Hello world!"} 就OK！
 */

public class ForEachDemo {
    public static void main(String[] args) {
        ArrayList<String> al = new ArrayList<String>();
        al.add("abc1");
        al.add("abc2");
        al.add("abc3");
        al.add("abc4");

        /* 迭代器写起来不爽
        Iterator<String> it = al.iterator();
        while (it.hasNext()) {
            sop(it.next());
        }
        */
        
        // 这个写法是有局限性的，因为str只能从al中取出元素，而不能写入（修改）
        sop("ArrayList<String>有泛型，可以使用 for (String str : al)");
        for (String str : al) { // 这个哥们的底层原理还是迭代器，只是又封装了一次
            sop(str);    // 其实这个升级，是简化书写
        }
        
        // 如果上面的ArrayList<>中没有使用泛型，那么要写成 for (Object obj : al)才能使用
        sop("ArrayList<>无泛型，使用 for (Objcet obj : all)");
        ArrayList all = new ArrayList();
        all.add("q");
        all.add("w");
        all.add("x");
        for (Object str : all) {
            sop(str);
        }
        
        /* ---------------- 高级for Map ---------------------------- */
        HashMap<Integer, String> hm = new HashMap<Integer, String>();
        hm.put(1, "a");
        hm.put(2, "b");
        hm.put(3, "c");
        
        // 使用keySet取出
        sop("使用keySet取出：");
        Set<Integer> keySet = hm.keySet();
        for (Integer key : keySet) {
            sop(key + " -> " + hm.get(key));
        }
        
        // 使用entrySet取出
        sop("使用entrySet取出：");
        Set<HashMap.Entry<Integer, String>> entrySet = hm.entrySet();
        for (HashMap.Entry<Integer, String> entry : entrySet) {
            sop(entry.getKey() + " -> " + entry.getValue());
        }
        // 上面的代码还可以简化为这样，但是直接在后面写hm.entrySet()感觉会存在效率低下的问题
        for (HashMap.Entry<Integer, String> me : hm.entrySet()) {
            sop(me.getKey() + " ----> " + me.getValue());            
        }
    }

    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
