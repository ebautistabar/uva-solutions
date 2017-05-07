package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

// 11100 - Greedy, Involving Sorting (Or The Input Is Already Sorted), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2041
class UVA11100TheTrip2007 {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        boolean separator = false;
        String line = in.readLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int bags = Integer.parseInt(st.nextToken());
            if (bags != 0) {
                if (separator) {
                    out.println();
                }
                separator = true;

                int[] dimensions = new int[bags];
                st = new StringTokenizer(in.readLine());
                for (int i = 0; i < dimensions.length; i++) {
                    if (!st.hasMoreTokens()) {
                        st = new StringTokenizer(in.readLine());
                    }
                    dimensions[i] = Integer.parseInt(st.nextToken());
                }

                Arrays.sort(dimensions);
                // Count the repetitions in the dimension with the most
                // repetitions. That's the number of pieces needed
                // e.g. if the most repeated dimension is 2 with 3 repetitions,
                // then we need 3 pieces, because the bags with dimension 2
                // cannot be contained in each other
                int count = 1;
                int maxCount = 0;
                for (int i = 1; i < dimensions.length; i++) {
                    if (dimensions[i - 1] == dimensions[i]) {
                        count++;
                    } else {
                        maxCount = Math.max(maxCount, count);
                        count = 1;
                    }
                }
                // as we always check the previous item in the loop, we do this
                // outside again to account for the last item. Another option
                // was building dimensions to have bags+1 items, having the last
                // item an invalid dimension to trigger the last maxCount update
                int pieces = Math.max(maxCount, count);
                out.println(pieces);

                for (int i = 0; i < pieces; i++) {
                    // each iteration fills the ith piece
                    StringBuilder sb = new StringBuilder();
                    for (int j = i; j < dimensions.length; j += pieces) {
                        sb.append(dimensions[j]);
                        sb.append(' ');
                    }
                    sb.setLength(sb.length() - 1); // remove the last space
                    out.println(sb.toString());
                }
            }
            line = in.readLine();
        }
        out.close();
    }

}
