package southday.java.acm.nowcoder.jzoffer;

import java.util.Stack;
import java.util.LinkedList;

/* 包含min函数的栈 
定义栈的数据结构，请在该类型中实现一个能够得到栈中所含最小元素的min函数（时间复杂度应为O（1））。
*/
public class JZOffer_20_Solution_01 {
    Stack<Integer> stack = new Stack<Integer>();
    LinkedList<Integer> list = new LinkedList<Integer>();

    public void push(int node) {
        stack.push(node);
        if (list.isEmpty()) {
            list.add(node);
        } else {
            int i = -1, len = list.size();
            while (++i < len && node > list.get(i));
            list.add(i, node);
        }
    }
    
    public void pop() {
        list.remove(stack.pop());
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int min() {
        return list.peek();
    }
    
    public static void main(String[] args) {
        JZOffer_20_Solution_01 obj = new JZOffer_20_Solution_01();
        obj.push(3);
        System.out.println(obj.min());
        obj.push(4);
        System.out.println(obj.min());
        obj.push(2);
        System.out.println(obj.min());
    }
}
