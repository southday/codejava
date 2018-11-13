package southday.java.acm.other.solution;

/* 矩阵链乘法问题描述：
 * 给定n个矩阵的链<A1,A2,...,An>，矩阵Ai的规模是pi-1 X pi (1<=i<=n)，
 * 求完全括号化方案，使得计算乘积A1A2...An， 所需标量乘法次数最少。
 * 
 * 完全括号化：它是单一矩阵，或者是两个完全括号化的矩阵乘积链的积，且已外加括号。
 */

/**
 *《算法导论》第3版 第15章 动态规划 15.2矩阵链乘法
 * @author southday
 * @date 2018年10月27日
 */
public class MatrixChainMultiplication {
    private static final int INFV = Integer.MAX_VALUE;
    
    /**
     * 矩阵链乘法顺序 （自底向上）
     * @param p 矩阵规模序列 p0..pn
     * @param m 辅助表，用于保存代价m[i,j] 1..n (表示计算矩阵Ai..j所需标量乘法次数的最小值)
     * @param s 辅助表，用于记录最优值m[i,j]对应的分割点k
     */
    public static void matrixChainOrder(int[] p, int[][] m, int[][] s) {
        int n = p.length - 1;
        for (int i = 1; i <= n; i++)
            m[i][i] = 0;
        for (int len = 2; len <= n; len++) {
            for (int i = 1; i <= n-len+1; i++) {
                int j = i + len - 1;
                m[i][j] = INFV;
                for (int k = i; k <= j-1; k++) {
                    int q = m[i][k] + m[k+1][j] + p[i-1]*p[k]*p[j];
                    if (q < m[i][j]) {
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }
    }
    
    /**
      * 矩阵链乘法顺序 （自顶向下 - 备忘录）
     * @param p 矩阵规模序列 p0..pn
     * @param m 辅助表，用于保存代价m[i,j] (m[1..n][1..n]表示计算矩阵Ai..j所需标量乘法次数的最小值)
     * @param s 辅助表，用于记录最优值m[i,j]对应的分割点k (s[1..n-1][2..n])
     */
    public static void matrixChainMemorized(int[] p, int[][] m, int[][] s) {
        int n = p.length - 1;
        for (int i = 1; i <= n; i++)
            for (int j = i; j <= n; j++)
                m[i][j] = INFV;
        lookupChain(p, m, s, 1, n);
    }
    
    private static int lookupChain(int[] p, int[][] m, int[][] s, int i, int j) {
        if (m[i][j] < INFV)
            return m[i][j];
        if (i == j)
            m[i][j] = 0;
        else {
            for (int k = i; k <= j-1; k++) {
                int q = lookupChain(p, m, s, i, k) + lookupChain(p, m, s, k+1, j) + p[i-1]*p[k]*p[j];
                if (q < m[i][j]) {
                    m[i][j] = q;
                    s[i][j] = k;
                }
            }
        }
        return m[i][j];
    }
    
    
    /**
     * 打印完全括号化的矩阵乘法链
     * @param s 辅助表，用于记录最优值m[i,j]对应的分割点k
     * @param i 矩阵链乘法起点Ai
     * @param j 矩阵链乘法终点Aj
     */
    public static void printOptimalParens(int[][] s, int i, int j) {
        if (i == j)
            System.out.print("A" + i);
        else {
            System.out.print("(");
            printOptimalParens(s, i, s[i][j]);
            printOptimalParens(s, s[i][j]+1, j);
            System.out.print(")");
        }
    }
    
    /**
     * 根据最优括号化进行矩阵链乘法
     * @param p 矩阵规模序列
     * @param s 辅助表，用于记录最优值m[i,j]对应的分割点k
     * @param i 矩阵链乘法起点Ai
     * @param j 矩阵链乘法起点Aj
     * @return 最优括号化矩阵链(Ai...Aj)的乘积
     */
    public static int multiply(int[] p, int[][] s, int i, int j) {
        if (i == j)
            return 0;
        if (j-i == 1)
            return p[i-1]*p[i]*p[j];
        else {
            int ml = multiply(p, s, i, s[i][j]);
            int mr = multiply(p, s, s[i][j]+1, j);
            int mm = p[i-1] * p[s[i][j]] * p[j];
            return ml+mr+mm;
        }
    }
    
    public static void main(String[] args) {
        int p[] = {30, 35, 15, 5, 10, 20, 25};
//        int p[] = {5, 10, 3, 12, 5, 50, 6};
        int sz = p.length;
        int[][] m = new int[sz][sz];
        int[][] s = new int[sz][sz];
        
//        matrixChainOrder(p, m, s);
        matrixChainMemorized(p, m, s);
        printOptimalParens(s, 1, 6);
        System.out.println();
        
        printMatrix(s);
        System.out.println();
        
        printMatrix(m);
        System.out.println();
        
        int result = multiply(p, s, 1, sz-1);
        System.out.println(result);
    }
    
    public static void printMatrix(int[][] matrix) {
        for (int i = 1, m = matrix.length; i < m; i++) {
            for (int j = 2, n = matrix[0].length; j < n; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
    }
}
