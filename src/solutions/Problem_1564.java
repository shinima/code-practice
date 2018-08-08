package solutions;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Problem_1564 {
  private static long result = 0;

  public static void main(String[] args) throws IOException {
//        String input = "8   3 4 12 1 6 3 3 8\n" +
//            "1 2  1 3  1 4  2 5  2 6  4 7  4 8";
//        InputStream in = new ByteArrayInputStream(input.getBytes());
    InputStream in = System.in;
    int N = readInt(in);
    int[] A = new int[N + 1];
    for (int i = 1; i <= N; i++) {
      A[i] = readInt(in);
    }
    if (N == 1) {
      System.out.println(0);
      return;
    }
    Map<Integer, List<Integer>> g = new HashMap<>();
    for (int i = 1; i <= N; i++) {
      g.put(i, new ArrayList<Integer>());
    }

    Set<Integer> parents = new HashSet<>();
    Set<Integer> childs = new HashSet<>();
    for (int i = 0; i < N - 1; i++) {
      int parent = readInt(in);
      int child = readInt(in);
      g.get(parent).add(child);
      parents.add(parent);
      childs.add(child);
    }
//        System.out.println("parents: " + parents);
//        System.out.println("childs: " + childs);

    for (int child : childs) {
      parents.remove(child);
    }
    int root = parents.iterator().next();
//        System.out.println("root: " + root);
    dfs(A, g, root);

//        System.out.println("Final result: " + result);

    System.out.println(result);
  }

  private static void dfs(int[] A, Map<Integer, List<Integer>> g, int parent) {
    for (int child : g.get(parent)) {
      dfs(A, g, child);
    }
    int max = 0;
    for (int child : g.get(parent)) {
      max = Math.max(max, A[child]);
    }
    for (int child : g.get(parent)) {
      result += max - A[child];
//            System.out.println("Node " + child
//                + " add " + (max - A[child]));
      A[child] = 0;
    }
    A[parent] += max;
  }

  private static int readInt(InputStream in) throws IOException {
    int result = 0;
    boolean dig = false;

    while (true) {
      int c = in.read();
      if (c == -1) {
        break;
      }
      if (c >= '0' && c <= '9') {
        result = result * 10 + (c - '0');
        dig = true;
      } else if (dig) {
        break;
      }
    }
    return result;
  }
}
