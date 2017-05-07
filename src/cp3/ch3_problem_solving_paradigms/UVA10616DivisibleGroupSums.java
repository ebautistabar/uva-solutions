package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

// 10616 - Dynamic Programming, 0-1 Knapsack (Subset Sum), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1557
class UVA10616DivisibleGroupSums {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static int divisor;
    private static int[] nums;
    private static long[][][] table;

    public static void main(String args[]) throws NumberFormatException, IOException {
        int test = 1;
        String line = in.readLine();
        while (line != null && !"0 0".equals(line.trim())) {
            StringTokenizer st = new StringTokenizer(line.trim());
            int n = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());
            nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = Integer.parseInt(in.readLine().trim());
            }
            out.printf("SET %d:%n", test);
            for (int i = 1; i <= q; i++) {
                st = new StringTokenizer(in.readLine().trim());
                divisor = Integer.parseInt(st.nextToken());
                int slots = Integer.parseInt(st.nextToken());
                table = initializeTable(n, slots + 1, divisor);
                long result = divisibleGroupSums(n - 1, slots, 0);
                out.printf("QUERY %d: %d%n", i, result);
            }
            test++;
            line = in.readLine();
        }
        out.close();
    }

    private static long[][][] initializeTable(int n, int slots, int divisor) {
        long[][][] table = new long[n][slots][divisor];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                Arrays.fill(table[i][j], -1L);
            }
        }
        return table;
    }

    private static long divisibleGroupSums(int id, int remainingSlots, int sum) {
        if (remainingSlots == 0) {
            return sum == 0 ? 1 : 0;
        } else if (id < 0) {
            return 0;
        } else if (table[id][remainingSlots][sum] == -1L) {
            // ignore nums[id]
            long result = divisibleGroupSums(id - 1, remainingSlots, sum);
            // take nums[id]
            // by doing the % operation on every step we ensure sum is always < divisor and fits the table
            // we need to ensure that the result is positive so it fits the table. It will be
            // negative when sum + nums[id] is negative
            result += divisibleGroupSums(id - 1, remainingSlots - 1, positiveMod(sum + nums[id], divisor));
            table[id][remainingSlots][sum] = result;
        }
        return table[id][remainingSlots][sum];
    }

    private static int positiveMod(int dividend, int divisor) {
        int result = dividend % divisor;
        if (result < 0) {
            result += divisor;
        }
        return result;
    }

}
