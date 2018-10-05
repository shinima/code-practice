package hiho;

import java.util.Scanner;

public class Problem_1040 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int T = scanner.nextInt();
    for (int tc = 0; tc < T; tc++) {
      Line[] lines = new Line[4];
      for (int i = 0; i < 4; i++) {
        Line line = new Line();
        line.x1 = scanner.nextInt();
        line.y1 = scanner.nextInt();
        line.x2 = scanner.nextInt();
        line.y2 = scanner.nextInt();
        lines[i] = line;
      }
      if (isRect(lines)) {
        System.out.println("YES");
      } else {
        System.out.println("NO");
      }
    }
  }

  private static boolean isRect(Line[] lines) {
    int justOneIntersectionCount = 0;
    for (int i = 0; i < lines.length; i++) {
      Line l1 = lines[i];
      for (int j = i + 1; j < lines.length; j++) {
        Line l2 = lines[j];
        if (justOneIntersection(l1, l2)) {
          justOneIntersectionCount++;
          if (!formOneCornerOfRect(l1, l2)) {
            return false;
          }
        }
      }
    }
    return justOneIntersectionCount == 4;
  }

  private static boolean formOneCornerOfRect(Line l1, Line l2) {
    boolean one = l1.x1 == l2.x1 && l1.y1 == l2.y1;
    boolean two = l1.x2 == l2.x2 && l1.y2 == l2.y2;
    boolean three = l1.x1 == l2.x2 && l1.y1 == l2.y2;
//        boolean four = l1.x2 == l2.x1 && l1.y2 == l2.y1;
    if (one) {
      int x = l1.x1;
      int y = l1.y1;
      return (l1.y2 - y) * (l2.y2 - y) == -(l1.x2 - x) * (l2.x2 - x);
    } else if (two) {
      return formOneCornerOfRect(l1.flip(), l2.flip());
    } else if (three) {
      return formOneCornerOfRect(l1, l2.flip());
    } else { // four
      return formOneCornerOfRect(l1.flip(), l2);
    }
  }

  private static boolean justOneIntersection(Line l1, Line l2) {
    boolean one = l1.x1 == l2.x1 && l1.y1 == l2.y1;
    boolean two = l1.x2 == l2.x2 && l1.y2 == l2.y2;
    boolean three = l1.x1 == l2.x2 && l1.y1 == l2.y2;
    boolean four = l1.x2 == l2.x1 && l1.y2 == l2.y1;
    int intersectionCount = 0;
    if (one) intersectionCount++;
    if (two) intersectionCount++;
    if (three) intersectionCount++;
    if (four) intersectionCount++;
    return intersectionCount == 1;
  }

  static class Line {
    int x1, y1, x2, y2;

    Line flip() {
      Line line = new Line();
      line.x1 = x2;
      line.y1 = y2;
      line.x2 = x1;
      line.y2 = y1;
      return line;
    }
  }
}
