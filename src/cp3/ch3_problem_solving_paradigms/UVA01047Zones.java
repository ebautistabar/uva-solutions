package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 1047 - Complete Search, Iterative (Fancy Techniques), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3488
class UVA01047Zones {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int test = 1;
        String line = in.readLine();
        while (line != null && !"0 0".equals(line.trim())) {

            StringTokenizer st = new StringTokenizer(line);
            int planned = Integer.parseInt(st.nextToken()); // <= 20
            int built = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(in.readLine());
            int[] customersServed = new int[planned];
            for (int i = 0; i < customersServed.length; i++)
                customersServed[i] = Integer.parseInt(st.nextToken()); // <= 1M
            int serviceAreas = Integer.parseInt(in.readLine().trim()); // <= 10
            int[] towersInArea = new int[serviceAreas];
            int[] customersInArea = new int[serviceAreas];
            for (int i = 0; i < serviceAreas; i++) {
                st = new StringTokenizer(in.readLine());
                int inArea = Integer.parseInt(st.nextToken()); // > 1
                for (int j = 0; j < inArea; j++) {
                    int towerInCommonArea = Integer.parseInt(st.nextToken()) - 1; // Mine are 0-based
                    towersInArea[i] |= (1 << towerInCommonArea);
                }
                customersInArea[i] = Integer.parseInt(st.nextToken());
            }

            int bestCustomers = -1;
            int[] bestTowers = null;
            int fullSubset = (1 << planned) - 1;
            for (int subset = 1; subset <= fullSubset; subset++) { // all possible subsets
                if (getTowersInSubset(subset, planned) != built)
                    continue;
                int[] towers = extractTowers(subset, built, planned);
                int customers = 0;
                // Sum the full counts of the selected towers
                for (int tower : towers) {
                    customers += customersServed[tower];
                }
                // Remove the customers in common areas covered by more than
                // one of our towers
                for (int i = 0; i < serviceAreas; i++) {
                    int ourTowersInArea = 0;
                    for (int tower : towers) {
                        if ((towersInArea[i] & (1 << tower)) > 0) {
                            ourTowersInArea++;
                        }
                    }
                    if (ourTowersInArea > 1) {
                        customers -= (ourTowersInArea - 1) * customersInArea[i];
                    }
                }
                // Update best choice
                if (bestCustomers < customers || (bestCustomers == customers && isPreferable(towers, bestTowers))) {
                    bestCustomers = customers;
                    bestTowers = towers;
                }
            }
            String formattedTowers = formatTowers(bestTowers);
            out.printf("Case Number  %d%n", test++);
            out.printf("Number of Customers: %d%n", bestCustomers);
            out.printf("Locations recommended: %s%n%n", formattedTowers);
            line = in.readLine();
        }
        out.close();
    }

    private static boolean isPreferable(int[] towers, int[] bestTowers) {
        for (int i = 0; i < towers.length; i++) {
            if (towers[i] < bestTowers[i]) {
                return true;
            }
        }
        return false;
    }

    private static int[] extractTowers(int subset, int built, int planned) {
        int[] towers = new int[built];
        int next = 0;
        for (int tower = 0; tower < planned; tower++) {
            if ((subset & (1 << tower)) > 0) {
                towers[next++] = tower;
            }
        }
        return towers;
    }

    private static int getTowersInSubset(int subset, int planned) {
        int towers = 0;
        for (int tower = 0; tower < planned; tower++)
            if ((subset & (1 << tower)) > 0)
                towers++;
        return towers;
    }

    private static String formatTowers(int[] towers) {
        StringBuilder sb = new StringBuilder();
        for (int tower : towers) {
            sb.append(tower + 1);
            sb.append(' ');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

}
