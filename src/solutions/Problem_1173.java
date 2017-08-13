package solutions;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class Problem_1173 {
    public static void main(String[] args) {
        String input = "8\n" +
                "HHTHTTHT";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
//        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        String s = scanner.next();
        int t = 0;
        for (int i = 1; i <= s.length(); i++) {
            if (s.charAt(i - 1) == 'H') {
                t ^= i;
            }
        }
        if (t == 0) {
            System.out.println("Bob");
        } else {
            System.out.println("Alice");
        }
    }
}
