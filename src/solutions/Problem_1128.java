package solutions;

import java.util.Scanner;

public class Problem_1128 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int target = scanner.nextInt();
        int smallCount = 0;
        boolean appeared = false;
        for (int index = 1; index <= N; index++) {
            int number = scanner.nextInt();
            if (number == target) {
                appeared = true;
            } else if (number < target) {
                smallCount++;
            }
        }
        if (appeared) {
            System.out.println(smallCount + 1);
        } else {
            System.out.println(-1);
        }
    }
}
