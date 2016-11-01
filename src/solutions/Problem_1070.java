package solutions;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class Problem_1070 {
    public static void main(String[] args) {
        String input = "10\n" +
                "618 5122 1923 8934 2518 6024 5406 1020 8291 2647 \n" +
                "6\n" +
                "0 3 6\n" +
                "1 2 2009\n" +
                "0 2 2\n" +
                "0 2 10\n" +
                "1 1 5284\n" +
                "0 2 5";
        /* Expected output
            1923
            2009
            1020
            1923
         */

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        int N = scanner.nextInt();
        int[] weights = new int[N];
        for (int i = 0; i < N; i++) {
            weights[i] = scanner.nextInt();
        }

        Node root = makeNode(weights, 0, N - 1);

        int Q = scanner.nextInt();
        for (int i = 0; i < Q; i++) {
            int operation = scanner.nextInt();
            if (operation == 0) { // query
                int start = scanner.nextInt() - 1;
                int end = scanner.nextInt() - 1;
                System.out.println(root.query(start, end));
            } else { // update
                int index = scanner.nextInt() - 1;
                int value = scanner.nextInt();
                root.update(index, value);
            }
        }
    }

    private static Node makeNode(int[] weights, int leftIndex, int rightIndex) {
        Node node = new Node();
        node.leftIndex = leftIndex;
        node.rightIndex = rightIndex;
        if (leftIndex == rightIndex) {
            node.value = weights[leftIndex];
        } else {
            int medianIndex = (leftIndex + rightIndex) / 2;
            node.left = makeNode(weights, leftIndex, medianIndex);
            node.right = makeNode(weights, medianIndex + 1, rightIndex);
            node.value = Math.min(node.left.value, node.right.value);
        }
        return node;
    }

    private static class Node {
        int leftIndex;
        int rightIndex;
        int value;
        Node left;
        Node right;

        int query(int start, int end) {
            if (start > rightIndex || end < leftIndex) {
                return Integer.MAX_VALUE;
            }
            if (start <= leftIndex && end >= rightIndex) {
                return value;
            }
            return Math.min(left.query(start, end), right.query(start, end));
        }

        void update(int index, int newValue) {
            if (index < leftIndex || index > rightIndex) {
                return;
            }
            if (index == leftIndex && index == rightIndex) {
                value = newValue;
                return;
            }
            left.update(index, newValue);
            right.update(index, newValue);
            value = Math.min(left.value, right.value);
        }
    }

}