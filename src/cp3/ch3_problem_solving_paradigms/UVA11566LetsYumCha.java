package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

// 11566 - Dynamic Programming, 0-1 Knapsack (Subset Sum), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2613
class UVA11566LetsYumCha {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    private static final DecimalFormat df = buildDecimalFormat();

    private static int people;
    private static int moneyPerFriend;
    private static int teaCharge;
    private static int[] prices;
    private static int[][] favour;
    private static int[][][] table;

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null) {
            while ("".equals(line.trim())) {
                line = in.readLine();
            }
            StringTokenizer st = new StringTokenizer(line);
            int friends = Integer.parseInt(st.nextToken());
            moneyPerFriend = Integer.parseInt(st.nextToken());
            teaCharge = Integer.parseInt(st.nextToken());
            int kinds = Integer.parseInt(st.nextToken());
            if (friends == 0 && moneyPerFriend == 0 && teaCharge == 0 && kinds == 0) {
                break;
            }
            people = friends + 1; // your friends and yourself
            // We can pick each dim sum 2 times: just copy all information twice
            // and proceed like if they were different items
            favour = new int[kinds * 2][];
            prices = new int[kinds * 2];
            for (int i = 0; i < prices.length; i += 2) {
                st = new StringTokenizer(in.readLine());
                prices[i] = Integer.parseInt(st.nextToken());
                prices[i + 1] = prices[i];
                favour[i] = new int[people];
                for (int j = 0; j < people; j++) {
                    favour[i][j] = Integer.parseInt(st.nextToken());
                }
                favour[i + 1] = Arrays.copyOf(favour[i], people);
            }
            table = initializeTable(kinds * 2 + 1, moneyPerFriend * people + 1, 2 * people + 1);
            double meanFavour = getMeanFavour(0, 0, 0) / (double)people;
            out.println(df.format(meanFavour));
            line = in.readLine();
        }
        out.close();
    }

    private static int[][][] initializeTable(int kinds, int money, int orders) {
        int[][][] tbl = new int[kinds][money][orders];
        for (int i = 0; i < tbl.length; i++) {
            for (int j = 0; j < tbl[i].length; j++) {
                Arrays.fill(tbl[i][j], -1);
            }
        }
        return tbl;
    }

    private static int getMeanFavour(int id, int paid, int orders) {
        int cost = paid + teaCharge * people;
        double serviceCharge = Math.ceil(cost * 0.1);
        if (Double.compare(cost + serviceCharge, moneyPerFriend * people) > 0) {
            return -1000; // big negative number so the max() will discard this
        } else if (orders > 2 * people) {
            return -1000; // big negative number so the max() will discard this
        } else if (id == prices.length) {
            return 0; // return 0 to start accumulating the favour
        } else if (table[id][paid][orders] == -1) {
            int maxFavour = getMeanFavour(id + 1, paid + prices[id], orders + 1) + getFavour(id);
            maxFavour = Math.max(maxFavour, getMeanFavour(id + 1, paid, orders));
            table[id][paid][orders] = maxFavour;
        }
        return table[id][paid][orders];
    }

    private static int getFavour(int id) {
        int fav = 0;
        for (int i = 0; i < favour[id].length; i++) {
            fav += favour[id][i];
        }
        return fav;
    }

    private static DecimalFormat buildDecimalFormat() {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("#0.00");
        return df;
    }
}
