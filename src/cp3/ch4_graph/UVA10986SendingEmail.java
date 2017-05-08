package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 10986 - Single-Source Shortest Paths (SSSP) On Weighted Graph: Dijkstra's, Easier, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1927
// Dijkstra, notes below
class UVA10986SendingEmail {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    // smaller infinity, to avoid overflow. Although it shouldn't be a problem
    private static final int INFINITY = Integer.MAX_VALUE / 2;

    public static void main(String args[]) throws IOException {
        int tests = Integer.parseInt(getLine().trim());
        for (int t = 1; t <= tests; t++) {
            StringTokenizer st = new StringTokenizer(getLine());
            int servers = Integer.parseInt(st.nextToken());
            int cables = Integer.parseInt(st.nextToken());
            int start = Integer.parseInt(st.nextToken());
            int goal = Integer.parseInt(st.nextToken());
            List<List<Pair>> adjList = new ArrayList<>();
            for (int i = 0; i < servers; i++) adjList.add(new ArrayList<Pair>());
            for (int i = 0; i < cables; i++) {
                st = new StringTokenizer(getLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int latency = Integer.parseInt(st.nextToken());
                adjList.get(a).add(new Pair(latency, b));
                adjList.get(b).add(new Pair(latency, a));
            }

            int[] distance = new int[servers];
            Arrays.fill(distance, INFINITY);
            distance[start] = 0;
            PriorityQueue<Pair> pq = new PriorityQueue<>();
            pq.add(new Pair(0, start));
            while (!pq.isEmpty()) {
                Pair current = pq.remove();
                // If current is an old pair, that has been superseded by a
                //   more recent pair with a smaller distance, then discard.
                // In other words, if we have a better path to v already, don't
                //   bother doing anything with this worse path
                // This is needed to avoid useless work, as we don't have eager
                //   deletion in the default priority queue
                if (distance[current.v] < current.w) continue;
                for (Pair next : adjList.get(current.v)) {
                    // If we improve the distance to next by going from current
                    //   then update distance and enqueue
                    if (distance[next.v] > distance[current.v] + next.w) {
                        distance[next.v] = distance[current.v] + next.w;
                        pq.add(new Pair(distance[next.v], next.v));
                    }
                }
            }

            if (distance[goal] == INFINITY) {
                out.printf("Case #%d: unreachable%n", t);
            } else {
                out.printf("Case #%d: %d%n", t, distance[goal]);
            }
        }
        out.close();
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }


    private static class Pair implements Comparable<Pair> {
        int w; // weight
        int v; // node
        public Pair(int w, int v) {
            this.w = w;
            this.v = v;
        }
        public int compareTo(Pair other) {
            int wCmp = Integer.compare(w, other.w);
            return wCmp == 0 ? Integer.compare(v, other.v) : wCmp;
        }
    }
}
/*
Cables can be 0: the queue will empty without any updates on distance[]. Thus
  we'll print unreachable.
Overflow of distance: there can't be. There can be no cycles in the path: negative
  cycles would cause an infinite loop, and positive cycles increase the distance
  needlessly. Thus the longest path is one in a linked list. If there are max 20k
  nodes, a cable connecting each pair and each cable is max 10k, 20k*10k < 200M
Repeated cables: we take care of it with the innermost if in Dijkstra. The first
  copy of the cable will pass the if and update the distance. The second copy won't
  pass the if now that the distance is updated.
*/
