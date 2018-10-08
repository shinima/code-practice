package hiho;

import java.util.Arrays;
import java.util.Scanner;

public class Hiho_223 {
  private static class Tuple implements Comparable<Tuple> {
    final int s, t;

    Tuple(int s, int t) {
      this.s = s;
      this.t = t;
    }

    public int compareTo(Tuple o) {
      if (this.s == o.s) {
        return this.t - o.t;
      } else {
        return this.s - o.s;
      }
    }
  }

  public static void main(String[] args) {
    String input = "5 1 5\n" +
      "1 2    \n" +
      "1 3  \n" +
      "2 4  \n" +
      "3 5  \n" +
      "4 5 ";
    Scanner scanner = new Scanner(input);
    int N = scanner.nextInt();
    int X = scanner.nextInt();
    int Y = scanner.nextInt();
    Tuple[] tuples = new Tuple[N];
    for (int i = 0; i < N; i++) {
      int s = scanner.nextInt();
      int t = scanner.nextInt();
      tuples[i] = new Tuple(s, t);
    }
    Arrays.sort(tuples);

    int pickCount = 0;
    int extend = X;
    int i = 0;

    // 跳过在左侧的区间
    while (i < N && tuples[i].t <= X) {
      i++;
    }

    while (extend < Y) {
      if (i == N || tuples[i].s > extend) {
        System.out.println(-1);
        return;
      }
      int pick = i;
      while (true) {
        i++;
        if (i == N || tuples[i].s > extend) {
          break;
        }
        if (tuples[i].t > tuples[pick].t) {
          pick = i;
        }
      }
//      System.out.println("pick [" + tuples[pick].s + ", " + tuples[pick].t + "]");
      pickCount++;
      extend = tuples[pick].t;
    }
    System.out.println(pickCount);
  }
}
