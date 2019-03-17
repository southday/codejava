package southday.java.acm.leetcode.solution;

/**
 * 并查集
 * 684. Redundant Connection https://leetcode.com/problems/redundant-connection/
 * @author southday
 * @date 2019/3/18
 */
public class LeetCode_0684_Solution_02 {
    int MAX_NODE_VAL = 1001;
    int[] father = new int[MAX_NODE_VAL];

    public int[] findRedundantConnection(int[][] edges) {
        init();
        for (int i = 0, len = edges.length; i < len; i++) {
            for (int[] edge : edges) {
                if (father(edge[0]) == father(edge[1]))
                    return edge;
                union(edge[0], edge[1]);
            }
        }
        throw new AssertionError();
    }

    private void init() {
        for (int i = 0; i < MAX_NODE_VAL; i++)
            father[i] = i;
    }

    private int father(int x) {
        int a = x;
        for (; x != father[x]; x = father[x]);
        compress(a, x);
        return x;
    }

    private void compress(int x, int rootParent) {
        int z = father[x];
        while (z != rootParent) {
            father[x] = rootParent;
            x = z;
            z = father[z];
        }
    }

    private void union(int x, int y) {
        int fx = father(x);
        int fy = father(y);
        father[fy] = fx;
    }
}
