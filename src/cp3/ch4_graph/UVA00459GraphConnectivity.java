package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 459 - Flood Fill/Finding Connected Components, not starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=400
class UVA00459GraphConnectivity {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static List<List<Integer>> adjList;

    public static void main(String args[]) throws Exception {
        int tests = Integer.parseInt(getLine().trim());
        while (tests-- > 0) {
            adjList = new ArrayList<List<Integer>>();
            int nodes = 1 + (getLine().trim().charAt(0) - 'A');
            for (int i = 0; i < nodes; i++) {
                adjList.add(new ArrayList<Integer>());
            }
            String line = in.readLine();
            while (line != null && !line.trim().isEmpty()) {
                line = line.trim();
                int origin = line.charAt(0) - 'A';
                int destination = line.charAt(1) - 'A';
                adjList.get(origin).add(destination); // undirected, goes both ways
                adjList.get(destination).add(origin);
                line = in.readLine();
            }

            // straight application of connected components on undirected graph
            // through graph traversal (DFS)
            int connectedComponents = 0;
            Set<Integer> visited = new HashSet<Integer>();
            for (int i = 0; i < nodes; i++) {
                if (!visited.contains(i)) {
                    dfs(i, visited);
                    connectedComponents++;
                }
            }

            out.println(connectedComponents);
            if (tests > 0) {
                out.println();
            }
        }
        out.close();
    }

    private static void dfs(int node, Set<Integer> visited) {
        visited.add(node);
        List<Integer> destinations = adjList.get(node);
        for (int i = 0; i < destinations.size(); i++) {
            int destination = destinations.get(i);
            if (!visited.contains(destination)) {
                dfs(destination, visited);
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
}
