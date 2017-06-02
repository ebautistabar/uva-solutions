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

// 11506 - Max Flow Problem (Edmond Karp's), Variants, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2501
// Network flow, notes below
class UVA11506AngryProgrammer {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int INFINITY = (int) 1e9;

    public static void main(String args[]) throws IOException {
        while (true) {
            StringTokenizer st = new StringTokenizer(getLine());
            int machines = parseInt(st.nextToken());
            int wires = parseInt(st.nextToken());
            if (machines == 0 && wires == 0) break;
            int nodes = machines * 2;
            int boss = 0;
            int server = machines - 1;
            int[][] residual = new int[nodes][nodes];
            List<List<Integer>> adjList = new ArrayList<>();
            for (int i = 0; i < nodes; i++) adjList.add(new ArrayList<>());
            for (int m = 0; m < machines - 2; m++) {
                st = new StringTokenizer(getLine());
                int id = parseInt(st.nextToken()) - 1; // 1-based
                int cost = parseInt(st.nextToken());
                addEdge(adjList, residual, id, id + machines, cost);
            }
            addEdge(adjList, residual, boss, boss + machines, INFINITY);
            addEdge(adjList, residual, server, server + machines, INFINITY);
            for (int w = 0; w < wires; w++) {
                st = new StringTokenizer(getLine());
                int from = parseInt(st.nextToken()) - 1; // 1-based
                int to = parseInt(st.nextToken()) - 1; // 1-based
                int cost = parseInt(st.nextToken());
                addEdge(adjList, residual, from + machines, to, cost);
                addEdge(adjList, residual, to + machines, from, cost);
            }

            // max flow via Edmond-Karp
            int maxflow = 0;
            int[] previous = new int[nodes];
            while (hasAugmentingPath(previous, adjList, residual, boss, server)) {
                int flow = INFINITY;
                for (int v = server; v != boss; v = previous[v]) {
                    flow = Math.min(flow, residual[previous[v]][v]);
                }
                for (int v = server; v != boss; v = previous[v]) {
                    residual[previous[v]][v] -= flow;
                    residual[v][previous[v]] += flow;
                }
                maxflow += flow;
            }

            // value of maxflow equals value of mincut
            out.println(maxflow);
        }
        out.close();
    }

    private static boolean hasAugmentingPath(int[] previous, List<List<Integer>> adjList, int[][] residual, int source, int goal) {
        Arrays.fill(previous, -1);
        previous[source] = -2;
        Queue<Integer> pending = new ArrayDeque<>();
        pending.add(source);
        while (!pending.isEmpty()) {
            int current = pending.remove();
            for (int next : adjList.get(current)) {
                if (residual[current][next] > 0 && previous[next] == -1) {
                    previous[next] = current;
                    if (next == goal) return true;
                    pending.add(next);
                }
            }
        }
        return false;
    }

    private static void addEdge(List<List<Integer>> adjList, int[][] residual, int from, int to, int capacity) {
        adjList.get(from).add(to);
        adjList.get(to).add(from);
        residual[from][to] = capacity;
        residual[to][from] = 0;
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
Network flow problem, more specifically it's a min cut problem. The capacities are the costs. Vertices (machines) have
costs too, so we must split them in 2 vertices with an edge connecting them with the specified capacity of the vertex.
After doing a regular max flow, the value of the min cut can be easily obtained: it's the exact same as the maxflow.

If we needed the exact edges of the mincut they could be retrieved too. After there's no more augmenting paths, a node
is in the S-component if it still can be reached from the source through edges with positive residual capacity, the
rest of the nodes are in the T-component and the sum of capacities of edges connecting both components is the min cut.
*/
