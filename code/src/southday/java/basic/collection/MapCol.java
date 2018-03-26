package southday.java.basic.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* 接口 MAP<K,V>---K:key && V:value
 * Map集合：首先，该集合存储键值对，是一对一对往里存，而且要保证键的唯一性
 * （1）添加
 * V    |--put(K key,V value)
 * void    |--putAll(Map<? extends K,? extends V> m) 
 * 
 * （2）删除
 * void    |--clear()
 * V    |--remove(Object key)
 * 
 * （3）判断
 * boolean    |--containsKey(Object key)
 * boolean    |--containsValue(Object value)
 * boolean  |--isEmpty()
 * 
 * （4）获取
 *     V     |-- get(Object key)
 *     int |--size()
 *     Collection<V>         |-- values()
 *     Set<Map.Entry<K,V>> |-- entrySet()
 *  Set<K>  |--keySet()
 *  
 *  
 *  Map集合的小弟：
 *      |--Hashtable：底层是Hash表数据结构，不可以存入null作为键或值，该集合是线程同步的--jdk1.0
 *      |--HashMap：底层是Hash表数据结构，并允许使用出现null键和null值，该集合是不同步的--jdk1.2（效率高）
 *      |--TreeMap：底层是二叉树数据结构（红黑树），线程不同步，可以用于给Map集合中的键进行排序
 *  
 *  【和Set很像，其实Set集合底层就是使用了Map集合】
 */

public class MapCol {
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<String,String>();
        //（1）添加
        map.put("01","demo1");    //最开始键值对（键唯一）的时候put()方法返回的是null
        map.put("02","demo2");
        map.put("03","demo3");
        sop("print: " + map);    //{01=demo1, 02=demo2, 03=demo3}
        /* 【注意】：
         *         ① 当你存了相同键时，新的值会替换老的值
         *         ② put方法会将你对应的老的值返回回来
         * 如：
         * 我又添加了个键值对：map.put("01","demo111");    //这时返回 demo1
         * 这时输出的map为：{01=demo111, 02=demo2, 03=demo3}
         */
        sop("map.put(\"01\",\"demo111\"): " + map.put("01","demo111"));    //返回 demo1（01对应的老值 demo1）
        sop("map = " + map);    //{01=demo111, 02=demo2, 03=demo3}
        
        //（2）删除
        map.remove("01");
        sop("remove(\"01\"): " + map);    //{02=demo2, 03=demo3}
//        map.clear();
//        sop("clear(): " + map);    //{}
        
        //（3）判断---{02=demo2, 03=demo3}
        sop("containsKey(\"02\"): " + map.containsKey("02"));    //true
        sop("containsKey(\"08\"): " + map.containsKey("08"));    //false
        sop("containsValue(\"demo3\"): " + map.containsValue("demo3"));    //true
        sop("containsValue(\"demo5\"): " + map.containsValue("demo5"));    //false
        sop("isEmpt() : " + map.isEmpty());    //false
        
        //（4）获取
        sop("map.get(\"02\"): " + map.get("02"));    //demo2
        sop("map.get(\"08\"): " + map.get("08"));    //null (不存在)
        /*【注意】：
         * get(Object key)用来获取key对应的值(value)，如果存在则返回该值，否则返回null。
         * 这样一来，也可以用get()方法来判断某个元素是否存在。
         * 但需要注意的是，HashMap集合中是允许使用null键和null值的，如果出现 map.put("04",null)，
         * 最后使用map.get("04")返回的也是null，但这个键值对却是存在的！！
         */
        map.put("04", null);
        sop("map.get(\"04\"): " + map.get("04"));    //null (键值对是存在的)
        
        Collection<String> coll = map.values();    //返回此映射包含的值的 Collection 视图
        sop("coll: " + coll);    //[demo2, demo3, null]
    }
    
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
