package hiho;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

// AC
public class Problem_1399 {
  public static void main(String[] args) throws IOException {
    InputStream in = new BufferedInputStream(System.in);
    int N = readInt(in);
    int t = 0;
    for (int i = 0; i < N; i++) {
      int x = readInt(in);
      if (x % 2 == 0) {
        t++;
      } else {
        t--;
      }
    }
    System.out.println(Math.abs(t));
  }

  private static int readInt(InputStream in) throws IOException {
    int result = 0;
    boolean dig = false;

    while (true) {
      int c = in.read();
      if (c == -1) {
        break;
      }
      if (c >= '0' && c <= '9') {
        result = result * 10 + (c - '0');
        dig = true;
      } else if (dig) {
        break;
      }
    }
    return result;
  }
}
