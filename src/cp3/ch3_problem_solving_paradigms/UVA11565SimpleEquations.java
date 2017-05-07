package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 11565 - Complete Search, Iterative (Three or More Nested Loops, Harder), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2612
class UVA11565SimpleEquations {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        while (tests-- > 0) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            boolean found = false;
            // Upper limit for a, b and c is 10K. Because of the third equation
            // we know that we can have x^2 ~ 10K, with y = 1 and z=2 for example
            // Hence x ~ 100. The same reasoning applies to y and z, hence the
            // limits in the loops.
            // Also, if x+y+z=A, then z=A-x-y, i.e. we can do simply 2 loops
            for (int x = -100; x <= 100 && !found; x++) {
                for (int y = -100; y <= 100 && !found; y++) {
                    if (x != y) {
                        int z = a - x - y;
                        if (x != z && y != z && areEquationsSolved(x, y, z, a, b, c)) {
                            found = true;
                            out.print(String.format("%d %d %d%n", x, y, z));
                        }
                    }
                }
            }
            if (!found)
                out.println("No solution.");
        }
        out.close();
    }

    private static boolean areEquationsSolved(int x, int y, int z, int a, int b,
            int c) {
        return x + y + z == a && x * y * z == b && x * x + y * y + z * z == c;
    }

}
