package southday.java.basic.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* 问题描述：
 * 每一个外星人都有相应的归属地，外星人Aline,归属地String
 * 外星人属性：姓名，年龄
 * 注意：姓名和年龄相同视为同一个外星人，保证外星人的唯一性
 */

public class MapTest {
    public static void main(String[] args) {
        Map<Alien, String> alienMap = new HashMap<Alien, String>();
        // 添加外星人
        alienMap.put(new Alien("ET1", 15), "Mars");
        alienMap.put(new Alien("ET2", 16), "Earth");
        alienMap.put(new Alien("ET3", 17), "Mercury");
        alienMap.put(new Alien("ET2", 16), "Mars"); // 姓名与年龄与来自"Earth"的外星人重复了，新值覆盖旧值
 
        // 输出
        keySet_Method(alienMap); // 方法一，使用keySet()
        entrySet_Method(alienMap); // 方法二，使用entrySet()
    }

    public static void keySet_Method(Map<Alien, String> alienMap) {
        System.out.println("方法一：");
        Set<Alien> keySet = alienMap.keySet();
        Iterator<Alien> it = keySet.iterator();
        while (it.hasNext()) {
            Alien et = it.next();
            String address = alienMap.get(et);
            System.out.println("<" + et.getName() + "," + et.getAge() + ">" + ":" + address);
        }
        System.out.println("-------------------------");
    }

    public static void entrySet_Method(Map<Alien, String> alienMap) {
        System.out.println("方法二：");
        Set<Map.Entry<Alien, String>> entrySet = alienMap.entrySet();
        Iterator<Map.Entry<Alien, String>> it = entrySet.iterator();
        while (it.hasNext()) {
            Map.Entry<Alien, String> map = it.next();
            Alien et = map.getKey();
            String address = map.getValue();
            System.out.println("<" + et.getName() + "," + et.getAge() + ">" + ":" + address);
        }
        System.out.println("-------------------------");
    }
}

class Alien implements Comparable<Alien>{
    private String name;
    private int age;

    Alien(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int hashCode() {
        //主要是还是要让其产生Hash碰撞，然后访问equals方法（根据题目：姓名年龄相同者为同一人）
        return name.hashCode()+age;    //这个可以自定义，如果不这样的话，因为地址不同，估计都不会访问到equals方法
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof Alien))
            throw new ClassCastException("类型不匹配");
            //return false;
        Alien alien = (Alien) obj;
        return (this.name.equals(alien.name) && this.age == alien.age);
    }
    
    //外星人对象有可能被存入二叉树TreeSet，如果存入二叉树，则得先让外星人对象具备可比性（存在一个自然顺序）
    public int compareTo(Alien a) {
        int num = (new Integer(this.age).compareTo(new Integer(a.age)));
        
        if(num==0)
            return this.name.compareTo(a.name);
        return num;
    }
}
