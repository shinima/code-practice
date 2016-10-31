import java.util.*;

// TODO: 2016/10/31 WA
public class Problem_1079 {
    public static void main(String[] args) {
//        String input = "5 10\n" +
//                "4 10\n" + // 0
//                "0 2\n" + // 1
//                "1 6\n" + // 2
//                "5 9\n" + // 3
//                "3 4"; // 4
//        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int L = scanner.nextInt();
        int[] startIndices = new int[N];
        int[] endIndices = new int[N];
        int[] indices = new int[N * 2];
        for (int i = 0; i < N; i++) {
            int start = scanner.nextInt();
            int end = scanner.nextInt() - 1;
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

        Node root = new Node(0, size - 1);
        for (int i = 0; i < N; i++) {
            int start = map.get(startIndices[i]);
            int end = map.get(endIndices[i]);
            root.setPoster(start, end, i);
        }

        Set<Integer> seen = new HashSet<>(N);
        traversal(root, seen);
        System.out.println(seen.size());
    }

    private static void traversal(Node node, Set<Integer> seen) {
        if (node.isLeaf()) {
            if (node.poster != -1) {
//                System.out.println("Add " + node.poster);
                seen.add(node.poster);
            }
        } else {
            if (node.needUpdate) {
//                System.out.println("Add " + node.poster);
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

        Node(int start, int end) {
            this.start = start;
            this.end = end;
            if (start != end) {
                int median = (start + end) / 2;
                this.left = new Node(start, median);
                this.right = new Node(median + 1, end);
            }
        }

        boolean isLeaf() {
            return start == end;
        }

        void setPoster(int start, int end, int poster) {
            if (start > this.end || end < this.start) {
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