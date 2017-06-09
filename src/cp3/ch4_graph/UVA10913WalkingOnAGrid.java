package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

// 10913 - Special Graph (Directed Acyclic Graph), Converting General Graph to DAG, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1854
// DP, notes below
class UVA10913WalkingOnAGrid {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int DOWN = 2;
    private static final int MOVEMENTS = 3;
    private static final int INFINITY = (int) 1e9;

    public static void main(String args[]) throws IOException {
        int test = 1;
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int n = parseInt(st.nextToken());
            int maxNegatives = parseInt(st.nextToken());
            if (n == 0 && maxNegatives == 0) break;
            int[][] grid = new int[n][n];
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(getLine());
                for (int j = 0; j < n; j++) {
                    grid[i][j] = parseInt(st.nextToken());
                }
            }

            int cells = n * n;
            int[][][] memo = new int[cells][maxNegatives + 1][MOVEMENTS];
            for (int i = 0; i < cells; i++) {
                for (int j = 0; j < maxNegatives + 1; j++) {
                    Arrays.fill(memo[i][j], -1);
                }
            }
            int max = getMaxPath(n - 1, n - 1, maxNegatives, RIGHT, grid, memo, n);
            max = Math.max(max, getMaxPath(n - 1, n - 1, maxNegatives, DOWN, grid, memo, n));
            if (max == -INFINITY) {
                out.printf("Case %d: impossible%n", test++);
            } else {
                out.printf("Case %d: %d%n", test++, max);
            }
            line = getLine();
        }
        out.close();
    }

    private static int getMaxPath(int r, int c, int negativesLeft, int lastMovement, int[][] grid, int[][][] memo, int n) {
        if (r < 0 || c < 0 || c >= n) {
            return -INFINITY; // Out of bounds
        }
        if (grid[r][c] < 0) {
            negativesLeft--;
        }
        if (negativesLeft < 0) {
            return -INFINITY; // Surpassed the negative limit
        }
        if (r == 0 && c == 0) {
            return grid[r][c]; // Reached the start cell
        }

        int cell = r * n + c;
        if (memo[cell][negativesLeft][lastMovement] == -1) {
            int max = -INFINITY;
            if (lastMovement == LEFT) {
                max = getMaxPath(r, c + 1, negativesLeft, LEFT, grid, memo, n);
                max = Math.max(max, getMaxPath(r, c + 1, negativesLeft, DOWN, grid, memo, n));
            } else if (lastMovement == RIGHT) {
                max = getMaxPath(r, c - 1, negativesLeft, RIGHT, grid, memo, n);
                max = Math.max(max, getMaxPath(r, c - 1, negativesLeft, DOWN, grid, memo, n));
            } else {
                max = getMaxPath(r - 1, c, negativesLeft, RIGHT, grid, memo, n);
                max = Math.max(max, getMaxPath(r - 1, c, negativesLeft, LEFT, grid, memo, n));
                max = Math.max(max, getMaxPath(r - 1, c, negativesLeft, DOWN, grid, memo, n));
            }
            memo[cell][negativesLeft][lastMovement] = max;
            if (max != -INFINITY) {
                memo[cell][negativesLeft][lastMovement] += grid[r][c];
            }
        }
        return memo[cell][negativesLeft][lastMovement];
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
The function to calculate is the max sum of a path from (0,0) to (n-1,n-1).
The statement says that we cannot revisit any cell. The allowed movements are down, left and right. This means that,
if we go down, afterwards we can go in any of the 3 directions without repeating. But, if we go left or right, in order
not to repeat a cell we can only go down or repeat the same direction (left or right) in our next movement, i.e. if
we go back, we will repeat a cell.
In order to track this, the state must contain the movement that brought us to the current cell. Hence the state is
composed of:
- cell (row and column)
- negatives that we can consume
- movement that brought us to the current cell
*/
