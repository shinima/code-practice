package solutions;

import java.util.Scanner;

public class Problem_1052 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        for (int tc = 0; tc < T; tc++) {
            String seq = scanner.next();
            int K = scanner.nextInt();
            System.out.println(minEditCount(seq, K));
        }
    }

    private static int minEditCount(String seq, int k) {
        char[] chars = seq.toCharArray();
        int size = chars.length;
        if (chars.length >= 2 * k) {
            int count = 0;
            for (int i = 0; i < k; i++) {
                if (chars[i] != chars[size - k + i]) {
                    count++;
                }
            }
            return count;
        }
        int d = size - k;
        int count = 0;
        for (int i = 0; i < d; i++) {
            int a = 0;
            int c = 0;
            int g = 0;
            int t = 0;
            for (int j = 0; i + j * d < size; j++) {
                char ch = chars[i + j * d];
                if (ch == 'A') {
                    a++;
                } else if (ch == 'C') {
                    c++;
                } else if (ch == 'G') {
                    g++;
                } else { // ch == 'T'
                    t++;
                }
            }
//            System.out.println("A:" + a + "  T:" + t + "  C:" + c + "  G:" + g);
            int max = Math.max(a, Math.max(c, Math.max(g, t)));
            count += a + c + g + t - max;
        }
        return count;
    }
}
