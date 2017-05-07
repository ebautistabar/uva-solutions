package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 10755 - Dynamic Programming, Max 1D Range Sum, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1696
class UVA10755GarbageHeap {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine());
        while (tests-- > 0) {
            String line = in.readLine();
            while ("".equals(line.trim())) {
                line = in.readLine();
            }
            StringTokenizer st = new StringTokenizer(line);
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            long[][][] heap = readCumulativeHeap(a, b, c);
            long maxValue = getMaxValue(heap);
            out.println(maxValue);
            if (tests > 0) {
                out.println();
            }
        }
        out.close();
    }

    // Reads the heap and returns a cumulative heap. Think of it as a stack of
    //  matrices. For each "depth level" in the stack, each matrix cell m[j][k]
    //  contains the sum of all values in the sub-matrix that starts in m[0][0]
    //  and ends in m[j][k]
    private static long[][][] readCumulativeHeap(int a, int b, int c)
            throws IOException {
        StringTokenizer st = new StringTokenizer("");
        long[][][] heap = new long[a][b][c];
        for (int i = 0; i < a; i++) { // depth
            for (int j = 0; j < b; j++) { // height
                for (int k = 0; k < c; k++) { // width
                    if (!st.hasMoreTokens()) {
                        st = new StringTokenizer(in.readLine());
                    }
                    heap[i][j][k] = Long.parseLong(st.nextToken());
                    if (j > 0) { // if not the top row of this "depth level"
                        heap[i][j][k] += heap[i][j - 1][k];
                    }
                    if (k > 0) { // if not the first col of this "depth level"
                        heap[i][j][k] += heap[i][j][k - 1];
                    }
                    if (j > 0 && k > 0) { // if not the top left corner of this "depth level"
                        // subtract to avoid double-counting
                        heap[i][j][k] -= heap[i][j - 1][k - 1];
                    }
                }
            }
        }
        return heap;
    }

    // Max 1D range sum
    private static long getMaxValue(long[][][] heap) {
        long result = Long.MIN_VALUE;
        int depth = heap.length;
        int height = heap[0].length;
        int width = heap[0][0].length;
        // Iterate all sub-matrices
        for (int startR = 0; startR < height; startR++) {
            for (int startC = 0; startC < width; startC++) {
                for (int endR = startR; endR < height; endR++) {
                    for (int endC = startC; endC < width; endC++) {
                        // For this sub-matrix, iterate all levels of the stack
                        //  and calculate the best contiguous streak of matrices
                        long maxStackSum = Long.MIN_VALUE;
                        long stackSum = 0;
                        for (int level = 0; level < depth; level++) {
                            stackSum += getMatrixSum(heap[level], startR, startC, endR, endC);
                            maxStackSum = Math.max(maxStackSum, stackSum);
                            stackSum = Math.max(stackSum, 0);
                        }
                        // We have the best sum for a sub-stack within these coordinates.
                        // Keep it if it's the best sub-stack over all coordinates
                        // so far
                        result = Math.max(result, maxStackSum);
                    }
                }
            }
        }
        return result;
    }

    // Max 2D range sum
    private static long getMatrixSum(long[][] matrix, int startR, int startC,
            int endR, int endC) {
        long matrixSum = matrix[endR][endC];
        if (startR > 0) { // if not top row
            // subtract to avoid double-counting
            matrixSum -= matrix[startR - 1][endC];
        }
        if (startC > 0) { // if not first column
            // subtract to avoid double-counting
            matrixSum -= matrix[endR][startC - 1];
        }
        if (startR > 0 && startC > 0) { // if not top left cell
            // add to avoid double-subtracting
            matrixSum += matrix[startR - 1][startC - 1];
        }
        return matrixSum;
    }
}
