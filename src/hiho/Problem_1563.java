package hiho;

import java.util.Scanner;

public class Problem_1563 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    // N个座位
    int N = scanner.nextInt();
    // M个小伙伴
    int M = scanner.nextInt();
    boolean[] used = new boolean[N];
    int slotCount = 0;
    for (int i = 0; i < N; i++) {
      if (scanner.nextInt() == 1) {
        used[i] = true;
      } else {
        slotCount++;
      }
    }
    if (slotCount < M) {
      System.out.println(-1);
      return;
    }

    int[] slots = new int[slotCount];
    int t = 0;
    for (int i = 0; i < N; i++) {
      if (!used[i]) {
        slots[t++] = i;
      }
    }

//        System.out.println(Arrays.toString(slots));

    int pivot = 0;
    int start = 0;
    int end = 0;
    while (end - start + 1 < M) {
      int left = distance(N, slots, start - 1, pivot);
      int right = distance(N, slots, pivot, end + 1);
      if (left < right) {
        start--;
      } else {
        end++;
      }
    }

    int totalDistance = 0;
    for (int i = start; i < pivot; i++) {
      totalDistance += distance(N, slots, i, pivot);
    }
    for (int i = pivot + 1; i <= end; i++) {
      totalDistance += distance(N, slots, pivot, i);
    }
//        System.out.println("pivot: " + pivot
//            + "   total-distance: " + totalDistance
//            + "   left: " + (pivot - start)
//            + "   right: " + (end - pivot));

    int result = totalDistance;
    while (pivot < t) {
      int d = distance(N, slots, pivot, pivot + 1);
      int leftCount = pivot - start;
      int rightCount = end - pivot - 1;
      totalDistance -= (rightCount - leftCount) * d;
      pivot++;

      while (distance(N, slots, pivot, end + 1) < distance(N, slots, start, pivot)) {
        totalDistance -= distance(N, slots, start, pivot);
        start++;
        end++;
        totalDistance += distance(N, slots, pivot, end);
      }

//            System.out.println("pivot: " + pivot
//                + "   total-distance: " + totalDistance
//                + "   left: " + (pivot - start)
//                + "   right: " + (end - pivot));

      result = Math.min(result, totalDistance);
    }

    System.out.println(result);
  }

  private static int distance(int N, int[] slots, int i, int j) {
    int size = slots.length;

    int x = (i / size) * N;
    int rex = i % size;
    if (rex < 0) {
      rex += size;
      x -= N;
    }
    x += slots[rex];

    int y = (j / size) * N;
    int rey = j % size;
    if (rey < 0) {
      rey += size;
      y -= N;
    }
    y += slots[rey];


    return y - x;
  }
}
