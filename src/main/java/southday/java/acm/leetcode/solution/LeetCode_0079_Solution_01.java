package southday.java.acm.leetcode.solution;

/*
79. Word Search https://leetcode.com/problems/word-search/description/
Given a 2D board and a word, find if the word exists in the grid.

The word can be constructed from letters of sequentially adjacent cell, 
where "adjacent" cells are those horizontally or vertically neighboring. 
The same letter cell may not be used more than once.

Example:

board =
[
  ['A','B','C','E'],
  ['S','F','C','S'],
  ['A','D','E','E']
]

Given word = "ABCCED", return true.
Given word = "SEE", return true.
Given word = "ABCB", return false.

*/

/**
 * 
 * @author southday
 * @date 2018年10月30日
 */
public class LeetCode_0079_Solution_01 {
    
    public boolean exist(char[][] board, String word) {
        char[] ss = word.toCharArray();
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                if (board[i][j] == ss[0] && exist(board, i, j, ss, 0))
                    return true;
        return false;
    }
    
    private boolean exist(char[][] board, int i, int j, char[] ss, int cur) {
        if (cur == ss.length)
            return true;
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != ss[cur])
            return false;
        board[i][j] ^= 256;
        boolean b = exist(board, i, j+1, ss, cur+1)
                    || exist(board, i+1, j, ss, cur+1)
                    || exist(board, i, j-1, ss, cur+1)
                    || exist(board, i-1, j, ss, cur+1);
        board[i][j] ^= 256;
        return b;
    }
    
    public static void main(String[] args) {
        LeetCode_0079_Solution_01 o = new LeetCode_0079_Solution_01();
        char[][] board = {{'A','B','C','E'},
                          {'S','F','C','S'},
                          {'A','D','E','E'}};
        String wd1 = "ABCCED";
        System.out.println(wd1 + " : " + o.exist(board, wd1));
        String wd2 = "ABCB";
        System.out.println(wd2 + " : " + o.exist(board, wd2));
    }
}
