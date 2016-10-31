import java.io.ByteArrayInputStream;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Problem_1078 {
    public static void main(String[] args) {
        String input = "50\n" +
                "6043 3370 9985 4338 8717 5057 2325 597 7743 6193 6953 4646 3159 8950 4674 6253 4724 1075 7727 9673 3045 9554 9463 1075 4798 3342 6618 7030 1962 9205 5539 2923 9790 9354 3734 6096 5978 9469 5910 4940 3882 960 9332 1755 6016 1877 6759 4193 98 6740\n" +
                "1 37 45 34\n" +
                "1 31 33 42\n" +
                "1 29 49 48\n" +
                "0 25 33\n" +
                "0 31 47\n" +
                "0 31 31\n" +
                "0 45 47\n" +
                "0 19 40\n" +
                "0 36 48\n" +
                "1 16 36 4\n" +
                "1 39 50 1\n" +
                "1 19 37 44\n" +
                "1 26 50 6\n" +
                "1 18 20 50\n" +
                "0 12 25\n" +
                "0 48 48\n" +
                "0 3 40\n" +
                "0 18 25\n" +
                "0 13 35\n" +
                "1 8 50 46\n" +
                "0 7 21\n" +
                "1 9 29 19\n" +
                "0 31 37\n" +
                "1 4 16 42\n" +
                "1 18 31 27\n" +
                "0 2 14\n" +
                "1 3 35 31\n" +
                "0 2 34\n" +
                "0 29 36\n" +
                "1 17 39 27\n" +
                "0 26 28\n" +
                "1 34 34 50\n" +
                "0 46 46\n" +
                "1 12 44 41\n" +
                "1 11 37 46\n" +
                "0 24 24\n" +
                "1 16 17 29\n" +
                "0 22 43\n" +
                "0 2 3\n" +
                "1 27 49 50\n" +
                "1 15 23 6\n" +
                "0 22 43\n" +
                "1 16 18 36\n" +
                "0 13 31\n" +
                "0 5 33\n" +
                "1 45 46 5\n" +
                "1 3 15 42\n" +
                "1 13 37 38\n" +
                "1 2 21 10\n" +
                "1 41 46 8\n" +
                "0 14 38\n" +
                "0 10 22\n" +
                "0 5 7\n" +
                "0 26 30\n" +
                "1 23 42 8\n" +
                "1 42 44 19\n" +
                "1 24 32 39\n" +
                "1 11 27 37\n" +
                "1 10 11 14\n" +
                "1 22 50 50\n" +
                "0 4 34\n" +
                "0 23 27\n" +
                "0 16 40\n" +
                "0 24 28\n" +
                "1 10 49 46\n" +
                "0 19 41\n" +
                "0 14 47\n" +
                "0 12 42\n" +
                "1 10 34 14\n" +
                "1 16 22 44\n" +
                "0 33 50\n" +
                "1 25 32 45\n" +
                "1 21 32 37\n" +
                "0 4 38\n" +
                "1 25 41 47\n" +
                "1 21 24 33\n" +
                "1 3 10 8\n" +
                "1 24 44 45\n" +
                "0 8 29\n" +
                "1 20 33 45\n" +
                "1 5 22 40\n" +
                "1 23 29 46\n" +
                "0 9 45\n" +
                "1 15 27 31\n" +
                "0 13 20\n" +
                "0 22 29\n" +
                "1 12 50 33\n" +
                "1 27 47 17\n" +
                "1 22 28 29\n" +
                "0 4 18\n" +
                "1 4 48 30\n" +
                "1 1 9 25\n" +
                "0 32 35\n" +
                "0 41 45\n" +
                "0 7 23\n" +
                "0 37 40\n" +
                "0 18 38\n" +
                "0 19 46\n" +
                "0 11 43\n" +
                "0 21 31\n" +
                "0 25 34\n" +
                "0 29 33\n" +
                "1 13 37 3\n" +
                "1 20 35 21\n" +
                "0 45 46\n" +
                "1 10 13 9\n" +
                "1 26 46 11\n" +
                "1 27 42 47\n" +
                "1 23 27 13\n" +
                "1 5 38 18\n" +
                "1 33 40 12\n" +
                "0 23 44\n" +
                "0 3 44\n" +
                "1 27 47 17\n" +
                "0 16 26\n" +
                "1 32 47 50\n" +
                "0 4 9\n" +
                "1 7 35 38\n" +
                "0 4 28\n" +
                "0 1 39\n" +
                "1 4 40 14\n" +
                "1 8 40 10\n" +
                "1 7 17 5\n" +
                "1 42 43 19\n" +
                "1 14 38 43\n" +
                "1 25 29 25\n" +
                "0 11 17\n" +
                "0 12 17\n" +
                "0 23 41\n" +
                "1 23 26 32\n" +
                "1 7 28 19\n" +
                "0 9 39\n" +
                "0 22 35\n" +
                "1 18 30 23\n" +
                "1 43 43 5\n" +
                "1 13 16 15\n" +
                "1 19 41 17\n" +
                "1 2 28 15\n" +
                "1 38 38 19\n" +
                "1 19 26 38\n" +
                "0 30 31\n" +
                "1 15 47 1\n" +
                "1 3 38 42\n" +
                "1 16 19 41\n" +
                "0 2 3\n" +
                "0 31 35\n" +
                "1 6 16 33\n" +
                "0 26 46\n" +
                "1 10 41 14\n" +
                "0 1 5\n" +
                "1 36 49 9\n" +
                "1 41 43 26\n" +
                "0 2 32\n" +
                "1 29 47 31\n" +
                "1 9 49 23\n" +
                "0 20 30\n" +
                "1 18 31 12\n" +
                "0 26 30\n" +
                "1 13 25 28\n" +
                "0 18 24\n" +
                "0 12 16\n" +
                "1 2 37 35\n" +
                "1 36 41 30\n" +
                "1 6 44 6\n" +
                "0 1 27\n" +
                "0 38 50\n" +
                "0 19 22\n" +
                "1 9 47 7\n" +
                "1 31 48 44\n" +
                "1 19 44 24\n" +
                "1 1 25 49\n" +
                "1 12 22 1\n" +
                "0 7 29\n" +
                "0 12 43\n" +
                "1 42 43 21\n" +
                "0 8 9\n" +
                "0 5 44\n" +
                "1 4 43 32\n" +
                "1 40 47 20\n" +
                "1 8 41 48\n" +
                "0 7 14\n" +
                "0 33 37\n" +
                "0 3 22\n" +
                "0 16 50\n" +
                "0 9 19\n" +
                "1 3 38 44\n" +
                "0 5 41\n" +
                "0 7 41\n" +
                "0 15 32\n" +
                "1 20 33 46\n" +
                "0 21 40\n" +
                "1 3 48 45\n" +
                "0 33 39\n" +
                "1 42 50 24\n" +
                "1 3 14 22\n" +
                "1 6 18 1\n" +
                "1 44 50 2\n" +
                "1 45 50 32\n" +
                "1 7 25 47\n" +
                "0 40 47";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        int N = scanner.nextInt();
        int[] weights = new int[N];
        for (int i = 0; i < N; i++) {
            weights[i] = scanner.nextInt();
        }

        Node root = new Node(weights);

        while (scanner.hasNext()) {
            int operation = scanner.nextInt();
            int start = scanner.nextInt() - 1;
            int end = scanner.nextInt() - 1;
            if (operation == 0) {
                int naiveResult = naiveQuery(weights, start, end);
                int excitingResult = root.query(start, end);
                System.out.println("naive:    " + naiveResult);
                System.out.println("exciting: " + excitingResult);
                if (naiveResult != excitingResult) {
                    System.out.println("!!!!!!!!!!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!!!!!!!!!!");
                    System.out.println("naive:    " + naiveQuery(weights, start, end));
                    System.out.println("exciting: " + root.query(start, end));
                }
            } else {
                int value = scanner.nextInt();
                naiveUpdate(weights, start, end, value);
                root.update(start, end, value);
            }
        }
    }

    private static int naiveQuery(int[] weights, int start, int end) {
        int result = 0;
        for (int i = start; i <= end; i++) {
            result += weights[i];
        }
        return result;
    }

    private static void naiveUpdate(int[] weights, int start, int end, int value) {
        for (int i = start; i <= end; i++) {
            weights[i] = value;
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


        public String toString() {
            if (start == end) {
                return String.format("Node[%d]{value=%d}", start, value);
            }
            if (needUpdate) {
                return String.format("Node[%d...%d -> %d]", start, end, updateValue);
            }
            return String.format("Node[%d...%d]{sum=%d}", start, end, value);
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