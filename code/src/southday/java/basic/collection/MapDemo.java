package southday.java.basic.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* Map 集合的两种取出方式：
 * Map 集合的取出原理——将Map集合转换为Set集合，然后根据迭代器取出
 * 方法：
 *  keySet()：将Map中所有的键存入set集合，而Set集合具备迭代器，
 *               所以可以通过迭代方式取出所有的键，然后在根据get方法获取每一个键对应的值
 *  entrySet()：将Map集合中的映射关系存入到Set集合中，而这个关系的数据类型就是Map.Entry
 *  
 * Map.Entry<K,V>
 * 其实Entry<K,V>也是一个接口，它是Map接口的一个内部接口，就和内部类一样
 * 嵌套类摘要 ：
        static interface Map.Entry<K,V> 映射项（键-值对）。 
 * 定义：
 * interface Map {
 *         public static interface Entry {        //静态 static
 *             public abstract Object getKey();
 *             public abstract Object getValue();
 *         }
 * }
 * 实现：    //内部类实现了内部方法
 * class HashMap implements Map {
 *         class HashMapEntry implements Map.Entry {
 *             public Object getKey();
 *             public Object getValue();
 *         }
 * }
 */

public class MapDemo {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("01", "demo1");
        map.put("02", "demo2");
        map.put("03", "demo3");
        map.put("04", "demo4");
        
        System.out.println("------- 普通迭代  -----------");
        keySetMethod(map);
        System.out.println("---keySet()↑ -- entrySet()↓");
        entrySetMetod(map);
        System.out.println("------- 高级for循环迭代  ---------");
        keySetMethod2(map);
        System.out.println("---keySet()↑ -- entrySet()↓");
        entrySetMethod2(map);
        
    }

    public static void keySetMethod(Map<String, String> map) {
        // (1)先获取Map集合中的所有键的Set集合
        Set<String> keySet = map.keySet();

        // (2)创建Set集合迭代器
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String key = it.next();
            // (3)有了键就可以通过Map集合的get()方法获取其对应的值
            String value = map.get(key);
            System.out.println(key + ":" + value);
        }
    }
    // 高级for循环   for (数据类型  变量名 : 被遍历的Collection对象) {...}
    public static void keySetMethod2(Map<String, String> map) {
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            System.out.println(key + " -> " + map.get(key));
        }
    }
    
    public static void entrySetMetod(Map<String, String> map) {
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        Iterator<Map.Entry<String, String>> it = entrySet.iterator();
        while(it.hasNext()) {
            Map.Entry<String, String> me = it.next();
            String key = me.getKey();
            String value = me.getValue();
            System.out.println(key + ":" + value);
        }
    }
    public static void entrySetMethod2(Map<String, String> map) {
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> me : entrySet) {
            System.out.println(me.getKey() + " -> " + me.getValue());
        }
        
        // 或者是这样简写，虽然我个人觉得效率不高
        for (Map.Entry<String, String> me : map.entrySet()) {
            System.out.println(me.getKey() + " ---> " + me.getValue());
        }
    }
}
