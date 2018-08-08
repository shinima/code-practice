package solutions;

import java.util.Scanner;

public class Problem_1042 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int N = scanner.nextInt();
    int M = scanner.nextInt();
    int L = scanner.nextInt();
    int l = scanner.nextInt();
    int r = scanner.nextInt();
    int t = scanner.nextInt();
    int b = scanner.nextInt();
    System.out.println(maxSize(N, M, L, new Rect(l, r, t, b)));
  }

  private static int maxSize(int N, int M, int L, Rect rect) {
    if (N < M) {
      return maxSize(M, N, L, new Rect(rect.t, rect.b, rect.l, rect.r));
    }
    // N >= M
    int left = rect.l;
    int top = rect.t;
    int right = N - rect.r;
    int bottom = N - rect.b;
    if (left < right) {
      return maxSize(N, M, L, new Rect(N - rect.r, N - rect.l, rect.t, rect.b));
    }
    if (top < bottom) {
      return maxSize(N, M, L, new Rect(rect.l, rect.r, M - rect.b, M - rect.t));
    }
    if (left < top) {
      return maxSize(M, N, L, new Rect(rect.t, rect.b, N - rect.r, N - rect.l));
    }
    int width = rect.r - rect.l;
    int height = rect.b - rect.t;
    // left >= right && left >= top >= bottom
    if (L <= 4 * Math.min(left, M)) {
      return unrestricted(L);
    } else if (L <= 2 * (M + left)) {
      return oneSideFixed(left, L);
    } else if (L < 2 * (M + left) + 2 * width) {
      return left * M + top * (L - 2 * (M + left)) / 2;
    } else {
      return Math.min(oneSideFixed(M, L) - width * height, N * M - width * height);
    }
  }

  private static int oneSideFixed(int shorter, int L) {
    int longer = L / 2 - shorter;
    return shorter * longer;
  }

  private static int unrestricted(int L) {
    if (L % 4 == 0) {
      return (L / 4) * (L / 4);
    } else if (L % 4 == 2) {
      return (L / 4) * (L / 4 + 1);
    } else {
      return unrestricted(L - 1);
    }
  }

  private static class Rect {
    int l, r, t, b;

    Rect(int l, int r, int t, int b) {
      this.l = l;
      this.r = r;
      this.b = b;
      this.t = t;
    }
  }
}
