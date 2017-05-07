package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 12455 - Complete Search, Iterative (Fancy Techniques), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3886
class UVA12455Bars {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        while (tests-- > 0) {
            int targetLength = Integer.parseInt(in.readLine().trim());
            int bars = Integer.parseInt(in.readLine().trim());
            StringTokenizer st = new StringTokenizer(in.readLine());
            int[] lengths = new int[bars];
            for (int i = 0; i < bars; i++)
                lengths[i] = Integer.parseInt(st.nextToken());
            // I need to go over all the possible subsets of bars, adding their
            // length and comparing to the target. As the number of bars is small
            // (<= 20) it is doable. For each bar we have a pick/leave decision.
            // 2 possibilities for each of the 20 bars, i.e. 2^20 ~ 1M
            // Quick way to generate subsets is with bitsets. 0s represent 'leave'
            // and 1s represent 'take'.
            boolean possible = false;
            int fullBitSet = (1 << bars) - 1;
            for (int i = 0; i <= fullBitSet; i++) { // for every possible subset
                int sum = 0;
                for (int j = 0; j < bars; j++) { // sum the length of the bars in the subset
                    if ((i & (1 << j)) > 0) // if bar j is contained in the subset
                        sum += lengths[j];
                }
                if (sum == targetLength) {
                    possible = true;
                    break;
                }
            }
            if (possible)
                out.println("YES");
            else
                out.println("NO");
        }
        out.close();
    }

}
