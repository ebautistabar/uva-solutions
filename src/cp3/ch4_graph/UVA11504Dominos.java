package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

// 11504 - Finding Strongly Connected Components, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2499
/*
 * Book says it's a scc problem but it's more of a toposort problem. It's true
 * that at the start of each scc we must knock a tile by hand, but it's not
 * necessary to find the exact contents of each scc. It's enough to traverse
 * the graph in toposort order. We knock a tile by hand for each component that
 * we traverse.
 */
class UVA11504Dominos {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int tests = Integer.parseInt(getLine().trim());
        while (tests-- > 0) {
            StringTokenizer st = new StringTokenizer(getLine());
            int tiles = Integer.parseInt(st.nextToken());
            List<List<Integer>> adjList = new ArrayList<>();
            for (int i = 0; i < tiles; i++) {
                adjList.add(new ArrayList<Integer>());
            }
            int lines = Integer.parseInt(st.nextToken());
            while (lines-- > 0) {
                st = new StringTokenizer(getLine());
                // tiles are 1-based, so subtract 1
                int origin = Integer.parseInt(st.nextToken()) - 1;
                int destination = Integer.parseInt(st.nextToken()) - 1;
                adjList.get(origin).add(destination);
            }

            int byHand = 0;
            Deque<Integer> toposort = getToposort(adjList);
            boolean[] visited = new boolean[tiles];
            for (int node : toposort) {
                if (!visited[node]) {
                    byHand++;
                    bfs(node, adjList, visited);
                }
            }
            out.println(byHand);
        }
        out.close();
    }

    private static Deque<Integer> getToposort(List<List<Integer>> adjList) {
        Deque<Integer> toposort = new ArrayDeque<>(adjList.size());
        boolean[] visited = new boolean[adjList.size()];
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                dfs(i, adjList, visited, toposort);
            }
        }
        return toposort;
    }

    private static void dfs(int node, List<List<Integer>> adjList,
            boolean[] visited, Deque<Integer> toposort) {
        visited[node] = true;
        for (int neighbor : adjList.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor, adjList, visited, toposort);
            }
        }
        toposort.push(node);
    }

    private static void bfs(int node, List<List<Integer>> adjList,
            boolean[] visited) {
        visited[node] = true;
        Queue<Integer> q = new ArrayDeque<>(adjList.size());
        q.add(node);
        while (!q.isEmpty()) {
            int current = q.remove();
            for (int neighbor : adjList.get(current)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    q.add(neighbor);
                }
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
