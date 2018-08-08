package solutions;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Problem_1061 {
  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in)));
    int N = Integer.parseInt(reader.readLine());
    for (int caseIndex = 0; caseIndex < N; caseIndex++) {
      reader.readLine(); // skip one line
      if (isBeautifulString(reader.readLine())) {
        System.out.println("YES");
      } else {
        System.out.println("NO");
      }
    }
  }

  private static boolean isBeautifulString(String string) {
    int fcount = 0;
    int scount = 0;
    int tcount = 0;
    char fc = 0;
    for (char c : string.toCharArray()) {
      if (fcount == 0) {
        fc = c;
        fcount = 1;
      } else if (scount == 0) {
        if (c == fc) {
          fcount++;
        } else if (c == fc + 1) {
          scount++;
          if (scount > fcount) { // scroll
            fc++;
            fcount = scount;
            scount = 0;
          }
        } else { // reset
          fcount = 1;
          fc = c;
          scount = 0;
          tcount = 0;
        }
      } else if (tcount == 0) {
        if (c == fc + 1) {
          scount++;
          if (scount > fcount) { // scroll
            fcount = scount;
            fc++;
            scount = 0;
          }
        } else if (c == fc + 2) {
          tcount++;
          if (tcount == scount) {
            return true;
          }
        } else { // reset
          fcount = 1;
          fc = c;
          scount = 0;
          tcount = 0;
        }
      } else {
        if (c == fc + 2) {
          tcount++;
          if (tcount == scount) {
            return true;
          }
        } else if (c == fc + 3) { // scroll
          fc++;
          fcount = scount;
          scount = tcount;
          tcount = 1;
        } else { // reset
          fcount = 1;
          fc = c;
          scount = 0;
          tcount = 0;
        }
      }
    }
    return false;
  }
}
