package southday.java.acm.nowcoder.jzoffer;

/* 矩阵中的路径
请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一个格子开始，
每一步可以在矩阵中向左，向右，向上，向下移动一个格子。如果一条路径经过了矩阵中的某一个格子，则之后不能再次进入这个格子。 
例如 a b c e s f c s a d e e 这样的3 X 4 矩阵中包含一条字符串"bcced"的路径，
但是矩阵中不包含"abcb"路径，因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入该格子。

类似于迷宫求解问题，使用栈来保存路径，使用递归的话可能会溢出
for i行
   for j列
             找到 第1个字符'b'
                    执行搜索
         搜索失败则在第i行找下个'b'（如果存在的话）
*/

public class JZOffer_65_Solution_01 {
    boolean[] vis = null;
    int d[][] = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // 方向：顺时针 → ↓ ← ↑
    int rn = 0, cn = 0; // 行数，列数
    
    public boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        if (matrix == null || str == null) return false;
        vis = new boolean[matrix.length];
        rn = rows;
        cn = cols;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int k = i*cols + j;
                if (matrix[k] != str[0]) continue;
                if (hasPath2(matrix, i, j, str, 0)) return true;
            }
        }
        return false;
    }
    
    private boolean hasPath2(char[] matrix, int i, int j, char[] str, int sp) {
        int k = i * cn + j;
        if (k < 0 || k >= matrix.length || vis[k] == true) return false;
        if (str[sp] == matrix[k]) {
            if (++sp == str.length) return true;
            vis[k] = true;
            boolean res = hasPath2(matrix, i+d[0][0], j+d[0][1], str, sp) // →
                    || hasPath2(matrix, i+d[1][0], j+d[1][1], str, sp) // ↓
                    || hasPath2(matrix, i+d[2][0], j+d[2][1], str, sp) // ←
                    || hasPath2(matrix, i+d[3][0], j+d[3][1], str, sp); // ↑
            if (res == true) return true;
            vis[k] = false;
        }
        return false;
    }
    
    public static void main(String[] args) {
        JZOffer_65_Solution_01 o = new JZOffer_65_Solution_01();
        char[] matrix = "abcesfcsadee".toCharArray();
        char[] str = "bccfdeese".toCharArray();
        System.out.println(o.hasPath(matrix, 3, 4, str));
    }
}
