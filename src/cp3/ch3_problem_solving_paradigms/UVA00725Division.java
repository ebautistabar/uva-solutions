package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

// 725 - Complete Search, Iterative (Two Nested Loops), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=666
class UVA00725Division {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    private static final int MAX_NUMERATOR = 98765;
    private static final int TEN_DIGITS = (1 << 10) - 1; // this way we set the ten least significant bits

    public static void main(String args[]) throws NumberFormatException, IOException {
        int n = Integer.parseInt(in.readLine().trim());
        while (n != 0) {
            // Brute force all the possibilities
            int count = 0;
            int maxDenominator = MAX_NUMERATOR / n;
            for (int denominator = 1234; denominator <= maxDenominator; denominator++) {
                int numerator = denominator * n;
                int used = 0;
                int tmp = numerator;
                while (tmp > 0) {
                    int digit = tmp % 10; // get last digit
                    used |= 1 << digit; // register that digit in the set
                    tmp /= 10; // move to next digit
                }
                tmp = denominator;
                while (tmp > 0) { // same as above
                    int digit = tmp % 10;
                    used += 1 << digit;
                    tmp /= 10;
                }
                if (denominator < 10000) // if there's a leading 0
                    used |= 1; // register the least significant bit
                if (used == TEN_DIGITS) {
                    count++;
                    out.print(String.format("%05d / %05d = %d%n", numerator, denominator, n));
                }
            }
            if (count == 0)
                out.print(String.format("There are no solutions for %d.%n", n));
            n = Integer.parseInt(in.readLine().trim());
            if (n != 0)
                out.println();
        }
        out.close();
    }

}
