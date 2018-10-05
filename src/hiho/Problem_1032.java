package hiho;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Problem_1032 {
  public static void main(String[] args) {
    Scanner scanner;
    scanner = new Scanner(new ByteArrayInputStream(("10\n" +
      "bccbbaacccccccaabacacccbcacaacacbbabbccc\n" +
      "cbaccbbbbcbbcabcacabaabaacabcbccbcbcbbbc\n" +
      "aaababaaacccbbccaabbabacacacbcacaaabcbaa\n" +
      "bacccccabbacacabccabbacabcccbbcbcaccabbb\n" +
      "bacccbccccbbbaacbbcbbaccabcabccabaccabaa\n" +
      "cacccbcbbcbbcaaaabbabbaaabbcbbaaabcacccc\n" +
      "caaaaabcbcaaacabcaaaaccaabbcacacccacacac\n" +
      "ccbcaaacbababbbccbbcacacbaaababcabcbbaab\n" +
      "cbbcbabacbaccbacbcacbaccbcbcbababacbcaab\n" +
      "bbcccaaabacabcbccaaabcabacbcbabaacbcbcac\n").getBytes()));
    int N = scanner.nextInt();
    for (int i = 0; i < N; i++) {
      String string = scanner.next();
      System.out.println("[manacher] " + string + "  " + manacher(string));
      System.out.println("[naive]    " + string + "  " + naiveLongestPalindrome(string));
      System.out.println();
    }
  }

  private static int naiveLongestPalindrome(String string) {
    char[] word = prepare(string);
    int maxRL = 0;
    for (int middle = 0; middle < word.length; middle++) {
      int RL = 1;
      while (middle - RL >= 0 && middle + RL < word.length && word[middle - RL] == word[middle + RL]) {
        RL++;
      }
      maxRL = Math.max(maxRL, RL);
    }
    return maxRL - 1;
  }

  private static int manacher(String string) {
    char[] word = prepare(string);
    int pos = 0; // position that has the max-right
    int t = 1;
    int[] RLArray = new int[word.length];
    RLArray[0] = 1;
    while (t < word.length) {
      int maxRight = RLArray[pos] + pos - 1;
      int RL = t >= maxRight ? 1 : Math.min(RLArray[2 * pos - t], maxRight - t + 1);
      while (t - RL >= 0
        && t + RL < word.length
        && word[t - RL] == word[t + RL]) {
        RL++;
      }
      RLArray[t] = RL;
      int cntRight = t + RL - 1;
      if (cntRight > maxRight) {
        pos = t;
      }
      t++;
    }
    int maxRL = 0;
    for (int RL : RLArray) {
      maxRL = Math.max(maxRL, RL);
    }
    return maxRL - 1;
  }

  private static char[] prepare(String string) {
    char[] result = new char[string.length() * 2 + 1];
    for (int i = 0; i < result.length; i++) {
      if (i % 2 == 0) {
        result[i] = '#';
      } else {
        result[i] = string.charAt(i / 2);
      }
    }
    return result;
  }
}
