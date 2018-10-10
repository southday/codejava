package southday.java.acm.nowcoder.jzoffer;

/* 机器人的运动范围
地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，每一次只能向左，右，上，下四个方向移动一格，
但是不能进入行坐标和列坐标的数位之和大于k的格子。 例如，当k为18时，机器人能够进入方格（35,37），因为3+5+3+7 = 18。
但是，它不能进入方格（35,38），因为3+5+3+8 = 19。请问该机器人能够达到多少个格子？
*/

public class JZOffer_66_Solution_01 {
    boolean[][] vis = null;
    int d[][] = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    int rn = 0, cn = 0; // 行数，列数
    int cnt = 0, k = 0;; // k为阀值
    
    public int movingCount(int threshold, int rows, int cols) {
        if (threshold <= 0 || rows <= 0 || cols <= 0) return 0;
        vis = new boolean[rows][cols];
        rn = rows;
        cn = cols;
        k = threshold;
        moveCnt(0, 0);
        return cnt;
    }
    
    private void moveCnt(int i, int j) {
        if (i < 0 || i >= rn || j < 0 || j >= cn) return;
        if (vis[i][j] == true || !checked(i, j)) return;
        vis[i][j] = true;
        cnt++;
        for (int p = 0; p < 4; p++) moveCnt(i+d[p][0], j+d[p][1]);
    }
    
    private boolean checked(int i, int j) {
        int sum = 0;
        for (; i != 0; i /= 10) sum += (i % 10);
        for (; j != 0; j /= 10) sum += (j % 10);
        if (sum > k) vis[i][j] = true;
        return sum <= k;
    }
    
    public static void main(String[] args) {
        JZOffer_66_Solution_01 o = new JZOffer_66_Solution_01();
        System.out.println(o.movingCount(10, 1, 100));
    }
}
