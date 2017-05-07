package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 927 - Complete Search, Iterative (One Loop, Linear Scan), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=868
class UVA00927IntegerSequencesFromAdditionOfTerms {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        while (tests-- > 0) {
            StringTokenizer st = new StringTokenizer(in.readLine().trim());
            int i = Integer.parseInt(st.nextToken()); // >= 1, <= 20
            int[] coefficients = new int[i + 1];
            for (int j = 0; j <= i; j++) {
                coefficients[j] = Integer.parseInt(st.nextToken()); // >= 0, <= 10K
            }
            int d = Integer.parseInt(in.readLine().trim()); // <= 100K
            int k = Integer.parseInt(in.readLine().trim()); // <= 1M
            // Search for the position that holds the occurrences of the item we want
            int items = 0;
            int n = 0;
            while (items < k) {
                n++;
                items += n * d;
            }
            // Calculate the value of the item
            long result = 0;
            for (int j = 0; j <= i; j++) {
                result += coefficients[j] * Math.pow(n, j);
            }
            out.println(result);
        }
        out.close();
    }

}
