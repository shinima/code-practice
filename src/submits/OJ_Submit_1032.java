package submits;

import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class OJ_Submit_1032 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        for (int i = 0; i < N; i++) {
            System.out.println(manacher(scanner.next()));
        }
    }

    private static int manacher(String string) {
        char[] word = prepare(string);
        int pos = 0; // position that has the max-right
        int t = 1;
        int[] RLArray = new int[word.length];
        RLArray[0] = 1;
        while (t < word.length) {
            int maxRight = RLArray[pos] + pos - 1;
            int RL = t >= maxRight ? 1 : Math.min(RLArray[2 * pos - t], maxRight - t + 1);
            while (t - RL >= 0
                    && t + RL < word.length
                    && word[t - RL] == word[t + RL]) {
                RL++;
            }
            RLArray[t] = RL;
            int cntRight = t + RL - 1;
            if (cntRight > maxRight) {
                pos = t;
            }
            t++;
        }
        int maxRL = 0;
        for (int RL : RLArray) {
            maxRL = Math.max(maxRL, RL);
        }
        return maxRL - 1;
    }

    private static char[] prepare(String string) {
        char[] result = new char[string.length() * 2 + 1];
        for (int i = 0; i < result.length; i++) {
            if (i % 2 == 0) {
                result[i] = '#';
            } else {
                result[i] = string.charAt(i / 2);
            }
        }
        return result;
    }
}
