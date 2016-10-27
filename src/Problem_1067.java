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
        /*
        Expected output:
            Sam
            Adam
            Adam
         */
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        int N = scanner.nextInt(); // N对父子关系. 共N+1个人
        Map<String, Integer> map = new HashMap<>(N + 1);
        Node[] nodes = new Node[N + 1];

        nodes[0] = new Node(scanner.next());
        map.put(nodes[0].name, 0);
        nodes[1] = new Node(scanner.next());
        map.put(nodes[1].name, 1);
        nodes[0].children.add(1);

        // node[2] ... node[N]
        for (int child = 2; child <= N; child++) {
            String parentName = scanner.next();
            String childName = scanner.next();
            nodes[child] = new Node(childName);
            Node parentNode = nodes[map.get(parentName)];
            parentNode.children.add(child);
        }

        int M = scanner.nextInt();
        QuerySet querySet = new QuerySet(nodes, M);
        for (int i = 0; i < M; i++) {
            String name1 = scanner.next();
            String name2 = scanner.next();
            querySet.addQuery(name1, name2);
        }
    }

    private static class QuerySet {
        Node[] nodes;
        int M;

        QuerySet(Node[] nodes, int M) {
            this.nodes = nodes;
            this.M = M;
        }

        void addQuery(String name1, String name2) {
            // TODO: 2016/10/27
        }
    }

    private enum Color {
        WHITE, GRAY, BLACK
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