package hiho;

import java.io.IOException;
import java.util.Arrays;

public class Problem_1133 {
  public static void main(String[] args) throws IOException {
    int N = readInt();
    int order = readInt();
    int[] array = new int[N];
    for (int i = 0; i < N; i++) {
      array[i] = readInt();
    }
    System.out.println(quickSelect(array, 0, array.length - 1, order - 1));
  }

  private static int quickSelect(int[] array, int left, int right, int targetIndex) {
    if (right - left <= 40) {
      Arrays.sort(array, left, right + 1);
      return array[targetIndex];
    }
    int median = (left + right) / 2;
    int pivot = pickPivot(array, left, median, right);
    swap(array, median, right - 1); // put pivot at array[right-1]
    int x = left;
    int y = right - 1;
    while (true) {
      x++;
      while (array[x] < pivot) {
        x++;
      }

      y--;
      while (array[y] > pivot) {
        y--;
      }

      if (x < y) {
        swap(array, x, y);
      } else {
        break;
      }
    }
    swap(array, x, right - 1); // put pivot back to array[x]
    // now we can ensure that array[x] = pivot.
    if (targetIndex == x) {
      return pivot;
    } else if (targetIndex < x) {
      return quickSelect(array, left, x - 1, targetIndex);
    } else { // targetIndex > x
      return quickSelect(array, x + 1, right, targetIndex);
    }
  }

  private static int pickPivot(int[] array, int left, int median, int right) {
    if (array[left] > array[median]) {
      swap(array, left, median);
    }
    if (array[left] > array[right]) {
      swap(array, left, right);
    }
    if (array[median] > array[right]) {
      swap(array, median, right);
    }
    return array[median];
  }

  private static void swap(int[] array, int m, int n) {
    int temp = array[m];
    array[m] = array[n];
    array[n] = temp;
  }

  private static int readInt() throws IOException {
    int result = 0;
    boolean dig = false;

    while (true) {
      int c = System.in.read();
      if (c == -1) {
        break;
      }
      if (c >= '0' && c <= '9') {
        result = result * 10 + (c - '0');
        dig = true;
      } else if (dig) {
        break;
      }
    }
    return result;
  }
}
