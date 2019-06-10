package southday.java.acm.other.solution;

import southday.java.acm.leetcode.common.LeetCodeUtil;
import southday.java.acm.leetcode.common.TreeNode;

import java.util.Stack;

/**
 * 二叉树中序、先序、后序遍历的实现
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
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
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
        Stack<TreeNode> stack = new Stack<>();
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
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode lastPopNode = null;
        while (!stack.empty()) {
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
    }
}
