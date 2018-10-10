package southday.java.acm.nowcoder.jzoffer;

import java.util.Stack;

/* 用两个栈实现队列 
用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
*/


public class JZOffer_05_Solution_01 {
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();
    int select = 1; // 1, 2 分别代表stack1, stack2, 表示元素存储的stack
    int except = 0; // 0, 1 分别代表期待push，期待pop
    
    public void push(int node) {
        if (except == 0) {
            if (select == 1) {
                stack1.push(node);
            } else {
                stack2.push(node);
            }
        } else {
            if (select == 1) {
                while (!stack1.empty()) {
                    stack2.push(stack1.pop());
                }
                select = 2;
                stack2.push(node);
            } else {
                while (!stack2.empty()) {
                    stack1.push(stack2.pop());
                }
                select = 1;
                stack1.push(node);
            }
            except = 0;
        }
    }
    
    public int pop() {
        if (except == 0) {
            except = 1;
            if (select == 1) {
                while (!stack1.empty()) {
                    stack2.push(stack1.pop());
                }
                select = 2;
                return stack2.pop();
            } else {
                while (!stack2.empty()) {
                    stack1.push(stack2.pop());
                }
                select = 1;
                return stack1.pop();
            }
        } else {
            return select == 1 ? stack1.pop() : stack2.pop();
        }
    }
    
    // --------------------------------------------------
    // 以下为测试
    public static void main(String[] args) {
        JZOffer_05_Solution_01 queue = new JZOffer_05_Solution_01();
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
