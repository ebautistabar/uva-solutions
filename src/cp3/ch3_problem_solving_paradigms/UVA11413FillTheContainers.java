package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 11413 - Binary Search, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2408
// See:
//   https://www.redgreencode.com/binary-search-answer-uva-11413-uva-12032/
class UVA11413FillTheContainers {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null) {
            if ("".equals(line.trim())) {
                line = in.readLine();
                continue;
            }
            StringTokenizer st = new StringTokenizer(line);
            int vessels = Integer.parseInt(st.nextToken());
            int containers = Integer.parseInt(st.nextToken());
            int[] capacities = new int[vessels];
            int totalCapacity = 0;
            st = new StringTokenizer(in.readLine());
            for (int i = 0; i < capacities.length; i++) {
                capacities[i] = Integer.parseInt(st.nextToken());
                totalCapacity += capacities[i];
            }
            // lo could be capacity of smallest vessel. Probably not worth it
            // as we are using binary search: log n
            int lo = 1;
            int hi = totalCapacity;
            int best = hi;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (isValidCapacity(mid, capacities, containers)) {
                    // try to find a smaller capacity, lower the high end
                    hi = mid - 1;
                    best = mid;
                } else { // we need a bigger capacity, raise the lower end
                    lo = mid + 1;
                }
            }
            out.println(best);
            line = in.readLine();
        }
        out.close();
    }

    private static boolean isValidCapacity(int maxCapacity, int[] capacities, int maxContainers) {
        int containers = 1;
        int currentSize = 0;
        for (int i = 0; i < capacities.length; i++) {
            if (capacities[i] > maxCapacity) {
                // this bottle doesn't fit in the biggest empty container
                return false;
            }
            if (currentSize + capacities[i] > maxCapacity) {
                // this bottle doesn't fit in the current container, start new one
                currentSize = 0;
                containers++;
            }
            if (containers > maxContainers) {
                // we have too many containers
                return false;
            }
            currentSize += capacities[i];
        }
        return true;
    }
}
