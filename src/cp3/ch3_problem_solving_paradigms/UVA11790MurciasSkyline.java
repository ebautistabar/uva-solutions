package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 11790 - Dynamic Programming, Longest Increasing Subsequence (LIS), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2890
class UVA11790MurciasSkyline {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        for (int i = 1; i <= tests; i++) {
            int buildings = Integer.parseInt(in.readLine().trim());
            int[] heights = readArray(buildings);
            int[] widths = readArray(buildings);
            int[] lis = new int[buildings]; // lis[j] = size of lis up to and including item j
            int[] lds = new int[buildings]; // lds[j] = size of lds up to and including item j
            int maxLis = 0;
            int maxLds = 0;
            for (int j = 0; j < buildings; j++) {
                // just this building, as the start of a new subsequence
                lis[j] = lds[j] = widths[j];
                for (int k = 0; k < j; k++) {
                    if (heights[k] < heights[j]) {
                        // increasing (lis)
                        lis[j] = Math.max(lis[j], lis[k] + widths[j]);
                    } else if (heights[k] > heights[j]) {
                        // decreasing (lds)
                        lds[j] = Math.max(lds[j], lds[k] + widths[j]);
                    }
                }
                maxLis = Math.max(maxLis, lis[j]);
                maxLds = Math.max(maxLds, lds[j]);
            }
            if (maxLis >= maxLds) {
                out.printf("Case %d. Increasing (%d). Decreasing (%d).%n", i, maxLis, maxLds);
            } else {
                out.printf("Case %d. Decreasing (%d). Increasing (%d).%n", i, maxLds, maxLis);
            }
        }
        out.close();
    }

    private static int[] readArray(int length) throws IOException {
        int[] array = new int[length];
        StringTokenizer st = new StringTokenizer(in.readLine());
        for (int i = 0; i < length; i++) {
            array[i] = Integer.parseInt(st.nextToken());
        }
        return array;
    }

}
