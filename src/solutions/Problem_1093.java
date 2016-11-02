package solutions;

import java.io.ByteArrayInputStream;
import java.util.*;

public class Problem_1093 {
    private static final int MAX_LEN = 1000000;

    // 最短路算法 假冒的SPFA o(*≧▽≦)ツ
    // AC
    public static void main(String[] args) {
        String input = "5 10 3 5\n" +
                "1 2 997\n" +
                "2 3 505\n" +
                "3 4 118\n" +
                "4 5 54\n" +
                "3 5 480\n" +
                "3 4 796\n" +
                "5 2 794\n" +
                "2 5 146\n" +
                "5 4 604\n" +
                "2 5 63";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int S = scanner.nextInt() - 1;
        int T = scanner.nextInt() - 1;
        Routes routes = new Routes(N);
        for (int i = 0; i < M; i++) {
            int start = scanner.nextInt() - 1;
            int end = scanner.nextInt() - 1;
            int length = scanner.nextInt();
            routes.update(start, end, length);
        }
        int[] path = new int[N];
        Arrays.fill(path, MAX_LEN);
        path[S] = 0;

        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(S);
        while (!queue.isEmpty()) {
            int t = queue.poll();
            Map<Integer, Integer> map = routes.get(t);
            for (int adj : map.keySet()) {
                int candidatePath = path[t] + map.get(adj);
                if (candidatePath < path[adj]) {
                    path[adj] = candidatePath;
                    queue.offer(adj);
                }
            }
        }
        System.out.println(path[T]);
    }

    private static class Routes {
        List<Map<Integer, Integer>> lists;

        Routes(int N) {
            lists = new ArrayList<>(N);
            for (int i = 0; i < N; i++) {
                lists.add(new HashMap<>());
            }
        }

        void update(int start, int end, int length) {
            Map<Integer, Integer> startMap = lists.get(start);
            if (startMap.containsKey(end)) {
                int oldValue = startMap.get(end);
                startMap.put(end, Math.min(oldValue, length));
            } else {
                startMap.put(end, length);
            }

            Map<Integer, Integer> endMap = lists.get(end);
            if (endMap.containsKey(start)) {
                int oldValue = endMap.get(start);
                endMap.put(start, Math.min(oldValue, length));
            } else {
                endMap.put(start, length);
            }
        }

        Map<Integer, Integer> get(int index) {
            return lists.get(index);
        }
    }
}
