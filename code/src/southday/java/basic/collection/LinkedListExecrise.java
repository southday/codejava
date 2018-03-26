package southday.java.basic.collection;
import java.util.*;
    
// 用用链表LinkedList模拟堆栈结构【FILO】和队列结构【FIFO】

class MyStack {
    private LinkedList ls;
    MyStack() {
        this.ls = new LinkedList();
    }
    
    // 新元素入栈
    public void push(Object e) {
        ls.offerFirst(e);
    }
    // 元素出栈
    public Object pop() {
        return ls.pollFirst();
    }
    
    // 删除栈
    public void removeStack() {
        ls.clear();
    }
    
    // 打印栈元素信息----其实不应该有
    public void printStack() {
        ListIterator li = ls.listIterator();
        while(li.hasNext()) {
            System.out.println(li.next());
        }
    }
    
    // 判断栈是否为空
    public boolean isEmpty() {
        return ls.isEmpty();
    }
}

class MyQueue {
    private LinkedList ls;
    MyQueue() {
        ls = new LinkedList();
    }
    
    //新元素入队列
    public void EnQueue(Object e) {
        ls.offerLast(e);
    }
    
    //元素出队列
    public Object DeQueue() {
        return ls.pollFirst();
    }
    
    //删除队列
    public void removeQueue() {
        ls.clear();
    }
    
    //打印队列元素信息----其实不应该有
    public void printQueue() {
        ListIterator li = ls.listIterator();
        while(li.hasNext()) {
            System.out.println(li.next());
        }
    }
    
    //判断队列是否为空
    public boolean isEmpty() {
        return ls.isEmpty();
    }
}


public class LinkedListExecrise {
    public static void main(String[] args) {
        
        //关于栈的测试
        MyStack ms = new MyStack();
        ms.push("java-1");
        ms.push("java-2");
        ms.push(1234);
        ms.printStack();    //打印栈中元素
        
        sop("ms.pop() = "+ms.pop());
        sop("ms.pop() = "+ms.pop());
        ms.printStack();
        sop("ms.isEmpty() = "+ms.isEmpty());
        ms.removeStack();    //清空栈
        sop("After removeStack, ms.isEmpty() = "+ms.isEmpty());
        
        //关于队列的测试
        MyQueue mq = new MyQueue();
        mq.EnQueue("yoyo-1");
        mq.EnQueue("yoyo-2");
        mq.EnQueue(5678);
        mq.printQueue();  //打印队列中元素
        
        sop("mq.DeQueue() = "+mq.DeQueue());
        sop("mq.DeQueue() = "+mq.DeQueue());
        mq.printQueue();
        sop("mq.isEmpty() = "+mq.isEmpty());
        mq.removeQueue();
        sop("After removeQueue, mq.isEmpty() = "+mq.isEmpty());
    }
    
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
