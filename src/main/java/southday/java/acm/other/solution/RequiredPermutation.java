package southday.java.acm.other.solution;

/*
《Java程序员面试笔试宝典》P298
问题描述：针对{1, 2, 2, 3, 4, 5}这6个数字，写一个函数，打印出所有不同的排列，例如：512234、215432等；
要求：
    1)“4”不能在第3位
    2)“3”和“5”不能相连

解题思路：
图的遍历路径，并按要求过滤路径；
 */

/**
 * 按要求打印数组的排列情况
 * @author southday
 * @date 2019/5/5
 */
public class RequiredPermutation {
    int[] nodes = null;
    boolean[][] graph = null;
    boolean[] vis = null;
    StringBuilder sb = new StringBuilder();

    private void constructGraph(int[] arr) {
        int len = arr.length;
        nodes = arr;
        graph = new boolean[len][len];
        vis = new boolean[len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                graph[i][j] = i != j;
            }
            vis[i] = false;
        }
        /* 对于要求(2)：“3”和“5”不能相连，假设“3”角标是 u，“5”的角标是 v
         * 也可以在这里设置 graph[u][v] = graph[v][u] = false 来满足条件
         */
    }

    private void dfs(int u, int nodeNum) {
        if (!isMeetRequire(u))
            return;
        vis[u] = true;
        sb.append(nodes[u]);
        if (nodeNum == nodes.length) {
            System.out.println(sb.toString());
        } else {
            for (int v = 0; v < graph[u].length; v++)
                if (!vis[v] && graph[u][v])
                    dfs(v, nodeNum+1);
        }
        vis[u] = false;
        sb.deleteCharAt(sb.length()-1);
    }

    private boolean isMeetRequire(int u) {
        if (sb.length() < 1)
            return true;
        // 第3位不能是“4”
        if (sb.length() == 2 && nodes[u] == 4)
            return false;
        // “3”和“5”不能相连
        int pre = sb.charAt(sb.length()-1) - '0';
        if ((pre == 3 && nodes[u] == 5) || (pre == 5 && nodes[u] == 3))
            return false;
        return true;
    }

    public void printPermulation(int[] arr) {
        constructGraph(arr);
        for (int i = 0; i < arr.length; i++)
            dfs(i, 1);
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 3, 4, 5};
        RequiredPermutation o = new RequiredPermutation();
        o.printPermulation(arr);
    }
}
