package leetcode;

import java.util.*;

public class P_802_TLE {
  private static final int UNKNOWN = 0;
  private static final int SAFE = 1;
  private static final int UNSAFE = 2;

  public static void main(String[] args) {
    int[][] graph = {{1, 2}, {2, 3}, {5}, {0}, {5}, {}, {}};
    System.out.println(new P_802_TLE().eventualSafeNodes(graph));
    // expected output: [2, 4, 5, 6]
  }

  public List<Integer> eventualSafeNodes(int[][] graph) {
    int[] status = new int[graph.length];
    Arrays.fill(status, UNKNOWN);

    for (int i = 0; i < status.length; i++) {
      if (status[i] != UNKNOWN) {
        continue;
      }
      Set<Integer> prefix = new HashSet<>();
      Set<Integer> visited = new HashSet<>();
      boolean dfsResult = dfs(status, graph, i, prefix, visited);
//      System.out.println("dfs(" + i + ") => " + dfsResult);
//      System.out.println("prefix: " + prefix);
//      System.out.println("visited: " + visited);
      for (int j : visited) {
        status[j] = SAFE;
      }
      if (dfsResult) {
        for (int j : prefix) {
          status[j] = UNSAFE;
        }
      }
    }

    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < status.length; i++) {
      if (status[i] == SAFE) {
        result.add(i);
      }
    }
    return result;
  }

  private static boolean dfs(int[] status, int[][] graph, int i, Set<Integer> prefix, Set<Integer> visited) {
    if (prefix.contains(i) || status[i] == UNSAFE) {
      return true;
    }
    if (status[i] == SAFE) {
      return false;
    }
    visited.add(i);
    prefix.add(i);
    for (int j : graph[i]) {
      if (dfs(status, graph, j, prefix, visited)) {
        return true;
      }
    }
    prefix.remove(i);
    return false;
  }
}
