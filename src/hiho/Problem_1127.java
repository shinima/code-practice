package hiho;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Problem_1127 {
  public static void main(String[] args) throws IOException {
    int N = readInt(System.in);
    int M = readInt(System.in);
    Graph graph = new Graph(N);
    for (int edgeIndex = 0; edgeIndex < M; edgeIndex++) {
      int start = readInt(System.in);
      int end = readInt(System.in);
      graph.addEdge(start, end);
    }
    int maxPairCount = graph.getMaxPairsCount();
    System.out.println(maxPairCount);
    System.out.println(N - maxPairCount);
  }

  private static class Graph {
    int N;
    Map<Integer, List<Integer>> edges;
    Set<Integer> U, V;
    int[] pairs;

    Graph(int N) {
      this.N = N;
      this.edges = new HashMap<>();
    }

    void addEdge(int start, int end) {
      if (!edges.containsKey(start)) {
        edges.put(start, new ArrayList<Integer>());
      }
      edges.get(start).add(end);
      if (!edges.containsKey(end)) {
        edges.put(end, new ArrayList<Integer>());
      }
      edges.get(end).add(start);
    }

    int getMaxPairsCount() {
      bipartite();
      pairs = new int[N + 1];
      Arrays.fill(pairs, -1); // -1 表示还没有匹配
      for (int vertex : U) {
        if (pairs[vertex] == -1) {
          List<Integer> path = queryCrossPath(vertex);
          if (path != null) { // 进行虚实转换
            transformCrossPath(path);
          } // else 说明没有找到路径, 跳过该点
        }
      }
      int count = 0;
      for (int other : pairs) {
        if (other != -1) {
          count++;
        }
      }
      return count / 2;
    }

    void transformCrossPath(List<Integer> path) {
      // dash - solid - dash  ==> solid - dash - solid
      for (int i = 0; i + 1 < path.size(); i += 2) {
        int start = path.get(i);
        int end = path.get(i + 1);
        pairs[start] = end;
        pairs[end] = start;
      }
    }

    List<Integer> queryCrossPath(int vertex) {
      List<Integer> path = new ArrayList<>();
      Set<Integer> visited = new HashSet<>();
      // hihocoder优化2
      Set<Integer> cache = new HashSet<>();
      if (findDash(path, visited, vertex, cache)) {
        return path;
      } else {
        return null;
      }
    }

    boolean findDash(List<Integer> path, Set<Integer> visited, int start, Set<Integer> cache) {
      path.add(start);
      visited.add(start);
      if (edges.containsKey(start)) {
        for (int connected : edges.get(start)) {
          if (!visited.contains(connected)
            && findSolid(path, visited, connected, cache)) {
            return true;
          }
        }
      }
      path.remove(path.size() - 1);
      visited.remove(start);
      return false;
    }

    boolean findSolid(List<Integer> path, Set<Integer> visited, int start, Set<Integer> cache) {
      if (cache.contains(start)) {
        return false;
      }
      path.add(start);
      visited.add(start);
      int peer = pairs[start];
      if (peer == -1) { // 如果该结点目前没有匹配的结点, 则返回true表示当前path是可选的
        return true;
      }
      // 否则的话 递归寻找peer起始的路径
      if (findDash(path, visited, peer, cache)) {
        return true;
      }
      path.remove(path.size() - 1); // remove start
      visited.remove(start);
      cache.add(start);
      return false;
    }

    // 根据目前的情况将图二分. 可以认为该图一定可以二分
    void bipartite() {
      U = new HashSet<>();
      V = new HashSet<>();
      Set<Integer> unparted = new HashSet<>();
      for (int vertex = 1; vertex <= N; vertex++) {
        unparted.add(vertex);
      }
      while (!unparted.isEmpty()) {
        int vertex = unparted.iterator().next();
        part(U, vertex, unparted);
      }
    }

    void part(Set<Integer> set, int vertex, Set<Integer> unparted) {
      set.add(vertex);
      unparted.remove(vertex);
      if (edges.containsKey(vertex)) {
        for (int connected : edges.get(vertex)) {
          if (unparted.contains(connected)) {
            part(set == U ? V : U, connected, unparted);
          }
        }
      }
    }
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
