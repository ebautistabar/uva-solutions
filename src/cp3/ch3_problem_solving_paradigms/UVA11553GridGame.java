package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 11553 - Complete Search, Iterative (Fancy Techniques), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2548
//
// Reference:
// https://www.redgreencode.com/when-not-to-simulate-a-game-uva-11553/
class UVA11553GridGame {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        while (tests-- > 0) {
            int size = Integer.parseInt(in.readLine().trim());
            int[][] grid = new int[size][size];
            for (int i = 0; i < size; i++) {
                StringTokenizer st = new StringTokenizer(in.readLine());
                for (int j = 0; j < size; j++) {
                    grid[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            int bestCandies = Integer.MAX_VALUE;
            int[] cols = new int[size];
            for (int i = 0; i < size; i++)
                cols[i] = i;
            boolean hasNextCols = true;
            while (hasNextCols) {
                int candies = getCandies(grid, cols);
                bestCandies = Math.min(bestCandies, candies);
                cols = nextPermutation(cols);
                hasNextCols = cols != null;
            }
            out.println(bestCandies);
        }
        out.close();
    }

    private static int getCandies(int[][] grid, int[] cols) {
        int candies = 0;
        for (int i = 0; i < cols.length; i++) {
            candies += grid[i][cols[i]];
        }
        return candies;
    }

    private static int[] nextPermutation(int[] values) {
        // next_permutation algorithm, similar to the C++ function
        // http://stackoverflow.com/questions/2920315/permutation-of-array
        // http://wordaligned.org/articles/next-permutation#tocwhats-happening-here
        for(int tail = values.length - 1; tail > 0; tail--) {
            // as long as the string is monotonically decreasing, keep
            // going. If it increases, it means we have found a suffix
            // for which we have completed all the possible permutations
            if (values[tail - 1] < values[tail]) {
                // That means we have to increase the item at the left
                // of the suffix by the min. amount, i.e. finding the
                // next biggest element in the suffix and swapping them
                int s = values.length - 1;
                while (values[tail - 1] >= values[s]) {
                    s--;
                }
                swap(values, tail - 1, s);
                // As we incremented the item at the left of the suffix,
                // we have a new suffix we have to permute, so we need
                // to reverse the suffix into a monotonically increasing
                // order again to start over
                for(int i = tail, j = values.length - 1; i < j; i++, j--) {
                    swap(values, i, j);
                }
                return values;
            }
        }
        return null;
    }

    private static void swap(int[] values, int a, int b) {
        int aux = values[a];
        values[a] = values[b];
        values[b] = aux;
    }
}
