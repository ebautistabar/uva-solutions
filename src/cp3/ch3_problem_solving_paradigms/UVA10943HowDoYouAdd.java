package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 10943 - Non Classical (The Easier Ones), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1884
// Comments below
class UVA10943HowDoYouAdd {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            if (n == 0 && k == 0) break;

            long result = getWays(n, k);
            out.println(result);

            line = getLine();
        }
        out.close();
    }

    private static long getWays(int n, int k) {
        long[][] ways = new long[n + 1][k + 1];
        // if k == 0 ^ n == 0, then result is 0
        // if both are 0, then we have 1 way
        ways[0][0] = 1;
        for (int in = 0; in < n + 1; in++) {
            for (int ik = 1; ik < k + 1; ik++) {
                // Transitions
                for (int i = 0; i <= in; i++) {
                    // pick i and recurse
                    ways[in][ik] += ways[in - i][ik - 1] % 1_000_000;
                }
            }
        }
        return ways[n][k] % 1_000_000;
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
f(n, k) =
1 if k == 0, n == 0 (n is depleted using all k numbers)
0 if k == 0 && n != 0, i.e. we can't use more numbers but n is not depleted
0 if k != 0 && n == 0, i.e. n is already depleted but we must use more numbers

for i=0 to n
 sum += f(n-i, k-1)
*/