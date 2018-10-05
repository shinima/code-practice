package hiho;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Problem_1176 {
  public static void main(String[] args) {
//        Scanner scanner = new Scanner("6 8\n" +
//                "1 2\n" +
//                "1 4\n" +
//                "2 4\n" +
//                "2 5\n" +
//                "2 3\n" +
//                "3 6\n" +
//                "4 5\n" +
//                "5 6");
    Scanner scanner = new Scanner(System.in);
    int N = scanner.nextInt();
    int M = scanner.nextInt();
    Map<Integer, Integer> countMap = new HashMap<>();
    for (int i = 0; i < M; i++) {
      int u = scanner.nextInt();
      int v = scanner.nextInt();
      if (!countMap.containsKey(u)) {
        countMap.put(u, 1);
      } else {
        countMap.put(u, countMap.get(u) + 1);
      }
      if (!countMap.containsKey(v)) {
        countMap.put(v, 1);
      } else {
        countMap.put(v, countMap.get(v) + 1);
      }
    }
    int oddCount = 0;
    for (int n : countMap.values()) {
      if (n % 2 == 1) {
        oddCount++;
      }
    }
    if (oddCount <= 2) {
      System.out.println("Full");
    } else {
      System.out.println("Part");
    }
  }
}
