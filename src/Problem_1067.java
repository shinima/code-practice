import java.io.ByteArrayInputStream;
import java.util.*;

public class Problem_1067 {
    public static void main(String[] args) {
        String input = "4\n" +
                "Adam Sam\n" +
                "Sam Joey\n" +
                "Sam Micheal\n" +
                "Adam Kevin\n" +
                "3\n" +
                "Sam Sam\n" +
                "Adam Sam\n" +
                "Micheal Kevin";
        // Expected output:
        //     Sam
        //     Adam
        //     Adam
        /*
         *           0 Adam
         *         /       \
         *      1 Sam      4 Kevin
         *     /     \
         *  2 Joey   3 Micheal
         *
         */
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        int N = scanner.nextInt(); // N pair parent-child. N+2 persons.
        int[] disjointSet = new int[N + 1];
        Arrays.fill(disjointSet, -1);
        Map<String, Integer> nodeMap = new HashMap<>(N + 1);
        Node[] nodes = new Node[N + 1];

        nodes[0] = new Node(scanner.next());
        nodeMap.put(nodes[0].name, 0);
        nodes[1] = new Node(scanner.next());
        nodeMap.put(nodes[1].name, 1);
        nodes[0].children.add(1);

        // node[2] ... node[N]
        for (int child = 2; child <= N; child++) {
            String parentName = scanner.next();
            String childName = scanner.next();

            nodes[child] = new Node(childName);
            nodeMap.put(childName, child);

            Node parentNode = nodes[nodeMap.get(parentName)];
            parentNode.children.add(child);
        }

        int M = scanner.nextInt();
        Context context = new Context(nodes, nodeMap, disjointSet, M);
        for (int i = 0; i < M; i++) {
            String name1 = scanner.next();
            String name2 = scanner.next();
            context.addQuery(new Query(i, name1, name2));
        }

        // Traverse to set result.
        traverse(context, nodes[0]);

        context.result.forEach(System.out::println);
    }

    private static void traverse(Context context, Node node) {
        node.color = Color.GRAY;

        Set<Query> related = context.queriesMap.get(node.name);
        if (related != null) {
            for (Query query : related) {
                if (Objects.equals(query.name1, query.name2)) { // query.name1 == query.name2 == node.name
                    context.result.set(query.index, node.name);
                } else {
                    String otherName = node.name.equals(query.name1) ? query.name2 : query.name1;
                    int otherIndex = context.nodeMap.get(otherName);
                    Node otherNode = context.nodes[otherIndex];
                    switch (otherNode.color) {
                        case WHITE:
                            break; // do nothing
                        case GRAY:
                            context.result.set(query.index, otherName);
                            break;
                        case BLACK:
                            int firstGrayIndex = find(context.disjointSet, otherIndex);
                            context.result.set(query.index, context.nodes[firstGrayIndex].name);
                            break;
                    }
                }
            }
        }

        int nodeIndex = context.nodeMap.get(node.name);
        for (int child : node.children) {
            Node childNode = context.nodes[child];
            traverse(context, childNode);
            context.disjointSet[child] = nodeIndex; // union node and child
        }
        node.color = Color.BLACK;
    }

    private static int find(int[] disjointSet, int x) {
        if (disjointSet[x] < 0) {
            return x;
        }
        return disjointSet[x] = find(disjointSet, disjointSet[x]);
    }

    private static class Query {
        int index;
        String name1;
        String name2;

        Query(int index, String name1, String name2) {
            this.index = index;
            this.name1 = name1;
            this.name2 = name2;
        }
    }

    private static class Context {
        int M;
        Node[] nodes;
        Map<String, Integer> nodeMap;
        List<Query> queries;
        List<String> result;
        Map<String, Set<Query>> queriesMap;
        int[] disjointSet;

        Context(Node[] nodes, Map<String, Integer> nodeMap, int[] disjointSet, int M) {
            this.nodes = nodes;
            this.nodeMap = nodeMap;
            this.disjointSet = disjointSet;
            this.M = M;

            this.queries = new ArrayList<>(M);
            this.result = new ArrayList<>(M);
            for (int i = 0; i < M; i++) {
                this.result.add(null);
            }
            this.queriesMap = new HashMap<>();
        }

        void addQuery(Query query) {
            queries.add(query);

            Set<Query> set1;
            if (!queriesMap.containsKey(query.name1)) {
                set1 = new HashSet<>();
                set1.add(query);
                queriesMap.put(query.name1, set1);
            } else {
                queriesMap.get(query.name1).add(query);
            }

            Set<Query> set2;
            if (!queriesMap.containsKey(query.name2)) {
                set2 = new HashSet<>();
                set2.add(query);
                queriesMap.put(query.name2, set2);
            } else {
                queriesMap.get(query.name2).add(query);
            }
        }
    }

    private enum Color {
        WHITE, // not visited
        GRAY, // visiting
        BLACK, // visited
    }

    private static class Node {
        String name;
        List<Integer> children = new ArrayList<>();
        Color color = Color.WHITE;

        Node(String name) {
            this.name = name;
        }
    }
}