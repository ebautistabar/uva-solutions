package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

// 10976 - Complete Search, Iterative (One Loop, Linear Scan), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1917
class UVA10976FractionsAgain {

    private static final String OUTPUT_FORMAT = "1/%d = 1/%d + 1/%d\n";
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null) {
            double k = Double.parseDouble(line.trim());
            StringBuilder sb = new StringBuilder();
            int count = 0;
            for (double y = k + 1; y <= k * 2; y++) {
                //double x = 1 / (1 / k - 1 / y);
                // Transform the equation so it does less operations. It seems
                // to reduce loss of precision
                double x = y * k / (y - k);
                if (x == (int) x) {
                    count++;
                    sb.append(String.format(OUTPUT_FORMAT, (int) k, (int) x, (int) y));
                }
            }
            out.println(count);
            out.print(sb.toString());
            line = in.readLine();
        }
        out.close();
    }

}
