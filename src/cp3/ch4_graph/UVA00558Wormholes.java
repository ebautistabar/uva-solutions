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
import java.util.StringTokenizer;

// 558 - Single Source Shortest Paths (SSSP), On Graph With Negative Weight Cycle: Bellman Ford's, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=499
// Find negative cycle with Bellman Ford
class UVA00558Wormholes {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int tests = Integer.parseInt(getLine().trim());
        for (int t = 1; t <= tests; t++) {
            StringTokenizer st = new StringTokenizer(getLine());
            int systems = Integer.parseInt(st.nextToken());
            int holes = Integer.parseInt(st.nextToken());
            List<List<Tuple>> adjList = new ArrayList<>();
            for (int i = 0; i < systems; i++) adjList.add(new ArrayList<Tuple>());
            for (int i = 0; i < holes; i++) {
                st = new StringTokenizer(getLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                int time = Integer.parseInt(st.nextToken());
                adjList.get(from).add(new Tuple(to, time));
            }

            int[] distance = new int[systems];
            // Use a "smaller" infinity to prevent overflow when doing
            // distance[u] + cost. Here we process the nodes in any order
            // so we are not protected from it. In the case of Dijkstra we
            // always know that distance[u] has been updated and will not hold
            // infinity when we execute the sum
            Arrays.fill(distance, (int) 1e9);
            distance[0] = 0;
            for (int i = 0; i < systems - 1; i++) { // v - 1 times
                for (int u = 0; u < systems; u++) { // for each node
                    for (int j = 0; j < adjList.get(u).size(); j++) {
                        Tuple adj = adjList.get(u).get(j);
                        if (distance[adj.node] > distance[u] + adj.cost) {
                            distance[adj.node] = distance[u] + adj.cost;
                        }
                    }
                }
            }

            // Check for negative weight cycle
            boolean hasCycle = false;
            for (int u = 0; u < systems; u++) { // for each node
                for (int j = 0; j < adjList.get(u).size(); j++) {
                    Tuple adj = adjList.get(u).get(j);
                    if (distance[adj.node] > distance[u] + adj.cost) {
                        hasCycle = true;
                        break;
                    }
                }
                if (hasCycle) break;
            }

            if (hasCycle) {
                out.println("possible");
            } else {
                out.println("not possible");
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

    private static class Tuple {
        int node;
        int cost;
        public Tuple(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }
        public String toString() {
            return String.format("[%d,%d]", node, cost);
        }
    }
}
/*
edges are directed
weight is the change in time outside the wormhole (the statement
 means time is negligible for the traveler)
starting from our solar system (node) we can reach any node
no more than 1 edge connecting two nodes
no self-loops

find a negative cycle if it exists

can't use Integer.MAX_VALUE for infinity: it's too big and overflows
*/
