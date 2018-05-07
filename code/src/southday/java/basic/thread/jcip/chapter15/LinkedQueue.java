package southday.java.basic.thread.jcip.chapter15;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 程序清单 15-7 Michael-Scott(Michael and Scott, 1996)非阻塞算法中的插入算法<p>
 * 在<code>ConcurrentLinkedQueue</code>中使用的正是该算法
 * @author southday
 * @date 2018年5月2日
 * @param <E>
 */
public class LinkedQueue<E> {
    private static class Node<E> {
        final E item;
        final AtomicReference<Node<E>> next;
        
        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<Node<E>>(next);
        }
    }
    
    private final Node<E> dummy = new Node<E>(null, null);
    private final AtomicReference<Node<E>> head = new AtomicReference<Node<E>>(dummy);
    private final AtomicReference<Node<E>> tail = new AtomicReference<Node<E>>(dummy);
    
    public boolean put(E item) {
        Node<E> newNode = new Node<E>(item, null);
        while (true) {
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();
            if (curTail == tail.get()) {
                if (tailNext != null) {
                    // 队列处于中间状态，推进尾节点（帮助其他 线程完成）
                    tail.compareAndSet(curTail, tailNext);
                } else { // 直到队列处于稳定状态，开始进行自己的put工作
                    // 处于稳定状态，尝试插入新节点
                    if (curTail.next.compareAndSet(null, newNode)) {
                        // 插入操作成功，尝试推进尾节点
                        tail.compareAndSet(curTail, newNode);
                        return true;
                    }
                }
            }
        }
    }
    
    /* 技巧：
     * 1.即使在一个包含多个步骤的更新操作中，也要确保数据结构总是处于一致的状态
     * 2.如果当B到达时发现A正在修改数据结构，那么在数据结构中应该有足够多的信息，使得B能完成A的更新操作
     * 
     */
}

/*
 * 在实际情况中，ConcurrentLinkedQueue没有使用原子引用来表示每个Node，而是使用普通的volatile类型引用，
 * 并通过基于反射的AtmoicReferenceFieldUpdater来进行更新，如下：
private class Node<E> {
    private final E item;
    private volatile Node<E> next;
    
    public Node(E item) {
        this.item = item;
    }
}

private static AtmoicReferenceFieldUpdater<Node, Node> nextUpdater
        = AtomicReferenceFieldUpdater.newUpdater(Node.class, Node.class, "next");
 */
