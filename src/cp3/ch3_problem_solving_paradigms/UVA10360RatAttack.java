package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 10360 - Complete Search, Iterative (Three or More Nested Loops, Harder), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1301
//
// Reference:
// CP3 Page 77 (tip 5)
//
// Warning: example input in uDebug is wrong; there shouldn't be blank newlines
// between test cases
class UVA10360RatAttack {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int MAX_SIDE = 1025;

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        while (tests-- > 0) {
            int strength = Integer.parseInt(in.readLine().trim());
            int nests = Integer.parseInt(in.readLine().trim());
            int[] ratsX = new int[nests];
            int[] ratsY = new int[nests];
            int[] rats = new int[nests];
            for (int i = 0; i < nests; i++) {
                StringTokenizer st = new StringTokenizer(in.readLine());
                ratsX[i] = Integer.parseInt(st.nextToken());
                ratsY[i] = Integer.parseInt(st.nextToken());
                rats[i] = Integer.parseInt(st.nextToken());
            }
            int side = MAX_SIDE + strength + strength;
            int[][] killed = new int[side][side];
            // Iterate each nest adding its size to all the cells from which
            // we could kill it
            for (int i = 0; i < nests; i++) {
                int row = ratsX[i] + strength;
                int col = ratsY[i] + strength;
                int size = rats[i];
                for (int j = -strength; j <= strength; j++) {
                    int nrow = row + j;
                    for (int k = -strength; k <= strength; k++) {
                        int ncol = col + k;
                        killed[nrow][ncol] += size;
                    }
                }
            }
            int bestSize = 0;
            int bestX = -1;
            int bestY = -1;
            // I cannot calculate the best cell on the flight, because in case
            // of ties I must pick the "smaller", sorted by x, y. So I traverse
            // the kill counts again
            int limit = strength + MAX_SIDE;
            for (int j = strength; j < limit; j++)
                for (int k = strength; k < limit; k++)
                    if (bestSize < killed[j][k]) {
                        bestSize = killed[j][k];
                        bestX = j;
                        bestY = k;
                    }
            // The coordinates are offset by strength so I don't go out of bounds
            // when updating the kill counts. That way there's no ifs anywhere
            // Then, when searching for the best kill counts, I must ignore
            // the outer 'strength' rows and cols all around the grid
            out.printf("%d %d %d%n", bestX - strength, bestY - strength, bestSize);
        }
        out.close();
    }

}
