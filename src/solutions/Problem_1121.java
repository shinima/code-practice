package solutions;

import java.util.*;

public class Problem_1121 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        for (int testIndex = 0; testIndex < T; testIndex++) {
            int N = scanner.nextInt(); // N个顶点
            int M = scanner.nextInt(); // M条边
            Map<Integer, List<Integer>> graph = new HashMap<>();
            for (int edgeIndex = 0; edgeIndex < M; edgeIndex++) {
                int start = scanner.nextInt();
                int end = scanner.nextInt();
                // add start->end
                if (!graph.containsKey(start)) {
                    graph.put(start, new ArrayList<Integer>());
                }
                graph.get(start).add(end);
                // add end->start
                if (!graph.containsKey(end)) {
                    graph.put(end, new ArrayList<Integer>());
                }
                graph.get(end).add(start);
            }
            Map<Integer, Color> colorMap = new HashMap<>(N);
            for (int edge = 1; edge <= N; edge++) {
                colorMap.put(edge, Color.none);
            }
            if (isBipartiteGraph(graph, colorMap)) {
                System.out.println("Correct");
            } else {
                System.out.println("Wrong");
            }
        }
    }

    private static boolean isBipartiteGraph(
            Map<Integer, List<Integer>> graph,
            Map<Integer, Color> colorMap) {
        int uncoloredCount = colorMap.size();
        while (uncoloredCount > 0) {
            int vertex = findUncolored(colorMap);
            // 将vertex染成red
            int dyeCount = dye(graph, colorMap, vertex, Color.red);
            if (dyeCount == -1) { // 染色失败
                return false;
            } else { // 染色成功, 返回染色的数量
                uncoloredCount -= dyeCount;
            }
        }
        return true;
    }

    private static int dye(
            Map<Integer, List<Integer>> graph,
            Map<Integer, Color> colorMap,
            int vertex, Color color) {
        colorMap.put(vertex, color);
        int returnValue = 1;
        for (int connected : (graph.containsKey(vertex) ? graph.get(vertex) : new ArrayList<Integer>())) {
            if (colorMap.get(connected) == Color.none) {
                int dyeCount = dye(graph, colorMap, connected, Color.invert(color));
                if (dyeCount == -1) {
                    // 子节点染色失败, 返回-1表示失败
                    return -1;
                } else {
                    returnValue += dyeCount;
                }
            } else if (colorMap.get(connected) == color) {
                // 与当前颜色相等, 直接返回-1表示失败
                return -1;
            }
        }
        return returnValue;
    }

    private static int findUncolored(Map<Integer, Color> colorMap) {
        for (Map.Entry<Integer, Color> entry : colorMap.entrySet()) {
            int vertex = entry.getKey();
            Color color = entry.getValue();
            if (color == Color.none) {
                return vertex;
            }
        }
        return -1;
    }

    private enum Color {
        red, black, none;

        static Color invert(Color color) {
            if (color == red) {
                return black;
            } else {
                return red;
            }
        }
    }
}
