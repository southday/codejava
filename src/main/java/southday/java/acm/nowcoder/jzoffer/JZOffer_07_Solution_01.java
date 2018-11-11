package southday.java.acm.nowcoder.jzoffer;

/* 斐波那契数列 
大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）。
n<=39 
 */

public class JZOffer_07_Solution_01 {
    static int[] arr = new int[40];
    
    static {
        arr[0] = 0;
        arr[1] = arr[2] = 1;
        for (int i = 3; i < 40; i++) {
            arr[i] = arr[i-1] + arr[i-2];
        }
    }
    
    public int Fibonacci(int n) {
        return arr[n];
    }
}
