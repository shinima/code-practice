package hiho;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Problem_1044 {
  public static void main(String[] args) {
    String input = "5 2 1\n" + // N M Q
      "36 9 80 69 85"; // expected output 201

    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
    int N = scanner.nextInt(); // 数组大小 N <= 1000
    int M = scanner.nextInt(); // 连续区间 2 <= M <= 10
    int Q = scanner.nextInt(); // 限制值 1 <= Q <= M
    int[] array = new int[N];
    for (int i = 0; i < N; i++) {
      array[i] = scanner.nextInt(); // array[i] <= 100
    }
    System.out.println(maxValue(N, M, Q, array));
  }

  private static int maxValue(int N, int M, int Q, int[] array) {
    int size = 1 << M;
    int maxBit = 1 << (M - 1);
    int[][] dp = new int[N + 1][size];
    for (int t = 1; t <= N; t++) {
      for (int k = 0; k < size; k++) {
        if (isValid(k, Q)) {
          int option1 = dp[t - 1][k >> 1];
          int option2 = dp[t - 1][maxBit | (k >> 1)];
          int current = (k & 1) > 0 ? array[t - 1] : 0;
          dp[t][k] = Math.max(option1, option2) + current;
        } else {
          dp[t][k] = Integer.MIN_VALUE;
        }
      }
    }
    int result = 0;
    for (int k = 0; k < size; k++) {
      result = Math.max(result, dp[array.length][k]);
    }
    return result;
  }

  // 判断 k 的二进制表示中 bit-1 的数量是否小于等于 Q
  private static boolean isValid(int k, int Q) {
    int value = k;
    int count = 0;
    while (value != 0) {
      count += value & 1;
      value >>= 1;
    }
    return count <= Q;
  }
}
