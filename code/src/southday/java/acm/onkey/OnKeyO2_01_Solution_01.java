package southday.java.acm.onkey;

/*
1、 给定一个m×n 矩阵，如果某个元素为0，则 将其整个行和列都设置为0。 
Example: Input:
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

LeetCode: https://leetcode.com/problems/set-matrix-zeroes/description/
*/

/**
 * 
 * @author southday
 * @date 2018年10月23日
 */
public class OnKeyO2_01_Solution_01 {
    
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
