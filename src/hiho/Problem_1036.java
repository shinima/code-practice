package hiho;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class Problem_1036 {
  public static void main(String[] args) {
    String[] dict = {"aaabc", "aaac", "abcc", "ac", "bcd", "cd"};
    System.out.println(containsHXWord(dict, "aaaaaaaaaaabaaadaaac"));
  }

  static boolean containsHXWord(String[] dict, String article) {
    Tree tree = new Tree();
    for (String string : dict) {
      tree.insert(string.toCharArray());
    }
    tree.updateFailPointers();

    Node node = tree.root;
    char[] chars = article.toCharArray();
    System.out.println(article);
    for (char c : chars) {
      if (node.hasWord) {
        System.out.println("Found " + node.prefix);
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

      System.out.println(MessageFormat.format("Get {0}, current prefix: {1}",
        c, node.prefix.equals("") ? "<empty>" : node.prefix));
    }
    if (node.hasWord) {
      System.out.println("Found " + node.prefix);
    }
    return node.hasWord;
  }

  private static class Tree {
    private Node root;

    Tree() {
      root = new Node(' ');
      root.prefix = "";
    }

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
          node.children[key] = new Node(c);
        }
        Node child = node.children[key];
        child.prefix = node.prefix + c;
        node = child;
      }
      node.hasWord = true;
    }
  }

  private static class Node {
    Node[] children = new Node[26];
    Node fail;
    boolean hasWord;
    String prefix;
    char ch;

    Node(char ch) {
      this.ch = ch;
    }
  }
}
