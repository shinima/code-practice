package submits;

import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class OJ_Submit_1078 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        int[] weights = new int[N];
        for (int i = 0; i < N; i++) {
            weights[i] = scanner.nextInt();
        }

        Node root = new Node(weights);

        int Q = scanner.nextInt();
        for (int i = 0; i < Q; i++) {
            int operation = scanner.nextInt();
            int start = scanner.nextInt() - 1;
            int end = scanner.nextInt() - 1;
            if (operation == 0) {
                System.out.println(root.query(start, end));
            } else {
                int value = scanner.nextInt();
                root.update(start, end, value);
            }
        }
    }

    private static class Node {
        int value;
        int start;
        int end;
        Node left;
        Node right;
        boolean needUpdate = false;
        int updateValue;

        Node(int[] weights) {
            this(weights, 0, weights.length - 1);
        }

        Node(int[] weights, int start, int end) {
            this.start = start;
            this.end = end;
            if (start == end) {
                this.value = weights[start];
            } else {
                int median = (start + end) / 2;
                this.left = new Node(weights, start, median);
                this.right = new Node(weights, median + 1, end);
                this.value = left.value + right.value;
            }
        }


        int query(int start, int end) {
            if (end < this.start || start > this.end) {
                return 0;
            }
            if (this.start == this.end) {
                return value;
            }
            if (start <= this.start && end >= this.end) {
                return getValue();
            }
            if (needUpdate) {
                this.value = this.getValue();
                left.setUpdateValue(updateValue);
                right.setUpdateValue(updateValue);

                needUpdate = false;
            }
            return left.query(start, end) + right.query(start, end);
        }

        void update(int start, int end, int value) {
            if (this.start == this.end) {
                this.needUpdate = false;
                int index = this.start;
                if (start <= index && index <= end) {
                    this.value = value;
                }
            } else if (start <= this.start && end >= this.end) {
                needUpdate = true;
                updateValue = value;
            } else if (end >= this.start && start <= this.end) {
                if (needUpdate) {
                    left.setUpdateValue(updateValue);
                    right.setUpdateValue(updateValue);
                    this.needUpdate = false;
                }

                left.update(start, end, value);
                right.update(start, end, value);

                this.value = left.getValue() + right.getValue();
            }
        }

        private int getValue() {
            if (needUpdate) {
                return (end - start + 1) * updateValue;
            } else {
                return value;
            }
        }

        private void setUpdateValue(int updateValue) {
            if (this.start == this.end) {
                this.value = updateValue;
            } else {
                this.needUpdate = true;
                this.updateValue = updateValue;
            }
        }
    }
}