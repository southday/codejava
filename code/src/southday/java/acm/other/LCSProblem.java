package southday.java.acm.other;

/* X = {A, B, C, B, D, A, B}
 * Y = {B, D, C, A, B, A}
 * Z = {B, C, A} 是X和Y的公共子序列，长度为3
 * Z = {B, C, B, A} 是X和Y的最长公共子序列LCS，长度为4
 * Z = {B, D, A, B} 也是X和Y的最长公共子序列LCS，长度为4
 * 
 * 设 X = {x1, x2, ..., xm}，则 Xi = {x1, x2, ..., xi}，X0 为空串
 */

/**
 * 最长公共子序列问题 (longest-common-subsequence problem)
 * @author southday
 * @date 2018年10月27日
 */
public class LCSProblem {
    private static final int INFV = Integer.MAX_VALUE;
    
    /**
     * 求解X和Y的最长公共子序列长度（自底向上）
     * @param X X[1...m]
     * @param Y Y[1...n]
     * @param c c[0..m, 0..n]，c[i][j]表示Xi和Yj的LCS的长度
     */
    public static void LCSLength(char[] X, char[] Y, int[][] c) {
        int m = X.length;
        int n = Y.length;
        for (int i = 0; i < m; i++)
            c[i][0] = 0;
        for (int j = 0; j < n; j++)
            c[0][j] = 0;
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (X[i] == Y[j]) // 左上 ↖
                    c[i][j] = c[i-1][j-1] + 1;
                else if (c[i-1][j] >= c[i][j-1]) // 上 ↑
                    c[i][j] = c[i-1][j];
                else // 左 ←
                    c[i][j] = c[i][j-1];
            }
        }
    }
    
    /**
     * 求解X和Y的最长公共子序列长度（自顶向下） - 备忘录
     * @param X
     * @param Y
     * @param c
     */
    public static void LCSLengthMemorized(char[] X, char[] Y, int[][] c) {
        for (int i = 0, m = c.length; i < m; i++)
            for (int j = 0, n = c[0].length; j < n; j++)
                c[i][j] = INFV;
        lookupLCSLength(X, Y, c, c.length-1, c[0].length-1);
    }
    
    private static int lookupLCSLength(char[] X, char[] Y, int[][] c, int i, int j) {
        if (c[i][j] < INFV)
            return c[i][j];
        if (i == 0 || j == 0)
            c[i][j] = 0;
        else if (X[i] == Y[j])
            c[i][j] = 1 + lookupLCSLength(X, Y, c, i-1, j-1);
        else {
            int a = lookupLCSLength(X, Y, c, i-1, j);
            int b = lookupLCSLength(X, Y, c, i, j-1);
            c[i][j] = a >= b ? a : b;
        }
        return c[i][j];
    }
    
    public static void printLCS(char[] X, int[][] c, int i, int j) {
        if (i == 0 || j == 0)
            return;
        if (c[i][j] == c[i-1][j]) // 先判断上 ↑
            printLCS(X, c, i-1, j);
        else if (c[i][j] == c[i][j-1]) // 判断左 ←
            printLCS(X, c, i, j-1);
        else { // 最后左上↖
            printLCS(X, c, i-1, j-1);
            System.out.print(X[i] + "(" + i + ")");
        }
    }
    
    public static void main(String[] args) {
        // 由于序列 X, Y都是从1开始计数的，为了方便，在每个序列前都加了一个空格字符，算法运行中不使用该字符
        char[] X = " ABCBDAB".toCharArray();
        char[] Y = " BDCABA".toCharArray();
        int[][] c = new int[X.length][Y.length];
        
        LCSLength(X, Y, c);
//        LCSLengthMemorized(X, Y, c);
        printLCS(X, c, c.length-1, c[0].length-1);
        System.out.println();
        printMatrix(c);
    }
    
    public static void printMatrix(int[][] c) {
        int m = c.length;
        int n = c[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++)
                System.out.print(c[i][j] + " ");
            System.out.println();
        }
    }
}
