package solutions;

import java.io.ByteArrayInputStream;
import java.util.*;

@SuppressWarnings("Duplicates")
public class Problem_1079 {
    public static void main(String[] args) {
        test("3 10 " +
                "1 10 " +
                "0 3 " +
                "8 10");
    }

    public static void test(String input) {
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        int N = scanner.nextInt();
        int L = scanner.nextInt();
        int[] naiveArray = new int[L];
        Arrays.fill(naiveArray, -1);
        int[] startIndices = new int[N];
        int[] endIndices = new int[N];
        int[] indices = new int[N * 2];
        for (int i = 0; i < N; i++) {
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            Arrays.fill(naiveArray, start, end, i);
            startIndices[i] = start;
            endIndices[i] = end;
            indices[i * 2] = start;
            indices[i * 2 + 1] = end;
        }
        Arrays.sort(indices);
        int size = 0; // [0, L-1]空间被压缩到[0, size-1]的空间
        for (int i = 0; i < indices.length; i++) {
            if (i == 0 || indices[i] != indices[i - 1]) {
                indices[size++] = indices[i];
            }
        }
        Map<Integer, Integer> map = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            map.put(indices[i], i);
        }
//        System.out.println(size);
        // 空间压缩映射放在map中
//        System.out.println(map);

        Node root = new Node(0, size);
        for (int i = 0; i < N; i++) {
            int start = map.get(startIndices[i]);
            int end = map.get(endIndices[i]);
            root.setPoster(start, end, i);
        }

        Set<Integer> seen = new HashSet<>(N);
        traversal(root, seen);
        int size1 = seen.size();
        System.out.println(size1);

        Set<Integer> naiveSet = new HashSet<>();
        for (int t : naiveArray) {
            if (t != -1) {
                naiveSet.add(t);
            }
        }
        int size2 = naiveSet.size();
        System.out.println(size2);
        if (size1 != size2) {
            System.out.println("!!!!");
        }
        System.out.println("==========");
    }

    private static void traversal(Node node, Set<Integer> seen) {
        if (node.isLeaf()) {
            if (node.poster != -1) {
                seen.add(node.poster);
            }
        } else {
            if (node.needUpdate) {
                seen.add(node.poster);
            } else {
                traversal(node.left, seen);
                traversal(node.right, seen);
            }
        }
    }


    private static class Node {
        int start;
        int end;
        int poster = -1;
        Node left;
        Node right;
        boolean needUpdate;

        @Override
        public String toString() {
            if (isLeaf()) {
                return String.format("Node{[%d] = %d}", start, poster);
            }
            if (needUpdate) {
                return String.format("Node{[%d...%d] = %d}", start, end, poster);
            }
            return String.format("Node{[%d...%d]}", start, end);
        }

        Node(int start, int end) {
            this.start = start;
            this.end = end;
            if (start != end - 1) {
                int median = (start + end) / 2;
                this.left = new Node(start, median);
                this.right = new Node(median, end);
            }
        }

        boolean isLeaf() {
            return start == end - 1;
        }

        void setPoster(int start, int end, int poster) {
            if (start >= this.end || end <= this.start) {
                return;
            }

            if (start <= this.start && end >= this.end) { // 如果是leaf, 则该if判断一定为true
                this.needUpdate = true;
                this.poster = poster;
            } else {
                if (this.needUpdate) {
                    left.needUpdate = true;
                    left.poster = this.poster;
                    right.needUpdate = true;
                    right.poster = this.poster;

                    this.needUpdate = false;
                }
                left.setPoster(start, end, poster);
                right.setPoster(start, end, poster);
            }
        }
    }
}