package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

// 11456 - Dynamic Programming, Longest Increasing Subsequence (LIS), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2451
class UVA11456Trainsorting {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        Integer tests = Integer.parseInt(in.readLine().trim());
        while (tests-- > 0) {
            int cars = Integer.parseInt(in.readLine().trim());
            int[] weight = new int[cars];
            int[] lis = new int[cars];
            int[] lds = new int[cars];
            int maxLength = 0;
            for (int i = 0; i < cars; i++) {
                weight[i] = Integer.parseInt(in.readLine().trim());
            }
            // LDS[i] is the length of the LDS *starting* at i, as opposed
            // to the book definition which is *ending*. This means it's
            // calculated with items > i
            // LIS[i] is the length of the LIS *starting* at i, as opposed
            // to the book definition which is *ending*. This means it's
            // calculated with items > i.
            // We use i as a reference point: the first item where we start
            // to build our train. With the items till the final item, we
            // calculate the LDS (which are the items that will be attached
            // in the tail) and the LIS (which are the items that will be
            // attached in the front).
            for (int i = cars - 1; i >= 0; i--) {
                lis[i] = lds[i] = 1;
                for (int j = i + 1; j < cars; j++) {
                    if (weight[i] < weight[j]) {
                        lis[i] = Math.max(lis[i], 1 + lis[j]);
                    } else if (weight[i] > weight[j]) {
                        lds[i] = Math.max(lds[i], 1 + lds[j]);
                    }
                }
                // the -1 fixes the double-counting of item i (both in lis and lds)
                maxLength = Math.max(maxLength, lis[i] + lds[i] - 1);
            }
            out.println(maxLength);
        }
        out.close();
    }

}
