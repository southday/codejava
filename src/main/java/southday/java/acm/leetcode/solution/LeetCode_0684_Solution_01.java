package southday.java.acm.leetcode.solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 官方 Solution DFS
 * 684. Redundant Connection https://leetcode.com/problems/redundant-connection/
 * @author southday
 * @date 2019/3/17
 */
public class LeetCode_0684_Solution_01 {
    Set<Integer> seen = new HashSet<>();
    int MAX_EDGE_VAL = 1000;

    public int[] findRedundantConnection(int[][] edges) {
        List<Integer>[] graph = new ArrayList[MAX_EDGE_VAL+1];
        for (int i = 0; i <= MAX_EDGE_VAL; i++)
            graph[i] = new ArrayList<>();
        for (int[] edge : edges) {
            seen.clear();
            if (!graph[edge[0]].isEmpty() && !graph[edge[1]].isEmpty() && dfs(graph, edge[0], edge[1])) {
                return edge;
            }
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }
        throw new AssertionError();
    }

    public boolean dfs(List<Integer>[] graph, int u, int v) {
        if (!seen.contains(u)) {
            seen.add(u);
            if (u == v)
                return true;
            for (int nei : graph[u])
                if (dfs(graph, nei, v))
                    return true;
        }
        return false;
    }
}
