package southday.java.acm.nowcoder.jzoffer;

import java.util.ArrayList;

/* 二叉搜索树的第k个结点 
给定一棵二叉搜索树，请找出其中的第k小的结点。例如， （5，3，7，2，4，6，8）    中，按结点数值大小顺序第三小结点的值为4。

虽然通过了测试，但不是我想要的算法，这个算法存在多余运算，无论k是多少，都要把树遍历完
我想要的是和k有关的算法，点到为止
*/

public class JZOffer_62_Solution_01 {
    
    TreeNode KthNode(TreeNode pRoot, int k) {
        if (pRoot == null || k <= 0) return null;
        ArrayList<TreeNode> ls = new ArrayList<TreeNode>();
        inOrder(pRoot, ls);
        if (k-1 >= ls.size()) return null;
        return ls.get(k-1);
    }
    
    private void inOrder(TreeNode t, ArrayList<TreeNode> ls) {
        if (t != null) {
            if (t.left != null) inOrder(t.left, ls);
            ls.add(t);
            if (t.right != null) inOrder(t.right, ls);
        }
    }
}
