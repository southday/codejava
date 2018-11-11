package southday.java.acm.nowcoder.jzoffer;

/* 序列化二叉树
请实现两个函数，分别用来序列化和反序列化二叉树

序列化 => String(前序 + '#' + 中序)
反序列化 => 前序 + 中序 => TreeNode
*/


public class JZOffer_61_Solution_01 {
    StringBuilder presb = new StringBuilder();
    StringBuilder insb = new StringBuilder();
    
    String Serialize(TreeNode root) {
        if (root == null) return null;
        preOrder(root);
        inOrder(root);
        presb.deleteCharAt(presb.length()-1);
        insb.deleteCharAt(insb.length()-1);
        return presb.append('#').append(insb).toString();
    }
    
    TreeNode Deserialize(String str) {
        if (str == null || "".equals(str.trim())) return null;
        String[] ss = str.split("#");
        if (ss == null || ss.length != 2 || ss[0].length() != ss[1].length())
            return null;
        String[] s1 = ss[0].split(",");
        String[] s2 = ss[1].split(",");
        int[] pres = new int[s1.length];
        int[] ins = new int[s2.length];
        for (int i = 0; i < pres.length; i++) {
            pres[i] = Integer.parseInt(s1[i]);
            ins[i] = Integer.parseInt(s2[i]);
        }
        return constructBTree(pres, 0, pres.length-1, ins, 0, ins.length-1);
    }
    
    private TreeNode constructBTree(int[] pres, int l1, int r1, int[] ins, int l2, int r2) {
        if (l1 > r1 || l2 > r2) return null;
        TreeNode root = new TreeNode(pres[l1]);
        int k = l2;
        while (k <= r2 && ins[k] != pres[l1]) k++;
        root.left = constructBTree(pres, l1+1, l1+k-l2, ins, l2, k-1);
        root.right = constructBTree(pres, l1+k-l2+1, r1, ins, k+1, r2);
        return root;
    }
    
    private void preOrder(TreeNode root) {
        if (root != null) {
            presb.append(root.val + ",");
            preOrder(root.left);
            preOrder(root.right);
        }
    }
    
    private void inOrder(TreeNode root) {
        if (root != null) {
            inOrder(root.left);
            insb.append(root.val + ",");
            inOrder(root.right);
        }
    }
    
    public static void main(String[] args) {
        JZOffer_61_Solution_01 o = new JZOffer_61_Solution_01();
        String str = "1,2,4,5,7,3,6#4,2,7,5,1,3,6";
        TreeNode root = o.Deserialize(str);
        String s = o.Serialize(root);
        System.out.println(str);
        System.out.println(s);
    }
}
