package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 11221 - Palindrome, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2162
class UVA11221MagicSquarePalindromes {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int t = Integer.parseInt(in.readLine());
        for (int i = 1; i <= t; i++) {
            out.println("Case #" + i + ":");
            String line = cleanLine(in.readLine());
            double sqrt = Math.sqrt(line.length());
            int dimension = (int)sqrt;
            // From SO: If it does indeed store an exact integer, then
            // ((int)d == d) works fine. And indeed, for any 32-bit integer i,
            // (int)((double)i) == i since a double can exactly represent it.
            if (sqrt == dimension && isSquarePalindrome(line, dimension)) {
                out.println(dimension);
            } else {
                out.println("No magic :(");
            }
        }
        out.close();
    }

    private static boolean isSquarePalindrome(String line, int dimension) {
        int l = 0, r = line.length() - 1, lc = 0, rc = dimension - 1, row = 0;
        while (l <= r) {
         // traditional order
            if (line.charAt(l) != line.charAt(r)
                    // traditional order compared to up-down left-right columns
                    || line.charAt(l) != line.charAt(row * dimension + lc)
                    // traditional order compared to down-up right-left columns
                    || line.charAt(l) != line.charAt((dimension - 1 - row) * dimension + rc)) {
                return false;
            }
            l++;
            r--;
            row = (row + 1) % dimension;
            if (row == 0) {
                lc++;
                rc--;
            }
        }
        return true;
    }

    private static String cleanLine(String line) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c >= 'a' && c <= 'z') {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

}
