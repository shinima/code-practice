package hiho;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class Problem_1053 {
  public static void main(String[] args) throws IOException {
    int T = readInt();
    for (int tc = 0; tc < T; tc++) {
      int N = readInt();
      int R = readInt();
      Tuple[] tuples = new Tuple[N];
      for (int i = 0; i < N; i++) {
        int x = readInt();
        int y = readInt();
        tuples[i] = new Tuple(x, y);
      }
      System.out.println(find(tuples, R));
    }
  }

  private static class Tuple implements Comparable<Tuple> {
    private int initX;
    private int initY;
    int x;
    int y;
    int in;

    Tuple(int initX, int initY) {
      this.initX = initX;
      this.initY = initY;
      reset();
    }

    void reset() {
      x = initX;
      y = initY;
      in = 0;
    }

    public int compareTo(Tuple o) {
      return x - o.x;
    }
  }

  private static int find(Tuple[] tuples, int R) {
    log("=============   FIND   START  ==============");
    Arrays.sort(tuples);
    int ymax = 0;
    int ysum = 0;
    for (Tuple t : tuples) {
      ymax = Math.max(ymax, t.y);
      ysum += t.y;
    }
    int high = ymax;
    int low = ysum / tuples.length;

    while (low < high) {
      int middle = (low + high) / 2;
      if (check(tuples, R, middle)) {
        high = middle;
      } else {
        low = middle + 1;
      }
    }
    return low;
  }

  private static boolean check(Tuple[] tuples, int R, int target) {
    log("=====================");
    log("check for : " + target);
    // 重置x,y,in
    for (Tuple tuple : tuples) {
      tuple.reset();
    }
    int toIndex = 0;
    for (int fromIndex = 0; fromIndex < tuples.length; fromIndex++) {
      Tuple from = tuples[fromIndex];
      if (from.in > target) {
        log("`in` > " + target + " at " + from.x);
        return false;
      }
      while (from.x - tuples[toIndex].x > R) {
        toIndex++;
      }

      while (from.y > 0 && toIndex < fromIndex) { // 向左传递
        Tuple to = tuples[toIndex];
        int capacity = target - (to.in + to.y);
        if (capacity >= from.y) {
          log(String.format("Transfer to  left %d -> %d: %d", from.x, to.x, from.y));
          to.in += from.y;
          from.y = 0;
        } else {
          log(String.format("Transfer to  left %d -> %d: %d", from.x, to.x, capacity));
          to.in += capacity;
          from.y -= capacity;
          toIndex++;
        }
      }
      if (from.y == 0) {
        continue;
      }
      // toIndex >= fromIndex
      int needToTransfer = from.in + from.y - target;
      while (needToTransfer > 0) { // 向右传递
        if (toIndex == fromIndex) {
          toIndex++;
        }
        if (toIndex == tuples.length) {
          log("!overflow at " + from.x);
          return false;
        }
        Tuple to = tuples[toIndex];
        if (to.x - from.x > R) {
          log("overflow at " + from.x);
          return false;
        }
        int capacity = target - to.in;
        // needToTransfer在这一次可以传递完成
        if (needToTransfer <= capacity) {
          log(String.format("Transfer to right %d -> %d: %d", from.x, to.x, needToTransfer));
          to.in += needToTransfer;
          needToTransfer = 0;
        } else {
          log(String.format("Transfer to right %d -> %d: %d", from.x, to.x, capacity));
          to.in += capacity;
          needToTransfer -= capacity;
          toIndex++;
        }
      }
    }

    int[] ys = new int[tuples.length];
    for (int i = 0; i < ys.length; i++) {
      ys[i] = tuples[i].y + tuples[i].in;
    }
    log(Arrays.toString(ys));
    log("success");
    return true;
  }

  private static void log(String s) {
    System.out.println(s);
  }


  private static int readInt() throws IOException {
    return readInt(System.in);
  }

  private static int readInt(InputStream in) throws IOException {
    int result = 0;
    boolean dig = false;

    while (true) {
      int c = in.read();
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
