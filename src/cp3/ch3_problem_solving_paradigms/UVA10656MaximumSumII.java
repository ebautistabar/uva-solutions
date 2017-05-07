package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

// 10656 - Greedy, Non Classical, Usually Harder, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1597
class UVA10656MaximumSumII {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null) {
            int n = Integer.parseInt(line.trim());
            if (n != 0) {
                boolean printSeparator = false;
                for (int i = 0; i < n; i++) {
                    int num = Integer.parseInt(in.readLine().trim());
                    if (num != 0) {
                        if (printSeparator) {
                            out.print(' ');
                        } else {
                            printSeparator = true;
                        }
                        out.print(num);
                    }
                }
                if (!printSeparator) {
                    out.print('0');
                }
                out.println();
            }
            line = in.readLine();
        }
        out.close();
    }

}
