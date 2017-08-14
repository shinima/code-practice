package solutions;

import java.util.*;

public class Problem_1175 {
    private static final int MOD = 142857;

    public static void main(String[] args) {
//        Scanner scanner = new Scanner("4 4 1\n" +
//                "1\n" +
//                "1 2\n" +
//                "1 3\n" +
//                "2 3\n" +
//                "3 4");
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int K = scanner.nextInt();

        int[] count = new int[N + 1];
        int[] inorders = new int[N + 1];
        for (int i = 0; i < K; i++) {
            count[scanner.nextInt()] = 1;
        }
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int node = 1; node <= N; node++) {
            graph.put(node, new ArrayList<Integer>());
        }
        for (int edge = 0; edge < M; edge++) {
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            graph.get(start).add(end);
            inorders[end]++;
        }
        Queue<Integer> queue = new ArrayDeque<>();
        for (int node = 1; node <= N; node++) {
            if (inorders[node] == 0) {
                queue.add(node);
            }
        }

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int next : graph.get(node)) {
                count[next] = (count[next] + count[node]) % MOD;
                inorders[next]--;
                if (inorders[next] == 0) {
                    queue.add(next);
                }
            }
        }
        int result = 0;
        for (int c : count) {
            result = (result + c) % MOD;
        }
        System.out.println(result);
    }
}