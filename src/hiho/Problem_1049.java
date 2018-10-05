package hiho;

import java.util.Scanner;

public class Problem_1049 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String preorder = scanner.next();
    String inorder = scanner.next();
    System.out.println(calculatePostorder(preorder.toCharArray(), 0,
      inorder.toCharArray(), 0, inorder.length() - 1));
  }

  private static String calculatePostorder(char[] preorder, int preStart,
                                           char[] inorder, int inStart, int inEnd) {
    if (inStart > inEnd) {
      return "";
    }
    char root = preorder[preStart];
    int t = inStart;
    while (t <= inEnd) {
      if (root == inorder[t]) {
        break;
      }
      t++;
    }
    int leftSize = t - inStart;

    String left = calculatePostorder(preorder, preStart + 1, inorder, inStart, t - 1);
    String right = calculatePostorder(preorder, preStart + 1 + leftSize, inorder, t + 1, inEnd);
    return left + right + root;
  }
}
