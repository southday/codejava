package southday.java.acm.nowcoder.jzoffer;

/* 序列化二叉树
请实现两个函数，分别用来序列化和反序列化二叉树

*/


public class JZOffer_61_Solution_02 {
    StringBuilder sb = new StringBuilder();
    int sp = 0;
    
    String Serialize(TreeNode root) {
        if (root == null) {
            sb.append("#,");
        } else {
            sb.append(root.val + ",");
            Serialize((root.left));
            Serialize((root.right));
        }
        return sb.toString();
    }
    
    TreeNode Deserialize(String str) {
        if (str == null || "#,".equals(str) || sp >= str.length())
            return null;
        int k = str.indexOf(",", sp);
        String s = str.substring(sp, k);
        sp = k + 1;
        if ("#".equals(s)) return null;
        TreeNode root = new TreeNode(Integer.parseInt(s));
        root.left = Deserialize(str);
        root.right = Deserialize(str);
        return root;
    }
    
    public static void main(String[] args) {
        JZOffer_61_Solution_02 o = new JZOffer_61_Solution_02();
        String str = "1,2,4,#,#,5,7,#,#,#,3,#,6,#,#,";
        TreeNode root = o.Deserialize(str);
        String s = o.Serialize(root);
        System.out.println(s);
    }
}
