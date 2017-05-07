package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

// 796 - Finding Articulation Points/Bridges, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=737
// Direct application of technique
class UVA00796CriticalLinks {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int UNVISITED = -1;

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            int nodes = Integer.parseInt(line.trim());
            List<List<Integer>> adjList = new ArrayList<>();
            for (int i = 0; i < nodes; i++) {
                adjList.add(new ArrayList<Integer>());
            }
            for (int i = 0; i < nodes; i++) {
                StringTokenizer st = new StringTokenizer(getLine());
                int node = Integer.parseInt(st.nextToken());
                st.nextToken(); // This is the amount of edges, I don't care
                while (st.hasMoreTokens()) {
                    int neighbor = Integer.parseInt(st.nextToken());
                    adjList.get(node).add(neighbor);
                }
            }

            List<Bridge> bridges = getBridges(adjList);
            Collections.sort(bridges);

            out.printf("%d critical links%n", bridges.size());
            for (Bridge bridge : bridges) {
                out.printf("%d - %d%n", bridge.a, bridge.b);
            }
            out.println();
            line = getLine();
        }
        out.close();
    }

    private static List<Bridge> getBridges(List<List<Integer>> adjList) {
        int[] dfsNum = new int[adjList.size()];
        Arrays.fill(dfsNum, UNVISITED);
        int[] dfsLow = new int[adjList.size()];
        int[] parent = new int[adjList.size()];
        int[] dfsCounter = new int[]{0};
        List<Bridge> bridges = new ArrayList<>();
        for (int i = 0; i < adjList.size(); i++) {
            if (dfsNum[i] == UNVISITED) {
                getBridges(i, adjList, dfsNum, dfsLow, parent,
                        dfsCounter, bridges);
            }
        }
        return bridges;
    }

    private static void getBridges(int node,
            List<List<Integer>> adjList, int[] dfsNum, int[] dfsLow, int[] parent,
            int[] dfsCounter, List<Bridge> bridges) {
        dfsNum[node] = dfsLow[node] = dfsCounter[0]++;
        List<Integer> neighbors = adjList.get(node);
        for (int i = 0; i < neighbors.size(); i++) {
            int neighbor = neighbors.get(i);
            if (dfsNum[neighbor] == UNVISITED) {
                parent[neighbor] = node;

                getBridges(neighbor, adjList, dfsNum, dfsLow, parent,
                        dfsCounter, bridges);

                if (dfsLow[neighbor] > dfsNum[node]) {
                    bridges.add(new Bridge(node, neighbor));
                }
                dfsLow[node] = Math.min(dfsLow[node], dfsLow[neighbor]);
            } else if (parent[node] != neighbor) { // visited and not direct edge
                dfsLow[node] = Math.min(dfsLow[node], dfsNum[neighbor]);
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

    private static class Bridge implements Comparable<Bridge> {
        int a;
        int b;
        public Bridge(int a, int b) {
            this.a = Math.min(a, b);
            this.b = Math.max(a, b);
        }
        public int compareTo(Bridge arg0) {
            int cmp = Integer.compare(a, arg0.a);
            if (cmp != 0) {
                return cmp;
            }
            return Integer.compare(b, arg0.b);
        }
    }
}
