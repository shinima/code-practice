package hiho;

import java.util.*;

public class Problem_1174 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int T = scanner.nextInt();
    for (int caseIndex = 0; caseIndex < T; caseIndex++) {
      int N = scanner.nextInt();
      int M = scanner.nextInt();
      Map<Integer, List<Integer>> graph = new HashMap<>();
      for (int course = 1; course <= N; course++) {
        graph.put(course, new ArrayList<Integer>());
      }
      for (int i = 0; i < M; i++) {
        int start = scanner.nextInt();
        int end = scanner.nextInt();
        graph.get(start).add(end);
      }

      int[] inOrders = new int[N + 1];
      for (int from = 1; from <= N; from++) {
        for (int to : graph.get(from)) {
          inOrders[to]++;
        }
      }

      Queue<Integer> queue = new ArrayDeque<>();
      for (int course = 1; course <= N; course++) {
        if (inOrders[course] == 0) {
          queue.add(course);
        }
      }
      while (!queue.isEmpty()) {
        int c = queue.poll();
        for (int to : graph.get(c)) {
          inOrders[to]--;
          if (inOrders[to] == 0) {
            queue.add(to);
          }
        }
      }

      boolean correct = true;
      for (int course = 1; course <= N; course++) {
        if (inOrders[course] != 0) {
          correct = false;
          break;
        }
      }
      if (correct) {
        System.out.println("Correct");
      } else {
        System.out.println("Wrong");
      }
    }
  }
}
