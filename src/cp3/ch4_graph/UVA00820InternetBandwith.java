package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

// 00820 - Standard Max Flow Problem (Edmond Karp's), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=761
// Network flow, notes below
class UVA00820InternetBandwith {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int INFINITY = (int) 1e9;

    public static void main(String args[]) throws IOException {
        int test = 1;
        String line = getLine();
        while (line != null) {
            int nodes = parseInt(line);
            if (nodes == 0) break;
            List<List<Integer>> adjList = new ArrayList<>(nodes);
            for (int i = 0; i < nodes; i++) adjList.add(new ArrayList<Integer>());
            int[][] adjMatrix = new int[nodes][nodes];
            StringTokenizer st = new StringTokenizer(getLine());
            int source = parseInt(st.nextToken()) - 1; // 1-based
            int goal = parseInt(st.nextToken()) - 1;
            int connections = parseInt(st.nextToken());
            for (int c = 0; c < connections; c++) {
                st = new StringTokenizer(getLine());
                int a = parseInt(st.nextToken()) - 1; // 1-based
                int b = parseInt(st.nextToken()) - 1;
                int bandwidth = parseInt(st.nextToken());
                if (adjMatrix[a][b] == 0) {
                    // if it's the first time we see this pair
                    adjList.get(a).add(b);
                    adjList.get(b).add(a);
                }
                adjMatrix[a][b] += bandwidth;
                adjMatrix[b][a] += bandwidth;
            }

            int totalBandwidth = 0;
            int[] previous = new int[nodes];
            while (hasAugmentingPath(source, goal, adjList, adjMatrix, previous)) {
                int flow = INFINITY;
                // Find bottleneck capacity
                for (int v = goal; v != source; v = previous[v]) {
                    flow = Math.min(flow, adjMatrix[previous[v]][v]);
                }
                // Increase flow
                for (int v = goal; v != source; v = previous[v]) {
                    // Decrease the forward edge (representing that we have less capacity left)
                    adjMatrix[previous[v]][v] -= flow;
                    // Increase the backward edge (representing the increase in flow)
                    adjMatrix[v][previous[v]] += flow;
                }
                totalBandwidth += flow;
            }

            out.printf("Network %d%n", test++);
            out.printf("The bandwidth is %d.%n%n", totalBandwidth);
            line = getLine();
        }
        out.close();
    }

    private static boolean hasAugmentingPath(int start, int goal, List<List<Integer>> adjList, int[][] adjMatrix, int[] previous) {
        Arrays.fill(previous, -1);
        previous[start] = start;
        Queue<Integer> pending = new ArrayDeque<>();
        pending.add(start);
        while (!pending.isEmpty()) {
            int current = pending.remove();
            for (int next : adjList.get(current)) {
                // If there's capacity left and we haven't visited yet
                if (adjMatrix[current][next] > 0 && previous[next] == -1) {
                    previous[next] = current;
                    if (next == goal) return true; // if we reached the goal, there's a path
                    pending.add(next);
                }
            }
        }
        return false;
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while (line != null && "".equals(line.trim())) // dealing with blank lines
            line = in.readLine();
        return line;
    }
    private static int parseInt(String text) {
        return Integer.parseInt(text.trim());
    }
}
/*
Direct application of Edmond Karp's for network flow. Using only the adjacency matrix in BFS seems was faster on
the judge than using the adjacency list, by a little margin. Maybe the input is small enough that it doesn't make
a difference.?
*/
