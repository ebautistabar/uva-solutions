package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 10721 - Dynamic Programming, Non Classical (The Easier Ones), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1662
// Comments below
class UVA10721BarCodes {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            long symbols = getSymbols(n, k, m);
            out.println(symbols);

            line = getLine();
        }
        out.close();
    }

    private static long getSymbols(int n, int k, int m) {
        long[][] table = new long[n + 1][k + 1];
        // Base cases
        // · k != n && (k == 0 || n == 0) ==> 0, which is the default
        // · k > n ==> 0, which is the default
        // · k == n ==> 1, each bar will have size 1 unit
        for (int i = 0; i < Math.min(n, k) + 1; i++)
            table[i][i] = 1;
        // Recursive case: iterate over all n and k, from small to big
        for (int in = 1; in < n + 1; in++) {
            for (int ik = 1; ik < Math.min(k + 1, in); ik++) { // in the rest of cases, k < n
                for (int im = 1; im <= m; im++) { // lock first bar with size w and "recurse"
                    if (in - im >= 0) { // n-i
                        table[in][ik] += table[in - im][ik - 1];
                    }
                }
            }
        }
        return table[n][k];
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }
}

/*
 m is a global variable
bc(n, k) =
 0 if n <= 0
 0 if k <= 0
 0 if k > n
 1 if n == k
 sum = 0
 for i=1 to m
  // lock bar of width i and recurse
  sum += bc(n-i, k-1)
*/