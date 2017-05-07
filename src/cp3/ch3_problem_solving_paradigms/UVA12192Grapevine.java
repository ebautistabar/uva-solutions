package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 12192 - Binary Search, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3344
class UVA12192Grapevine {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null && !"0 0".equals(line.trim())) {
            StringTokenizer st = new StringTokenizer(line);
            int rows = Integer.parseInt(st.nextToken());
            int cols = Integer.parseInt(st.nextToken());
            // Read grid
            int[][] grid = new int[rows][];
            for (int i = 0; i < rows; i++) {
                grid[i] = new int[cols];
                st = new StringTokenizer(in.readLine());
                for (int j = 0; j < cols; j++) {
                    grid[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            // Answer queries
            int queries = Integer.parseInt(in.readLine().trim());
            for (int q = 0; q < queries; q++) {
                st = new StringTokenizer(in.readLine());
                int lo = Integer.parseInt(st.nextToken());
                int hi = Integer.parseInt(st.nextToken());
                int size = 0;
                for (int r = 0; r < rows - size; r++) {
                    int c = lowerBound(grid[r], lo);
                    if (c > -1) {
                        size = Math.max(size, upperBound(grid, r, c, hi));
                    }
                }
                out.println(size);
            }
            out.println('-');
            line = in.readLine();
        }
        out.close();
    }

    // Returns index of first item with value >= target. Given array is sorted
    private static int lowerBound(int[] values, int target) {
        int start = 0;
        int end = values.length - 1;
        while (end - start + 1 > 1) { // finish on length < 2 (1 or 0)
            int mid = start + (end - start) / 2;
            if (values[mid] < target) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        if (start < values.length && target <= values[start]) {
            return start;
        }
        return -1;
    }

    // Returns index of first item with value > target.
    // Given matrix is sorted by row and column
    private static int upperBound(int[][] values, int startR, int startC, int target) {
        int len = Math.min(values.length - startR, values[0].length - startC);
        int start = 0;
        int end = len - 1;
        while (end - start + 1 > 1) { // finish on length < 2 (1 or 0)
            int mid = start + (end - start) / 2;
            if (target < values[startR + mid][startC + mid]) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        if (start < len && target < values[startR + start][startC + start]) {
            return start;
        }
        return len;
    }

    // upperBound version for a 1-dimension array
    @SuppressWarnings("unused")
    private static int upperBound(int[] values, int target) {
        int len = values.length;
        int start = 0;
        int end = len - 1;
        while (end - start + 1 > 1) { // finish on length < 2 (1 or 0)
            int mid = start + (end - start) / 2;
            if (target < values[mid]) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        if (start < len && target < values[start]) {
            return start;
        }
        return len;
    }

    @SuppressWarnings("unused")
    private static void originalSolution(int rows, int cols, int[][] grid)
            throws IOException {
        int queries = Integer.parseInt(in.readLine().trim());
        for (int q = 0; q < queries; q++) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            int lo = Integer.parseInt(st.nextToken());
            int hi = Integer.parseInt(st.nextToken());
            int size = 0;
            for (int r = 0; r < rows - size; r++) {
                int c = lowerBound(grid[r], lo);
                if (c > -1) {
                    while (r + size < rows && c + size < cols
                            && grid[r + size][c + size] <= hi) {
                        size++;
                    }
                }
            }
            out.println(size);
        }
        out.println('-');
    }
}
