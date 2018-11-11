package southday.java.basic.datastructure.rbtree;

import java.util.Comparator;

/**
 * 红黑树 - 《算法导论》（第3版）第13章
 * @author southday
 * @date 2018年10月15日
 * @param <E> {@code E extends Comparable<E>}
 */
public class RedBlackTree<E extends Comparable<E>> {
    public Node<E> root;
    public final Node<E> nil;
    private static final Color RED = Color.RED;
    private static final Color BLACK = Color.BLACK;
    private final Comparator<E> comparator;
    
    public RedBlackTree() {
        this(null, null);
    }
    
    public RedBlackTree(E rootKey) {
        this(rootKey, null);
    }
    
    public RedBlackTree(Comparator<E> comparator) {
        this(null, comparator);
    }
    
    public RedBlackTree(E rootKey, Comparator<E> comparator) {
        nil = new Node<E>(BLACK, null, null, null, null);
        if (rootKey == null)
            root = nil;
        else root = new Node<E>(BLACK, rootKey, nil, nil, nil);
        this.comparator = comparator;
    }
    
    public static enum Color {
        RED, BLACK
    }
    
    public static class Node<E> {
        public Color color;
        public Node<E> left, right, parent;
        public E key;
        
        public Node(Color color, E key, Node<E> left, Node<E> right, Node<E> parent) {
            this.color = color;
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }
    
    public void insert(E key) {
        insert(new Node<E>(RED, key, nil, nil, nil));
    }
    
    private void insert(Node<E> z) {
        Node<E> y = nil;
        Node<E> x = root;
        while (x != nil) {
            y = x;
            if (compare(z.key, y.key) < 0)
                x = x.left;
            else
                x = x.right;
        }
        z.parent = y;
        if (y == nil)
            root = z;
        else if (compare(z.key, y.key) < 0)
            y.left = z;
        else
            y.right = z;
        z.left = nil;
        z.right = nil;
        z.color = RED;
        insertFixup(z);
    }
    
    private void insertFixup(Node<E> z) {
        while (z.parent.color == RED) {
            if (z.parent == z.parent.parent.left) {
                Node<E> y = z.parent.parent.right;
                if (y.color == RED) {
                    z.parent.color = BLACK;
                    y.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                } else if (z == z.parent.right) {
                    z = z.parent;
                    leftRotate(z);
                } else {
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rightRotate(z.parent.parent);
                }
            } else {
                Node<E> y = z.parent.parent.left;
                if (y.color == RED) {
                    z.parent.color = BLACK;
                    y.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                } else if (z == z.parent.left) {
                    z = z.parent;
                    rightRotate(z);
                } else {
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }
    
    private void leftRotate(Node<E> x) {
       Node<E> y = x.right;
       x.right = y.left;
       if (y.left != nil)
           y.left.parent = x;
       y.parent = x.parent;
       if (x.parent == nil)
           root = y;
       else if (x == x.parent.left)
           x.parent.left = y;
       else
           x.parent.right = y;
       y.left = x;
       x.parent = y;
    }
    
    private void rightRotate(Node<E> x) {
        Node<E> y = x.left;
        x.left = y.right;
        if (y.right != nil)
            y.right.parent = x;
        y.parent = x.parent;
        if (x.parent == nil)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.right = x;
        x.parent = y;
    }
    
    public boolean delete(E key) {
        Node<E> x = search(root, key);
        if (x == nil)
            return false;
        delete(x);
        return true;
    }
    
    private void transplant(Node<E> u, Node<E> v) {
        if (u.parent == nil)
            root = v;
        else if (u == u.parent.left)
            u.parent.left = v;
        else
            u.parent.right = v;
        v.parent = u.parent;
    }
    
    private void delete(Node<E> z) {
        Node<E> y = z, x = null;
        Color originalColorOfY = y.color;
        if (z.left == nil) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == nil) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            originalColorOfY = y.color;
            x = y.right;
            if (y.parent == z)
                x.parent = y;
            else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (originalColorOfY == BLACK)
            deleteFixup(x);
    }
    
    private void deleteFixup(Node<E> x) {
        while (x != root && x.color == BLACK) {
            if (x == x.parent.left) {
                Node<E> w = x.parent.right;
                if (w.color == RED) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == BLACK && w.right.color == BLACK) {
                    w.color = RED;
                    x = x.parent;
                } else if (w.right.color == BLACK) {
                    w.left.color = BLACK;
                    w.color = RED;
                    rightRotate(w);
                    w = x.parent.right;
                } else {
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                Node<E> w = x.parent.left;
                if (w.color == RED) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == BLACK && w.left.color == BLACK) {
                    w.color = RED;
                    x = x.parent;
                } else if (w.left.color == BLACK) {
                    w.right.color = BLACK;
                    w.color = RED;
                    leftRotate(w);
                    w = x.parent.left;
                } else {
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.left.color = BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        } // while
        x.color = BLACK;
    }
    
    public Node<E> search(Node<E> x, E key) {
        while (x != nil && compare(key, x.key) != 0)
            x = compare(key, x.key) < 0 ? x.left : x.right;
        return x;
    }
    
    public Node<E> minimum(Node<E> x) {
        while (x.left != nil)
            x = x.left;
        return x;
    }
    
    public Node<E> maximum(Node<E> x) {
        while (x.right != nil)
            x = x.right;
        return x;
    }
    
    private int compare(E o1, E o2) {
        return comparator == null ? o1.compareTo(o2) : comparator.compare(o1, o2);
    }
}
