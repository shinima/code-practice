package solutions;

import java.util.*;

public class Problem_1041_TLE {
  public static void main(String[] args) {
    Scanner scanner = new Scanner("2\n" +
      "7\n" +
      "1 2\n" +
      "1 3\n" +
      "2 4\n" +
      "2 5\n" +
      "3 6\n" +
      "3 7\n" +
      "3\n" +
      "3 7 2\n" +
      "7\n" +
      "1 2\n" +
      "1 3\n" +
      "2 4\n" +
      "2 5\n" +
      "3 6\n" +
      "3 7\n" +
      "3\n" +
      "3 2 7");
    int T = scanner.nextInt();
    for (int testCase = 0; testCase < T; testCase++) {
      int n = scanner.nextInt(); // 城市的数量
      Edge[] edges = new Edge[n - 1];
      for (int i = 0; i < n - 1; i++) {
        int u = scanner.nextInt();
        int v = scanner.nextInt();
        edges[i] = new Edge(u, v);
      }
      int m = scanner.nextInt();
      int[] order = new int[m];
      for (int i = 0; i < m; i++) {
        order[i] = scanner.nextInt();
      }
      if (hasAnyRoute(n, edges, order)) {
        System.out.println("YES");
      } else {
        System.out.println("NO");
      }
    }
  }

  static class Edge {
    int u;
    int v;

    Edge(int u, int v) {
      this.u = u;
      this.v = v;
    }
  }

  static class Env {
    int[] path;
    int pathSize;
    Map<Integer, List<Integer>> graph;
    int[] order;
    int orderCount;
    int n;

    void pprint() {
      System.out.println("path(" + pathSize + "): " + Arrays.toString(path));
    }

    boolean pathContain(int u) {
      for (int i = 0; i < pathSize; i++) {
        if (path[i] == u) {
          return true;
        }
      }
      return false;
    }
  }

  private static boolean hasAnyRoute(int n, Edge[] edges, int[] order) {
    Env env = new Env();
    env.path = new int[n];
    env.pathSize = 0;
    env.graph = new HashMap<>();
    env.order = order;
    env.orderCount = 0;
    env.n = n;
    for (int u = 1; u <= n; u++) {
      env.graph.put(u, new ArrayList<Integer>());
    }
    for (Edge e : edges) {
      env.graph.get(e.u).add(e.v);
      env.graph.get(e.v).add(e.u);
    }
    return dfs(env, 1) == DfsResult.SUCCESS;
  }

  enum DfsResult {
    CONFLICT,
    PASS,
    SUCCESS,
  }

  private static DfsResult dfs(Env env, int u) {
    env.path[env.pathSize++] = u;
//        env.pprint();
    if (u == env.order[env.orderCount]) {
      env.orderCount++;
      if (env.orderCount == env.order.length) {
        return DfsResult.SUCCESS;
      }
    } else {
      for (int i = env.orderCount; i < env.order.length; i++) {
        if (env.order[i] == u) {
//                    System.out.println("conflict");
          return DfsResult.CONFLICT;
        }
      }
    }

    int pathSize = env.pathSize;
    choiceLoop:
    for (List<Integer> choice : permutate(env, u)) {
      DfsResult result = DfsResult.PASS;
      env.pathSize = pathSize;
      for (int v : choice) {
        if (!env.pathContain(v)) {
          result = dfs(env, v);
          if (result == DfsResult.CONFLICT) {
            continue choiceLoop;
          }
        }
      }
      if (result == DfsResult.SUCCESS) {
        return DfsResult.SUCCESS;
      }
    }
    return DfsResult.PASS;
  }

  private static List<List<Integer>> permutate(Env env, int u) {
//        System.out.println("permuate(" + vs + ")");
    Set<Integer> candidates = new HashSet<>(env.graph.get(u));
    for (int i = 0; i < env.pathSize; i++) {
      candidates.remove(env.path[i]);
    }
    List<List<Integer>> result = new ArrayList<>();
    permutateHelper(result, new ArrayList<Integer>(), candidates);
//        System.out.println("result: " + result);
    return result;
  }

  private static void permutateHelper(List<List<Integer>> result, List<Integer> prefix, Set<Integer> candidates) {
    if (prefix.size() == candidates.size()) {
      result.add(new ArrayList<>(prefix));
      return;
    }
    for (int v : candidates) {
      if (!prefix.contains(v)) {
        prefix.add(v);
        permutateHelper(result, prefix, candidates);
        prefix.remove(prefix.size() - 1);
      }
    }
  }
}
