package southday.java.basic.collection;
import java.util.*;

/*
  LinkedList 特有方法：
  addFirst();
  addLast();
  
  getFirst();    // 获取元素，但不删除元素
  getLast();    // 如果集合中没有元素，会出现NoSuchElementExecption();
  
  removeFirst(); // 获取元素，但元素被删除，长度减少
  removeLast();     // 如果集合中没有元素，会出现NoSuchElementExecption();
  
  在JDK1.6 版本中出现了以下替代方法:
  offerFirst();
  offerLast();
  
  peekFirst();    // 如果集合中没有元素，会返回NULL
  peekLast();
  
  pollFirst();    // 如果集合中没有元素，会返回NULL
  pollLast();
 */
public class LinkedListDemo {
    public static void main(String[] args){
        LinkedList ls = new LinkedList();
        ls.addFirst("java-1");
        ls.addFirst("java-2");
        ls.addFirst("java-3");
        ls.addFirst("java-4");
        sop(ls);    // 都是添加到头部，头部被不断更新，打印出来时是倒序的
        
        sop(ls.getFirst());    // java-4
        sop(ls.getFirst()); // java-4
        
        ls.clear();
        
        ls.addLast("haha-1");
        ls.addLast("haha-2");
        ls.addLast("haha-3");
        ls.addLast("haha-4");
        sop(ls);    // 都是添加到尾部，尾部被不断更新，打印出来时是顺序的
        
        // sop(ls.removeFirst()); //haha-1，删一个少一个
        // sop(ls.removeFirst()); //haha-2
        
        // 不用迭代器Iterator也可以取，只是元素不会相继删除
        while(!ls.isEmpty()) {
            sop(ls.removeLast()); // 倒序输出
        }
        sop("ls.isEmpty() = "+ls.isEmpty());
    }
    
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
