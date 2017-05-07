package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 108 - Dynamic Programming, Max 2D Range Sum, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=44
class UVA00108MaximumSum {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null) {
            int size = Integer.parseInt(line);
            int[][] matrix = getCumulativeMatrix(size);
            int maxSum = getMaxSum(matrix);
            out.println(maxSum);
            line = in.readLine();
        }
        out.close();
    }

    private static int[][] getCumulativeMatrix(int size) throws IOException {
        int[][] matrix = new int[size][size];
        StringTokenizer st = new StringTokenizer(in.readLine());
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!st.hasMoreTokens()) {
                    st = new StringTokenizer(in.readLine());
                }
                matrix[i][j] = Integer.parseInt(st.nextToken());
                if (i > 0) { // if not top row
                    matrix[i][j] += matrix[i - 1][j];
                }
                if (j > 0) { // if not first column
                    matrix[i][j] += matrix[i][j - 1];
                }
                if (i > 0 && j > 0) { // if not top left cell
                    // subtract to avoid double-counting
                    matrix[i][j] -= matrix[i - 1][j - 1];
                }
            }
        }
        return matrix;
    }

    private static int getMaxSum(int[][] matrix) {
        int result = Integer.MIN_VALUE;
        for (int startR = 0; startR < matrix.length; startR++) {
            for (int startC = 0; startC < matrix.length; startC++) {
                for (int endR = startR; endR < matrix.length; endR++) {
                    for (int endC = startC; endC < matrix.length; endC++) {
                        int sum = matrix[endR][endC];
                        if (startR > 0) { // if not top row
                            // subtract to avoid double-counting
                            sum -= matrix[startR - 1][endC];
                        }
                        if (startC > 0) { // if not first column
                            // subtract to avoid double-counting
                            sum -= matrix[endR][startC - 1];
                        }
                        if (startR > 0 && startC > 0) { // if not top left cell
                            // add to avoid double-subtracting
                            sum += matrix[startR - 1][startC - 1];
                        }
                        result = Math.max(result, sum);
                    }
                }
            }
        }
        return result;
    }
}
