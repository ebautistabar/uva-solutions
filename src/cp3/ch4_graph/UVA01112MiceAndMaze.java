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

// 1112 - Single-Source Shortest Paths (SSSP) On Weighted Graph: Dijkstra's, Easier, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=870
class UVA01112MiceAndMaze {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int MAX_CELLS = 100;

    public static void main(String args[]) throws IOException {
        int[] distance = new int[MAX_CELLS];
        int tests = Integer.parseInt(getLine().trim());
        while (tests-- > 0) {
            int cells = Integer.parseInt(getLine().trim());
            int exit = Integer.parseInt(getLine().trim()) - 1;
            int timeLimit = Integer.parseInt(getLine().trim());
            int connections = Integer.parseInt(getLine().trim());
            List<List<Tuple>> adjList = new ArrayList<>(cells);
            for (int i = 0; i < cells; i++) adjList.add(new ArrayList<Tuple>());
            for (int i = 0; i < connections; i++) {
                StringTokenizer st = new StringTokenizer(getLine());
                int from = Integer.parseInt(st.nextToken()) - 1; // 1-based
                int to = Integer.parseInt(st.nextToken()) - 1;
                int cost = Integer.parseInt(st.nextToken());
                // reversed graph, read below
                adjList.get(to).add(new Tuple(from, cost));
            }

            Arrays.fill(distance, Integer.MAX_VALUE);
            distance[exit] = 0;
            PriorityQueue<Tuple> pq = new PriorityQueue<>();
            pq.add(new Tuple(exit, 0));
            while (!pq.isEmpty()) {
                Tuple current = pq.remove();
                if (distance[current.node] < current.weight) continue; // found a better path
                for (Tuple next : adjList.get(current.node)) {
                    if (distance[next.node] > distance[current.node] + next.weight) {
                        distance[next.node] = distance[current.node] + next.weight;
                        pq.add(new Tuple(next.node, distance[next.node]));
                    }
                }
            }

            int mice = 0;
            for (int i = 0; i < cells; i++) {
                if (distance[i] <= timeLimit) {
                    mice++;
                }
            }
            out.println(mice);
            if (tests > 0) {
                out.println();
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

    private static class Tuple implements Comparable<Tuple> {
        int weight;
        int node;
        public Tuple(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }
        public int compareTo(Tuple other) {
            int wCmp = Integer.compare(weight, other.weight);
            return wCmp == 0 ? Integer.compare(node, other.node): wCmp;
        }
    }
}
/*
Dijkstra problem. If we consider the exit cell to be the goal state, then we
have to perform 1 dijkstra run for every other cell. That's inefficient.
We can consider the exit cell to be the start and measure the time from there
to every other cell. The statement draws attention to the fact that the graph
is directed. As we start the algo from the exit, and we want to measure the
time towards the exit, we need to use a reversed graph.
At the end we traverse the distances to check the mice that got out.
*/
