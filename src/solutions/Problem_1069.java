package solutions;

import java.io.ByteArrayInputStream;
import java.util.*;

public class Problem_1069 {
  public static void main(String[] args) {
    String input = "4\n" +
      "Adam Sam\n" +
      "Sam Joey\n" +
      "Sam Micheal\n" +
      "Adam Kevin\n" +
      "3\n" +
      "Sam Sam\n" +
      "Adam Sam\n" +
      "Micheal Kevin";
        /* Expected output:
            Sam
            Adam
            Adam
         */
    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
    int N = scanner.nextInt(); // N parent-child relations. N + 1 persons
    Node[] nodes = new Node[N + 1];
    Map<String, Integer> name2Index = new HashMap<>(N + 1);

    String rootName = scanner.next();
    Node rootNode = new Node(0, rootName, 0);
    nodes[0] = rootNode;
    name2Index.put(rootName, 0);

    String node1Name = scanner.next();
    Node node1 = new Node(1, node1Name, 1);
    nodes[1] = node1;
    name2Index.put(node1Name, 1);

    rootNode.childrenIndices.add(1);

    // node[2] ... node[N]
    for (int nodeIndex = 2; nodeIndex <= N; nodeIndex++) {
      String parentName = scanner.next();
      String childName = scanner.next();

      int parentIndex = name2Index.get(parentName);
      Node parentNode = nodes[parentIndex];

      Node childNode = new Node(nodeIndex, childName, parentNode.depth + 1);
      nodes[nodeIndex] = childNode;
      name2Index.put(childName, nodeIndex);
      parentNode.childrenIndices.add(nodeIndex);
    }

    // nodeTraversalList中存放的是"nodes数组的下标"
    List<Integer> nodeTraversalList = new ArrayList<>();
    traverse(nodes, nodeTraversalList, nodes[0]);

    int size = nodeTraversalList.size();
    int shift = getShift(size);
    // preCalculated中存放的是"nodes数组的下标"
    int[][] preCalculated = new int[size][shift + 1];
    for (int t = 0; t <= shift; t++) {
      int len = 1 << t;
      for (int i = 0; i < size; i++) {
        if (len == 1) {
          preCalculated[i][t] = nodeTraversalList.get(i);
          continue;
        }

        if (i + len / 2 < size) {
          int option1 = preCalculated[i][t - 1];
          int option2 = preCalculated[i + len / 2][t - 1];

          int depth1 = nodes[preCalculated[i][t - 1]].depth;
          int depth2 = nodes[preCalculated[i + len / 2][t - 1]].depth;

          if (depth1 < depth2) {
            preCalculated[i][t] = option1;
          } else {
            preCalculated[i][t] = option2;
          }
        } else { // option2 is invalid
          preCalculated[i][t] = preCalculated[i][t - 1];
        }
      }
    }

    int M = scanner.nextInt();
    for (int queryIndex = 0; queryIndex < M; queryIndex++) {
      int index1 = name2Index.get(scanner.next());
      int index2 = name2Index.get(scanner.next());

      int left = nodes[index1].lastAppearIndex;
      int right = nodes[index2].lastAppearIndex;
      // ensure left < right
      if (left > right) {
        int temp = left;
        left = right;
        right = temp;
      }

      int gap = right - left + 1;
      int myShift = getShift(gap);
      int option1 = preCalculated[left][myShift];
      int option2 = preCalculated[right - (1 << myShift) + 1][myShift]; // RMQ-ST
      int depth1 = nodes[option1].depth;
      int depth2 = nodes[option2].depth;
      if (depth1 < depth2) {
        // use option1
        System.out.println(nodes[option1].name);
      } else {
        // use option2
        System.out.println(nodes[option2].name);
      }
    }
  }

  private static void traverse(Node[] nodes, List<Integer> list, Node node) {
    node.lastAppearIndex = list.size();
    list.add(node.index);
    for (int childIndex : node.childrenIndices) {
      traverse(nodes, list, nodes[childIndex]);
      node.lastAppearIndex = list.size();
      list.add(node.index);
    }
  }

  private static class Node {
    int index;
    int depth;
    String name;
    List<Integer> childrenIndices;
    int lastAppearIndex = -1;

    Node(int index, String name, int depth) {
      this.index = index;
      this.name = name;
      this.depth = depth;
      this.childrenIndices = new ArrayList<>();
    }
  }

  private static int getShift(int N) {
    int shift = 0;
    while (N > 0) {
      N >>= 1;
      shift++;
    }
    return shift - 1;
  }
}
