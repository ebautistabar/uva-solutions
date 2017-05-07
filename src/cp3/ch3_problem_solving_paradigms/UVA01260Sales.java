package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 1260 - Complete Search, Iterative (Two Nested Loops), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3701
class UVA01260Sales {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        while (tests-- > 0) {
            int days = Integer.parseInt(in.readLine().trim());
            int[] sales = new int[days];
            StringTokenizer st = new StringTokenizer(in.readLine());
            for (int i = 0; i < days; i++) {
                sales[i] = Integer.parseInt(st.nextToken());
            }
            int result = 0;
            // days <= 1K, so we can use brute force
            for (int today = 1; today < days; today++) {
                int currentSale = sales[today];
                for (int day = 0; day < today; day++) {
                    if (sales[day] <= currentSale) {
                        result++;
                    }
                }
            }
            out.println(result);
        }
        out.close();
    }

}
