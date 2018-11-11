package southday.java.acm.nowcoder.jzoffer;

/* 二叉树的下一个结点 
给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。
*/

public class JZOffer_57_Solution_01 {
    
    public TreeLinkNode GetNext(TreeLinkNode pNode) {
        if (pNode == null) return null;
        TreeLinkNode rc = getPre(pNode.right);
        if (rc != null) return rc;
        return getFather(pNode);
    }
    
    private TreeLinkNode getPre(TreeLinkNode node) {
        if (node == null) return null;
        TreeLinkNode ln = getPre(node.left);
        return ln != null ? ln : node;
    }
    
    private TreeLinkNode getFather(TreeLinkNode node) {
        if (node == null) return null;
        TreeLinkNode father = node.next;
        return father == null ? null : father.left == node ? father : getFather(father);
    }
}
