package solutions;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Problem_1097 { // todo 输出不对, 还需要继续调试
    private static final int MAX_DISTANCE = 100000;

    public static void main(String[] args) {
        System.out.println(392 + 1005 + 1182);
        String input = "5\n" +
                "0    1005 6963 392  1182 \n" +
                "1005 0    1599 4213 1451 \n" +
                "6963 1599 0    9780 2789 \n" +
                "392  4213 9780 0    5236 \n" +
                "1182 1451 2789 5236 0    \n";
        // Expected output: 4178
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        int N = scanner.nextInt();
        int[][] distances = new int[N][N];
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                distances[x][y] = scanner.nextInt();
            }
        }
        int result = 0;
        boolean[] visited = new boolean[N];
        int[] pathLens = new int[N];
        Arrays.fill(pathLens, MAX_DISTANCE);
        pathLens[0] = 0; // 从下标为0的城市出发
        int visitCount = 0;

        while (visitCount < N) {
            int next = -1;
            for (int city = 0; city < N; city++) { // 选取下一个城市
                if (!visited[city] && (next == -1 || pathLens[city] < pathLens[next])) {
                    next = city;
                }
            }

            System.out.println("Add " + next);
            visited[next] = true;
            visitCount++;
            result += pathLens[next];
            // 更新其他城市的pathLens
            for (int other = 0; other < N; other++) {
                if (!visited[other]) {
                    pathLens[other] = Math.min(pathLens[other], pathLens[next] + distances[other][next]);
                }
            }
        }

        System.out.println(Arrays.deepToString(distances));
        System.out.println(result);
    }
}
