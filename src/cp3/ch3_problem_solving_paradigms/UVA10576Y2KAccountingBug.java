package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 10576 - Complete Search, Recursive Backtracking (Easy), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1517
class UVA10576Y2KAccountingBug {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int MONTHS = 12;

    private static int finalSurplus = 0;
    private static int surplus;
    private static int deficit;

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null && !line.trim().isEmpty()) {
            StringTokenizer st = new StringTokenizer(line);
            surplus = Integer.parseInt(st.nextToken());
            deficit = -Integer.parseInt(st.nextToken());

            finalSurplus = 0;
            int[] monthlyPostings = new int[MONTHS];
            int month = 1;
            recordBestSurplus(month, monthlyPostings);

            if (finalSurplus > 0) {
                out.println(finalSurplus);
            } else {
                out.println("Deficit");
            }
            line = in.readLine();
        }
        out.close();
    }

    private static void recordBestSurplus(int month, int[] monthlyPostings) {
        if (month > MONTHS) {
            int sum = 0;
            for (int i = 0; i < monthlyPostings.length; i++) {
                sum += monthlyPostings[i];
            }
            finalSurplus = Math.max(finalSurplus, sum);
        } else {
            monthlyPostings[month - 1] = surplus;
            if (month < 5 || getLastFiveMonthsSum(month, monthlyPostings) < 0) {
                recordBestSurplus(month + 1, monthlyPostings);
            }
            monthlyPostings[month - 1] = deficit;
            if (month < 5 || getLastFiveMonthsSum(month, monthlyPostings) < 0) {
                recordBestSurplus(month + 1, monthlyPostings);
            }
        }
    }

    private static int getLastFiveMonthsSum(int month, int[] monthlyPostings) {
        int sum = 0;
        for (int i = 4; i >= 0; i--) {
            sum += monthlyPostings[month - 1 - i];
        }
        return sum;
    }

}
