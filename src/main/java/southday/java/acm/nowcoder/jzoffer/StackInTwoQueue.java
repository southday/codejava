package southday.java.acm.nowcoder.jzoffer;

/* 思路：
1) 2个队列：inq(入队列) outq(出队列)，入队列和出队列是两种角色
2) push(x)时，先选择出 入队列inq，然后直接 inq.push(x)
3) pop()时，选择出 入队列inq 和 出队列outq：
    a) 若 inq.size() == 1，那么直接 inq.pop()，结束
    b) 若 inq.size() > 1，把 inq 中的元素（除了队尾元素）都 push 到 outq，然后 inq.pop()，最后需要 inq 和 outq 角色互换
 */

import java.util.LinkedList;
import java.util.Queue;

/**
 * 使用2个队列来实现栈
 * @author southday
 * @date 2019/5/3
 */
public class StackInTwoQueue<E> {
    private Queue<E> q1, q2;
    private int inqRole; // 默认 q1 为入队列角色，inqRole 取值{1, 2}

    public StackInTwoQueue() {
        q1 = new LinkedList<>();
        q2 = new LinkedList<>();
        inqRole = 1;
    }

    public boolean isEmpty() {
        return q1.isEmpty() && q2.isEmpty();
    }

    public E push(E x) {
        Queue<E> inq = selectInQueue();
        inq.add(x);
        return x;
    }

    public E pop() {
        if (isEmpty())
            return null;
        Queue<E> inq = selectInQueue();
        Queue<E> outq = selectOutQueue();
        while (inq.size() > 1)
            outq.add(inq.poll());
        inqRole = inqRole == 1 ? 2 : 1;
        return inq.poll();
    }

    private Queue<E> selectInQueue() {
        return inqRole == 1 ? q1 : q2;
    }

    public Queue<E> selectOutQueue() {
        return inqRole == 1 ? q2 : q1;
    }

    public static void main(String[] args) {
        StackInTwoQueue<Integer> stack = new StackInTwoQueue<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.pop()); // 3
        System.out.println(stack.pop()); // 2
        stack.push(4);
        System.out.println(stack.pop()); // 4
        stack.push(5);
        stack.push(6);
        System.out.println(stack.pop()); // 6
        System.out.println(stack.pop()); // 5
        System.out.println(stack.pop()); // 1
        System.out.println(stack.pop()); // null
    }
}
