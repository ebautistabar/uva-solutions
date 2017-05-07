package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 11906 - Just Graph Traversal, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3057
class UVA11906KnightInAWarGrid {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int WATER = -1;
    private static final int VISITED = -2;

    private static int visited;

    public static void main(String args[]) throws Exception {
        int tests = Integer.parseInt(getLine());
        for (int test = 1; test <= tests; test++) {
            StringTokenizer st = new StringTokenizer(getLine());
            int rows = Integer.parseInt(st.nextToken());
            int cols = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int n = Integer.parseInt(st.nextToken());
            int water = Integer.parseInt(getLine());
            int[][] grid = new int[rows][cols];
            for (int w = 0; w < water; w++) {
                st = new StringTokenizer(getLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                grid[x][y] = WATER;
            }
            int[] dr;
            int[] dc;
            if (m == 0) {
                dr = new int[]{0, 0, n, -n};
                dc = new int[]{n, -n, 0, 0};
            } else if (n == 0) {
                dr = new int[]{0, 0, m, -m};
                dc = new int[]{m, -m, 0, 0};
            } else if (m == n) {
                dr = new int[]{m, m, -m, -m};
                dc = new int[]{m, -m, m, -m};
            } else {
                dr = new int[]{m, m, n, n, -m, -m, -n, -n};
                dc = new int[]{n, -n, m, -m, n, -n, m, -m};
            }
            visited = 0;
            int odd = getOddCellsByDFS(0, 0, dr, dc, grid);
            int even = visited - odd;

            out.printf("Case %d: %d %d%n", test, even, odd);
        }
        out.close();
    }

    private static int getOddCellsByDFS(int r, int c, int[] dr, int[] dc, int[][] grid) {
        grid[r][c] = VISITED;
        visited++;
        int reachable = 0;
        int odds = 0;
        for (int i = 0; i < dr.length; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if (isInBounds(nr, nc, grid) && grid[nr][nc] != WATER) {
                reachable++;
                if (grid[nr][nc] != VISITED) {
                    odds += getOddCellsByDFS(nr, nc, dr, dc, grid);
                }
            }
        }
        return odds + (reachable & 1); // 1 if odd, 0 if even
    }

    private static boolean isInBounds(int r, int c, int[][] grid) {
        return r >= 0 && r < grid.length && c >= 0 && c < grid[r].length;
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }
}
