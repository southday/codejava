package southday.java.basic.collection;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
/* 【Alien 类在 MapTest类中】
 * TreeMap(Comparator<? super K> comparator) 
 *     构造一个新的、空的树映射，该映射根据给定比较器进行排序
 */
public class TreeMapTest {
    
    public static void main(String[] args) {
        //Map<Alien,String> alienMap = new TreeMap<Alien,String>();
        Map<Alien,String> alienMap = new TreeMap<Alien,String>(new AlienCompare());//传自制比较器
        alienMap.put(new Alien("ET1", 15), "Mars");
        alienMap.put(new Alien("ET2", 18), "Earth");
        alienMap.put(new Alien("ET3", 17), "Saturn");
        alienMap.put(new Alien("ET4", 12), "Mercury");
        alienMap.put(new Alien("ET3", 14), "Earth");
        alienMap.put(new Alien("ET2", 16), "Mars"); // 姓名与年龄与来自"Earth"的外星人重复了，新值覆盖旧值
        
        //采用第二种方式取出值：会发现输出的结果按年龄由小→大排序，因为Alien类中已经实现了compareTo()方法
        Set<Map.Entry<Alien, String>> entrySet = alienMap.entrySet();
        Iterator<Map.Entry<Alien, String>> it = entrySet.iterator();
        while(it.hasNext()) {
            Map.Entry<Alien, String> map = it.next();
            Alien et = map.getKey();
            String address = map.getValue();
            System.out.println("<" + et.getName() + "," + et.getAge() + ">" + ":" + address);
        }
    }
}

class AlienCompare implements Comparator<Alien> {
    
    //实现，先按姓名排序，如果姓名相同再按年龄排序！
    public int compare(Alien et1, Alien et2) {
        int num = et1.getName().compareTo(et2.getName());
        if(num==0)
            return (new Integer(et1.getAge()).compareTo(new Integer(et2.getAge())));
        return num;
    }
}
