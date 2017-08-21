package solutions;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Problem_1539 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        for (int i = 0; i < T; i++) {
            String s = scanner.next();

            int size = s.length();
            long hex = Long.parseLong(s, 16);

            boolean found = false;
            int step = 0;
            Set<Long> conquer = new HashSet<>();
            Set<Long> seen = new HashSet<>();
            conquer.add(hex);
            loop:
            while (step <= s.length()) {
                Set<Long> nextConquer = new HashSet<>();
                for (long x : conquer) {
                    if (done(x)) {
                        found = true;
                        break loop;
                    }
                    for (int start = 0; start < size - 1; start++) {
                        for (int target = 0; target < start; target++) {
                            long next = slice(x,
                                    0, target,
                                    start, start + 2,
                                    target, start,
                                    start + 2, size);
                            if (!conquer.contains(next) && !seen.contains(next)) {
                                nextConquer.add(next);
                            }
                        }

                        for (int target = start + 2; target <= size; target++) {
                            long next = slice(x,
                                    0, start,
                                    start + 2, target,
                                    start, start + 2,
                                    target, size);
                            if (!conquer.contains(next) && !seen.contains(next)) {
                                nextConquer.add(next);
                            }
                        }
                    }
                    seen.add(x);
                }
                conquer = nextConquer;
                step++;
            }
            if (found) {
                System.out.println(step);
            } else {
                System.out.println(-1);
            }
        }
    }

    private static long slice(long hex, int s1, int e1, int s2, int e2, int s3, int e3, int s4, int e4) {
        int l1 = e1 - s1;
        int l2 = e2 - s2;
        int l3 = e3 - s3;
        int l4 = e4 - s4;
        long r = 0;
        // 注意优先级
        r |= get(hex, s1, l1) >>> 4 * s1;
        r |= s2 - l1 > 0
                ? get(hex, s2, l2) >>> 4 * (s2 - l1)
                : get(hex, s2, l2) << 4 * -(s2 - l1);
        r |= s3 - l1 - l2 > 0
                ? get(hex, s3, l3) >>> 4 * (s3 - l1 - l2)
                : get(hex, s3, l3) << 4 * -(s3 - l1 - l2);
        r |= s4 - l1 - l2 - l3 > 0
                ? get(hex, s4, l4) >>> 4 * (s4 - l1 - l2 - l3)
                : get(hex, s4, l4) << 4 * -(s4 - l1 - l2 - l3);
        return r;
    }

    private static long get(long hex, int s, int l) {
        long A = ((long) 1 << ((s + l) * 4)) - 1;
        long B = ((long) 1 << (s * 4)) - 1;
        return hex & (A ^ B);
    }

    private static boolean done(long hex) {
        return hex == 0x1
                || hex == 0x12
                || hex == 0x123
                || hex == 0x1234
                || hex == 0x12345
                || hex == 0x123456
                || hex == 0x1234567
                || hex == 0x12345678;
    }
}
