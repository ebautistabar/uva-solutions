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

// 11396 - Bipartite Graph Check, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2391
// Direct application of bipartite check:
// - 1 vertex can be included in more than 1 claw
// - the central vertex of the claw is color A and the 3 fingers of the claw
// are color B
class UVA11396ClawDecomposition {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    // WHITE == 0
    private static final int BLACK = 1;
    private static final int UNVISITED = 2;

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            int vertices = Integer.parseInt(line.trim());
            if (vertices == 0) break;
            List<List<Integer>> adjList = new ArrayList<>();
            for (int i = 0; i < vertices; i++) {
                adjList.add(new ArrayList<Integer>());
            }
            StringTokenizer st = new StringTokenizer(getLine());
            int origin = Integer.parseInt(st.nextToken());
            int destination = Integer.parseInt(st.nextToken());
            while (origin != 0 && destination != 0) {
                // they are 1-based, so subtract 1
                adjList.get(origin - 1).add(destination - 1);
                adjList.get(destination - 1).add(origin - 1);
                st = new StringTokenizer(getLine());
                origin = Integer.parseInt(st.nextToken());
                destination = Integer.parseInt(st.nextToken());
            }

            int[] color = new int[vertices];
            Arrays.fill(color, UNVISITED);
            // the graph has only 1 component, so no need to iterate over all
            // vertices to initiate several bipartite checks
            if (isBipartiteDFS(0, BLACK, adjList, color)) {
                out.println("YES");
            } else {
                out.println("NO");
            }
            line = getLine();
        }
        out.close();
    }

    private static boolean isBipartiteDFS(int node, int color,
            List<List<Integer>> adjList, int[] colors) {
        colors[node] = color;
        List<Integer> adjacent = adjList.get(node);
        for (int i = 0; i < adjacent.size(); i++) {
            int neighbor = adjacent.get(i);
            if (colors[neighbor] == UNVISITED) {
                // flip the color with 1-color
                if (!isBipartiteDFS(neighbor, 1 - color, adjList, colors)) {
                    return false;
                }
            } else if (colors[neighbor] == colors[node]) {
                return false;
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
