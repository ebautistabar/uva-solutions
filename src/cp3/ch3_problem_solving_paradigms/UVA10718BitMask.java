package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 10718 - Greedy, Non Classical, Usually Harder, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1659
class UVA10718BitMask {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            long n = Long.parseLong(st.nextToken());
            long l = Long.parseLong(st.nextToken());
            long u = Long.parseLong(st.nextToken());

            long m = 0;
            // Process bits from left to right
            for (int i = 31; i >= 0; i--) {
                long bit = n & (1 << i);
                if (bit > 0) {
                    // If bit is 1, our best option would be writing a 0 in the
                    // mask. But first we need to check if the mask is between
                    // l and u. Concretely, we check if the biggest number with
                    // 0 in that position is in the range. The biggest number
                    // with 0 in that position is bit - 1.
                    long temp = m | (bit - 1);
                    if (temp < l) {
                        // If it's out of range, it means we can't set 0 in the
                        // mask. So we set 1. Don't need to check the upper
                        // range, as we assume a solution exists with the given
                        // input parameters
                        m |= bit;
                    }
                } else {
                    // If bit is 0, our best option to maximize n|m is
                    // setting a 1. Again we need to check if the mask is in
                    // the range. We check the lowest number with 1 in that
                    // position, which is the current mask with 1 in that
                    // position, as the bits to the right are 0 from when it was
                    // initialized.
                    long temp = m | (1L << i);
                    if (temp <= u) {
                        // If it's in range, it means we can set 1 in the mask.
                        // Otherwise we do nothing, which leaves the original 0
                        // in m from when it was initialized.
                        m = temp;
                    }
                }
            }
            out.println(m);

            line = in.readLine();
        }
        out.close();
    }

}
