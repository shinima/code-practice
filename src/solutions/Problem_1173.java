package solutions;

import java.util.Scanner;

public class Problem_1173 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int r = 0;
        int N = scanner.nextInt();
        for (int i = 0; i < N; i++) {
            r ^= sg(scanner.nextInt());
        }
        if (r == 0) {
            System.out.println("Bob");
        } else {
            System.out.println("Alice");
        }
    }

    private static int sg(int k) {
        if (k == 0) {
            return k;
        }
        if (k % 4 == 0) {
            return k - 1;
        } else if (k % 4 == 3) {
            return k + 1;
        } else {
            return k;
        }
    }
}