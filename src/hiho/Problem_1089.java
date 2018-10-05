package hiho;

import java.util.Scanner;

public class Problem_1089 {
  private static final int MAX_LEN = 10000;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int N = scanner.nextInt();
    int M = scanner.nextInt();

    int[][] routeMap = new int[N][N];
    for (int u = 0; u < N; u++) {
      for (int v = u; v < N; v++) {
        if (u == v) {
          routeMap[u][v] = 0;
        } else {
          routeMap[u][v] = MAX_LEN;
        }
      }
    }

    for (int i = 0; i < M; i++) {
      int u = scanner.nextInt() - 1; // -1 to show respects
      int v = scanner.nextInt() - 1;
      int length = scanner.nextInt();
      if (u > v) {
        int temp = u;
        u = v;
        v = temp;
        // ensure u <= v
      }
      routeMap[u][v] = Math.min(routeMap[u][v], length);
    }

    for (int middle = 0; middle < N; middle++) {
      for (int start = 0; start < N; start++) {
        for (int end = start; end < N; end++) {
          routeMap[start][end] = Math.min(
            routeMap[start][end],
            get(routeMap, start, middle) + get(routeMap, middle, end)
          );
        }
      }
    }
    print(routeMap);
  }

  private static void print(int[][] routeMap) {
    int N = routeMap.length;
    for (int u = 0; u < N; u++) {
      for (int v = 0; v < N; v++) {
        if (v <= u) {
          System.out.print(routeMap[v][u] + (v == N - 1 ? "\n" : " "));
        } else {
          System.out.print(routeMap[u][v] + (v == N - 1 ? "\n" : " "));
        }
      }
    }
  }

  private static int get(int[][] routeMap, int x, int y) {
    if (x <= y) {
      return routeMap[x][y];
    } else {
      return get(routeMap, y, x);
    }
  }
}
