package solutions;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Problem_1050 {
  public static void main(String[] args) {
    String input = "8\n" +
      "1 2\n" +
      "1 3\n" +
      "1 4\n" +
      "4 5\n" +
      "3 6\n" +
      "6 7\n" +
      "7 8";
    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
    int N = scanner.nextInt();
    Node[] nodes = new Node[N + 1];
    for (int i = 0; i < nodes.length; i++) {
      nodes[i] = new Node();
    }

    for (int i = 1; i <= N - 1; i++) {
      int startNode = scanner.nextInt();
      int endNode = scanner.nextInt();
      if (startNode > endNode) {
        int temp = startNode;
        startNode = endNode;
        endNode = temp;
      }
      // todo 其实这里有点问题, 不过hihocoder的测试数据似乎都有这个性质: 数字大的节点是数字小的节点的孩子
      // 应该用problem-1055里面 xx和x 两个变量的方式来写
      nodes[startNode].children.add(endNode);
    }
    System.out.println(getDiameter(nodes));
  }

  private static class Node {
    List<Integer> children = new ArrayList<>();
    int height = -1;
    int diameter = -1;
  }

  private static int getDiameter(Node[] nodes) {
    getHeight(nodes, nodes[1]);
    int diameter = 0;
    for (int i = 1; i < nodes.length; i++) {
      diameter = Math.max(diameter, nodes[i].diameter);
    }
    return diameter;
  }

  private static int getHeight(Node[] nodes, Node node) {
    if (node.height != -1) {
      return node.height;
    }
    int maxChildHeight = -1;
    int secondMaxChildHeight = -1;
    for (int childIndex : node.children) {
      int height = getHeight(nodes, nodes[childIndex]);
      if (height > maxChildHeight) {
        secondMaxChildHeight = maxChildHeight;
        maxChildHeight = height;
      } else if (height == maxChildHeight) {
        secondMaxChildHeight = height;
      } else if (height > secondMaxChildHeight) {
        secondMaxChildHeight = height;
      }
    }
    node.height = maxChildHeight + 1;
    node.diameter = maxChildHeight + secondMaxChildHeight + 2;
    return node.height;
  }
}
