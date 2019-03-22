package southday.java.acm.leetcode.solution;

import southday.java.acm.leetcode.common.LeetCodeUtil;
import southday.java.acm.leetcode.common.TreeNode;

import java.util.LinkedList;

/**
 * 自己写的非递归版本实现<br/>
 * Time Limit Exceeded ! wtf!
 * 236. Lowest Common Ancestor of a Binary Tree https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
 * @author southday
 * @date 2019/3/15
 */
public class LeetCode_0236_Solution_02 {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null)
            return null;
        /*
        一开始以为是用Stack（同步）的原因导致Time Limit，结果换成LinkedList后也是一样；
         */
        LinkedList<TreeNode> stack = new LinkedList<>();
        LinkedList<Integer> foundLCAStack = new LinkedList<>();
        stack.push(root);
        foundLCAStack.push(0);
        TreeNode lastPopNode = null;
        while (!stack.isEmpty()) {
            TreeNode top = stack.peek();
            if (top.right == lastPopNode || (top.left == null && top.right == null)) {
                lastPopNode = stack.pop();
                int found = foundLCAStack.pop();
                if (lastPopNode == p || lastPopNode == q)
                    found++;
                if (found == 2)
                    return lastPopNode;
                foundLCAStack.push(foundLCAStack.pop() + found);
            } if (top.left != lastPopNode && top.left != null) {
                stack.push(top.left);
                foundLCAStack.push(0);
            } else if (top.right != null) {
                stack.push(top.right);
                foundLCAStack.push(0);
            } else {
                lastPopNode = null;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String str = "3,5,6,#,#,2,7,#,#,4,#,#,1,0,#,#,8,#,#";
        TreeNode root = LeetCodeUtil.generateByPreOrder(str);
        TreeNode p = LeetCodeUtil.findTreeNode(root, 5);
        TreeNode q = LeetCodeUtil.findTreeNode(root, 4);
        LeetCode_0236_Solution_01 o = new LeetCode_0236_Solution_01();
        TreeNode lca = o.lowestCommonAncestor(root, p, q);
        System.out.println(lca.val);
    }
}
