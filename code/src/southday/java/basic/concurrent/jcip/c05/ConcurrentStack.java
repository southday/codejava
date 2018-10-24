package southday.java.basic.concurrent.jcip.c05;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 程序清单 15-6 使用Treiber算法（Treiber，1986）构造的非阻塞栈<p>
 * 基于CAS，非阻塞算法的特性：某项工作的完成具有不确定性，必须重新执行
 * @author southday
 * @date 2018年5月2日
 * @param <E>
 */
public class ConcurrentStack<E> {
    AtomicReference<Node<E>> top = new AtomicReference<Node<E>>();
    
    public void push(E item) {
        Node<E> newHead = new Node<E>(item);
        Node<E> oldHead;
        do {
            oldHead = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead, newHead));
    }
    
    public E pop() {
        Node<E> oldHead;
        Node<E> newHead;
        do {
            oldHead = top.get();
            if (oldHead == null) {
                return null;
            }
            newHead = oldHead.next;
        } while (!top.compareAndSet(oldHead, newHead));
        return oldHead.item;
    }
    
    
    private static class Node<E> {
        public final E item;
        public Node<E> next;
        
        public Node(E item) {
            this.item = item;
        }
    }
}
