package southday.java.acm.nowcoder.jzoffer;

/* 求1+2+3+...+n 
题目描述
求1+2+3+...+n，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
*/

public class JZOffer_47_Solution_01 {
    
    public int Sum_Solution(int n) {
        int ans = n;
        @SuppressWarnings("unused")
        boolean f = (ans > 0) && (ans += Sum_Solution(n-1)) > 0;
        return ans;
    }
    
    public static void main(String[] args) {
        JZOffer_47_Solution_01 o = new JZOffer_47_Solution_01();
        System.out.println(o.Sum_Solution(100));
        
    }
}
