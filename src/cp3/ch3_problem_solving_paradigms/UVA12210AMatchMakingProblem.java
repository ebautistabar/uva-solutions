package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

// 12210 - Greedy, Involving Sorting (Or The Input Is Already Sorted), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3362
class UVA12210AMatchMakingProblem {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int test = 1;
        String line = in.readLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int men = Integer.parseInt(st.nextToken());
            int women = Integer.parseInt(st.nextToken());
            if (men != 0 && women != 0) {
                int[] bachelors = new int[men];
                for (int i = 0; i < bachelors.length; i++) {
                    bachelors[i] = Integer.parseInt(in.readLine().trim());
                }
                int[] spinsters = new int[women];
                for (int i = 0; i < spinsters.length; i++) {
                    spinsters[i] = Integer.parseInt(in.readLine().trim());
                }

                if (men <= women) {
                    out.printf("Case %d: 0%n", test);
                } else {
                    Arrays.sort(bachelors);
                    out.printf("Case %d: %d %d%n", test, men - women, bachelors[0]);
                }

                test++;
            }
            line = in.readLine();
        }
        out.close();
    }

}
