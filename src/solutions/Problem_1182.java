package solutions;

import java.util.*;

public class Problem_1182 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int N = scanner.nextInt();
    Set<Integer> used = new HashSet<>();
    List<Integer> path = new ArrayList<>();
    used.add(0);
    used.add(1 << N);
    dfs(N, used, path, 0);
    for (int edge : path) {
      System.out.print(edge >> (N - 1));
    }
  }

  private static void dfs(int N, Set<Integer> used, List<Integer> path, int start) {
    int mask = (1 << N) - 1;
    int edge1 = start << 1;
    int edge2 = (start << 1) + 1;
    int[] edges = {edge1, edge2};
    for (int edge : edges) {
      int next = edge & mask;
      if (!used.contains(edge)
        && !used.contains(next & mask)
        && !used.contains((1 << N) + (next & mask))) {
        used.add(edge);
        dfs(N, used, path, next);
      }
    }
    path.add(start);
  }
}
