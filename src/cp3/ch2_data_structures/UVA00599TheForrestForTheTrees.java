package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

// 599 - Graph Data Structures Problems, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=540
class UVA00599TheForrestForTheTrees {

    private static final int MAX_VERTICES = 26;
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        while (tests-- > 0) {
            Graph forest = Graph.readGraph();
            boolean[] marked = new boolean[forest.getVerticesCount()];
            int trees = 0;
            int acorns = 0;
            for (int i = 0; i < forest.getVerticesCount(); i++) {
                if (!marked[i]) {
                    if (forest.getDegree(i) == 0) {
                        acorns++;
                    } else {
                        trees++;
                        marked = dfs(forest, i, marked);
                    }
                }
            }
            out.printf("There are %d tree(s) and %d acorn(s).\n", trees, acorns);
        }
        out.flush();
    }

    private static boolean[] dfs(Graph graph, int source, boolean[] marked) {
        marked[source] = true;
        Deque<Integer> pending = new ArrayDeque<>();
        pending.push(source);
        while (!pending.isEmpty()) {
            int vertex = pending.pop();
            for (int neighbor : graph.getAdj(vertex)) {
                if (!marked[neighbor]) {
                    marked[neighbor] = true;
                    pending.push(neighbor);
                }
            }
        }
        return marked;
    }

    private static class Graph {
        private static final int UNSEEN = -1;
        private List<List<Integer>> adjList;

        public Graph(List<List<Integer>> adjList) {
            this.adjList = adjList;
        }

        public List<Integer> getAdj(int vertex) {
            return adjList.get(vertex);
        }

        public int getDegree(int vertex) {
            return adjList.get(vertex).size();
        }
        public int getVerticesCount() {
            return adjList.size();
        }

        public static Graph readGraph() throws IOException {
            int[] vertexToOrder = new int[MAX_VERTICES];
            Arrays.fill(vertexToOrder, UNSEEN);
            List<List<Integer>> adjList = new ArrayList<List<Integer>>();
            String line = in.readLine().trim();
            // Read edges
            while (!line.startsWith("*")) {
                int origin = toVertexId(line.charAt(1));
                int destination = toVertexId(line.charAt(3));
                if (vertexToOrder[origin] == UNSEEN) {
                    vertexToOrder[origin] = adjList.size();
                    adjList.add(new ArrayList<Integer>());
                }
                if (vertexToOrder[destination] == UNSEEN) {
                    vertexToOrder[destination] = adjList.size();
                    adjList.add(new ArrayList<Integer>());
                }
                adjList.get(vertexToOrder[origin]).add(vertexToOrder[destination]);
                adjList.get(vertexToOrder[destination]).add(vertexToOrder[origin]);
                line = in.readLine().trim();
            }
            // Read vertices - I need this to account for vertices with no edges
            line = in.readLine().trim();
            for (int i = 0; i < line.length(); i += 2) {
                int vertex = toVertexId(line.charAt(i));
                if (vertexToOrder[vertex] == UNSEEN) {
                    adjList.add(new ArrayList<Integer>());
                    // Don't bother to set vertexToOrder: won't be used
                }
            }
            return new Graph(adjList);
        }

        private static int toVertexId(char vertex) {
            return vertex - 'A';
        }
    }
}
