package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

// 357 - Coin Change (CC), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=293
class UVA00357LetMeCountTheWays {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int MAX_NUM = 30000;
    private static int num;
    private static int[] coins = {1, 5, 10, 25, 50};
    private static long[][] table;

    public static void main(String args[]) throws NumberFormatException, IOException {
        long[] tbl = getWaysBottomUpSpaceEfficient();
        String line = in.readLine();
        while (line != null) {
            num = Integer.parseInt(line.trim());
            long ways = tbl[num];
            if (ways == 1L) {
                out.printf("There is only 1 way to produce %d cents change.%n", num);
            } else {
                out.printf("There are %d ways to produce %d cents change.%n", ways, num);
            }
            line = in.readLine();
        }
        out.close();
    }

    private static long[] getWaysBottomUpSpaceEfficient() {
        long[] tbl = new long[MAX_NUM + 1];
        tbl[0] = 1; // 1 way to pay 0 money
        // the case where a coin is not used is contained in tbl[m] before adding, I guess
        for (int c = 0; c < coins.length; c++) {
            for (int m = coins[c]; m < tbl.length; m++) {
                tbl[m] += tbl[m - coins[c]];
            }
        }
        return tbl;
    }

    // Bottom up with matrix. Fast but wastes space
    @SuppressWarnings("unused")
    private static long[][] getWaysBottomUp() {
        long[][] tbl = new long[MAX_NUM + 1][coins.length];
        for (int i = 0; i < tbl[0].length; i++) {
            tbl[0][i] = 1; // there's 1 way to produce 0 change
        }
        for (int m = 1; m < tbl.length; m++) {
            for (int c = 0; c < coins.length; c++) {
                if (c > 0)
                    tbl[m][c] = tbl[m][c - 1];
                if (m - coins[c] >= 0)
                    tbl[m][c] += tbl[m - coins[c]][c];
            }
        }
        return tbl;
    }

    // Stack overflow for max amount. try bottom up
    @SuppressWarnings("unused")
    private static long getWays(int coin, int amount) {
        if (amount == num) {
            return 1;
        } else if (amount > num || coin == coins.length) {
            return 0;
        } else if (table[coin][amount] == -1) {
            long ways = getWays(coin + 1, amount); // don't use this coin
            ways += getWays(coin, amount + coins[coin]); // use it and consider it again
            table[coin][amount] = ways;
        }
        return table[coin][amount];
    }

    @SuppressWarnings("unused")
    private static long[][] initializeTable(int coinTypes, int cents) {
        long[][] tbl = new long[coinTypes][cents];
        for (int i = 0; i < tbl.length; i++) {
            Arrays.fill(tbl[i], -1);
        }
        return tbl;
    }
}
