package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

// 11389 - Greedy, Classical, Usually Easier, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2384
class UVA11389TheBusDriverProblem {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null) {
            if (!"".equals(line.trim())) {
                StringTokenizer st = new StringTokenizer(line);
                int n = Integer.parseInt(st.nextToken());
                int limit = Integer.parseInt(st.nextToken());
                int rate = Integer.parseInt(st.nextToken());
                if (n == 0 && limit == 0 && rate == 0) {
                    break;
                }
                st = new StringTokenizer(in.readLine());
                int[] morningRoutes = new int[n];
                for (int i = 0; i < morningRoutes.length; i++) {
                    morningRoutes[i] = Integer.parseInt(st.nextToken());
                }
                st = new StringTokenizer(in.readLine());
                int[] eveningRoutes = new int[n];
                for (int i = 0; i < eveningRoutes.length; i++) {
                    // Negative to reverse the order of the sort
                    eveningRoutes[i] = -Integer.parseInt(st.nextToken());
                }
                // Sort both. Morning ascending, evening descending (hence the -)
                Arrays.sort(morningRoutes);
                Arrays.sort(eveningRoutes);
                int cost = 0;
                for (int i = 0; i < morningRoutes.length; i++) {
                    // Subtract to reverse the negative applied earlier
                    int length = morningRoutes[i] - eveningRoutes[i];
                    int excess = length - limit;
                    if (excess > 0) {
                        cost += excess * rate;
                    }
                }
                out.println(cost);
            }
            line = in.readLine();
        }
        out.close();
    }

}
