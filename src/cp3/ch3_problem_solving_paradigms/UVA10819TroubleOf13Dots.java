package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

// 10819 - 0-1 Knapsack (Subset Sum), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1760
class UVA10819TroubleOf13Dots {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static int originalMoney;
    private static int[] price;
    private static int[] favour;
    private static int[][] table;

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            originalMoney = Integer.parseInt(st.nextToken());
            int items = Integer.parseInt(st.nextToken());
            price = new int[items];
            favour = new int[items];
            for (int i = 0; i < items; i++) {
                st = new StringTokenizer(in.readLine());
                price[i] = Integer.parseInt(st.nextToken());
                favour[i] = Integer.parseInt(st.nextToken());
            }
            table = initializeTable(items, originalMoney + 201); // -200, 0, and the original money
            int maxFavour = getMaxFavour(items - 1, originalMoney);
            out.println(maxFavour);
            line = in.readLine();
        }
        out.close();
    }

    private static int getMaxFavour(int id, int remainingMoney) {
        int spentMoney = originalMoney - remainingMoney;
        if (remainingMoney < -200 || (remainingMoney < 0 && spentMoney <= 2000)) {
            return -1000;
        } else if (id < 0) {
            return 0;
        } else if (table[id][remainingMoney + 200] == -1) {
            int max = getMaxFavour(id - 1, remainingMoney); // ignore
            max = Math.max(max, favour[id] + getMaxFavour(id - 1, remainingMoney - price[id]));
            table[id][remainingMoney + 200] = max;
        }
        return table[id][remainingMoney + 200];
    }

    private static int[][] initializeTable(int items, int money) {
        int[][] tbl = new int[items][money];
        for (int i = 0; i < items; i++) {
            Arrays.fill(tbl[i], -1);
        }
        return tbl;
    }

}
