package southday.java.basic.datastructure.rbtree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import southday.java.basic.datastructure.rbtree.RedBlackTree.Node;

public class RedBlackTreeTest {
    
    public static void main(String[] args) {
        RedBlackTree<Integer> rbt = new RedBlackTree<Integer>();
        int arr[] = {41, 38, 31, 12, 19, 8};
        for (int a : arr) {
            rbt.insert(a);
            System.out.println("After insert(" + a + ")");
            sop(levelTraverse(rbt));
        }
        System.out.println();
        int vv[] = {8, 12, 19, 31, 38, 41};
        for (int v : vv) {
            rbt.delete(v);
            System.out.println("After delete(" + v + ")");
            sop(levelTraverse(rbt));
        }
    }
    
    public static <E extends Comparable<E>> ArrayList<Node<E>> levelTraverse(RedBlackTree<E> rbt) {
        ArrayList<Node<E>> ls = new ArrayList<Node<E>>();
        if (rbt == null || rbt.root == rbt.nil) return ls;
        Queue<Node<E>> queue = new LinkedList<Node<E>>();
        queue.add(rbt.root);
        while (!queue.isEmpty()) {
            Node<E> nd = queue.poll();
            ls.add(nd);
            if (nd.left != rbt.nil) queue.add(nd.left);
            if (nd.right != rbt.nil) queue.add(nd.right);
        }
        return ls;
    }
    
    public static <E extends Comparable<E>> void sop(ArrayList<Node<E>> ls) {
        for (Node<E> nd : ls) {
            System.out.print(nd.key + "(" + nd.color + ") ");
        }
        System.out.println("\n");
    }
}
