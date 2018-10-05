package hiho;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Problem_1097 { // todo WA
  // 最小生成树: Prim算法
  private static final int MAX_DISTANCE = 10000000;

  public static void main(String[] args) {
    String input = "5\n" +
      "0    1005 6963 392  1182 \n" +
      "1005 0    1599 4213 1451 \n" +
      "6963 1599 0    9780 2789 \n" +
      "392  4213 9780 0    5236 \n" +
      "1182 1451 2789 5236 0    \n";
    // Expected output: 4178
    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
    int N = scanner.nextInt();
    int[][] distances = new int[N][N];
    for (int x = 0; x < N; x++) {
      for (int y = 0; y < N; y++) {
        distances[x][y] = scanner.nextInt();
      }
    }
    int result = 0;
    boolean[] visited = new boolean[N];
    int[] lastCity = new int[N];
    Arrays.fill(lastCity, -1);
    int[] pathLens = new int[N];
    Arrays.fill(pathLens, MAX_DISTANCE);
    pathLens[0] = 0; // 从下标为0的城市出发
    int visitCount = 0;

    while (visitCount < N) {
      int next = -1;
      // 选取下一个城市
      // 选取的依据是下一个没有被visited且pathLen[x]最小的那个city
      for (int city = 0; city < N; city++) {
        if (!visited[city] && (next == -1 || pathLens[city] < pathLens[next])) {
          next = city;
        }
      }

      if (next != 0) {
        System.out.println("Add " + lastCity[next] + "~" + next + " " + distances[next][lastCity[next]]);
        result += distances[next][lastCity[next]];
      }
      visited[next] = true;
      visitCount++;

      // 以该节点为依据来更新其他尚未被访问的城市的pathLens
      for (int other = 0; other < N; other++) {
        if (other != next && !visited[other]) {
          if (pathLens[next] + distances[other][next] < pathLens[other]) {
            pathLens[other] = pathLens[next] + distances[other][next];
            lastCity[other] = next;
          }
        }
      }
    }

    System.out.println(result);
  }
}
