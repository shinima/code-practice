package solutions;

import java.util.Scanner;

public class Problem_1037 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int N = scanner.nextInt();
    int[] currentBest = new int[N];
    for (int row = 1; row <= N; row++) {
      int[] line = new int[row];
      for (int i = 0; i < row; i++) {
        line[i] = scanner.nextInt();
      }

      int[] nextBest = new int[N];
      for (int i = 0; i < row; i++) {
        if (i == 0) {
          nextBest[i] = currentBest[i] + line[i];
        } else {
          nextBest[i] = Math.max(currentBest[i - 1], currentBest[i]) + line[i];
        }
      }
      currentBest = nextBest;
    }
    int max = currentBest[0];
    for (int num : currentBest) {
      max = Math.max(max, num);
    }
    System.out.println(max);
  }
}
