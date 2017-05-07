package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 11264 - Greedy, Classical, Usually Easier, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2231
class UVA11264CoinCollector {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine());
        while (tests-- > 0) {
            int n = Integer.parseInt(in.readLine());
            StringTokenizer st = new StringTokenizer(in.readLine());
            int[] coins = new int[n];
            for (int i = 0; i < n; i++) {
                coins[i] = Integer.parseInt(st.nextToken());
            }
            int types = 1; // at least the coin with value=1
            int sum = coins[0];
            for (int i = 1; i < coins.length - 1; i++) {
                // the question is, can we use coin i?
                // we have a total of sum, and we add the coin i.
                // if it exceeds the value of the next coin, then we wouldn't
                // have taken this coin, but the next bigger coin i+1, as we
                // always must take the biggest coin that doesn't exceed the total.
                // Thus, we can take coin i if it's the biggest <= new sum.
                // We must consider that, in the simulation, we go from the total
                // to 0. But when searching for the solution, we go from 0 to the
                // total.
                if (sum + coins[i] < coins[i + 1]) {
                    sum += coins[i];
                    types++;
                }
            }
            if (coins.length > 1) {
                types++;
            }
            out.println(types);
        }
        out.close();
    }

}
