package solutions;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Problem_1055 {
  public static void main(String[] args) {
    String input = "10 4\n" +
      "370 328 750 930 604 732 159 167 945 210 \n" +
      "1 2\n" +
      "2 3\n" +
      "1 4\n" +
      "1 5\n" +
      "4 6\n" +
      "4 7\n" +
      "4 8\n" +
      "6 9\n" +
      "5 10";
    // expected output: 2977
    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
//        Scanner scanner = new Scanner(System.in);

    int N = scanner.nextInt();
    int M = scanner.nextInt();
    int[] values = new int[N];
    Node[] nodes = new Node[N];

    for (int i = 0; i < N; i++) {
      values[i] = scanner.nextInt();
    }
    for (int i = 0; i < nodes.length; i++) {
      nodes[i] = new Node();
    }
    for (int i = 0; i < N - 1; i++) {
      int x = scanner.nextInt() - 1;
      int y = scanner.nextInt() - 1;
      nodes[x].connected.add(y);
      nodes[y].connected.add(x);
    }

    int bestValue = getBestValue(N, M, nodes, values);
    System.out.println(bestValue);
  }

  private static class Node {
    List<Integer> connected = new ArrayList<>();
  }

  private static int getBestValue(int N, int M, Node[] nodes, int[] values) {
    // f(t, m) 以t为结点的子树, 涂色m个 的最佳值
    int[][] f = new int[N][M + 1];
    helper(M, f, nodes, values, 0, -1);
    return f[0][M];
  }

  private static void helper(int m, int[][] f, Node[] nodes, int[] values, int current, int parent) {
    if (m == 1) { // 如果只能涂一个结点, 那么只有涂当前结点这种情况
      f[current][1] = values[current];
      return;
    }

    int connectedCount = nodes[current].connected.size();
    int childCount = connectedCount - (parent == -1 ? 0 : 1);

    // dp[x][y] 前x个孩子, 共涂色y个(包括current) 的最佳值
    // dp[0][*] = 0; dp[*][0] = 0; dp[*][1] = values[current]
    int[][] dp = new int[childCount + 1][m + 1];
    dp[0][1] = values[current]; // dp[*][1] = value[current]会在xx的循环里面被赋值, 所以不在这儿赋值了

    boolean parentVisited = false;

    for (int xx = 1; xx <= connectedCount; xx++) {
      // (xx-1)是连通结点的index, 但不一定是子结点的index
      int child = nodes[current].connected.get(xx - 1);
      if (child == parent) {
        parentVisited = true;
        continue;
      }
      helper(m - 1, f, nodes, values, child, current); // 后序遍历所有的子结点. 计算f[child][0...m-1]

      int x = parentVisited ? xx - 1 : xx; // (x-1)子结点的index

      dp[x][0] = 0; // 一个结点也不涂的话 就是0

      dp[x][1] = values[current]; // 只涂一个结点的话, 那只能涂当前结点

      for (int y = 2; y <= m; y++) { // 当 y >= 2的时候, current结点必须涂色
        int sub = dp[x - 1][y]; // 最后一棵子树 涂色0个结点的情况下 所有子结点的总和
        // 最后一棵子树中 涂色t个结点的情况(前x-1棵子树共涂色y-t个结点) 所有子结点的总和
        for (int t = 1; t <= y - 1; t++) {
          // 这里f[child][t]已经计算好了.
          // f[child][t]可能遇到t比`子树child的结点总数`更大的情况, 此时f[child][t]为0, 不会导致错误
          sub = Math.max(sub, dp[x - 1][y - t] + f[child][t]);
        }
        // 总是加上 values[current], 因为当前结点在y >= 2的情况下总是必须涂的
        dp[x][y] = sub;
      }
    }
    // 将dp[childCount][0...m]拷贝到f[child][0...m]
    System.arraycopy(dp[childCount], 0, f[current], 0, m + 1);
  }
}
