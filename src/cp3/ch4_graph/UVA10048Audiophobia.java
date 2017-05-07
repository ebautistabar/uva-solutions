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

// 10048 - Minimum Spanning Tree, Variants, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=989
// MST-minimax: we want to minimize the max edge in the i-j path. By building
//  MST first, we guarantee that we know which are the smallest edges in the
//  graph which form a spanning tree. By having a spanning tree, we have a path
//  between every pair of edges. Now we just need to go from i to j and take
//  note of the biggest edge in the path
class UVA10048Audiophobia {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int test = 1;
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int crossings = Integer.parseInt(st.nextToken());
            int streets = Integer.parseInt(st.nextToken());
            int queries = Integer.parseInt(st.nextToken());
            if (crossings == 0 && streets == 0 && queries == 0) break;
            List<List<Edge>> adjList = new ArrayList<>(crossings);
            for (int i = 0; i < crossings; i++) {
                adjList.add(new ArrayList<Edge>());
            }
            for (int i = 0; i < streets; i++) {
                st = new StringTokenizer(getLine());
                int a = Integer.parseInt(st.nextToken()) - 1;
                int b = Integer.parseInt(st.nextToken()) - 1;
                int noise = Integer.parseInt(st.nextToken());
                Edge street = new Edge(a, b, noise);
                adjList.get(a).add(street);
                adjList.get(b).add(street);
            }

            List<List<Edge>> mst = null;
            if (queries > 0) {
                mst = getMST(adjList);
            }

            if (test > 1) {
                out.println();
            }
            out.printf("Case #%d%n", test);
            for (int i = 0; i < queries; i++) {
                st = new StringTokenizer(getLine());
                int origin = Integer.parseInt(st.nextToken()) - 1;
                int destination = Integer.parseInt(st.nextToken()) - 1;
                if (!printMaxNoise(mst, origin, destination)) {
                    out.println("no path");
                }
            }

            test++;
            line = getLine();
        }
        out.close();
    }

    private static boolean printMaxNoise(List<List<Edge>> mst, int origin,
            int destination) {
        return printMaxNoise(mst, origin, destination, 0, -1);
    }

    private static boolean printMaxNoise(List<List<Edge>> mst, int origin,
            int destination, int maxNoise, int parent) {
        if (origin == destination) {
            out.println(maxNoise);
            return true;
        }
        for (Edge edge : mst.get(origin)) {
            int next = edge.other(origin);
            if (parent != next) {
                if (printMaxNoise(mst, next, destination, Math.max(maxNoise, edge.weight), origin)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static List<List<Edge>> getMST(List<List<Edge>> adjList) {
        List<List<Edge>> mst = new ArrayList<>();
        for (int i = 0; i < adjList.size(); i++) {
            mst.add(new ArrayList<Edge>());
        }
        boolean[] visited = new boolean[adjList.size()];
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                visit(i, pq, visited, adjList);
                while (!pq.isEmpty()) {
                    Edge current = pq.remove();
                    if (visited[current.a] && visited[current.b]) continue;
                    if (!visited[current.a]) visit(current.a, pq, visited, adjList);
                    if (!visited[current.b]) visit(current.b, pq, visited, adjList);
                    mst.get(current.a).add(current);
                    mst.get(current.b).add(current);
                }
            }
        }
        return mst;
    }

    private static void visit(int node, PriorityQueue<Edge> pq, boolean[] visited, List<List<Edge>> adjList) {
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
        public Edge(int a, int b, int weight) {
            this.a = a;
            this.b = b;
            this.weight = weight;
        }
        public int other(int node) {
            if (node == a) return b;
            if (node == b) return a;
            return -1;
        }
        public int compareTo(Edge other) {
            return Integer.compare(weight, other.weight);
        }
        public String toString() {
            return String.format("[%d-%d:%d]", a, b, weight);
        }
    }
}
