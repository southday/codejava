package southday.java.acm.nowcoder.jzoffer;

/* 二叉搜索树与双向链表 
输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向。 

二叉排序树线索化，利用中序遍历，使用队列，第一个元素left = null，最后一个元素right = null
*/

import java.util.Queue;
import java.util.LinkedList;

public class JZOffer_26_Solution_01 {
    Queue<TreeNode> queue = new LinkedList<TreeNode>();
    
    public TreeNode Convert(TreeNode pRootOfTree) {
        if (pRootOfTree == null) return null;
        inOrder(pRootOfTree);
        TreeNode pre = null;
        TreeNode node = queue.peek();
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            cur.left = pre;
            if (pre != null) {
                pre.right = cur;
            }
            pre = cur;
        }
        return node;
    }
    
    private void inOrder(TreeNode root) {
        if(root != null) {
            inOrder(root.left);
            queue.add(root);
            inOrder(root.right);
        }
    }
}
