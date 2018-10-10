package southday.java.acm.nowcoder.jzoffer;

import java.util.Stack;

/* 二叉搜索树的第k个结点 
给定一棵二叉搜索树，请找出其中的第k小的结点。例如， （5，3，7，2，4，6，8）    中，按结点数值大小顺序第三小结点的值为4。

使用中序遍历非递归算法实现
*/

public class JZOffer_62_Solution_02 {
    
    TreeNode KthNode(TreeNode pRoot, int k) {
        if (pRoot == null || k <= 0) return null;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(pRoot);
        boolean hasVisitedLeftChild = false;
        while (!stack.isEmpty()) {
            TreeNode t = stack.peek();
            if (!hasVisitedLeftChild && t.left != null) {
                stack.push(t.left);
            } else {
                stack.pop();
                if (--k == 0) return t;
                hasVisitedLeftChild = true;
                if (t.right != null) {
                    stack.push(t.right);
                    hasVisitedLeftChild = false;
                }
            }
        }
        return null;
    }
}
