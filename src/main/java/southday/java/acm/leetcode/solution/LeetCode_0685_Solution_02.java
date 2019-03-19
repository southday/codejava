package southday.java.acm.leetcode.solution;

/* 解题思路不变，只不过换了一种DFS模式
 * LeetCode_0685_Solution_01.java 中使用 List<Integer>[] graph 来正向DFS，从父结点到子结点；
 * LeetCode_0685_Solution_02.java 中使用 father[][2] 来反向DFS，从子结点到父结点；
 */

/**
 * 685. Redundant Connection II https://leetcode.com/problems/redundant-connection-ii/
 * @author southday
 * @date 2019/3/19
 */
public class LeetCode_0685_Solution_02 {
    int _2fnd = -1; // has 2 fathers node
    int firstCycle = -1;
    int[][] father = null;

    public int[] findRedundantDirectedConnection(int[][] edges) {
        father = new int[edges.length+1][2];
        for (int i = 0, len = father.length; i < len; i++)
            father[i][0] = father[i][1] = -1;
        for (int i = 0, len = edges.length; i < len; i++) {
            int[] edge = edges[i];
            if (firstCycle == - 1 && canTo(edge[1], edge[0]))
                firstCycle = i;
            if (father[edge[1]][0] == -1) {
                father[edge[1]][0] = edge[0];
            } else {
                father[edge[1]][1] = edge[0];
                _2fnd = edge[1];
            }
        }
        if (_2fnd != -1 && firstCycle != -1) {
            return canTo(_2fnd, father[_2fnd][1]) ?
                    new int[]{father[_2fnd][1], _2fnd} :
                    new int[]{father[_2fnd][0], _2fnd};
        } else if (_2fnd != -1) {
            return new int[]{father[_2fnd][1], _2fnd};
        } else {
            return edges[firstCycle];
        }
    }

    private boolean canTo(int from, int to) {
        if (to == -1 || father[to][0] == -1)
            return false;
        if (father[to][0] == from || father[to][1] == from)
            return true;
        return canTo(from, father[to][0]) || canTo(from, father[to][1]);
    }

    public static void main(String[] args) {
//        int edges[][] = {{2,1},{3,1},{4,2},{1,4}};
        int edges[][] = {{1,2},{1,3},{2,3}};
//        int edges[][] = {{1,2},{2,3},{3,4},{4,1},{1,5}};
        LeetCode_0685_Solution_02 o = new LeetCode_0685_Solution_02();
        int[] edge = o.findRedundantDirectedConnection(edges);
        System.out.println(edge[0] + ", " + edge[1]);
    }
}
