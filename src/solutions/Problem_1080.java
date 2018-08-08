package solutions;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Problem_1080 {
  public static void naiveTest(String input) {
    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
    int N = scanner.nextInt() + 1;
    int M = scanner.nextInt();
    int[] prices = new int[N];
    for (int i = 0; i < N; i++) {
      prices[i] = scanner.nextInt();
    }

    for (int i = 0; i < M; i++) {
      int operation = scanner.nextInt();
      int start = scanner.nextInt();
      int end = scanner.nextInt();
      int value = scanner.nextInt();
      if (operation == 0) { // value是相对值
        naiveUpdateDelta(prices, start, end, value);
      } else { // value是绝对值
        naiveUpdateValue(prices, start, end, value);
      }
    }
    naiveSum(prices);
//        System.out.println(naiveSum(prices));
  }

  public static void excitingTest(String input) {
    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
    int N = scanner.nextInt() + 1;
    int M = scanner.nextInt();
    int[] prices = new int[N];
    for (int i = 0; i < N; i++) {
      prices[i] = scanner.nextInt();
    }

    Node root = new Node(prices);

    for (int i = 0; i < M; i++) {
      int operation = scanner.nextInt();
      int start = scanner.nextInt();
      int end = scanner.nextInt();
      int value = scanner.nextInt();
      if (operation == 0) { // value是相对值
        root.updateDelta(start, end, value);
      } else { // value是绝对值
        root.updateValue(start, end, value);
      }
      root.getValue();
//            System.out.println(root.getValue());
    }
  }

  private static class Node {
    int start;
    int end;
    Node left;
    Node right;
    LazyTag deltaTag;
    LazyTag valueTag;
    int value; // 结点的值. 对应prices[start...end]的和

    public String toString() {
      if (isLeaf()) {
        return String.format("Node{%d = %d}", start, getValue());
      } else {
        return String.format("Node{%d...%d = %d}", start, end, getValue());
      }
    }

    Node(int[] prices) {
      this(prices, 0, prices.length - 1);
    }

    Node(int[] prices, int start, int end) {
      this.start = start;
      this.end = end;
      if (start != end) {
        int median = (start + end) / 2;
        left = new Node(prices, start, median);
        right = new Node(prices, median + 1, end);
        value = left.value + right.value;
        deltaTag = new LazyTag();
        valueTag = new LazyTag();
      } else { // leaf node
        value = prices[start];
        // leaf node没有left right, 也没有lazyTags
      }
    }

    void updateDelta(int start, int end, int delta) {
      if (start > this.end || end < this.start) {
        return;
      }
      if (start <= this.start && end >= this.end) { // 如果是leaf, 则该if判断一定为true
        if (isLeaf()) {
          value += delta;
        } else { // internal node
          clearLazyTags();
          deltaTag.setValue(delta);
        }
      } else { // must be internal node
        clearLazyTags();
        left.updateDelta(start, end, delta);
        right.updateDelta(start, end, delta);
        value = left.getValue() + right.getValue();
      }
    }

    void updateValue(int start, int end, int newValue) {
      if (start > this.end || end < this.start) {
        return;
      }
      if (start <= this.start && end >= this.end) { // 如果是leaf, 则该if判断一定为true
        if (isLeaf()) {
          value = newValue;
        } else { // internal node
          clearLazyTags();
          valueTag.setValue(newValue);
        }
      } else { // must be internal node
        clearLazyTags();
        left.updateValue(start, end, newValue);
        right.updateValue(start, end, newValue);
        value = left.getValue() + right.getValue();
      }
    }

    int getValue() {
      if (!isLeaf()) {
        clearLazyTags();
      }
      return value;
    }

    void clearValueTag() {
      if (valueTag.set) {
        int newValue = valueTag.value;
        value = (end - start + 1) * newValue;
        for (Node child : new Node[]{left, right}) {
          if (child.isLeaf()) {
            child.value = newValue;
          } else {
            // parent node的value-tag覆盖child node的value-tag
            child.deltaTag.clear();
            child.valueTag.setValue(newValue);
          }
        }
        valueTag.clear();
      }
    }

    void clearDeltaTag() {
      if (deltaTag.set) {
        int deltaValue = deltaTag.value;
        value += (end - start + 1) * deltaValue;
        for (Node child : new Node[]{left, right}) {
          if (child.isLeaf()) {
            child.value += deltaValue;
          } else { // child is internal node
            // 首先清理valueTag
            child.clearValueTag();
            // 然后设置deltaTag
            if (child.deltaTag.set) { // 如果原来已有deltaTag, 则进行合并
              child.deltaTag.setValue(deltaValue + child.deltaTag.value);
            } else {
              child.deltaTag.setValue(deltaValue);
            }
          }
          deltaTag.clear();
        }
      }
    }

    // 目前可以保证只有internal node会调用该方法.
    // 且一个node最多同时包含一个lazy-tag
    void clearLazyTags() {
      clearValueTag();
      clearDeltaTag();
    }

    boolean isLeaf() {
      return start == end;
    }
  }

  private static class LazyTag {
    boolean set;
    int value;

    void setValue(int value) {
      this.value = value;
      this.set = true;
    }

    void clear() {
      this.set = false;
    }
  }

  private static void naiveUpdateDelta(int[] prices, int start, int end, int delta) {
    for (int i = start; i <= end; i++) {
      prices[i] += delta;
    }
  }

  private static void naiveUpdateValue(int[] prices, int start, int end, int value) {
    for (int i = start; i <= end; i++) {
      prices[i] = value;
    }
  }

  private static int naiveSum(int[] prices) {
    int result = 0;
    for (int p : prices) {
      result += p;
    }
    return result;
  }
}
