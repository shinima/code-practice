package hiho;

import java.io.ByteArrayInputStream;
import java.util.*;

// AC
public class Problem_1098 {
  // 最小生成树: Kruscal算法

  public static void main(String[] args) {
    String input = "5 29\n" +
      "1 2 674\n" +
      "2 3 249\n" +
      "3 4 672\n" +
      "4 5 933\n" +
      "1 2 788\n" +
      "3 4 147\n" +
      "2 4 504\n" +
      "3 4 38\n" +
      "1 3 65\n" +
      "3 5 6\n" +
      "1 5 865\n" +
      "1 3 590\n" +
      "1 4 682\n" +
      "2 4 227\n" +
      "2 4 636\n" +
      "1 4 312\n" +
      "1 3 143\n" +
      "2 5 158\n" +
      "2 3 516\n" +
      "3 5 102\n" +
      "1 5 605\n" +
      "1 4 99\n" +
      "4 5 224\n" +
      "2 4 198\n" +
      "3 5 894\n" +
      "1 5 845\n" +
      "3 4 7\n" +
      "2 4 14\n" +
      "1 4 185";
    // Expected output: 92
    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
    int N = scanner.nextInt();
    int M = scanner.nextInt();

    List<Route> list = new ArrayList<>(M);
    for (int i = 0; i < M; i++) {
      int start = scanner.nextInt() - 1;
      int end = scanner.nextInt() - 1;
      int distance = scanner.nextInt();
      list.add(new Route(start, end, distance));
    }

    PriorityQueue<Route> routes = new PriorityQueue<>(list);
    DisjointSet set = new DisjointSet(N);
    int cost = 0;
    while (!set.isAllConnected()) {
      Route route = routes.poll();
      if (set.union(route.start, route.end)) {
        cost += route.distance;
      }
    }
    System.out.println(cost);
  }

  private static class DisjointSet {
    int[] array;
    int partsCount;

    DisjointSet(int size) {
      array = new int[size];
      Arrays.fill(array, -1);
      partsCount = size;
    }

    boolean isAllConnected() {
      return partsCount == 1;
    }

    boolean union(int x, int y) {
      int sx = find(x);
      int sy = find(y);
      if (sx != sy) { // union-by-rank
        partsCount--;
        if (array[sx] < array[sy]) {
          array[sy] = sx;
        } else if (array[sy] < array[sx]) {
          array[sx] = sy;
        } else {
          array[sy] = sx;
          array[sx]--;
        }
        return true;
      } else {
        return false;
      }
    }

    int find(int t) {
      if (array[t] < 0) {
        return t;
      } else {
        return array[t] = find(array[t]);
      }
    }
  }

  private static class Route implements Comparable<Route> {
    int start;
    int end;
    int distance;

    Route(int start, int end, int distance) {
      this.start = start;
      this.end = end;
      this.distance = distance;
    }

    public int compareTo(Route other) {
      return this.distance - other.distance;
    }
  }
}
