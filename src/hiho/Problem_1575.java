package hiho;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Problem_1575 {
  private static int N;
  private static int M;
  private static boolean[][] barriers;
  private static int target;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    N = scanner.nextInt();
    M = scanner.nextInt();
    target = hash(N - 1, M - 1, 0, 0);
    barriers = new boolean[N][M];
    for (int i = 0; i < N; i++) {
      char[] chars = scanner.next().toCharArray();
      for (int j = 0; j < M; j++) {
        barriers[i][j] = chars[j] == '1';
      }
    }
    solve();
  }

  private static void solve() {
    final Set<Integer> conquer = new HashSet<>();
    Set<Integer> field = new HashSet<>();
    field.add(hash(0, 0, N - 1, M - 1));
    int step = 0;

    while (!field.isEmpty()) {
      Set<Integer> nextField = new HashSet<>();
      for (final int spot : field) {
        conquer.add(spot);

        final int a = spot / (M * N);
        final int b = spot % (M * N);
        final int row1 = a / M;
        final int col1 = a % M;
        final int row2 = b / M;
        final int col2 = b % M;

        if (spot == target) {
          System.out.println(step);
          return;
        }

        // A向右  B向左
        if (!(row1 == row2 && (col1 == col2 - 1 || col1 == col2 - 2))) {
          final int nextCol1 = hasBarrier(row1, col1 + 1) ? col1 : col1 + 1;
          final int nextCol2 = hasBarrier(row2, col2 - 1) ? col2 : col2 - 1;
          final int nextT = hash(row1, nextCol1, row2, nextCol2);
          if (!conquer.contains(nextT) && !field.contains(nextT)) {
            nextField.add(nextT);
          }
        }

        // A向下 B向上
        if (!(col1 == col2 && (row1 == row2 - 1 || row1 == row2 - 2))) {
          final int nextRow1 = hasBarrier(row1 + 1, col1) ? row1 : row1 + 1;
          final int nextRow2 = hasBarrier(row2 - 1, col2) ? row2 : row2 - 1;
          final int nextT = hash(nextRow1, col1, nextRow2, col2);
          if (!conquer.contains(nextT) && !field.contains(nextT)) {
            nextField.add(nextT);
          }
        }
      }
      field = nextField;
      step++;
    }
    System.out.println(-1);
  }

  private static boolean hasBarrier(int row, int col) {
    return row < 0 || row >= N || col < 0 || col >= M || barriers[row][col];
  }

  private static int hash(int row1, int col1, int row2, int col2) {
    return (row1 * M + col1) * (M * N) + (row2 * M + col2);
  }
}
