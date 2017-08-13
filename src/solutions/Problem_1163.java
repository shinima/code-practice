package solutions;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class Problem_1163 {
    public static void main(String[] args) {
        String input = "3\n" +
                "3 2 1";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
//        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int[] nums = new int[N];
        for (int i = 0; i < N; i++) {
            nums[i] = scanner.nextInt();
        }
        int t = 0;
        for (int n : nums) {
            t ^= n;
        }
        if (t == 0) {
            System.out.println("Bob");
        } else {
            System.out.println("Alice");
        }
    }
}
