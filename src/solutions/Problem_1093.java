package solutions;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class Problem_1093 {
    public static void main(String[] args) {
        String input = "5 10 3 5\n" +
                "1 2 997\n" +
                "2 3 505\n" +
                "3 4 118\n" +
                "4 5 54\n" +
                "3 5 480\n" +
                "3 4 796\n" +
                "5 2 794\n" +
                "2 5 146\n" +
                "5 4 604\n" +
                "2 5 63";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int S = scanner.nextInt();
        int T = scanner.nextInt();
        // todo SPFA算法 Java对于10^6的输入一定会超时 ╮(￣▽￣")╭
    }
}
