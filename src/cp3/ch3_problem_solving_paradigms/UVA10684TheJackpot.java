package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 10684 - Dynamic Programming, Max 1D Range Sum, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1625
class UVA10684TheJackpot {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int len = Integer.parseInt(in.readLine());
        while (len > 0) {
            int[] bets = readBets(len);
            int maxStreak = getMaxStreak(bets);
            if (maxStreak > 0) {
                out.printf("The maximum winning streak is %d.%n", maxStreak);
            } else {
                out.println("Losing streak.");
            }
            String line = in.readLine();
            while ("".equals(line.trim())) {
                line = in.readLine();
            }
            len = Integer.parseInt(line);
        }
        out.close();
    }

    private static int[] readBets(int len) throws IOException {
        int[] bets = new int[len];
        StringTokenizer st = new StringTokenizer("");
        for (int i = 0; i < len; i++) {
            while (!st.hasMoreTokens()) {
                st = new StringTokenizer(in.readLine());
            }
            bets[i] = Integer.parseInt(st.nextToken());
        }
        return bets;
    }

    private static int getMaxStreak(int[] bets) {
        int result = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = 0; i < bets.length; i++) {
            sum += bets[i];
            result = Math.max(sum, result);
            sum = Math.max(0, sum);
        }
        return result;
    }
}
