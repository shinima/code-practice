package submits;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// TODO: 2016/10/29 TLE

@SuppressWarnings("Duplicates")
public class OJ_Submit_1068 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(reader.readLine());
        int shift = getShift(N);
        int[] weights = new int[N];
        for (int i = 0; i < N; i++) {
            weights[i] = Integer.parseInt(reader.readLine());
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

        int Q = Integer.parseInt(reader.readLine());
        for (int i = 0; i < Q; i++) {
            String line = reader.readLine();
            int spaceIndex = line.indexOf(' ');
            int left = Integer.parseInt(line.substring(0, spaceIndex)) - 1;
            int right = Integer.parseInt(line.substring(spaceIndex + 1)) - 1;
            System.out.println(exciting(f, left, right));
        }
    }

    private static int exciting(int[][] f, int left, int right) {
        int shift = getShift(right - left + 1);
        int len = 1 << shift;
        return Math.min(f[left][shift], f[right - len + 1][shift]);
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