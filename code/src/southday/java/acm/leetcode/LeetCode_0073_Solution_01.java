package southday.java.acm.leetcode;

/*
73. Set Matrix Zeroes https://leetcode.com/problems/set-matrix-zeroes/description/
Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in-place.

Example 1:

Input: 
[
  [1,1,1],
  [1,0,1],
  [1,1,1]
]
Output: 
[
  [1,0,1],
  [0,0,0],
  [1,0,1]
]

Example 2:

Input: 
[
  [0,1,2,0],
  [3,4,5,2],
  [1,3,1,5]
]
Output: 
[
  [0,0,0,0],
  [0,4,5,0],
  [0,3,1,0]
]

Follow up:
    A straight forward solution using O(mn) space is probably a bad idea.
    A simple improvement uses O(m + n) space, but still not the best solution.
    Could you devise a constant space solution?
*/

/**
 * 
 * @author southday
 * @date 2018年10月23日
 */
public class LeetCode_0073_Solution_01 {
    public static void setZero(int[][] matrix) {
        if (matrix == null)
            return;
        int m = matrix.length;
        int n = matrix[0].length;
        boolean row0thHasZero = false;
        boolean col0thHasZero = false;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[0][j] = matrix[i][0] = 0;
                    if (i == 0)
                        row0thHasZero = true;
                    if (j == 0)
                        col0thHasZero = true;
                }
            }
        }
        for (int i = 1; i < m; i++)
            if (matrix[i][0] == 0)
                for (int j = 1; j < n; j++)
                    matrix[i][j] = 0;
        for (int j = 1; j < n; j++)
            if (matrix[0][j] == 0)
                for (int i = 0; i < m; i++)
                    matrix[i][j] = 0;
        if (row0thHasZero)
            for (int j = 0; j < n; j++)
                matrix[0][j] = 0;
        if (col0thHasZero)
            for (int i = 0; i < m; i++)
                matrix[i][0] = 0;
    }
    
    public static void main(String[] args) {
        int[][] matrix1 = {{1,1,1},
                           {1,0,1},
                           {1,1,1}};
        
        setZero(matrix1);
        sop(matrix1);
        
        System.out.println("---------------------");
        int[][] matrix2 = {{0,1,2,0},
                           {3,4,5,2},
                           {1,3,1,5}};
        setZero(matrix2);
        sop(matrix2);
    }

    public static void sop(int[][] matrix) {
        for (int[] arr : matrix) {
            for (int x : arr)
                System.out.print(x + ",");
            System.out.println();
        }
    }
}
