package solutions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

// AC
public class Problem_1109 {
    public static void main(String[] args) throws IOException {
        String input = "5 29\n" +
                "1 2 674\n" +
                "2 3 249\n" +
                "3 4 672\n" +
                "4 5 933\n" +
                "1 2 788\n" +
                "3 4 147\n" +
                "2 4 504\n" +
                "3 4 38\n" +
                "1 3 65\n" +
                "3 5 6\n" +
                "1 5 865\n" +
                "1 3 590\n" +
                "1 4 682\n" +
                "2 4 227\n" +
                "2 4 636\n" +
                "1 4 312\n" +
                "1 3 143\n" +
                "2 5 158\n" +
                "2 3 516\n" +
                "3 5 102\n" +
                "1 5 605\n" +
                "1 4 99\n" +
                "4 5 224\n" +
                "2 4 198\n" +
                "3 5 894\n" +
                "1 5 845\n" +
                "3 4 7\n" +
                "2 4 14\n" +
                "1 4 185";
        // Expected output: 92
        InputStream in = new ByteArrayInputStream(input.getBytes());
        int N = readInt(in);
        int M = readInt(in);
        RoutesManager manager = new RoutesManager();
        for (int i = 0; i < M; i++) {
            // 这次以1为起始下标吧
            int start = readInt(in);
            int end = readInt(in);
            int distance = readInt(in);
            manager.add(start, end, distance);
        }
        boolean[] visited = new boolean[N + 1]; // 下标从1开始, 所以需要N+1的空间
        PriorityQueue<Route> heap = new PriorityQueue<>(manager.getRoutes(1));
        int cost = 0;
        int visitedCount = 0;
        while (visitedCount < N) {
            int next;
            if (!visited[1]) { // 从1号节点开始生成
                next = 1;
            } else {
                Route route;
                while (true) {
                    route = heap.poll();
                    next = route.end;
                    if (!visited[next]) {
                        break;
                    }
                }
                System.out.println(route);
                cost += route.distance;
            }
            // visit next
            visitedCount++;
            visited[next] = true;

            for (Route foo : manager.getRoutes(next)) {
                heap.offer(foo);
            }
        }
        System.out.println(cost);
    }

    private static class RoutesManager {
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();

        void add(int start, int end, int distance) {
            Map<Integer, Integer> startMap;
            if (map.containsKey(start)) {
                startMap = map.get(start);
            } else {
                startMap = new HashMap<>();
                map.put(start, startMap);
            }
            if (startMap.containsKey(end)) {
                int old = startMap.get(end);
                startMap.put(end, Math.min(old, distance));
            } else {
                startMap.put(end, distance);
            }

            Map<Integer, Integer> endMap;
            if (map.containsKey(end)) {
                endMap = map.get(end);
            } else {
                endMap = new HashMap<>();
                map.put(end, endMap);
            }
            if (endMap.containsKey(start)) {
                int old = endMap.get(start);
                endMap.put(start, Math.min(old, distance));
            } else {
                endMap.put(start, distance);
            }
        }

        List<Route> getRoutes(int start) {
            Map<Integer, Integer> startMap = map.get(start);
            List<Route> routes = new ArrayList<>(startMap.size());
            for (int end : startMap.keySet()) {
                routes.add(new Route(start, end, startMap.get(end)));
            }
            return routes;
        }
    }

    private static class Route implements Comparable<Route> {
        int start;
        int end;
        int distance;

        public String toString() {
            return String.format("Route{ %d -> %d : %d }", start, end, distance);
        }

        Route(int start, int end, int distance) {
            this.start = start;
            this.end = end;
            this.distance = distance;
        }

        public int compareTo(Route other) {
            return this.distance - other.distance;
        }
    }

    private static int readInt(InputStream in) throws IOException {
        int result = 0;
        boolean dig = false;

        while (true) {
            int c = in.read();
            if (c == -1) {
                break;
            }
            if (c >= '0' && c <= '9') {
                result = result * 10 + (c - '0');
                dig = true;
            } else if (dig) {
                break;
            }
        }
        return result;
    }
}
