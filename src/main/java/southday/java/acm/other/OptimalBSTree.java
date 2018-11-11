package southday.java.acm.other;

import southday.java.acm.AcmUtil;

/*
最优二叉搜索树：Optimal binary search tree
1) 给定n个不同关键字的已排序的序列 K = <k1, k2, ..., kn> (k1 < k2 <... < kn)
2) 对于每个关键字ki 都有一个概率 pi 表示其搜索频率
3) 考虑到有一些搜索的值不在集合 K中，所以还定义了 n+1 个伪关键字：d0, d1, ..., dn 表示不在K中的值
   a) d0 表示所有小于k1的值
   b) dn 表示所有大于kn的值
   c) 对于 i = 1, 2, ..., n-1， di 表示所有在 ki 和 ki+1 之间的值
4) 对于每个伪关键字di，也都有一个概率 qi 表示被搜索的频率
5) 定义一次搜索的代价等于 访问的结点数；
   那么对于整棵树T，对其进行一次搜索的期望代价为：
   E[T] = for(i=1->n){depth(ki)+1}*pi + for(i=0->n){depth(di)+1}*qi
        = 1 + for(i=1->n){depth(ki)}*pi + for(i=0->n){depth(di)}*qi
6) 我们期望构造一棵E[T]最小的二叉搜索树，称这棵树为 最优二叉搜索树
*/

/**
 * 《算法导论》第3版 15.5 最优二叉搜索树
 * @author southday
 * @date 2018年11月4日
 */
public class OptimalBSTree {
    // e[i][j]：在包含关键字 ki,...,kj 的BST中进行一次搜索的E[T]
    public static double[][] e = null; // e[1..n+1, 0..n]，只对 i-1<=j 有意义
    // w[i][j]：包含关键字 ki,...,kj 的树T变成一个结点的子树时，其E[T]的变化（增加值）
    public static double[][] w = null; // w[1..n+1, 0..n]，只对 i-1<=j 有意义
    // r[i][j]：记录包含关键字 ki,...,kj 的子树的根
    public static int[][] root = null; // root[1..n+1, 0..n]，只对 i<=j 有意义
    private static final double INFV = Double.MAX_VALUE;
    
    private static void init(int n) {
        e = new double[n+2][n+1];
        w = new double[n+2][n+1];
        root = new int[n+2][n+1];
    }
    
    /**
     * 求解最优二叉搜索树（自底向上）
     * @param p 关键字ki对应的搜索概率pi(i=1:n)
     * @param q 伪关键字di对应的搜索概率qi(i=0:n)
     * @param n 关键字个数
     */
    public static double optimize(double[] p, double[] q, int n) {
        init(n);
        for (int i = 1; i <= n+1; i++) {
            e[i][i-1] = q[i-1];
            w[i][i-1] = q[i-1];
        }
        for (int len = 1; len <= n; len++) {
            for (int i = 1; i <= n-len+1; i++) {
                int j = i + len - 1;
                e[i][j] = INFV;
                w[i][j] = w[i][j-1] + p[j] + q[j];
                for (int r = i; r <= j; r++) {
                    double t = e[i][r-1] + e[r+1][j] + w[i][j];
                    if (t < e[i][j]) {
                        e[i][j] = t;
                        root[i][j] = r;
                    }
                }
            }
        }
        return e[1][n];
    }
    
    /**
     * 求解最优二叉搜索树（自顶向下（备忘录））
     * @param p 关键字ki对应的搜索概率pi(i=1:n)
     * @param q 伪关键字di对应的搜索概率qi(i=0:n)
     * @param n 关键字个数
     * @return
     */
    public static double optimizeMemorized(double[] p, double[] q, int n) {
        init(n);
        for (int i = 1; i <= n+1; i++)
            for (int j = 0; j <= n; j++) {
                e[i][j] = INFV;
                w[i][j] = INFV;
            }
        return lookupCostE(p, q, 1, n);
    }
    
    private static double lookupCostE(double[] p, double[] q, int i, int j) {
        if (e[i][j] < INFV)
            return e[i][j];
        if (i-1 == j)
            e[i][j] = q[j];
        else {
            w[i][j] = lookupCostW(p, q, i, j-1) + p[j] + q[j];
            for (int r = i; r <= j; r++) {
                double t = lookupCostE(p, q, i, r-1) + lookupCostE(p, q, r+1, j) + w[i][j];
                if (t < e[i][j]) {
                    e[i][j] = t;
                    root[i][j] = r;
                }
            }
        }
        return e[i][j];
    }
    
    private static double lookupCostW(double[] p, double[] q, int i, int j) {
        if (w[i][j] < INFV)
            return w[i][j];
        if (i-1 == j)
            w[i][j] = q[j];
        else
            w[i][j] = lookupCostW(p, q, i, j-1) + p[j] + q[j];
        return w[i][j];
    }
    
    /**
     * 根据root[][]打印最优二叉搜索树的结构
     * @param i
     * @param j
     */
    public static void printStructure(int i, int j) {
        if (i <= j) {
            int k = root[i][j];
            System.out.println("root[" + i + "][" + j + "] = " + k);
            printStructure(i, k-1);
            printStructure(k+1, j);
        }
    }
    
    public static void main(String[] args) {
//        double[] p = {0.0, 0.15, 0.10, 0.05, 0.10, 0.20};
//        double[] q = {0.05, 0.10, 0.05, 0.05, 0.05, 0.10};
        double[] p = {0.0, 0.04, 0.06, 0.08, 0.02, 0.10, 0.12, 0.14};
        double[] q = {0.06, 0.06, 0.06, 0.06, 0.05, 0.05, 0.05, 0.05};
        double et1 = optimize(p, q, p.length-1);
        System.out.println("et1 = " + et1);
        AcmUtil.printMatrix(e, AcmUtil.TWO_DECIMALS_FMT);
        double et2 = optimizeMemorized(p, q, p.length-1);
        System.out.println("et2 = " + et2);
        AcmUtil.printMatrix(e, AcmUtil.TWO_DECIMALS_FMT);
        
        printStructure(1, p.length-1);
    }
    
}
