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

// 12125 - Max Flow Problem (Edmond Karp's), Variants, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3277
// Network flow, notes below
class UVA12125MarchOfThePenguins {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int INFINITY = (int) 1e9;
    private static final int SOURCE = 0;

    public static void main(String args[]) throws IOException {
        int tests = parseInt(getLine());
        while (tests-- > 0) {
            StringTokenizer st = new StringTokenizer(getLine());
            int platforms = parseInt(st.nextToken());
            double maxDistance = parseDouble(st.nextToken());
            maxDistance *= maxDistance; // squared so we don't need square root when calculating distances
            int nodes = 1 + platforms * 2;
            int[][] residual = new int[nodes][nodes];
            List<List<Integer>> adjList = new ArrayList<>();
            for (int n = 0; n < nodes; n++) adjList.add(new ArrayList<Integer>());
            int[] x = new int[platforms + 1];
            int[] y = new int[platforms + 1];
            int totalPenguins = 0;
            for (int p = 1; p <= platforms; p++) {
                st = new StringTokenizer(getLine());
                x[p] = parseInt(st.nextToken());
                y[p] = parseInt(st.nextToken());
                int penguins = parseInt(st.nextToken());
                int jumps = parseInt(st.nextToken());
                if (penguins > 0)
                    addEdge(adjList, residual, SOURCE, p, penguins);
                if (jumps > 0)
                    addEdge(adjList, residual, p, p + platforms, jumps);
                totalPenguins += penguins;
            }
            for (int i = 1; i <= platforms; i++) {
                for (int j = i + 1; j <= platforms; j++) {
                    double distance = getDistance(x, y, i, j);
                    if (Double.compare(distance, maxDistance) < 1) {
                        //out.printf("%.4f%n", distance);
                        addEdge(adjList, residual, i + platforms, j, INFINITY);
                        addEdge(adjList, residual, j + platforms, i, INFINITY);
                    }
                }
            }

            List<Integer> meetingPoints = new ArrayList<>();
            for (int p = 1; p <= platforms; p++) {
                int sink = p;
                int[][] residualCopy = copy(residual);
                // Max flow
                int maxFlow = 0;
                int[] previous = new int[nodes];
                while (hasAugmentingPath(previous, adjList, residualCopy, SOURCE, sink)) {
                    int flow = INFINITY;
                    for (int v = sink; v != SOURCE; v = previous[v]) {
                        flow = Math.min(flow, residualCopy[previous[v]][v]);
                    }
                    for (int v = sink; v != SOURCE; v = previous[v]) {
                        residualCopy[previous[v]][v] -= flow;
                        residualCopy[v][previous[v]] += flow;
                    }
                    maxFlow += flow;
                }
                // Is valid meeting point?
                if (maxFlow == totalPenguins) {
                    meetingPoints.add(p);
                }
            }

            if (meetingPoints.isEmpty()) {
                out.println("-1");
            } else {
                StringBuilder sb = new StringBuilder();
                for (int point : meetingPoints) {
                    sb.append(point - 1); // output is 0-based
                    sb.append(' ');
                }
                sb.setLength(sb.length() - 1);
                out.println(sb);
            }
        }
        out.close();
    }

    private static int[][] copy(int[][] residual) {
        int[][] copy = new int[residual.length][residual[0].length];
        for (int i = 0; i < residual.length; i++) {
            System.arraycopy(residual[i], 0, copy[i], 0, residual[i].length);
        }
        return copy;
    }

    private static boolean hasAugmentingPath(int[] previous, List<List<Integer>> adjList, int[][] residual, int source, int sink) {
        Arrays.fill(previous, -1);
        Queue<Integer> pending = new ArrayDeque<>();
        pending.add(source);
        while (!pending.isEmpty()) {
            int current = pending.remove();
            for (int next : adjList.get(current)) {
                if (previous[next] == -1 && residual[current][next] > 0) {
                    previous[next] = current;
                    if (next == sink) return true;
                    pending.add(next);
                }
            }
        }
        return false;
    }

    private static double getDistance(int[] x, int[] y, int i, int j) {
        int dx = x[i] - x[j];
        int dy = y[i] - y[j];
        return dx * dx + dy * dy;
    }

    public static void addEdge(List<List<Integer>> adjList, int[][] residual, int from, int to, int capacity) {
        adjList.get(from).add(to);
        adjList.get(to).add(from);
        residual[from][to] = capacity;
        residual[to][from] = 0;
        //out.printf("(%d,%d) %d%n", from, to, capacity);
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
    private static double parseDouble(String text) {
        return Double.parseDouble(text.trim());
    }
}
/*
Network flow problem. Capacity is the number of penguins that flow through the graph. The initial penguins go from
the source node to the starting platforms read from the input. 2 platforms are connected if their euclidean distance
is below or equal to the limit from the input. Each platform has a capacity itself (number of penguins
that can jump off of it). We split the platforms in 2 with an edge that connects them and a capacity equal the number of
penguins that can jump off of it.

The issue is there is no definite destination. How do penguins leave the graph? There's no fancy trick. Just iterate
all the nodes and calculate max flow connecting the sink to each node in turn.
*/
