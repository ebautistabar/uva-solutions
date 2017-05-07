package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 12032 - Binary Search, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3183
// See:
//   https://www.redgreencode.com/binary-search-answer-uva-11413-uva-12032/
class UVA12032TheMonkeyAndTheOiledBamboo {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        for (int i = 1; i <= tests; i++) {
            int rungs = Integer.parseInt(in.readLine().trim());
            int[] heights = new int[rungs];
            StringTokenizer st = new StringTokenizer(in.readLine());
            for (int j = 0; j < heights.length; j++) {
                heights[j] = Integer.parseInt(st.nextToken());
            }
            int lo = 0; int hi = 10_000_000;
            int k = hi + 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (isValidStrength(mid, heights)) {
                    hi = mid - 1;
                    k = mid;
                } else {
                    lo = mid + 1;
                }
            }
            out.printf("Case %d: %d%n", i, k);
        }
        out.close();
    }

    private static boolean isValidStrength(int strength, int[] heights) {
        int current = 0;
        for (int i = 0; i < heights.length; i++) {
            int jump = current + strength;
            if (jump < heights[i]) {
                return false;
            } else if (jump == heights[i]) {
                strength--;
            }
            current = heights[i];
        }
        return true;
    }
}
