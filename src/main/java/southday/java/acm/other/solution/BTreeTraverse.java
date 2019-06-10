package southday.java.acm.other.solution;

import southday.java.acm.leetcode.common.LeetCodeUtil;
import southday.java.acm.leetcode.common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉树中序、先序、后序、层序遍历的实现
 * @author southday
 * @date 2019/3/15
 */
public class BTreeTraverse {
    /**
     * 二叉树先序遍历-非递归实现
     * @param root
     */
    public static void preOrderNonRec(TreeNode root) {
        if (root == null)
            return;
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode top = stack.pop();
            System.out.print(top.val + ", ");
            if (top.right != null) {
                stack.push(top.right);
            }
            if (top.left != null) {
                stack.push(top.left);
            }
        }
    }

    /**
     * 二叉树的中序遍历-非递归实现
     * @param root
     */
    public static void inOrderNonRec(TreeNode root) {
        if (root == null)
            return;
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        boolean visitedLeft = false;
        while (!stack.isEmpty()) {
            TreeNode top = stack.peek();
            if (!visitedLeft && top.left != null) {
                stack.push(top.left);
            } else {
                top = stack.pop();
                System.out.print(top.val + ", ");
                visitedLeft = true;
                if (top.right != null) {
                    stack.push(top.right);
                    visitedLeft = false;
                }
            }
        }
    }

    /**
     * 二叉树后序遍历-非递归实现
     * @param root
     */
    public static void postOrderNonRec(TreeNode root) {
        if (root == null)
            return;
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        TreeNode lastPopNode = null;
        while (!stack.isEmpty()) {
            TreeNode top = stack.peek();
            if ((top.left == null && top.right == null)
                || (lastPopNode == top.left && top.right == null)
                || lastPopNode == top.right) {
                lastPopNode = stack.pop();
                System.out.print(lastPopNode.val + ", ");
            } else {
                if (lastPopNode != top.right && top.right != null) {
                    stack.push(top.right);
                }
                if (lastPopNode != top.left && top.left != null) {
                    stack.push(top.left);
                }
            }
        }
    }

    /**
     * 层序遍历，标识每个结点所处层次（根节点层次为1）
     * @param root
     */
    public static void levelOrder(TreeNode root) {
        if (root == null)
            return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode lastNode = root; // 本层最后一个结点
        TreeNode recentNode = root; // 最近加入队列的结点
        int level = 1; // 层级，默认根节点层级为1
        while (!queue.isEmpty()) {
            TreeNode nd = queue.poll();
            System.out.printf("%d(%d) ", nd.val, level);
            if (nd.left != null) {
                queue.add(nd.left);
                recentNode = nd.left;
            }
            if (nd.right != null) {
                queue.add(nd.right);
                recentNode = nd.right;
            }
            if (nd == lastNode) {
                lastNode = recentNode;
                level++;
            }
        }
    }
    /* 关于层序遍历的一些扩展，如下面的题：
    按左视角打印下面的二叉树：
         ->       3
                /   \
         ->    5     1
             /  \      \
         -> 6    2       8
                /
         ->    7
     从左边观察这棵二叉树，可以看到的结点是：3 5 6 7
     解题思路：使用层序遍历，从右往左添加孩子结点，输出每层的最后一个结点；
     */

    public static void main(String[] args) {
//        String str = "3,5,6,#,#,2,7,#,#,4,#,#,1,0,#,#,8,#,#";
//        String str = "3,5,6,#,#,2,7,#,#,#,1,0,#,#,8,#,#";
        String str = "3,5,6,#,#,2,7,#,#,#,1,#,8,#,#";
        TreeNode root = LeetCodeUtil.generateByPreOrder(str);

        System.out.print("preOrder: ");
        BTreeTraverse.preOrderNonRec(root);
        System.out.println();

        System.out.print("inOrder: ");
        BTreeTraverse.inOrderNonRec(root);
        System.out.println();

        System.out.print("postOrder: ");
        BTreeTraverse.postOrderNonRec(root);
        System.out.println();

        System.out.print("levelOrder: ");
        BTreeTraverse.levelOrder(root);
        System.out.println();
    }
}
