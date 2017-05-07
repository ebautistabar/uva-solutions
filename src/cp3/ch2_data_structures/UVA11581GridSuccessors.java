package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 11581 - 2D Array Manipulation, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2628
class UVA11581GridSuccessors {

    private static final int[] movements = {0, 1, 0, -1, -1, 0, 1, 0};

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        int t = Integer.parseInt(line);
        while (t-- > 0) {
            in.readLine(); // blank line
            int[][] grid = new int[3][3];
            for (int i = 0; i < grid.length; i++) {
                String row = in.readLine();
                for (int j = 0; j < grid[i].length; j++) {
                    grid[i][j] = row.charAt(j) - '0';
                }
            }
            // The statement is a bit convoluted. It seems k_g(f^i(g)) is simply
            // i. We must find finite i. A guy did some tests and found that every
            // possible grid eventually transforms into the 0 grid, and the 0
            // grid transforms into itself, i.e. when we reach the 0 grid
            // then i becomes infinite. So we must find the number of steps
            // until we reach the 0 grid.
            int index = 0;
            while (!isZeroGrid(grid)) {
                grid = getNextGrid(grid);
                index++;
            }
            out.println(index - 1);
        }
        out.close();
    }

    private static int[][] getNextGrid(int[][] grid) {
        int[][] next = new int[3][3];
        for (int i = 0; i < next.length; i++) {
            for (int j = 0; j < next[i].length; j++) {
                // Sum all adjacent cells which are in bounds
                for (int k = 0; k < movements.length; k += 2) {
                    int ai = i + movements[k];
                    int aj = j + movements[k + 1];
                    if (ai >= 0 && ai < grid.length && aj >= 0
                            && aj < grid[ai].length) {
                        next[i][j] += grid[ai][aj];
                    }
                }
                next[i][j] = next[i][j] % 2;
            }
        }
        return next;
    }

    private static boolean isZeroGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

}
