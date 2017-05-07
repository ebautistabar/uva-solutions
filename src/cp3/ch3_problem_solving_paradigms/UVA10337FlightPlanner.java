package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

// 10337 - Dynamic Programming, Non Classical (The Easier Ones), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1278
// Notes at the bottom
class UVA10337FlightPlanner {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int MAX_ALTITUDE = 9;
    private static final int INFINITY = Integer.MAX_VALUE / 2; // to protect from overflow when adding later
    private static final int[] TRANSITIONS = {1, 0, -1};
    private static final int[] COSTS = {60, 30, 20};

    private static int goal;
    private static int[][] wind;
    private static int[][] table;

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(getLine().trim());
        while (tests-- > 0) {
            goal = Integer.parseInt(getLine().trim()) / 100;
            wind = new int[MAX_ALTITUDE + 1][goal]; // altitudes 0 to 9
            for (int i = wind.length - 1; i >= 0; i--) {
                StringTokenizer st = new StringTokenizer(getLine());
                for (int j = 0; j < wind[i].length; j++) {
                    wind[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            //int minFuel = getMinFuelTopDown();
            int minFuel = getMinFuelBottomUpSavingSpace();
            //int minFuel = getMinFuelBottomUp();

            out.println(minFuel);
            out.println();
        }
        out.close();
    }

    @SuppressWarnings("unused")
    private static int getMinFuelBottomUp() {
        table = new int[MAX_ALTITUDE + 1][goal + 1];
        table[0][goal] = 0;
        for (int altitude = 1; altitude < table.length; altitude++) {
            table[altitude][goal] = INFINITY;
        }
        for (int position = goal - 1; position >= 0; position--) {
            for (int altitude = 0; altitude < table.length; altitude++) {
                int min = INFINITY;
                for (int i = 0; i < TRANSITIONS.length; i++) {
                    int naltitude = altitude + TRANSITIONS[i];
                    if (naltitude >= 0 && naltitude < table.length) {
                        int cost = COSTS[i] - wind[altitude][position];
                        min = Math.min(min, cost + table[naltitude][position + 1]);
                    }
                }
                table[altitude][position] = min;
            }
        }
        return table[0][0];
    }

    private static int getMinFuelBottomUpSavingSpace() {
        // We just need 2 columns, as we only reference the previous one
        table = new int[MAX_ALTITUDE + 1][2];
        table[0][0] = 0;
        for (int altitude = 1; altitude < table.length; altitude++) {
            table[altitude][0] = INFINITY;
        }
        int idx = 1;
        for (int position = goal - 1; position >= 0; position--) {
            for (int altitude = 0; altitude < table.length; altitude++) {
                int min = INFINITY;
                for (int i = 0; i < TRANSITIONS.length; i++) {
                    int naltitude = altitude + TRANSITIONS[i];
                    if (naltitude >= 0 && naltitude < table.length) {
                        int cost = COSTS[i] - wind[altitude][position];
                        min = Math.min(min, cost + table[naltitude][idx ^ 1]);
                    }
                }
                table[altitude][idx] = min;
            }
            idx ^= 1; // toggle between one column and the other
        }
        return table[0][idx ^ 1];
    }

    @SuppressWarnings("unused")
    private static int getMinFuelTopDown() {
        table = new int[MAX_ALTITUDE + 1][goal]; // altitudes 0 to 9
        for (int i = 0; i < table.length; i++) {
            Arrays.fill(table[i], -1);
        }
        int altitude = 0;
        int position = 0;
        int minFuel = getMinFuel(altitude, position);
        return minFuel;
    }

    private static int getMinFuel(int altitude, int position) {
        if (altitude == 0 && position == goal) return 0;
        if (altitude > MAX_ALTITUDE || altitude < 0) return INFINITY;
        if (altitude > 0 && position == goal) return INFINITY;
        if (table[altitude][position] == -1) {
            int min = INFINITY;
            for (int i = 0; i < TRANSITIONS.length; i++) {
                int cost = Math.max(0, COSTS[i] - wind[altitude][position]); // tail wind is positive, reduces cost
                min = Math.min(min, cost + getMinFuel(altitude + TRANSITIONS[i], position + 1));
            }
            table[altitude][position] = min;
        }
        return table[altitude][position];
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }
}

/*
 * the distance is a multiple of 100
for each 100 miles of flight you can...
maybe we can straight divide distance/100

transitions?
- climb 1, cost 60
- hold, cost 30
- sink 1, cost 20

negative wind is head
positive wind is tail

1 tail saves 1 fuel per each 100 miles
1 head costs 1 fuel per each 100 miles
i.e.
+1 wind saves 1 fuel per each 100 miles
-1 wind costs 1 fuel per each 100 miles
i.e.
final cost = cost - wind

min fuel for distance D?

altitude <= 9 at every point of flight
start and end at altitude 0

we have wind at each altitude, for every position where we have to decide.

transitions[i] = [1, 0, -1]
costs[i] = [60, 30, 20]
wind[altitude][distance]

MAX_INT = MAX_VALUE / 2 to protect from overflow

minFuel(altitude, distance) =
  0 if distance == D && altitude == 0
  MAX_INT if distance == D && altitude > 0
  MAX_INT if altitude > 9
  min(wind[altitude][distance] * cost[i] + minFuel(altitude + transition[i], distance + 1)) for i=0 to 2
      minFuel(altitude + 1, distance + 1),
      minFuel(altitude - 1, distance + 1))
 * 
 */