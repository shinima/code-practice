package solutions;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

// AC
public class Problem_1105 {
  public static void main(String[] args) {
    String input = "5\n" +
      "A 77751\n" +
      "A 1329\n" +
      "A 26239\n" +
      "A 80317\n" +
      "T ";
    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
    int N = scanner.nextInt();
    Heap heap = new Heap(N);
    for (int i = 0; i < N; i++) {
      String operation = scanner.next();
      if (operation.equals("A")) {
        int value = scanner.nextInt();
        heap.offer(value);
      } else {
        System.out.println(heap.poll());
      }
    }
  }

  private static class Heap {
    // max-heap
    int capacity;
    int[] array;
    int size;

    Heap(int capacity) {
      this.capacity = capacity;
      array = new int[capacity];
      size = 0;
    }

    void offer(int value) {
      array[size] = value;
      size++;
      siftUp(size - 1);
    }

    int poll() {
      int result = array[0];
      array[0] = array[size - 1];
      size--;
      siftDown(0);
      return result;
    }

    void siftUp(int t) {
      int value = array[t];
      while (t != 0) {
        int p = (t - 1) / 2;
        if (array[p] < value) {
          array[t] = array[p];
          t = p;
        } else {
          break;
        }
      }
      array[t] = value;
    }

    void siftDown(int t) {
      int value = array[t];
      while (t < size) {
        int left = 2 * t + 1;
        int right = 2 * t + 2;

        int child = left;
        if (right < size && array[right] > array[left]) {
          child = right;
        }
        if (child < size && array[child] > value) {
          array[t] = array[child];
          t = child;
        } else {
          break;
        }
      }
      array[t] = value;
    }
  }
}
