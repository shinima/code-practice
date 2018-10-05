package hiho;

import java.util.Scanner;

public class Problem_1054 {
  private static int[][] m = new int[10][10];

  static {
    m[1][3] = 2;
    m[3][1] = 2;
    m[1][7] = 4;
    m[7][1] = 4;
    m[3][9] = 6;
    m[9][3] = 6;
    m[7][9] = 8;
    m[9][7] = 8;
    m[3][7] = 5;
    m[7][3] = 5;
    m[1][9] = 5;
    m[9][1] = 5;
    m[4][6] = 5;
    m[6][4] = 5;
    m[2][8] = 5;
    m[8][2] = 5;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner("3  \n" +
      "0  \n" +
      "8  \n" +
      "1 2  \n" +
      "2 3  \n" +
      "3 4  \n" +
      "4 5  \n" +
      "5 6  \n" +
      "6 7  \n" +
      "7 8  \n" +
      "8 9  \n" +
      "4  \n" +
      "2 4  \n" +
      "2 5   \n" +
      "8 5  \n" +
      "8 6  ");
    int T = scanner.nextInt();
    for (int tc = 0; tc < T; tc++) {
      int N = scanner.nextInt();
      boolean[][] g = new boolean[10][10];
      for (int i = 0; i < N; i++) {
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        g[x][y] = true;
        g[y][x] = true;
      }
      System.out.println(dfs(g, N, 0, new boolean[10], 0));
    }
  }

  private static int dfs(boolean[][] g, int remaining, int x, boolean[] visited, int len) {
    int count = len >= 4 && remaining == 0 ? 1 : 0;
    for (int next = 1; next <= 9; next++) {
      if (visited[next]) {
        continue;
      }
      if (m[x][next] != 0 && !visited[m[x][next]]) {
        continue;
      }
      visited[next] = true;
      // use x -> next
      if (g[x][next] || g[next][x]) {
        g[x][next] = g[next][x] = false;
        count += dfs(g, remaining - 1, next, visited, len + 1);
        g[x][next] = g[next][x] = true;
      } else {
        count += dfs(g, remaining, next, visited, len + 1);
      }
      visited[next] = false;
    }
    return count;
  }
}
