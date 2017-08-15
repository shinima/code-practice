package solutions;

import java.util.*;

public class Problem_1181 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner("5 5\n" +
                "3 5\n" +
                "3 2\n" +
                "4 2\n" +
                "3 4\n" +
                "5 1");
//        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        Map<Integer, List<Edge>> graph = new HashMap<>();
        for (int x = 1; x <= N; x++) {
            graph.put(x, new ArrayList<Edge>());
        }
        for (int i = 0; i < M; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            Edge edge = new Edge(u, v);
            graph.get(u).add(edge);
            graph.get(v).add(edge);
        }

        List<Integer> path = new ArrayList<>();

        int start = 1;
        for (int x = 1; x <= N; x++) {
            if (graph.get(x).size() % 2 == 1) {
                start = x;
                break;
            }
        }
        dfs(graph, start, path);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < path.size() - 1; i++) {
            stringBuilder.append(path.get(i))
                    .append(' ');
        }
        stringBuilder.append(path.get(path.size() - 1));
        System.out.println(stringBuilder);
    }

    private static void dfs(Map<Integer, List<Edge>> graph, int x, List<Integer> result) {
        for (Edge edge : graph.get(x)) {
            if (!edge.used) {
                edge.used = true;
                int next = edge.start == x ? edge.end : edge.start;
                dfs(graph, next, result);
            }
        }
        result.add(x);
    }

    private static class Edge {
        int start;
        int end;
        boolean used = false;

        Edge(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}