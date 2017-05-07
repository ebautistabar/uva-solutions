package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

// 11284 - Dynamic Programming, Traveling Salesman Problem (TSP), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2259
class UVA11284ShoppingTrip {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static int[][] cost;
    private static int[] savings;
    private static int[][] table;
    private static final int INFINITY = Integer.MAX_VALUE / 2;
    
    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(getLine().trim());
        while (tests-- > 0) {
            StringTokenizer st = new StringTokenizer(getLine());
            int stores = Integer.parseInt(st.nextToken());
            int roads = Integer.parseInt(st.nextToken());
            int[][] gas = new int[stores + 1][stores + 1]; // +1 to account for the house
            for (int i = 0; i < gas.length; i++) {
                Arrays.fill(gas[i], INFINITY); // we begin with no roads
                gas[i][i] = 0; // no cost between a position and itself
            }
            for (int i = 0; i < roads; i++) {
                st = new StringTokenizer(getLine());
                int store1 = Integer.parseInt(st.nextToken());
                int store2 = Integer.parseInt(st.nextToken());
                // Input may contain more than one routes for the same pair,
                // so keep the cheapest
                gas[store1][store2] = gas[store2][store1] = Math.min(gas[store1][store2], parseDoubleAsInteger(st.nextToken()));
            }
            int dvds = Integer.parseInt(getLine().trim());
            int[] sellers = new int[dvds + 1]; // +1 so indexing is easier later
            savings = new int[dvds + 1];
            for (int i = 1; i <= dvds; i++) {
                st = new StringTokenizer(getLine());
                sellers[i] = Integer.parseInt(st.nextToken());
                savings[i] = parseDoubleAsInteger(st.nextToken());
            }

            // The input gives us roads between some stores only. We need a
            // path between every pair of positions, and we need the shortest.
            // Application of Floyd Warshall (all pairs shortest path)
            for (int k = 0; k < gas.length; k++) {
                for (int i = 0; i < gas.length; i++) {
                    for (int j = 0; j < gas.length; j++) {
                        gas[i][j] = Math.min(gas[i][j], gas[i][k] + gas[k][j]);
                    }
                }
            }

            // Now we have shortest paths between every position, but not all
            // positions have dvds. We are only interested in stores that have
            // dvds, so the matrix of store distances must be transformed into
            // a matrix of dvd distances, i.e. shortest distances between each
            // pair of dvds (and between the house and every dvd). If a store
            // has no dvd, it doesn't exist for us from now on
            cost = new int[dvds + 1][dvds + 1]; // +1 for the house
            for (int i = 1; i <= dvds; i++) {
                // gas from the house to every dvd
                cost[0][i] = cost[i][0] = gas[0][sellers[i]];
            }
            for (int i = 1; i <= dvds; i++) {
                for (int j = 1; j <= dvds; j++) {
                    // gas to drive between each pair of dvds
                    cost[i][j] = cost[j][i] = gas[sellers[i]][sellers[j]];
                }
            }

            // Travelling salesman problem
            table = new int[cost.length][1 << cost.length];
            for (int i = 0; i < table.length; i++) {
                Arrays.fill(table[i], -INFINITY);
            }
            int startPosition = 0;
            int visited = 1; // bitset, we start on the house
            int totalSaved = getTotalSaved(startPosition, visited);

            if (Double.compare(totalSaved, 0.0) > 0) {
                out.printf(Locale.US, "Daniel can save $%d.%02d%n", totalSaved / 100, totalSaved % 100);
            } else {
                out.println("Don't leave the house");
            }
        }
        out.close();
    }

    private static int parseDoubleAsInteger(String string) {
        String[] saving = string.split("\\.");
        int result = Integer.parseInt(saving[0]) * 100;
        if (saving.length > 1) {
            result += Integer.parseInt(saving[1]);
        }
        return result;
    }

    private static int getTotalSaved(int current, int visited) {
        if (visited == (1 << cost.length) - 1) { // considered every store
            return -cost[current][0]; // go back home
        }
        if (table[current][visited] == -INFINITY) {
            int totalSaved = -INFINITY;
            for (int next = 1; next < cost.length; next++) {
                if ((visited & (1 << next)) == 0) { // not considered yet
                    int savedByIgnoringStore = getTotalSaved(current, visited | (1 << next));
                    int savedByBuyingInStore = savings[next] - cost[current][next] + getTotalSaved(next, visited | (1 << next));
                    totalSaved = Math.max(totalSaved, savedByIgnoringStore);
                    totalSaved = Math.max(totalSaved, savedByBuyingInStore);
                }
            }
            table[current][visited] = totalSaved;
        }
        return table[current][visited];
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }
}
