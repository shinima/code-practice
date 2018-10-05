package hiho;

import java.util.Scanner;

public class Problem_1039 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int T = scanner.nextInt();
    for (int testCase = 0; testCase < T; testCase++) {
      String S = scanner.next();
      int maxClearCount = 0;
      for (char c : "ABC".toCharArray()) {
        for (int i = 0; i <= S.length(); i++) {
          char[] chars = new char[S.length() + 1];
          System.arraycopy(S.toCharArray(), 0, chars, 0, S.length());
          System.arraycopy(chars, i, chars, i + 1, S.length() - i);
          chars[i] = c;
          maxClearCount = Math.max(maxClearCount, chars.length - clear(chars, chars.length));
        }
      }
      System.out.println(maxClearCount);
    }
  }

  private static int clear(char[] chars, int size) {
    boolean[] flag = new boolean[size];
    for (int i = 0; i < size - 1; i++) {
      if (chars[i] == chars[i + 1]) {
        flag[i] = flag[i + 1] = true;
      }
    }
    int t = 0;
    for (int i = 0; i < size; i++) {
      if (!flag[i]) {
        chars[t++] = chars[i];
      }
    }

    if (t == size) { // 没有发生消除
      return size;
    } else {
      return clear(chars, t);
    }
  }
}
