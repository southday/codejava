package southday.java.acm.nowcoder.jzoffer;

import java.util.ArrayList;

/* 顺时针打印矩阵 
输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，例如，
如果输入如下4 X 4矩阵：1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 
 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10. 
*/

public class JZOffer_19_Solution_01 {
    // →，↓，←，↑
    int dt[][] = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    boolean[][] vis = null;
    
    public ArrayList<Integer> printMatrix(int [][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean[][] vis = new boolean[m][n];
        for (int i = 0; i < m; i++) { 
            for (int j = 0; j < n; j++) {
                vis[i][j] = false;
            }
        }
        
        ArrayList<Integer> ls = new ArrayList<Integer>();
        int i = 0, j = 0, d = 0; // d 为方向，取值 0(→),1(↓),2(←),3(↑)
        int cnt = 0, total = m*n;
        while (cnt < total) {
            if (vis[i][j] == false) {
                System.out.print(" " + matrix[i][j]);
                ls.add(matrix[i][j]);
                vis[i][j] = true;
                cnt++;
            } else {
                i -= dt[d][0];
                j -= dt[d][1];
                d = (d + 1) % 4;
            }
            i += dt[d][0];
            j += dt[d][1];
            if (i < 0 || i >= m) {
                i -= dt[d][0];
                d = (d + 1) % 4;
                j += dt[d][1];
            } else if (j < 0 || j >= n) {
                j -= dt[d][1];
                d = (d + 1) % 4;
                i += dt[d][0];
            }
        }
        return ls;
    }
    
    public static void main(String[] args) {
        JZOffer_19_Solution_01 obj = new JZOffer_19_Solution_01();
        int matrix[][] = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        obj.printMatrix(matrix);
    }
}
