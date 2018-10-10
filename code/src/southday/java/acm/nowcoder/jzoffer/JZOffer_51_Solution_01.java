package southday.java.acm.nowcoder.jzoffer;

/* 构建乘积数组 
题目描述
给定一个数组A[0,1,...,n-1],请构建一个数组B[0,1,...,n-1],其中B中的元素B[i]=A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1]。不能使用除法。

左右开功 -> .... <-
*/

public class JZOffer_51_Solution_01 {
    public int[] multiply(int[] A) {
        if (A == null) return null;
        if (A.length <= 0) return new int[0];
        // left[]保存A[0]*...*A[i-1]
        int len = A.length;
        int[] left = new int[len];
        // right[]保存A[n-1]*...*A[i+1]
        int[] right = new int[len];
        left[0] = A[0];
        for (int i = 1; i < len; i++) {
            left[i] = left[i-1] * A[i];
        }
        right[len-1] = A[len-1];
        for (int j = len-2; j >= 0; j--) {
            right[j] = right[j+1] * A[j];
        }
        int[] B = new int[len];
        B[0] = right[1];
        B[len-1] = left[len-2];
        for (int i = 1; i <= len-2; i++) {
            B[i] = left[i-1] * right[i+1];
        }
        return B;
    }
    
    public static void main(String[] args) {
        JZOffer_51_Solution_01 o = new JZOffer_51_Solution_01();
        int A[] = {1,2,3,4,5};
        int[] B = o.multiply(A);
        for (int i = 0; i < B.length; i++) {
            System.out.println(B[i] + ", ");
        }
    }
}
