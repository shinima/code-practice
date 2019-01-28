package hiho;

import java.util.Scanner;

public class Hiho_239 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner("3\n" +
      "LLOLLALL  \n" +
      "OLLLOOOO  \n" +
      "OOAAOOOO");
//    Scanner scanner = new Scanner(System.in);
    int T = scanner.nextInt();
    for (int i = 0; i < T; i++) {
      char[] record = scanner.next().toCharArray();
      if (test(record)) {
        System.out.println("YES");
      } else {
        System.out.println("NO");
      }
    }
  }

  private static boolean test(char[] record) {
    int missCount = 0;
    int successiveDelayCount = 0;
    for (char c : record) {
      if (c == 'A') {
        missCount++;
        successiveDelayCount = 0;
        if (missCount > 1) {
          return false;
        }
      } else if (c == 'L') {
        successiveDelayCount++;
        if (successiveDelayCount == 3) {
          return false;
        }
      } else {
        successiveDelayCount = 0;
      }
    }
    return true;
  }
}
