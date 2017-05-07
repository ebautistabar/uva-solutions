package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

// 11157 - Greedy, Non Classical, Usually Harder, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2098
class UVA11157DynamicFrog {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final boolean BIG = false;

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine());
        for (int i = 1; i <= tests; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            int stones = Integer.parseInt(st.nextToken());
            int rightBank = Integer.parseInt(st.nextToken());
            Rock[] rocks = new Rock[stones + 2];
            for (int j = 1; j <= stones; j++) {
                if (!st.hasMoreTokens()) {
                    st = new StringTokenizer(in.readLine());
                }
                String rock = st.nextToken();
                int distance = Integer.parseInt(rock.substring(2));
                boolean isSmall = rock.charAt(0) == 'S';
                rocks[j] = new Rock(distance, isSmall);
            }
            rocks[0] = new Rock(0, BIG);
            rocks[rocks.length - 1] = new Rock(rightBank, BIG);

            Arrays.sort(rocks, Rock.distanceComparator);
            boolean[] used = new boolean[rocks.length];
            int max = 0;
            int lastPos = 0;
            // First we go to the right bank
            for (int j = 0; j < rocks.length; ) {
                max = Math.max(max, rocks[j].distance - lastPos);
                lastPos = rocks[j].distance;
                if (rocks[j].isSmall) {
                    used[j] = true;
                }
                if (j + 1 == rocks.length) {
                    break;
                }
                if (rocks[j + 1].isSmall) {
                    // If the next rock is small, save it for later by skipping it
                    j += 2;
                } else {
                    // If it's big, we won't spend it, so use it, as it gives
                    // us the smallest possible jump (it's the closest to the
                    // current position)
                    j++;
                }
            }
            // Then return to the left bank
            lastPos = rightBank;
            for (int j = rocks.length - 1; j >= 0; ) {
                max = Math.max(max, lastPos - rocks[j].distance);
                lastPos = rocks[j].distance;
                if (j == 0) {
                    break;
                }
                if (used[j - 1]) {
                    // If it's a small rock that we've used, skip it
                    j -= 2;
                } else {
                    // Otherwise it'll be a big rock, or an unused small rock
                    j--;
                }
            }

            out.printf("Case %d: %d%n", i, max);
        }
        out.close();
    }

    private static class Rock {
        public int distance;
        public boolean isSmall;
        
        public Rock(int distance, boolean isSmall) {
            this.distance = distance;
            this.isSmall = isSmall;
        }

        public static Comparator<Rock> distanceComparator = new Comparator<Rock>() {
            public int compare(Rock rock1, Rock rock2) {
                return Integer.compare(rock1.distance, rock2.distance);
            }
        };
    }
}
