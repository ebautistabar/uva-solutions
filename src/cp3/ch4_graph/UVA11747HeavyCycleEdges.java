package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;

// 11747 - Minimum Spanning Tree, Standard, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2847
class UVA11747HeavyCycleEdges {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int nodes = Integer.parseInt(st.nextToken());
            int edges = Integer.parseInt(st.nextToken());
            if (nodes == 0 && edges == 0) break;
            List<List<Edge>> adjList = new ArrayList<>(nodes);
            for (int i = 0; i < nodes; i++) adjList.add(new ArrayList<Edge>());
            for (int i = 0; i < edges; i++) {
                st = new StringTokenizer(getLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());
                Edge edge = new Edge(a, b, w);
                adjList.get(a).add(edge);
                adjList.get(b).add(edge);
            }

            Set<Integer> mstWeights = new HashSet<>();
            boolean[] visited = new boolean[nodes];
            // the graph from the input may not be connected (several subgraphs)
            // so try to create MST from each node
            for (int i = 0; i < nodes; i++) {
                if (!visited[i]) {
                    PriorityQueue<Edge> pq = new PriorityQueue<>();
                    visit(adjList, pq, visited, i);
                    while (!pq.isEmpty()) {
                        Edge edge = pq.remove();
                        if (visited[edge.a] && visited[edge.b]) continue;
                        if (!visited[edge.a]) {
                            visit(adjList, pq, visited, edge.a);
                        } else {
                            visit(adjList, pq, visited, edge.b);
                        }
                        mstWeights.add(edge.weight);
                    }
                }
            }
            // heaviest edges are all the edges that don't belong to the MST.
            // i == e.a filters duplicates (as every edge is present 2 times
            // in the graph, because of it being undirected)
            List<Integer> heaviestWeights = new ArrayList<>();
            for (int i = 0; i < nodes; i++) {
                for (int j = 0; j < adjList.get(i).size(); j++) {
                    Edge e = adjList.get(i).get(j);
                    if (!mstWeights.contains(e.weight) && i == e.a) {
                        heaviestWeights.add(adjList.get(i).get(j).weight);
                    }
                }
            }

            if (heaviestWeights.size() == 0) {
                out.println("forest");
            } else {
                Collections.sort(heaviestWeights);
                StringBuilder sb = new StringBuilder();
                for (int weight : heaviestWeights) {
                    sb.append(weight);
                    sb.append(' ');
                }
                sb.setLength(sb.length() - 1); // remove last space
                out.println(sb);
            }

            line = getLine();
        }

        out.close();
    }

    private static void visit(List<List<Edge>> adjList, PriorityQueue<Edge> pq,
            boolean[] visited, int node) {
        visited[node] = true;
        for (Edge edge : adjList.get(node)) {
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
