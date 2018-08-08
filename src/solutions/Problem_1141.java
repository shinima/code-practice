package solutions;

import java.util.Scanner;

public class Problem_1141 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int N = scanner.nextInt();
    int[] array = new int[N];
    for (int i = 0; i < N; i++) {
      array[i] = scanner.nextInt();
    }
    System.out.println(mergeSortAndCountInversions(array, new int[N], 0, N - 1));
  }

  private static long mergeSortAndCountInversions(int[] array, int[] other, int start, int end) {
    if (start == end) {
      return 0;
    }
    int median = (start + end) / 2;
    long leftCount = mergeSortAndCountInversions(array, other, start, median);
    long rightCount = mergeSortAndCountInversions(array, other, median + 1, end);

    // Merge array[start...median] & array[median+1, end] to other[start...end]
    long betweenCount = 0;
    int i = start;
    int j = median + 1;
    int t = start;
    while (i <= median && j <= end) {
      if (array[i] <= array[j]) {
        other[t++] = array[i++];
      } else {
        other[t++] = array[j++];
        betweenCount += median - i + 1;
      }
    }
    while (i <= median) {
      other[t++] = array[i++];
    }
    while (j <= end) {
      other[t++] = array[j++];
    }

    // Copy back to array
    System.arraycopy(other, start, array, start, end - start + 1);

    // Return count of inversions
    return leftCount + betweenCount + rightCount;
  }
}
