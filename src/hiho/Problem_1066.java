package hiho;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Problem_1066 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(new ByteArrayInputStream(("10\n" +
      "0 Steven David\n" +
      "0 Lcch Dzx\n" +
      "1 Lcch Dzx\n" +
      "1 David Dzx\n" +
      "0 Lcch David\n" +
      "0 Frank Dzx\n" +
      "1 Steven Dzx\n" +
      "1 Frank David\n" +
      "0 Steven Dzx\n" +
      "0 Dzx Frank").getBytes()));
        /* Expected output:
            yes
            no
            yes
            yes */
    int N = scanner.nextInt();
    // N行最多有2N个不同的名字
    int[] set = new int[2 * N];
    Map<String, Integer> map = new HashMap<>(2 * N);
    int nameCount = 0;

    Arrays.fill(set, -1);
    for (int i = 0; i < N; i++) {
      int option = scanner.nextInt();
      String name1 = scanner.next();
      String name2 = scanner.next();
      if (!map.containsKey(name1)) {
        map.put(name1, nameCount);
        nameCount++;
      }
      if (!map.containsKey(name2)) {
        map.put(name2, nameCount);
        nameCount++;
      }
      int key1 = map.get(name1);
      int key2 = map.get(name2);

      if (option == 0) { // union name1 & name2
        union(set, key1, key2);
      } else { // query
        if (find(set, key1) == find(set, key2)) {
          System.out.println("yes");
        } else {
          System.out.println("no");
        }
      }
    }
  }

  private static void union(int[] set, int key1, int key2) {
    int s1 = find(set, key1);
    int s2 = find(set, key2);
    if (s1 != s2) {
      // union-by-rank
      if (set[s1] < set[s2]) {
        set[s2] = s1;
      } else if (set[s2] < set[s1]) {
        set[s1] = s2;
      } else {
        set[s1] = s2;
        set[s2]--;
      }
    }
  }

  private static int find(int[] set, int key) {
    if (set[key] < 0) {
      return key;
    } else {
      // path-compression
      return set[key] = find(set, set[key]);
    }
  }
}
