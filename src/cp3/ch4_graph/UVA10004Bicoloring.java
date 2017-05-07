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

// 10004 - Bipartite Graph Check, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=945
// Direct application of bipartite graph check
class UVA10004Bicoloring {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            int nodes = Integer.parseInt(line.trim());
            if (nodes == 0) break;
            int edges = Integer.parseInt(getLine());
            List<List<Integer>> adjList = new ArrayList<List<Integer>>();
            for (int i = 0; i < nodes; i++) {
                adjList.add(new ArrayList<Integer>());
            }
            for (int i = 0; i < edges; i++) {
                StringTokenizer st = new StringTokenizer(getLine());
                int node1 = Integer.parseInt(st.nextToken());
                int node2 = Integer.parseInt(st.nextToken());
                adjList.get(node1).add(node2);
                adjList.get(node2).add(node1);
            }

            //boolean isBipartite = isBipartiteBFS(adjList);
            boolean isBipartite = isBipartiteDFS(adjList);

            if (isBipartite) {
                out.println("BICOLORABLE.");
            } else {
                out.println("NOT BICOLORABLE.");
            }
            line = getLine();
        }
        out.close();
    }

    private static boolean isBipartiteDFS(List<List<Integer>> adjList) {
        int[] color = new int[adjList.size()];
        Arrays.fill(color, -1);
        return isBipartiteDFS(0, 0, adjList, color);
    }

    private static boolean isBipartiteDFS(int node, int nodeColor, List<List<Integer>> adjList,
            int[] color) {
        color[node] = nodeColor;
        List<Integer> neighbors = adjList.get(node);
        for (int i = 0; i < neighbors.size(); i++) {
            int neighbor = neighbors.get(i);
            if (color[neighbor] == -1) {
                if (!isBipartiteDFS(neighbor, 1 - nodeColor, adjList, color)) {
                    return false;
                }
            } else if (color[neighbor] == color[node]) {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unused")
    private static boolean isBipartiteBFS(List<List<Integer>> adjList) {
        int[] color = new int[adjList.size()];
        Arrays.fill(color, -1);
        color[0] = 0;
        Queue<Integer> queue = new ArrayDeque<Integer>();
        queue.add(0);
        while (!queue.isEmpty()) {
            int current = queue.remove();
            List<Integer> neighbors = adjList.get(current);
            for (int i = 0; i < neighbors.size(); i++) {
                int neighbor = neighbors.get(i);
                if (color[neighbor] == -1) { // not visited
                    color[neighbor] = 1 - color[current]; // toggle color, could also be 1 ^ color[current]
                    queue.add(neighbor);
                } else if (color[neighbor] == color[current]) { // adj. nodes have same color
                    return false;
                }
            }
        }
        return true;
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }
}
