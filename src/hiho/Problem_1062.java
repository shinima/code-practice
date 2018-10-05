package hiho;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Problem_1062 { // todo WRONG ANSWER
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int N = scanner.nextInt();
    HashMap<String, String> map = new HashMap<>(N);
    for (int i = 0; i < N; i++) {
      String parent = scanner.next();
      String child = scanner.next();
      map.put(child, parent);
    }
    int M = scanner.nextInt();
    for (int i = 0; i < M; i++) {
      String person1 = scanner.next();
      String person2 = scanner.next();
      Set<String> ancestors = new HashSet<>();
      ancestors.add(person1);
      String temp = person1;
      while (map.containsKey(temp)) {
        temp = map.get(temp);
        ancestors.add(temp);
      }
      temp = person2;
      boolean flag = false;
      while (true) {
        if (ancestors.contains(temp)) {
          System.out.println(temp);
          flag = true;
          break;
        }
        if (map.containsKey(temp)) {
          temp = map.get(temp);
        } else {
          break;
        }
      }
      if (!flag) {
        System.out.println(-1);
      }
    }
  }
}
