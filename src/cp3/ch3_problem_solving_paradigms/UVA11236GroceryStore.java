package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

// 11236 - Complete Search, Iterative (Three or More Nested Loops, Harder), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2177
//
// Reference:
// https://www.redgreencode.com/equation-solving-is-the-key-to-uva-11236/
class UVA11236GroceryStore {

    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final DecimalFormat df = buildDecimalFormat();

    public static void main(String args[]) throws NumberFormatException, IOException {
        for (int a = 1; a < 2000 && a * a * a * a <= 2_000_000_000; a += 1) {
            for (int b = a; a + b < 2000 && a * b * b * b <= 2_000_000_000; b += 1) {
                for (int c = b; a + b + c < 2000 && a * b * c * c <= 2_000_000_000; c += 1) {
                    int denominator = a * b * c - 1_000_000;
                    if (denominator == 0)
                        continue;
                    int numerator = 1_000_000 * (a + b + c);
                    if (numerator % denominator != 0)
                        continue;
                    int d = numerator / denominator;
                    if (c <= d
                            && a + b + c + d <= 2000
                            && a * b * d * c <= 2_000_000_000) {
                        out.printf("%s %s %s %s%n", df.format(a / 100.0),
                                df.format(b / 100.0), df.format(c / 100.0),
                                df.format(d / 100.0));
                    }
                }
            }
        }
        out.close();
    }

    private static DecimalFormat buildDecimalFormat() {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("#0.00");
        return df;
    }

}
