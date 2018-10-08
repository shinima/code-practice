package hiho;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Problem_1786 {
  public static void main(String[] args) {
//    String input = "6\n1 2 1 3 4 2";
//    Scanner scanner = new Scanner(input);
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    int[] colors = new int[n];

    for (int i = 0; i < n; i++) {
      int c = scanner.nextInt();
      colors[i] = c;
    }

    Map<Integer, Integer> lastSeen = new HashMap<>();
    int[] pre = new int[n];
    Arrays.fill(pre, -1);

    for (int i = 0; i < colors.length; i++) {
      int color = colors[i];
      if (lastSeen.containsKey(color)) {
        pre[i] = lastSeen.get(color);
      }
      lastSeen.put(color, i);
    }

    int[] steps = new int[n];
    Arrays.fill(steps, Integer.MAX_VALUE);
    steps[0] = 0;

    for (int t = 1; t < n; t++) {
      if (pre[t] == -1) {
        steps[t] = steps[t - 1] + 1;
      } else {
        steps[t] = Math.min(steps[t - 1] + 1, steps[pre[t]] + 1);
      }
    }

    System.out.println(steps[n - 1]);
  }
}
