package solutions;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Problem_1114 {
  public static void main(String[] args) {
    String input = "2\n" +
      "3\n" +
      "1 1 1\n" +
      "10\n" +
      "1 2 1 2 2 3 2 2 2 2 ";
    // Expected output:
    // 1 2    // 一定为地雷的格子的数量
    // 2 1 3  // 所有一定为地雷的格子的位置
    // 7 1 3 5 6 7 9 10
    // 3 2 4 8
    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
    int testCaseCount = scanner.nextInt();
    for (int testIndex = 0; testIndex < testCaseCount; testIndex++) {
      int N = scanner.nextInt(); // 迷宫的宽度. (迷宫高度固定为2)
      int[] counts = new int[N + 1];
      for (int i = 1; i <= N; i++) {
        counts[i] = scanner.nextInt();
      }
      if (N == 1) {
        processSingle(counts);
      } else {
        process(counts);
      }
    }
  }

  private static void processSingle(int[] counts) {
    if (counts[1] == 0) {
      System.out.println("0");
      System.out.println("1 1");
    } else { // counts[1] == 1
      System.out.println("1 1");
      System.out.println("0");
    }
  }

  private static void process(int[] counts) {
    int N = counts.length - 1;
    Status[] result = new Status[N + 1];
    Arrays.fill(result, Status.unset);
    Context context = new Context(counts, result, N);

    // 当前的状态
    Status[] array = new Status[N + 1];
    Arrays.fill(array, Status.unset);

    array[1] = Status.yes;
    test(context, array, 2);
    array[1] = Status.no;
    test(context, array, 2);

    int yesCount = 0;
    int noCount = 0;
    for (int i = 1; i <= N; i++) {
      if (context.result[i] == Status.yes) {
        yesCount++;
      } else if (context.result[i] == Status.no) {
        noCount++;
      }
    }

    System.out.print(yesCount + " ");
    for (int i = 1; i <= N; i++) {
      if (context.result[i] == Status.yes) {
        System.out.print(i);
        if (yesCount != 1) {
          System.out.print(" ");
        }
        yesCount--;
      }
    }
    System.out.println();

    System.out.print(noCount + " ");
    for (int i = 1; i <= N; i++) {
      if (context.result[i] == Status.no) {
        System.out.print(i);
        if (noCount != 1) {
          System.out.print(" ");
        }
        noCount--;
      }
    }
    System.out.println();
  }

  private static void test(Context context, Status[] array, int start) {
    int N = array.length - 1;
    int t = start;
    while (t <= context.N) {
      Status next = tryCalculate(context, array, t);
      if (next == Status.fail) {
        return;
      }
      array[t] = next;
      t++;
    }
    // 没有提前return的话, 再检查一下最后一个counts[N]是否符合要求
    // 如果没问题的话, 表示array中存放的是一种合理的布局方式
    int lastYesCount = 0;
    if (array[N - 1] == Status.yes) {
      lastYesCount++;
    }
    if (array[N] == Status.yes) {
      lastYesCount++;
    }
    if (lastYesCount == context.counts[N]) {
      merge(context.result, array);
    }
  }

  private static void merge(Status[] result, Status[] array) {
    for (int i = 1; i < result.length; i++) {
      if (result[i] == Status.unset) {
        result[i] = array[i];
      } else if (result[i] != Status.either) { // yes or no
        result[i] = (result[i] == array[i]) ? array[i] : Status.either;
      }
    }
  }

  // 1 <= t <= N
  private static Status tryCalculate(Context context, Status[] statusArray, int t) {
    // 根据 t-1
    Status sa;
    int c = 0;
    c += (t - 1 >= 1 && statusArray[t - 1] == Status.yes) ? 1 : 0;
    c += (t - 2 >= 1 && statusArray[t - 2] == Status.yes) ? 1 : 0;

    int count = context.counts[t - 1];
    if (c == count - 1) {
      sa = Status.yes;
    } else if (c == count) {
      sa = Status.no;
    } else {
      sa = Status.fail;
    }

    return sa;
  }

  private enum Status {
    unset,  // 没设置过
    yes,    // 一定有炸弹
    no,     // 一定没有炸弹

    either, // 有和没有都有可能
    fail,   // 放置失败
  }

  private static class Context {
    int[] counts;
    Status[] result;
    int N;

    Context(int[] counts, Status[] result, int N) {
      this.counts = counts;
      this.result = result;
      this.N = N;
    }
  }
}
