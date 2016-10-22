package submits;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class OJ_Submit_1036 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        String[] dict = new String[N];
        for (int i = 0; i < N; i++) {
            dict[i] = scanner.next();
        }
        String article = scanner.next();
        if (containsHXWord(dict, article)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    private static boolean containsHXWord(String[] dict, String article) {
        Tree tree = new Tree();
        for (String string : dict) {
            tree.insert(string.toCharArray());
        }
        tree.updateFailPointers();

        Node node = tree.root;
        char[] chars = article.toCharArray();
        for (char c : chars) {
            if (node.hasWord) {
                return true;
            }

            int key = c - 'a';
            while (node != null && node.children[key] == null) {
                node = node.fail;
            }

            if (node == null) {
                node = tree.root;
            } else {
                node = node.children[key];
            }
        }
        return node.hasWord;
    }

    private static class Tree {
        private Node root = new Node();

        private void updateFailPointers() {
            List<Node> list = new ArrayList<>();
            list.add(root);
            while (!list.isEmpty()) {
                List<Node> next = new ArrayList<>();
                for (Node parent : list) {
                    for (int key = 0; key < parent.children.length; key++) {
                        if (parent.children[key] == null) {
                            continue;
                        }
                        Node child = parent.children[key];
                        if (!child.hasWord) {
                            Node failParent = parent.fail;
                            while (true) {
                                if (failParent == null) {
                                    child.fail = root;
                                    break;
                                }
                                if (failParent.children[key] != null) {
                                    child.fail = failParent.children[key];
                                    break;
                                }
                                failParent = failParent.fail;
                            }
                        }
                        next.add(child);
                    }
                }
                list = next;
            }
        }

        private void insert(char[] word) {
            Node node = root;
            for (char c : word) {
                int key = c - 'a';
                if (node.children[key] == null) {
                    node.children[key] = new Node();
                }
                node = node.children[key];
            }
            node.hasWord = true;
        }
    }

    private static class Node {
        Node[] children = new Node[26];
        Node fail;
        boolean hasWord;
    }
}
