package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

// 10487 - Complete Search, Iterative (Two Nested Loops), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1428
class UVA10487ClosestSums {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int test = 1;
        String line = in.readLine();
        while (line != null && !"0".equals(line.trim())) {
            out.printf("Case %d:%n", test);
            int nums = Integer.parseInt(line.trim());
            int[] values = new int[nums];
            for (int i = 0; i < nums; i++)
                values[i] = Integer.parseInt(in.readLine().trim());
            int[] sums = new int[nums * nums];
            int k = 0;
            for (int i = 0; i < nums; i++) {
                for (int j = i + 1; j < nums; j++) {
                    sums[k++] = values[i] + values[j];
                }
            }
            int queries = Integer.parseInt(in.readLine().trim());
            for (int i = 0; i < queries; i++) {
                int query = Integer.parseInt(in.readLine().trim());
                int bestSum = -1;
                int bestDiff = Integer.MAX_VALUE;
                for (int j = 0; j < k; j++) {
                    int diff = Math.abs(query - sums[j]);
                    if (bestDiff > diff) {
                        bestDiff = diff;
                        bestSum = sums[j];
                    }
                }
                out.printf("Closest sum to %d is %d.%n", query, bestSum);
            }
            test++;
            line = in.readLine();
        }
        out.close();
    }

}
