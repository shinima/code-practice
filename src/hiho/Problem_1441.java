package hiho;

import java.util.*;

public class Problem_1441 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Sam sam = new Sam(scanner.next());
    int N = scanner.nextInt();
    for (int i = 0; i < N; i++) {
      SamState state = sam.query(scanner.next());
      state.pprint();
    }
  }

  private static class SamState {
    Set<String> strSet = new HashSet<>();
    Set<Integer> endposSet;

    void pprint() {
      String shortest = null, longest = null;
      for (String s : strSet) {
        if (shortest == null || s.length() < shortest.length()) {
          shortest = s;
        }
        if (longest == null || s.length() > longest.length()) {
          longest = s;
        }
      }
      System.out.print(shortest + " " + longest);
      int[] array = new int[endposSet.size()];
      int index = 0;
      for (int endpos : endposSet) {
        array[index++] = endpos;
      }
      Arrays.sort(array);
      for (int endpos : array) {
        System.out.print(" " + endpos);
      }
      System.out.println();
    }

    public String toString() {
      return "SamState{" +
        "strSet=" + strSet +
        ", endposSet=" + endposSet +
        '}';
    }
  }

  private static class Sam {
    List<SamState> states = new ArrayList<>();

    Sam(String total) {
      Map<String, Set<Integer>> string2EndPosSet = new HashMap<>();
      for (int beginIndex = 0; beginIndex < total.length(); beginIndex++) {
        for (int endIndex = beginIndex; endIndex <= total.length(); endIndex++) {
          String substring = total.substring(beginIndex, endIndex);
          if (string2EndPosSet.containsKey(substring)) {
            string2EndPosSet.get(substring).add(endIndex);
          } else {
            Set<Integer> endPosSet = new HashSet<>();
            endPosSet.add(endIndex);
            string2EndPosSet.put(substring, endPosSet);
          }
        }
      }
      for (Map.Entry<String, Set<Integer>> entry : string2EndPosSet.entrySet()) {
        Set<Integer> endposSet = entry.getValue();
        boolean added = false;
        for (SamState state : states) {
          if (state.endposSet.equals(endposSet)) {
            state.strSet.add(entry.getKey());
            added = true;
          }
        }
        if (!added) {
          SamState state = new SamState();
          state.strSet.add(entry.getKey());
          state.endposSet = entry.getValue();
          states.add(state);
        }
      }
    }

    SamState query(String s) {
      for (SamState state : states) {
        if (state.strSet.contains(s)) {
          return state;
        }
      }
      return null;
    }
  }
}
