package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.StringTokenizer;

// 10341 - Binary Search, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1282
class UVA10341SolveIt {

    private static final DecimalFormat df = buildDecimalFormat();
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null && !"".equals(line.trim())) {
            StringTokenizer st = new StringTokenizer(line);
            int p = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            int u = Integer.parseInt(st.nextToken());
            double x = solveIt(p, q, r, s, t, u);
            if (Double.compare(x, 0) == -1) {
                out.println("No solution");
            } else {
                out.println(df.format(x));
            }
            line = in.readLine();
        }
        out.close();
    }

    private static double solveIt(int p, int q, int r, int s, int t, int u) {
        double lo = 0.0;
        double hi = 1.0;
        // According to the book, a precision of 1e-9 gives us 40ish iterations,
        // so we can just hardcode 50. That way there's no chance of getting
        // stuck in loop
        for (int i = 0; i < 50; i++) {
            double mid = (lo + hi) / 2.0;
            double result = applyValues(p, q, r, s, t, u, mid);
            if (Math.abs(result - 0.0) < 1e-9) {
                return mid;
            } else if (result < 0) {
                // The way to move the boundaries is determined by calling the
                // function with the limits (0 and 1) and seeing which direction
                // the values take
                hi = mid;
            } else {
                lo = mid;
            }
        }
        return -1;
    }

    public static double applyValues(int p, int q, int r, int s, int t, int u, double x) {
        return p * Math.exp(-x) + q * Math.sin(x) + r * Math.cos(x) + s * Math.tan(x) + t * x * x + u;
    }

    private static DecimalFormat buildDecimalFormat() {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("#0.0000");
        return df;
    }
}
