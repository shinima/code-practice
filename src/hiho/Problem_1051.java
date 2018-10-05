package hiho;

import java.util.Scanner;

public class Problem_1051 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int T = scanner.nextInt();
    for (int tc = 0; tc < T; tc++) {
      int N = scanner.nextInt();
      int M = scanner.nextInt();
      int[] xs = new int[N + 2];
      xs[0] = 0;
      xs[xs.length - 1] = 101;
      for (int i = 1; i <= N; i++) {
        xs[i] = scanner.nextInt();
      }
      System.out.println(maxCombo(xs, N, M));
    }
  }

  private static int maxCombo(int[] xs, int N, int M) {
    if (M >= N) {
      return 100;
    }
    int result = 0;
    for (int left = 0; left + M + 1 < xs.length; left++) {
      int right = left + M + 1;
      result = Math.max(result, xs[right] - xs[left] - 1);
    }
    return result;
  }
}
