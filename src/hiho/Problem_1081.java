package hiho;

import java.io.ByteArrayInputStream;
import java.util.*;

public class Problem_1081 {
  public static void main(String[] args) {
    String input = "5 23 5 4\n" +
      "1 2 708\n" +
      "2 3 112\n" +
      "3 4 721\n" +
      "4 5 339\n" +
      "5 4 960\n" +
      "1 5 849\n" +
      "2 5 98\n" +
      "1 4 99\n" +
      "2 4 25\n" +
      "2 1 200\n" +
      "3 1 146\n" +
      "3 2 106\n" +
      "1 4 860\n" +
      "4 1 795\n" +
      "5 4 479\n" +
      "5 4 280\n" +
      "3 4 341\n" +
      "1 4 622\n" +
      "4 2 362\n" +
      "2 3 415\n" +
      "4 1 904\n" +
      "2 1 716\n" +
      "2 5 575";
    // Expected output: 123

    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
    int N = scanner.nextInt(); // 地点个数
    int M = scanner.nextInt(); // 道路数目
    int S = scanner.nextInt() - 1; // 入口编号 // -1让地点下标从零开始
    int T = scanner.nextInt() - 1; // 出口编号

    List<Map<Integer, Integer>> routes = new ArrayList<>(N);
    for (int i = 0; i < N; i++) {
      routes.add(new HashMap<Integer, Integer>());
    }

    for (int i = 0; i < M; i++) {
      int u = scanner.nextInt() - 1;
      int v = scanner.nextInt() - 1;
      int length = scanner.nextInt();

      Map<Integer, Integer> uMap = routes.get(u);
      if (uMap.containsKey(v)) {
        int minLength = Math.min(length, uMap.get(v));
        uMap.put(v, minLength);
      } else {
        uMap.put(v, length);
      }

      Map<Integer, Integer> vMap = routes.get(v);
      if (vMap.containsKey(u)) {
        int minLength = Math.min(length, vMap.get(u));
        vMap.put(u, minLength);
      } else {
        vMap.put(u, length);
      }
    }
    // graph构建完成

    boolean[] visited = new boolean[N];

    int[] lengthArray = new int[N];
    Arrays.fill(lengthArray, Integer.MAX_VALUE);
    lengthArray[S] = 0;

    int visitedCount = 0;
    while (visitedCount < N) {
      int candidate = -1;

      for (int t = 0; t < N; t++) {
        if (!visited[t] && (candidate == -1 || lengthArray[t] < lengthArray[candidate])) {
          candidate = t;
        }
      }

      visited[candidate] = true;
      visitedCount++;

      // 更新与candidate相连的结点的length值
      Map<Integer, Integer> connected = routes.get(candidate);
      for (int other : connected.keySet()) {
        int candidateLen = connected.get(other) + lengthArray[candidate];
        lengthArray[other] = Math.min(lengthArray[other], candidateLen);
      }
    }

    System.out.println(lengthArray[T]);
  }
}
