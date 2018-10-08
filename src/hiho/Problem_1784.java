package hiho;

import java.util.Scanner;

public class Problem_1784 {
  public static void main(String[] args) {
//    String input = "10 2";
//    Scanner scanner = new Scanner(input);
    Scanner scanner = new Scanner(System.in);
    long n = scanner.nextInt();
    long k = scanner.nextInt();

    int time = 0;
    long count = 1;
    while (count < n) {
      time++;
      count *= k + 1;
    }
    System.out.println(time);
  }
}
