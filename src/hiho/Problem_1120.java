package hiho;

import java.io.ByteArrayInputStream;
import java.util.*;

public class Problem_1120 {
  private static final int unknown = -1; // ?
  private static final int yes = -2;     // X
  private static final int no = -3;      // .
  private static final int either = -4;  // #

  private static final int fail = -5; // 用于resolve的返回值

  public static void main(String[] args) {
    String input = "3\n" +
      "3 3\n" +
      "-1  2 -1\n" +
      "-1  2 -1\n" +
      "-1  2 -1\n" +
      "10 10\n" +
      "  0  0  1  2 -1  1  0  0  1 -1\n" +
      "  1  1  1 -1  2  1  0  0  1  1\n" +
      " -1  1  1  1  1  0  0  0  0  0\n" +
      "  1  1  0  0  0  1  1  1  0  0\n" +
      "  0  0  0  0  0  1 -1  1  0  0\n" +
      "  0  0  0  0  0  2  2  2  0  0\n" +
      "  0  0  0  0  0  1 -1  1  0 -1\n" +
      "  0  0  0  0  0  1  1  1  0  0\n" +
      "  0  0  0  0  1  1  1  0  0  0\n" +
      " -1  0  0 -1  1 -1  1  0  0  0\n" +
      "9 10\n" +
      "  0  0  0  0  0 -1  0  0 -1  0\n" +
      "  0  0 -1  0  0  0  0  0  0  0\n" +
      "  0  0  0  0  0  0  0  1  1  1\n" +
      "  0  0  0  0  0  1  2  3 -1  1\n" +
      "  0  0  0  0 -1  1 -1 -1  3  1\n" +
      "  1  1  0  0  0  1  3 -1  2  0\n" +
      " -1  1  0  0  0  0  1  1 -1  0\n" +
      "  1  1  0  0  0  0  0  0  0  0\n" +
      "  0  0  0  0  0  0  0  0  0  0";
    // Expected output:
    // 2 4
    // 7 3
    // 5 5
    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
    int testCaseCount = scanner.nextInt();
    for (int testIndex = 0; testIndex < testCaseCount; testIndex++) {
      int M = scanner.nextInt();
      int N = scanner.nextInt();
      int[][] mines = new int[M][N];
      for (int row = 0; row < M; row++) {
        for (int col = 0; col < N; col++) {
          mines[row][col] = scanner.nextInt();
        }
      }
      process(mines);
    }
  }

  private static int[][] process(int[][] mines) {
    int M = mines.length;
    int N = mines[0].length;
    Helper helper = new Helper(M, N, mines);

    System.out.println("Step-1:");
    helper.pprint();

    int yesCount = 0;
    int noCount = 0;

    // 第一步. 找到mine=0的格子, 并把其周围的格子设置为no
    for (int t = 0; t < helper.size; t++) {
      if (helper.get(t) == 0) {
        for (int neighbor : helper.neighbors(t)) {
          if (helper.get(neighbor) == unknown) {
            helper.set(neighbor, no);
            noCount++;
          }
        }
      }
    }

    // todo 优化思路: 找到所有的?点之后, 将这些点以 `?周围出现的数字最小值` 的顺序进行排序.


    System.out.println("Step-2:");
    helper.pprint();

    // 第二步. 找到目前仍然未知(?)的格子(unknowns), 确定(resolve)该格子的状态(yes/no/either)
    for (int t = 0; t < helper.size; t++) {
      if (helper.get(t) == unknown) {
        int resolved = helper.resolve(t);
        if (resolved == yes) {
          helper.set(t, yes);
          yesCount++;
        } else if (resolved == no) {
          helper.set(t, no);
          noCount++;
        } else { // either
          helper.set(t, either);
        }
      }
    }
    System.out.println("Result:");
    helper.pprint();
    System.out.println("yesCount: " + yesCount + " noCount: " + noCount);
    System.out.println();
    System.out.println();
    return mines;
  }

  private static String asSymbol(int mine) {
    if (mine >= 0) {
      return String.valueOf(mine);
    } else if (mine == yes) {
      return "X";
    } else if (mine == no) {
      return ".";
    } else if (mine == either) {
      return "#";
    } else if (mine == unknown) {
      return "?";
    } else {
      return "ERROR";
    }
  }

  private static class Helper {
    final int M;
    final int N;
    final int[][] mines;
    final int size;

    Helper(int M, int N, int[][] mines) {
      this.M = M;
      this.N = N;
      this.size = M * N;
      this.mines = mines;
    }

    /**
     * 设置t点为yes或是no之后, 用于判断t点是否符合周围的约束.<br/>
     * 周围的约束包括两项:<br/>
     * 如果 get(neighbor) == unknown, 则resolve(neighbor)不能为fail<br/>
     * 如果 get(neighbor) > 0, check(neighbor)不能为false<br/>
     */
    boolean test(int t) {
      assert get(t) == yes || get(t) == no;
      for (int neighbor : neighbors(t)) {
        int neighborStatus = get(neighbor);
        if (neighborStatus == unknown) {
          if (resolve(neighbor) == fail) {
            return false;
          }
        } else if (neighborStatus > 0) {
          if (!check(neighbor)) {
            return false;
          }
        } // else  yes/no/either提供不了额外信息. 不处理
      }
      return true;
    }


    /**
     * 判断get(t)是否符合要求. 调用该函数需要保证: get(t) > 0
     */
    boolean check(int t) {
      int mine = get(t);
      assert mine >= 0;
      List<Integer> yesNeighbors = filterNeighbors(t, yes);
      List<Integer> unknownNeighbors = filterNeighbors(t, unknown);
      int yesCount = yesNeighbors.size();
      int unknownCount = unknownNeighbors.size();

      boolean fail = false;
      if (mine < yesCount || mine > yesCount + unknownCount) {
        fail = true;
      } else if (mine == yesCount) { // 剩下的unknown均为no
        setAll(unknownNeighbors, no);
        fail = !testAll(unknownNeighbors);
        setAll(unknownNeighbors, unknown);
      } else if (mine == yesCount + unknownCount) { // 剩下的unknown均为yes
        setAll(unknownNeighbors, yes);
        fail = !testAll(unknownNeighbors);
        setAll(unknownNeighbors, unknown);
      }
      // else: yesCount < mine && mine < yesCount + unknownCount
      // 剩下的unknown可能为yes, 也可能为no
      // 暂时先认为是正确的.
      // todo 这里可能会出现问题

      return !fail;
    }

    /**
     * 计算t点是否有地雷. 调用该函数需要保证: get(t) == unknown
     */
    int resolve(int t) {
      assert get(t) == unknown;

      set(t, yes);
      boolean canBeYes = test(t);

      set(t, no);
      boolean canBeNo = test(t);

      set(t, unknown);

      if (canBeYes && canBeNo) {
        return either;
      } else if (canBeYes) {
        return yes;
      } else if (canBeNo) {
        return no;
      } else {
        return fail;
      }
    }

    int[] neighbors(int t) {
      final int topLeft = t - N - 1;
      final int top = t - N;
      final int topRight = t - N + 1;
      final int left = t - 1;
      final int right = t + 1;
      final int bottomLeft = t + N - 1;
      final int bottom = t + N;
      final int bottomRight = t + N + 1;

      if (t == 0) { // 左上角
        return new int[]{bottom, right, bottomRight};
      } else if (t == N - 1) { // 右上角
        return new int[]{left, bottom, bottomLeft};
      } else if (t == (M - 1) * N) { // 左下角
        return new int[]{top, right, topRight};
      } else if (t == N * M - 1) { // 右下角
        return new int[]{left, top, topLeft};
      }
      int row = t / N;
      int col = t % N;
      if (row == 0) {// 上边
        return new int[]{left, right, bottomLeft, bottom, bottomRight};
      } else if (row == M - 1) {//下边
        return new int[]{topLeft, top, topRight, left, right};
      } else if (col == 0) { // 左边
        return new int[]{top, bottom, topRight, right, bottomRight};
      } else if (col == N - 1) { // 右边
        return new int[]{top, bottom, topLeft, left, bottomLeft};
      } else { // 中间
        return new int[]{topLeft, top, topRight, left, right, bottomLeft, bottom, bottomRight};
      }
    }

    List<Integer> filterNeighbors(int t, int mine) {
      List<Integer> result = new ArrayList<>();
      for (int neighbor : neighbors(t)) {
        if (get(neighbor) == mine) {
          result.add(neighbor);
        }
      }
      return result;
    }

    int get(int t) {
      return mines[t / N][t % N];
    }

    void set(int t, int mine) {
      mines[t / N][t % N] = mine;
    }

    void setAll(List<Integer> ts, int mine) {
      for (int t : ts) {
        set(t, mine);
      }
    }

    boolean testAll(List<Integer> ts) {
      for (int t : ts) {
        if (!test(t)) {
          return false;
        }
      }
      return true;
    }

    void pprint() {
      System.out.println("==============");
      for (int row = 0; row < M; row++) {
        for (int col = 0; col < N; col++) {
          int mine = mines[row][col];
          System.out.print(asSymbol(mine));
        }
        System.out.println();
      }
      System.out.println("==============");
      System.out.println();
    }
  }
}
