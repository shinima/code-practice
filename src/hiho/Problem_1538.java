package hiho;

import java.util.Scanner;

public class Problem_1538 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int M = scanner.nextInt();
    int N = scanner.nextInt();
    int K = scanner.nextInt();
    // 基本地毯的样式
    char[][] basicPat = new char[M][];
    for (int row = 0; row < M; row++) {
      basicPat[row] = scanner.next().toCharArray();
    }
    for (int caseIndex = 0; caseIndex < K; caseIndex++) {
      int H = scanner.nextInt();
      int W = scanner.nextInt();
      char[][] carpet = new char[H][];
      for (int row = 0; row < H; row++) {
        carpet[row] = scanner.next().toCharArray();
      }
      char[][] carpetPat = getCarpetPat(carpet, M, N);
      if (carpetPat != null && match(basicPat, carpetPat, M, N)) {
        System.out.println("YES");
      } else {
        System.out.println("NO");
      }
    }
  }

  private static boolean match(char[][] basicPat, char[][] carpetPat, int M, int N) {
    for (int deltaRow = 0; deltaRow < M; deltaRow++) {
      loop:
      for (int deltaCol = 0; deltaCol < N; deltaCol++) {
        for (int row = 0; row < M && carpetPat[row][0] != '.'; row++) {
          for (int col = 0; col < N && carpetPat[row][col] != '.'; col++) {
            if (carpetPat[row][col] != basicPat[(row + deltaRow) % M][(col + deltaCol) % N]) {
              continue loop;
            }
          }
        }
        return true;
      }
    }
    return false;
  }

  private static char[][] getCarpetPat(char[][] carpet, int M, int N) {
    char[][] pat = new char[M][N];
    for (int row = 0; row < M; row++) {
      for (int col = 0; col < N; col++) {
        if (row < carpet.length && col < carpet[0].length) {
          pat[row][col] = carpet[row][col];
        } else {
          pat[row][col] = '.';
        }
      }
    }
    for (int row = 0; row < carpet.length; row++) {
      for (int col = 0; col < carpet[0].length; col++) {
        char patChar = pat[row % M][col % N];
        if (carpet[row][col] != patChar && patChar != '.') {
          // return表示 即使在不知道basic-pat的情况下, carpet也不可能由M*N基本块构成而来
          return null;
        }
      }
    }
    return pat;
  }
}
