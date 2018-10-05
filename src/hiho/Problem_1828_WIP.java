package hiho;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Problem_1828_WIP {
  public static void main(String[] args) {
    String input = "2 2\n" +
      "S#\n" +
      "#T\n" +
      "2 5\n" +
      "SB###\n" +
      "##P#T\n" +
      "4 7\n" +
      "SP.....\n" +
      "P#.....\n" +
      "......#\n" +
      "B...##T\n" +
      "0 0";
    Scanner sc = new Scanner(input);
    while (true) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      if (N == 0 && M == 0) {
        break;
      }
      char[][] palace = new char[N][];
      for (int row = 0; row < N; row++) {
        palace[row] = sc.next().toCharArray();
      }
      System.out.println(calMinTime(palace, N, M));
    }
  }

  static class Item {
    final int pos;
    final int oxy;
    final int time;

    Item(int pos, int oxy, int time) {
      this.pos = pos;
      this.oxy = oxy;
      this.time = time;
    }

    Item setPos(int pos) {
      return new Item(pos, this.oxy, this.time);
    }

    Item setOxy(int oxy) {
      return new Item(this.pos, oxy, this.time);
    }

    Item setTime(int time) {
      return new Item(this.pos, this.oxy, time);
    }
  }

  static int calMinTime(char[][] palace, int N, int M) {
    int start = -1;
    int target = -1;
    for (int t = 0; t < N * M; t++) {
      int row = t / M;
      int col = t % M;
      if (palace[row][col] == 'S') {
        start = t;
      } else if (palace[row][col] == 'T') {
        target = t;
      }
    }
    Set<Item> set = new HashSet<>();
    set.add(new Item(start, 0, 0));

    while (set.isEmpty()) { // TODO 条件是什么呢？
      Set<Item> nextSet = new HashSet<>();
      for (Item item : set) {
        int row = item.pos / M;
        int col = item.pos % M;
        if (col > 0) {
          // go left
          int nextPos = row * M + (col - 1);
          char room = palace[row][col - 1];
          if (room == '#') {
            if (item.oxy > 0) {
              nextSet.add(new Item(nextPos, item.oxy - 1, item.time + 1));
            }
          } else if (room == 'B') {
            nextSet.add(new Item(nextPos, item.oxy + 1, item.time + 1));
          } else if (room == 'P') {
            nextSet.add(new Item(nextPos, item.oxy, item.time));
          } else {
            // normal room
            nextSet.add(new Item(nextPos, item.oxy, item.time + 1));
          }
        }
      }
    }
    return -1;
  }
}
