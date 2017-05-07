package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 11951 - Dynamic Programming, Max 2D Range Sum, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3102
class UVA11951Area {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        for (int i = 1; i <= tests; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int limit = Integer.parseInt(st.nextToken());
            int[][] prices = new int[n][m];
            for (int j = 0; j < n; j++) {
                st = new StringTokenizer(in.readLine());
                for (int k = 0; k < m; k++) {
                    prices[j][k] = Integer.parseInt(st.nextToken());
                    // Accumulate sum
                    if (j > 0) prices[j][k] += prices[j - 1][k];
                    if (k > 0) prices[j][k] += prices[j][k - 1];
                    if (j > 0 && k > 0) prices[j][k] -= prices[j - 1][k - 1];
                }
            }
            int maxArea = 0;
            long maxPrice = 0;
            for (int startR = 0; startR < n; startR++) {
                for (int startC = 0; startC < m; startC++) {
                    for (int endR = startR; endR < n; endR++) {
                        for (int endC = startC; endC < m; endC++) {
                            int area = (endR - startR + 1) * (endC - startC + 1);
                            if (maxArea <= area) { // if it's ==, this one may be cheaper, so enter the 'if' too
                                long price = prices[endR][endC];
                                if (startR > 0) price -= (long)prices[startR - 1][endC];
                                if (startC > 0) price -= (long)prices[endR][startC - 1];
                                if (startR > 0 && startC > 0) price += (long)prices[startR - 1][startC - 1];
                                if (price <= limit && maxArea < area) {
                                    // if the area is bigger and we have money
                                    maxArea = area;
                                    maxPrice = price;
                                } else if (price < maxPrice && maxArea == area) {
                                    // if the area is the same but the price is cheaper (hence we have money)
                                    maxPrice = price;
                                }
                            }
                        }
                    }
                }
            }
            out.printf("Case #%d: %d %d%n", i, maxArea, maxPrice);
        }
        out.close();
    }

}
