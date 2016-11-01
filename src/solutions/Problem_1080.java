package solutions;

import com.sun.xml.internal.bind.v2.TODO;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class Problem_1080 {
    public static void main(String[] args) {
        test("10 6\n" +
                "3195 2202 4613 3744 2892 4858 619 5079 9478 7366 8942 \n" +
                "0 1 6 886\n" +
                "1 0 2 9710\n" +
                "1 0 10 7980\n" +
                "0 4 9 -7594\n" +
                "0 2 8 1581\n" +
                "0 4 4 -1010");
    }

    private static void test(String input) {
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
                naiveUpdateDelta(prices, start, end, value);
                root.updateDelta(start, end, value);
            } else { // value是绝对值
                naiveUpdateValue(prices, start, end, value);
                root.updateValue(start, end, value);
            }
            System.out.println("naive:    " + sum(prices));
            System.out.println("exciting: " + root.sum());
        }
    }

    private static class Node {
        int start;
        int end;
        Node left;
        Node right;
        LazyTag deltaTag = new LazyTag();
        LazyTag valueTag = new LazyTag();

        Node(int[] prices) {
            this(prices, 0, prices.length - 1);
        }

        Node(int[] prices, int start, int end) {
            this.start = start;
            this.end = end;
            if (start != end) {
                int median = (start + end) / 2;
                this.left = new Node(prices, start, median);
                this.right = new Node(prices, median + 1, end);
            }
        }

        void updateDelta(int start, int end, int delta) {
            if (start > this.end || end < this.start) {
                return;
            }
            if (start <= this.start && end >= this.end) { // 如果是leaf, 则该if判断一定为true
                clearLazyTags();
                deltaTag.setValue(delta);
            }
        }

        void updateValue(int start, int end, int value) {
            // todo
        }

        int sum(int start, int end) {
            return 0;
        }

        int sum() {
            return sum(start, end);
        }

        void clearLazyTags() {
            // todo
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

        boolean isSet() {
            return set;
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

    private static int sum(int[] prices) {
        int result = 0;
        for (int p : prices) {
            result += p;
        }
        return result;
    }
}
