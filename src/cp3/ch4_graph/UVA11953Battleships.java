package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

// 11953 - Flood Fill/Finding Connected Components, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3104
class UVA11953Battleships {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final char SHIP = 'x';
    private static final char HIT = '@';
    private static final char VISITED = 'v';

    private static int[] dr = {1, -1, 0, 0};
    private static int[] dc = {0, 0, 1, -1};

    public static void main(String args[]) throws Exception {
        int tests = Integer.parseInt(getLine().trim());
        for (int t = 1; t <= tests; t++) {
            int size = Integer.parseInt(getLine());
            char[][] grid = new char[size][];
            for (int i = 0; i < size; i++) {
                grid[i] = getLine().trim().toCharArray();
            }

            int alive = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (grid[i][j] == SHIP) {
                        floodFillShip(i, j, grid);
                        alive++;
                    }
                }
            }
            out.printf("Case %d: %d%n", t, alive);
        }
        out.close();
    }

    private static void floodFillShip(int r, int c, char[][] grid) {
        grid[r][c] = VISITED;
        for (int i = 0; i < dr.length; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if (isInBounds(r, grid, nr, nc) && (grid[nr][nc] == SHIP || grid[nr][nc] == HIT)) {
                floodFillShip(nr, nc, grid);
            }
        }
    }

    private static boolean isInBounds(int r, char[][] grid, int nr, int nc) {
        return nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[r].length;
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }
}
