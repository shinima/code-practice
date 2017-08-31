package solutions;

import java.util.Scanner;

public class Problem_1562 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        for (int i = 0; i < T; i++) {
            int h = scanner.nextInt();
            int m = scanner.nextInt();
            int s = scanner.nextInt();
            int t = scanner.nextInt();
            System.out.println(String.format("%.4f", foo(h, m, s, t)));
        }
    }

    private static final double A = 12 * 3600;
    private static final double B = 3600;

    private static double foo(int h, int m, int s, int t) {
        int x = h * 3600 + m * 60 + s + t;
        double deg = Math.abs(x / A - x / B) % 1 * 360;
        if (deg > 180) {
            return 360 - deg;
        } else {
            return deg;
        }
    }
}
