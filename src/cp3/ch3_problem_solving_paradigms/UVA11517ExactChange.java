package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

// 11517 - Dynamic Programming, Coin Change (CC), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2512
class UVA11517ExactChange {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int INFINITY = Integer.MAX_VALUE - 1;

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(getLine().trim());
        while (tests-- > 0) {
            int price = Integer.parseInt(getLine().trim()); // <= 10_000
            int types = Integer.parseInt(getLine().trim()); // <= 100
            int[] values = new int[types];
            for (int t = 0; t < types; t++) {
                values[t] = Integer.parseInt(getLine().trim()); // <= 10_000
            }

            //plan: do dp bottom up with a max value of X, greater than goal
            // then, go from val[i] forward with i >= goal, and find the
            // first item that has value != infinite
            //what's X exactly? the max possible value for price: 10_000
            // in the regular coin change problem we have unlimited supply of coins
            // the assignment inside the loop lets us pick the same coin several times
            // when we do coins[v] = min(coins[v], 1+coins[v-val]) we really are comparing
            // min(coins[t-1][v], 1+coins[t][v-val]). If the smaller of the two is the
            // second item, it means we picked the coin in this current iteration of t.
            // If we keep going and we end up picking the coin in 'values[t]' iterations
            // that means we pick the coin twice.
            // We got to prevent that somehow: http://stackoverflow.com/a/38949064
            //    dp[t][v+C] = min(dp[t-1][v+C], dp[t-1][v] + 1); starting in v = max-c
            //  but if we start in v = max we can have
            //    dp[t][v] = min(dp[t-1][v], dp[t-1][v-C] + 1)
            // Another problem: entries with a final paid value > 10_000 are not found:
            // That happens because we calculate values up to 10_000 but that limit is wrong.
            // What if we have to pay exactly 10_000 but have 2 coins of 9_999? The sum
            // will be 9_999*2 > 10_000. What's the biggest sum that can happen?
            // I think that's it: being short 1 cent, and then having just another coin
            // which is again a huge coin just under the original price
            int[] coins = new int[(9_999 * 2) + 1];
            Arrays.fill(coins, INFINITY);
            coins[0] = 0;
            for (int t = 0; t < types; t++) {
                //for (int v = values[t]; v < coins.length; v++) {
                for (int v = coins.length - 1; v >= values[t]; v--) {
                    coins[v] = Math.min(coins[v], 1 + coins[v - values[t]]);
                }
            }
            for (int v = price; v < coins.length; v++) {
                if (coins[v] < INFINITY) {
                    out.printf("%d %d%n", v, coins[v]);
                    break;
                }
            }
        }
        out.close();
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }
}
