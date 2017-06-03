package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

// 10285 - Special Graph (Directed Acyclic Graph), Single-Source Shortest/Longest Paths on DAG, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1226
// Longest path in a DAG, notes below
class UVA10285LongestRunOnASnowboard {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int[] DELTA_R = {-1, 1, 0, 0};
    private static final int[] DELTA_C = {0, 0, -1, 1};

    public static void main(String args[]) throws IOException {
        int tests = parseInt(getLine());
        while (tests-- > 0) {
            StringTokenizer st = new StringTokenizer(getLine());
            String name = st.nextToken();
            int rows = parseInt(st.nextToken());
            int cols = parseInt(st.nextToken());
            int[][] grid = new int[rows][cols];
            for (int r = 0; r < rows; r++) {
                st = new StringTokenizer(getLine());
                for (int c = 0; c < cols; c++) {
                    grid[r][c] = parseInt(st.nextToken());
                }
            }

            Deque<Integer> toposort = getToposort(grid);
            int[] distance = new int[rows * cols];
            Arrays.fill(distance, 1); // the minimum length of any path is 1, if we just stay in the cell
            int maxDistance = 1;
            for (int node : toposort) {
                // relax vertex
                int r = node / cols;
                int c = node % cols;
                for (int i = 0; i < DELTA_R.length; i++) { // adjacent cells
                    int nr = r + DELTA_R[i];
                    int nc = c + DELTA_C[i];
                    int next = nr * cols + nc;
                    if (nr >= 0 && nr < rows && nc >= 0 && nc < cols // valid cell
                            && grid[r][c] > grid[nr][nc] // cells are connected
                            && distance[next] < distance[node] + 1) { // can relax (i.e. can increase path length)
                        distance[next] = distance[node] + 1;
                        if (maxDistance < distance[next]) { // update longest path overall
                            maxDistance = distance[next];
                        }
                    }
                }
            }

            out.printf("%s: %d%n", name, maxDistance);
        }
        out.close();
    }

    private static Deque<Integer> getToposort(int[][] grid) {
        Deque<Integer> toposort = new ArrayDeque<>();
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[] visited = new boolean[rows * cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!visited[r * cols + c]) {
                    dfs(r, c, grid, visited, toposort);
                }
            }
        }
        return toposort;
    }

    private static void dfs(int r, int c, int[][] grid, boolean[] visited, Deque<Integer> toposort) {
        int rows = grid.length;
        int cols = grid[r].length;
        visited[r * cols + c] = true;
        for (int i = 0; i < DELTA_R.length; i++) {
            int nr = r + DELTA_R[i];
            int nc = c + DELTA_C[i];
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                    && grid[r][c] > grid[nr][nc]
                    && !visited[nr * cols + nc]) {
                dfs(nr, nc, grid, visited, toposort);
            }
        }
        toposort.push(r * cols + c);
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while (line != null && "".equals(line.trim())) // dealing with blank lines
            line = in.readLine();
        return line;
    }
    private static int parseInt(String text) {
        return Integer.parseInt(text.trim());
    }
}
/*
The given input is a DAG because there are edges only from nodes with height i to nodes with height j when i>j,
so there can be no cycles. We need to find the longest path between any pair of nodes. Because it's a DAG we can
find first the topological order and then relax vertices in that order. Because of the toposort it's guaranteed that
we'll get the shortest path without resorting to Dijkstra or other algos.
*/
