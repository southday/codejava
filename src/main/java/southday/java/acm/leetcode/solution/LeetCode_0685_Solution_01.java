package southday.java.acm.leetcode.solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * 基于DFS的比较垃圾的解法，可能是题目哪里没理解好，没get到重点
 * 我对题目的理解：
 *  1) 最多只有1个结点有2个父结点；所以我用father[i][2]来保存结点i的2个父结点；
 *  2) 图中有且只有1个环，所以我用firstCycle来标记使环产生的第一个edge[]；
 * 解题思想：
 *  1) 遍历edges[][]来构造graph，在构造的过程中，进行额外处理：
 *      a) 找出有2个父结点的结点，用_2fnd标记；
 *      b) 找出使graph第一次出现环的edge[]，用firstCycle标记；
 *  2) 遍历完成后，进行检测：
 *      a) 如果没有找到存在2个父结点的结点，则直接返回：被firstCycle标记的edge[]；
 *      b) 否则，判断子结点_2fnd是否可以到达其第一个父结点([father[_2fnd][0])（即：判断是否存在环）；
 *          b1) 若存在的话，则返回 [father[_2fnd][0], _2fnd]
 *          b2) 否则直接返回 [father[_2fnd][1], _2fnd]
 * Runtime: 10 ms, faster than 12.69% of Java online submissions for Redundant Connection II.
 * Memory Usage: 38.9 MB, less than 94.64% of Java online submissions for Redundant Connection II.
 */

/**
 * 685. Redundant Connection II https://leetcode.com/problems/redundant-connection-ii/
 * @author southday
 * @date 2019/3/19
 */
public class LeetCode_0685_Solution_01 {
    int MAX_EDGE_VAL = 1000;
    List<Integer>[] graph = new ArrayList[MAX_EDGE_VAL+1];
    Set<Integer> seen = new HashSet<>();
    int[][] father = new int[MAX_EDGE_VAL+1][2];
    int _2fnd = -1; // has 2 fathers node
    int firstCycle = -1;

    public int[] findRedundantDirectedConnection(int[][] edges) {
        for (int i = 0; i <= MAX_EDGE_VAL; i++) {
            graph[i] = new ArrayList<>();
            father[i][0] = father[i][1] = 0;
        }
        for (int i = 0, len = edges.length; i < len; i++) {
            int[] edge = edges[i];
            if (father[edge[1]][0] == 0) {
                father[edge[1]][0] = edge[0];
            } else {
                father[edge[1]][1] = edge[0];
                _2fnd = edge[1];
            }
            seen.clear();
            if (dfs(edge[1], edge[0]))
                firstCycle = i;
            graph[edge[0]].add(edge[1]);
        }
        if (_2fnd != -1) {
            seen.clear();
            return dfs(_2fnd, father[_2fnd][0]) ?
                    new int[]{father[_2fnd][0], _2fnd} :
                    new int[]{father[_2fnd][1], _2fnd};
        } else {
            return edges[firstCycle];
        }
    }

    private boolean dfs(int u, int v) {
        if (!seen.contains(u)) {
            if (u == v)
                return true;
            seen.add(u);
            for (int nei : graph[u])
                if (dfs(nei, v))
                    return true;
        }
        return false;
    }
}
