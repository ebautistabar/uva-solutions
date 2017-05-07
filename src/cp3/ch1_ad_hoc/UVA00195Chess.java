package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 195 - Game (Chess), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=214
class UVA00195Chess {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int t = Integer.parseInt(in.readLine());
        while (t-- > 0) {
            int count = 0;
            StringTokenizer st = new StringTokenizer(in.readLine());
            char piece = st.nextToken().charAt(0);
            int m = Integer.parseInt(st.nextToken());
            int n = Integer.parseInt(st.nextToken());
            switch (piece) {
            case 'r':
            case 'Q':
                // each rook has to be in a different line
                // each queen has to be in a different line (also has to be
                // in a different diagonal from another queen, which is always
                // possible in our case, because the number of queens we are
                // given always fits in the board apparently)
                count = Math.min(m, n);
                break;
            case 'K':
                // the eight cells around a king must be empty, so the best
                // way to arrange them is this:
                // x.x.x
                // .....
                // x.x.x
                // .....
                // So it's half the rows, and then in those rows it's half
                // the columns. When m is even, m/2 returns half, but when m is
                // odd, m/2 returns 1 less than the target, e.g. 5/2=2, but we
                // can really fill 3 rows. That's why we add 1 in the following
                // expression
                count = ((m + 1) / 2) * ((n + 1) / 2);
                break;
            case 'k':
                // knights can be arranged like this:
                // x.x.x
                // .x.x.
                // x.x.x
                // .x.x.
                // So it's the same as kings plus something else for the other
                // rows. That something is again m/2, but this time we want the
                // floor of the result, not the ceiling, so we add nothing
                count = ((m + 1) / 2) * ((n + 1) / 2) + (m / 2) * (n / 2);
                break;
            }
            out.println(count);
        }
        out.close();
    }
}
