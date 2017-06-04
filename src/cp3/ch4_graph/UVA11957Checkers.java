package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

// 11957 - Special Graph (Directed Acyclic Graph), Counting paths in DAG, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3108
// Notes below
class UVA11957Checkers {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int MAX_WAYS = 1_000_007;

    public static void main(String args[]) throws IOException {
        int tests = parseInt(getLine());
        for (int test = 1; test <= tests; test++) {
            int size = parseInt(getLine());
            boolean[][] occupied = new boolean[size][size];
            int startRow = 0;
            int startCol = 0;
            for (int r = 0; r < size; r++) {
                String line = getLine().trim();
                for (int c = 0; c < line.length(); c++) {
                    char cell = line.charAt(c);
                    if (cell == 'W') {
                        startRow = r;
                        startCol = c;
                    } else {
                        occupied[r][c] = cell == 'B';
                    }
                }
            }

            int[][] ways = new int[size][size];
            ways[startRow][startCol] = 1;
            for (int r = startRow; r > 0; r--) {
                for (int c = 0; c < size; c++) {
                    if (r - 1 >= 0 && c - 1 >= 0) {
                        if (occupied[r - 1][c - 1]) {
                            if (r - 2 >= 0 && c - 2 >= 0 && !occupied[r - 2][c - 2]) {
                                ways[r - 2][c - 2] = (ways[r - 2][c - 2] + ways[r][c]) % MAX_WAYS;
                            }
                        } else {
                            ways[r - 1][c - 1] = (ways[r - 1][c - 1] + ways[r][c]) % MAX_WAYS;
                        }
                    }
                    if (r - 1 >= 0 && c + 1 < size) {
                        if (occupied[r - 1][c + 1]) {
                            if (r - 2 >= 0 && c + 2 < size && !occupied[r - 2][c + 2]) {
                                ways[r - 2][c + 2] = (ways[r - 2][c + 2] + ways[r][c]) % MAX_WAYS;
                            }
                        } else {
                            ways[r - 1][c + 1] = (ways[r - 1][c + 1] + ways[r][c]) % MAX_WAYS;
                        }
                    }
                }
            }

            int totalWays = 0;
            for (int c = 0; c < size; c++) {
                totalWays = (totalWays + ways[0][c]) % MAX_WAYS;
            }
            out.printf("Case %d: %d%n", test, totalWays);
        }
        out.close();
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
DP problem. The function ways(r,c) is the number of ways to reach r,c from the starting place where the white piece is.
Let wr,wc be the cell where the white piece is. Then:
 ways(wr,wc) = 1
 ways(wr,wcb) = 0 where wcb != wc
 ways(r,c) = ways(r-1,c-1) if r-1,c-1 doesn't have a B piece else ways(r-2,c-2) if r-2,c-2 doesn't have a B piece else 0
            + ways(r-1,c+1) if r-1,c+1 doesn't have a B piece else ways(r-2, c+2) if r-2,c+2 doesn't have a B piece else 0
*/
