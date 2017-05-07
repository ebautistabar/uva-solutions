package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 11228 - Minimum Spanning Tree, Standard, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2169
/*
 * First calculate distance between each pair of cities
 * Then calculate MST keeping track of the edges under/above the threshold
 */
class UVA11228TransportationSystem {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int tests = Integer.parseInt(getLine().trim());
        for (int t = 1; t <= tests; t++) {
            StringTokenizer st = new StringTokenizer(getLine());
            int cities = Integer.parseInt(st.nextToken());
            int stateThreshold = Integer.parseInt(st.nextToken());
            int[] x = new int[cities];
            int[] y = new int[cities];
            for (int i = 0; i < cities; i++) {
                st = new StringTokenizer(getLine());
                x[i] = Integer.parseInt(st.nextToken());
                y[i] = Integer.parseInt(st.nextToken());
            }

            List<List<Edge>> adjList = new ArrayList<>();
            for (int i = 0; i < cities; i++) adjList.add(new ArrayList<Edge>());
            for (int i = 0; i < cities; i++) {
                for (int j = i + 1; j < cities; j++) {
                    int distance = getDistance(x, y, i, j);
                    Edge edge = new Edge(i, j, distance);
                    adjList.get(i).add(edge);
                    adjList.get(j).add(edge);
                }
            }
            stateThreshold *= stateThreshold; // we use squared distances

            int states = 1;
            double roads = 0;
            double railroads = 0;
            PriorityQueue<Edge> pq = new PriorityQueue<>();
            boolean[] visited = new boolean[cities];
            visit(adjList, visited, pq, 0);
            int visits = 1;
            while (visits != cities) {
                Edge edge = pq.remove();
                // both nodes are in the MST, so discard this edge
                if (visited[edge.a] && visited[edge.b]) continue;
                // visit the unvisited node
                if (!visited[edge.a]) {
                    visit(adjList, visited, pq, edge.a);
                } else {
                    visit(adjList, visited, pq, edge.b);
                }
                visits++;
                // logic of the problem
                if (edge.weight <= stateThreshold) {
                    roads += Math.sqrt(edge.weight);
                } else {
                    states++;
                    railroads += Math.sqrt(edge.weight);
                }
            }

            out.printf("Case #%d: %d %.0f %.0f%n", t, states, roads, railroads);
        }
        out.close();
    }

    private static void visit(List<List<Edge>> adjList, boolean[] visited,
            PriorityQueue<Edge> pq, int node) {
        visited[node] = true;
        for (Edge edge : adjList.get(node)) {
            if (!visited[edge.other(node)]) {
                pq.add(edge);
            }
        }
    }

    private static int getDistance(int[] x, int[] y, int i, int j) {
        int dx = x[i] - x[j];
        int dy = y[i] - y[j];
        // don't need to compute sqrt, we'll use the squared distances
        return dx * dx + dy * dy;
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }

    private static class Edge implements Comparable<Edge> {
        int a;
        int b;
        int weight;
        public Edge(int a, int b, int distance) {
            this.a = a;
            this.b = b;
            this.weight = distance;
        }
        public int other(int v) {
            return a == v ? b : a;
        }
        public int compareTo(Edge other) {
            return Integer.compare(weight, other.weight);
        }
    }
}
