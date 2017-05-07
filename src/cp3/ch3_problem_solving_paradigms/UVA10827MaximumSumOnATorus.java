package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 10827 - Dynamic Programming, Max 2D Range Sum, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1768
class UVA10827MaximumSumOnATorus {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(nextLine());
        while (tests-- > 0) {
            int size = Integer.parseInt(nextLine());
            int[][] torus = readCumulativeTorus(size);
            int maxSum = getMaxSum(torus);
            out.println(maxSum);
        }
        out.close();
    }

    private static int[][] readCumulativeTorus(int size) throws IOException {
        int[][] torus = new int[size * 2][size * 2];
        for (int i = 0; i < size; i++) {
            String line = nextLine();
            StringTokenizer st = new StringTokenizer(line);
            for (int j = 0; j < size; j++) {
                torus[i][j] = Integer.parseInt(st.nextToken());
                torus[i + size][j] = torus[i][j];
                torus[i][j + size] = torus[i][j];
                torus[i + size][j + size] = torus[i][j];
            }
        }
        for (int i = 0; i < torus.length; i++) {
            for (int j = 0; j < torus[0].length; j++) {
                if (i > 0) torus[i][j] += torus[i - 1][j];
                if (j > 0) torus[i][j] += torus[i][j - 1];
                if (i > 0 && j > 0) torus[i][j] -= torus[i - 1][j - 1];
            }
        }
        return torus;
    }

    private static String nextLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line.trim())) {
            line = in.readLine();
        }
        return line.trim();
    }

    private static int getMaxSum(int[][] torus) {
        int result = Integer.MIN_VALUE;
        for (int startR = 0; startR < torus.length; startR++) {
            for (int startC = 0; startC < torus[0].length; startC++) {
                // the matrix that we can select is has most side N (original torus dimension)
                int limitR = Math.min(torus.length, startR + torus.length / 2);
                for (int endR = startR; endR < limitR; endR++) {
                    // the matrix that we can select is has most side N (original torus dimension)
                    int limitC = Math.min(torus.length, startC + torus[0].length / 2);
                    for (int endC = startC; endC < limitC; endC++) {
                        int sum = torus[endR][endC];
                        if (startR > 0) sum -= torus[startR - 1][endC];
                        if (startC > 0) sum -= torus[endR][startC - 1];
                        if (startR > 0 && startC > 0) sum += torus[startR - 1][startC - 1];
                        result = Math.max(result, sum);
                    }
                }
            }
        }
        return result;
    }
}
