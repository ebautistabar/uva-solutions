package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

// 00907 - Special Graph (Directed Acyclic Graph), Converting General Graph to DAG, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=848
// DP, notes below
class UVA00907WinterimBackpackingTrip {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int campsites = parseInt(st.nextToken());
            int nights = parseInt(st.nextToken());
            int[] distance = new int[campsites + 2]; // all the campsites plus start and end of the trail
            for (int i = 1; i < distance.length; i++) {
                // accumulated distance so we can do range sum queries later
                distance[i] = distance[i - 1] + parseInt(getLine());
            }

            int[][] table = new int[distance.length][nights + 2];
            for (int i = 0; i < distance.length; i++) Arrays.fill(table[i], -1);
            int lastCampsite = distance.length - 1;
            int maxDailyDistance = getMaxDailyDistance(lastCampsite, nights + 1, distance, table); // 1 more night to rest at the end
            out.println(maxDailyDistance);

            line = getLine();
        }
        out.close();
    }

    private static int getMaxDailyDistance(int currentSite, int nightsLeft, int[] distance, int[][] table) {
        if (currentSite == 0 && nightsLeft == 0) return distance[0];
        // We have consumed all the nights but not all the campsites. If we are able to consume the nights there's
        // always an option to consume the campsites
        if (nightsLeft == 0) return Integer.MAX_VALUE;
        if (table[currentSite][nightsLeft] == -1) {
            int bestMaxDaily = Integer.MAX_VALUE;
            // Stop at nightsLeft - 1 because sites < nights is not a valid state
            for (int previousSite = currentSite - 1; previousSite >= nightsLeft - 1; previousSite--) {
                int lastDaysDistance = distance[currentSite] - distance[previousSite];
                int dailyDistance = getMaxDailyDistance(previousSite, nightsLeft - 1, distance, table);
                dailyDistance = Math.max(dailyDistance, lastDaysDistance);
                bestMaxDaily = Math.min(bestMaxDaily, dailyDistance);
            }
            table[currentSite][nightsLeft] = bestMaxDaily;
        }
        return table[currentSite][nightsLeft];
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while (line != null && "".equals(line.trim())) // dealing with blank lines
            line = in.readLine();
        return line;
    }
    private static int parseInt(String text) {
        return Integer.parseInt(text.trim());
    }
}
/*
DP problem. solved with top-down approach. The state is the current site we are considering and the nights we have
left to sleep. The function represents the max daily distance that we must walk if overall we must sleep a certain
number of nights and we sleep the last of the nights in the current camp site.

In a given state we try several places for our previous night of sleep, we calculate the max daily distance according
to that, and overall keep the configuration that gives us the best distance, i.e. the shorter.

The recurrence is then:
f(currentSite, nightsLeft) = 0 if currentSite == 0 and nightsLeft == 0
f(currentSite, nightsLeft) = INTMAX if currentSite != 0 and nightsLeft == 0
f(currentSite, nightsLeft) = INTMAX if currentSite < nightsLeft
f(currentSite, nightsLeft) = min of all max(f(currentSite - i, nightsLeft - 1), distance(currentSite - i, currentSite)) for i from 1 to currentSite
*/
