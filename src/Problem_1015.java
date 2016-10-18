import java.util.Scanner;

public class Problem_1015 {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        for (int i = 0; i < N; i += 1) {
            String target = scanner.next();
            String word = scanner.next();
            System.out.println(getAppearanceCount(word, target));
        }
    }

    private static int getAppearanceCount(String word, String target) {
        return getAppearanceCount(word.toCharArray(), target.toCharArray());
    }

    private static int getAppearanceCount(char[] word, char[] target) {
        int N = word.length;
        int M = target.length;
        int[] commonLenArray = getCommonLenArray(target);
        int result = 0;
        int start = 0;
        int t = 0;
        while (true) {
            while (t < M && start + t < N
                    && word[start + t] == target[t]) {
                t += 1;
            }
            if (t == M) { // 匹配一次
                int commonLen = commonLenArray[t - 1];
                start += t - commonLen;
                t = commonLen;

                result += 1;
            } else if (start + t == N) { // 匹配结束
                break;
            } else if (t == 0) { // 一个字符都没有匹配
                start++;
            } else { // 匹配了t个字符
                int commonLen = commonLenArray[t - 1];
                start += t - commonLen;
                t = commonLen;
            }
        }
        return result;
    }

    private static int kmp(String word, String target) {
        return kmp(word.toCharArray(), target.toCharArray());
    }

    private static int kmp(char[] word, char[] target) {
        int N = word.length;
        int M = target.length;
        int[] commonLenArray = getCommonLenArray(target);
        int start = 0;
        int t = 0;
        while (true) {
            while (t < M && start + t < N
                    && word[start + t] == target[t]) {
                t += 1;
            }
            if (t == M) { // 匹配成功
                return start;
            } else if (start + t == N) { // 匹配失败
                return -1;
            } else if (t == 0) { // 一个字符都没有匹配
                start++;
            } else { // 匹配了t个字符
                int commonLen = commonLenArray[t - 1];
                start += t - commonLen;
                t = commonLen;
            }
        }
    }

    private static int[] getCommonLenArray(char[] word) {
        int size = word.length;
        int repeat = 0;
        while (repeat < size && word[repeat] == word[0]) {
            repeat += 1;
        }

        int[] dp = new int[size];
        for (int t = 1; t < size; t++) {
            if (word[t] == word[dp[t - 1]]) {
                dp[t] = dp[t - 1] + 1;
            } else if (word[t] == word[0]) {
                dp[t] = dp[t - 1] == repeat ? repeat : 1;
            } else {
                dp[t] = 0;
            }
        }

        return dp;
    }
}
