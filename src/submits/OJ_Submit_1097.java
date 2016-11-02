package submits;

import java.util.Arrays;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class OJ_Submit_1097 {
    private static final int MAX_DISTANCE = 100000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int[][] distances = new int[N][N];
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                distances[x][y] = scanner.nextInt();
            }
        }
        boolean[] visited = new boolean[N];
        int[] lastCity = new int[N];
        Arrays.fill(lastCity, -1);
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

            visited[next] = true;
            visitCount++;
            // 更新其他城市的pathLens
            for (int other = 0; other < N; other++) {
                if (!visited[other]) {
                    if (pathLens[next] + distances[other][next] < pathLens[other]) {
                        pathLens[other] = pathLens[next] + distances[other][next];
                        lastCity[other] = next;
                    }
                }
            }
        }

        int result = 0;
        for (int city = 0; city < N; city++) {
            if (lastCity[city] != -1) {
                result += distances[city][lastCity[city]];
            }
        }
        System.out.println(result);
    }
}
