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

// 11631 - Minimum Spanning Tree, Standard, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2678
// Direct application of mst. Answer is original cost - mst cost
class UVA11631DarkRoads {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int junctions = Integer.parseInt(st.nextToken()); // <= 200k
            int roads = Integer.parseInt(st.nextToken()); // <= 200k
            if (junctions == 0 && roads == 0) break;
            List<List<Edge>> adjList = new ArrayList<>();
            for (int i = 0; i < junctions; i++) adjList.add(new ArrayList<Edge>());
            int originalCost = 0;
            for (int i = 0; i < roads; i++) {
                st = new StringTokenizer(getLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());
                Edge edge = new Edge(a, b, cost);
                adjList.get(a).add(edge);
                adjList.get(b).add(edge);
                originalCost += cost;
            }

            int finalCost = getMSTCost(adjList);

            out.println(originalCost - finalCost);
            line = getLine();
        }
        out.close();
    }

    private static int getMSTCost(List<List<Edge>> adjList) {
        int cost = 0;
        boolean[] visited = new boolean[adjList.size()];
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        visit(adjList, 0, visited, pq);
        int nodes = 1;
        while (nodes != visited.length) {
            Edge edge = pq.remove();
            // One of them must be in the MST already. If both are, then
            // this is an outdated edge that we don't need
            if (visited[edge.a] && visited[edge.b]) continue;
            cost += edge.weight;
            // Use the node that is not in the MST yet
            if (!visited[edge.a]) {
                visit(adjList, edge.a, visited, pq);
            } else {
                visit(adjList, edge.b, visited, pq);
            }
            nodes++;
        }
        return cost;
    }

    private static void visit(List<List<Edge>> adjList, int node, boolean[] visited, PriorityQueue<Edge> pq) {
        visited[node] = true;
        for (Edge edge : adjList.get(node)) {
            // insert edges to all unvisited neighbors
            if (!visited[edge.other(node)]) {
                pq.add(edge);
            }
        }
        
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
        public Edge(int a, int b, int w) {
            this.a = a;
            this.b = b;
            this.weight = w;
        }
        public int other(int v) {
            return a == v ? b : a;
        }
        public int compareTo(Edge other) {
            return Integer.compare(weight, other.weight);
        }
    }
}
