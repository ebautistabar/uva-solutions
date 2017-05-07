package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 696 - Game (Chess), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=637
class UVA00696HowManyKnights {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        while (line != null && !line.equals("0 0")) {
            String[] numbers = line.split(" ");
            int m = Integer.parseInt(numbers[0]);
            int n = Integer.parseInt(numbers[1]);
            int result = 0;
            if (Math.min(m, n) == 1) {
                // if there is one row, the knights cannot attack each other
                // so they can fill every column
                result = Math.max(m, n);
            } else if (Math.min(m, n) == 2) {
                // if there are 2 rows, the knights can be placed in this
                // pattern:
                // xx--xx--x
                // xx--xx--x
                // both rows are the same, so we can first calculate one.
                // every block of 4 columns, we can put 2 knights in a row.
                // if we have 0-3 columns left, we can put 0, 1, 2, 2
                // pieces respectively
                int cols = Math.max(m, n);
                int blocks = cols / 4;
                int colsLeft = cols % 4;
                result = blocks * 2 + (colsLeft == 3 ? 2 : colsLeft);
                // multiply by 2 to get the pieces in 2 rows
                result *= 2;
            } else {
                // if there are more than 2 rows, the knights can be placed in
                // this pattern:
                // x.x.x
                // .x.x.
                // x.x.x
                // .x.x.
                // First the odd columns: it's half the rows, and then in those
                // rows it's half the columns. When m is even, m/2 returns half,
                // but when m is odd, m/2 returns 1 less than the target,
                // e.g. 5/2=2, but we can really fill 3 rows. That's why we add
                // 1 in the following expression
                result = ((m + 1) / 2) * ((n + 1) / 2);
                // Then the even columns: again we want m/2, but this time we
                // want the floor of the result, not the ceiling, so we add
                // nothing
                result += (m / 2) * (n / 2);
            }
            out.print(result);
            out.print(" knights may be placed on a ");
            out.print(m);
            out.print(" row ");
            out.print(n);
            out.println(" column board.");
            line = in.readLine();
        }
        out.close();
    }
}
