package southday.java.basic.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* "sdlkfjweklthnwerlxz"获取字符串中每个字符出现的次数
 * 希望打印结果为：a1(),a2(),...,an()【()中显示字母ai出现的次数】
 * 每个字母都有对应的次数，说明字母和次数之间存在一一对应的关系
 * 当发现有映射关系时，可以使用Map集合，因为Map集合中存放的就是映射关系
 */

public class MapTest2 {
    public static void main(String[] args) {
        String str = "sdlkfjwe'k/slth@rdf$nhwe^srlxz";
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        int len = str.length();
        System.out.println(str);
        System.out.println("各个字母出现的次数：");
        
        /* 毕老师的做法是将字符串先转换成字符数组，这样就不需要用charAt(index)函数了
         * char[] chs = str.toCharArray();
         * for(int i=0; i<chs.length; i++) { }
         */
        
        /*  （1）方法一：这个方法需要两次遍历字符串，比较麻烦！！
        for(int index=0; index<len; index++) {
            char key = str.charAt(index);
            map.put(Character.valueOf(key), Integer.valueOf(0));
        }
        for(int index=0; index<len; index++) {
            char key = str.charAt(index);
            int num = map.get(key).intValue();
            num ++;
            map.put(Character.valueOf(key), Integer.valueOf(num));
        }
        */
        
        // 我们利用put()方法的特性：如果键相同，返回旧值的方法来实现一次遍历解决问题
        /*  （2）方法二：可能会用到两次put()方法，效率低
        for(int index=0; index<len; index++) {
            Character key = Character.valueOf(str.charAt(index));
             put_return = map.put(key, 1);    //put()返回V类型（这里V为Integer）
            if(put_return!=null) {
                put_return ++;    //这里涉及到Integer对象的自动拆箱，装箱过程
                map.put(key, put_return);
            }
        }
        */
        //  （3）方法三：使用count来计数，只是用一次put()方法
        int count = 0;//这里不把count设置到for(){}里面是为了减少内存分配与系统开销，不然每次都要new，然后再释放
        for(int index=0; index<len; index++) {
            Character key = Character.valueOf(str.charAt(index));
            if(!(key>='a' && key<='z' || key>='A' && key<='Z'))
                continue;    //对于特殊字符（非字母的），我们跳过不计数！
            
            Integer value = map.get(key);
            if(value!=null)
                count = value;
            count ++;
            map.put(key, count);
            count = 0;    
        }
                
        System.out.println(map);
        Set<Character> keySet = map.keySet();
        Iterator<Character> it = keySet.iterator();
        while(it.hasNext()) {
            Character key = it.next();
            int num = map.get(key);
            System.out.println(key.charValue() + "(" + num + ");");
        }
        
        //采用缓冲区技术实现输出： a(),b(),...,x()的形式
        System.out.println("采用字符缓冲区技术StringBuilder：");
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<Character, Integer>> entrySet = map.entrySet();
        Iterator<Map.Entry<Character, Integer>> it2 = entrySet.iterator();
        while(it2.hasNext()) {
            Map.Entry<Character, Integer> nmap = it2.next();
            Character key = nmap.getKey();
            Integer num = nmap.getValue();
            sb.append(key + "(" + num + ") ");
        }
        System.out.println(sb.toString());
    }
}
