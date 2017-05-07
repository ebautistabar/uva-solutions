package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 10306 - Dynamic Programming, Coin Change (CC), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1247
class UVA10306ECoins {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(getLine().trim());
        while (tests-- > 0) {
            StringTokenizer st = new StringTokenizer(getLine());
            int types = Integer.parseInt(st.nextToken());
            int goal = Integer.parseInt(st.nextToken());
            int[] conventional = new int[types];
            int[] infotechnical = new int[types];
            for (int t = 0; t < types; t++) {
                st = new StringTokenizer(getLine());
                conventional[t] = Integer.parseInt(st.nextToken());
                infotechnical[t] = Integer.parseInt(st.nextToken());
            }

            // coins[cv][it] contains the minimum number of coins to reach cv^2 + it^2 == goal^2
            // The dimensions are goal + 1. The + 1 is there to account for
            // the conventional (cv) or IT (it) values being 0.
            // And the goal part is the maximum value that cv or it can achieve.
            // As cv^2 sums it^2, and both terms are positive, if one term goes up the other
            // goes down. The furthest down that both cv and it can go is 0, which means
            // the other value will go as high as goal.
            int[][] coins = new int[goal + 1][goal + 1];
            // The state of this DP problem has 2 dimensions: the highest coin
            // we can consider and the value that we want to reach, i.e. types
            // and goal. But they are a bit different than usual:
            // - the goal is not represented explicitly like in other DP problems,
            // but is represented through the formula, which we've distilled already
            // into 2 variables: cv and it. Thus, we must traverse along the valid
            // values for these 2 variables
            // - in the case of the types, in a recursive solution we would
            // decrement it to limit the type of coins we are allowed to take
            // at each step; each level can build its answer based on the types
            // that came before, not the ones that come after. In this case, as
            // it's bottom up, we start with a loop over the types. And we don't
            // need to represent this dimension on the DP table, because in
            // step i (max type t) we only reference step i-1 (max type t-1),
            // which means we don't take this coin type, and in terms of the
            // code, we can access the same cell we are about to fill in. I guess
            // we could have put this loop inside, but then cv and it would start
            // in 0, and we'd need to be careful about out of bounds accesses
            for (int cv = 0; cv < coins.length; cv++) {
                for (int it = 0; it < coins.length; it++) {
                    // We want to minimize, so start with a huge value
                    // The -1 is so the +1 that is later applied doesn't cause
                    // an overflow
                    coins[cv][it] = Integer.MAX_VALUE - 1;
                }
            }
            coins[0][0] = 0;
            for (int t = 0; t < types; t++) {
                // we start on conv[t] and infot[t] to ensure that the statement
                // inside the loop cannot go out of bounds with a negative value
                // i.e. will be 0 at most
                for (int cv = conventional[t]; cv < coins.length; cv++) {
                    for (int it = infotechnical[t]; it < coins.length; it++) {
                        // The first item in min() is actually coins[t - 1][cv][it]
                        // which means, don't take coin t.
                        // The second means, take coin t, and so we decrease
                        // both cv and it values, and sum 1 because we've used
                        // 1 coin right now
                        coins[cv][it] = Math.min(coins[cv][it], 1 + coins[cv - conventional[t]][it - infotechnical[t]]);
                    }
                }
            }
            // Traverse the matrix looking for minimum amongst the cells that
            // reach the goal
            int squaredGoal = goal * goal;
            int minCoins = Integer.MAX_VALUE - 1;
            for (int cv = 0; cv < coins.length; cv++) {
                for (int it = 0; it < coins.length; it++) {
                    if (cv * cv + it * it == squaredGoal) {
                        minCoins = Math.min(minCoins, coins[cv][it]);
                    }
                }
            }

            if (minCoins == Integer.MAX_VALUE - 1) {
                out.println("not possible");
            } else {
                out.println(minCoins);
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
