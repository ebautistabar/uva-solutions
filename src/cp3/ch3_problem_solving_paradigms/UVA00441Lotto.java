package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 441 - Complete Search, Iterative (Three or More Nested Loops, Easier), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=382
class UVA00441Lotto {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine().trim();
        while (!"0".equals(line)) {
            StringTokenizer st = new StringTokenizer(line);
            int k = Integer.parseInt(st.nextToken());
            int[] set = new int[k];
            for (int i = 0; i < k; i++)
                set[i] = Integer.parseInt(st.nextToken());
            // I need to generate all the combinations:
            // - order doesn't matter; it only matters for permutations
            // - repetition is not allowed in lottery numbers
            // So combination without repetition: k choose 6
            // k!/6!/(k-6)!
            // It's under 1K even for the biggest k
            // A burdensome solution is generating all subsets of size 6,
            // ordering the items inside, adding each to a set and at the end
            // ordering the set contents.
            // It's easier to just loop. It works perfectly as the input is
            // ordered already.
            for (int a = 0; a < k - 5; a++)
                for (int b = a + 1; b < k - 4; b++)
                    for (int c = b + 1; c < k - 3; c++)
                        for (int d = c + 1; d < k - 2; d++)
                            for (int e = d + 1; e < k - 1; e++)
                                for (int f = e + 1; f < k; f++)
                                    out.print(String.format("%d %d %d %d %d %d%n", set[a], set[b], set[c], set[d], set[e], set[f]));
            line = in.readLine().trim();
            if (!"0".equals(line))
                out.println();
        }
        out.close();
    }

}
