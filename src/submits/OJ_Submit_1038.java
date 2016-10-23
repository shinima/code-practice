package submits;

import java.util.Scanner;

/**
 * 01背包问题
 */
@SuppressWarnings("Duplicates")
public class OJ_Submit_1038 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt(); // 奖品个数
        int M = scanner.nextInt(); // 奖券数量
        int[] need = new int[N];
        int[] value = new int[N];
        for (int i = 0; i < N; i++) {
            need[i] = scanner.nextInt();
            value[i] = scanner.nextInt();
        }
        System.out.println(maxValue(N, M, need, value));
    }

    private static int maxValue(int N, int M, int[] need, int[] value) {
        int[] best = new int[M + 1];
        for (int k = 1; k <= N; k++) {
            int[] nextBest = new int[M + 1];
            for (int v = 0; v <= M; v++) {
                int option1 = best[v]; // not-pick item[k-1]

                int option2; // pick item[k]
                if (v - need[k - 1] >= 0) {
                    option2 = best[v - need[k - 1]] + value[k - 1];
                } else {
                    option2 = 0;
                }

                nextBest[v] = Math.max(option1, option2);
            }
            best = nextBest;
        }
        return best[M];
    }
}