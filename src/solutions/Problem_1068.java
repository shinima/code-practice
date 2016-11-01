package solutions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Scanner;

// TODO: 2016/10/29 TLE
@SuppressWarnings("Duplicates")
public class Problem_1068 {
    public static void main(String[] args) throws IOException {
        String input = "10\n" +
                "7334\n" +
                "1556\n" +
                "8286\n" +
                "1640\n" +
                "2699\n" +
                "4807\n" +
                "8068\n" +
                "981\n" +
                "4120\n" +
                "2179\n" +
                "5\n" +
                "3 4\n" +
                "2 8\n" +
                "2 4\n" +
                "6 8\n" +
                "7 10";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        int N = scanner.nextInt();


        int shift = getShift(N);
        int[] weights = new int[N];
        for (int i = 0; i < N; i++) {
            weights[i] = scanner.nextInt();
        }

        int[][] f = new int[N][shift + 1];

        for (int t = 0; t <= shift; t++) {
            int len = 1 << t;
            for (int i = 0; i < N; i++) {
                if (len == 1) {
                    f[i][t] = weights[i];
                    continue;
                }
                if (i + len / 2 < N) {
                    f[i][t] = Math.min(f[i][t - 1], f[i + len / 2][t - 1]);
                } else {
                    f[i][t] = f[i][t - 1];
                }
            }
        }

        int Q = scanner.nextInt();
        for (int i = 0; i < Q; i++) {
            int left = scanner.nextInt() - 1;
            int right = scanner.nextInt() - 1;
            int naiveResult = naive(weights, left, right);
            int excitingResult = exciting(f, left, right);
            System.out.println("naive   : " + naiveResult);
            System.out.println("exciting: " + excitingResult);
            System.out.println();
        }
    }

    private static int exciting(int[][] f, int left, int right) {
        int shift = getShift(right - left + 1);
        int len = 1 << shift;
        return Math.min(f[left][shift], f[right - len + 1][shift]);
    }

    private static int naive(int[] weights, int left, int right) {
        int result = weights[left];
        for (int t = left + 1; t <= right; t++) {
            result = Math.min(result, weights[t]);
        }
        return result;
    }

    private static int getShift(int N) {
        int shift = 0;
        while (N > 0) {
            N >>= 1;
            shift++;
        }
        return shift - 1;
    }
}