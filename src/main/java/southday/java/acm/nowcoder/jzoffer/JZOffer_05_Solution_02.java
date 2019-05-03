package southday.java.acm.nowcoder.jzoffer;

/* 思路：
1) 设置2个栈：inStack(入栈)，outStack(出栈)
2) add(x)时，inStack.push(x)
3) poll()时：
    a) 若outStack不为空，则直接outStack.pop()；
    b) 若outStack为空，则先把inStack中的元素push到outStack，然后再 outStack.pop()
 */

import java.util.Stack;

/**
 * 使用2个栈来实现队列
 * @author southday
 * @date 2019/5/3
 */
public class JZOffer_05_Solution_02<E> {
    Stack<Integer> stack1 = new Stack<Integer>(); // inStack
    Stack<Integer> stack2 = new Stack<Integer>(); // outStack

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        if (stack2.isEmpty())
            while (!stack1.isEmpty())
                stack2.push(stack1.pop());
        return stack2.pop();
    }

    public static void main(String[] args) {
        JZOffer_05_Solution_02 queue = new JZOffer_05_Solution_02();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        System.out.println(queue.pop()); // 1
        System.out.println(queue.pop()); // 2
        queue.push(4);
        System.out.println(queue.pop()); // 3
        queue.push(5);
        System.out.println(queue.pop()); // 4
        System.out.println(queue.pop()); // 5
    }
}
