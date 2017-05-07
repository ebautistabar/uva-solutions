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

// 216 - Dynamic Programming, Traveling Salesman Problem (TSP), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=152
class UVA00216GettingInLine {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static double[][] dist;
    private static double[][] table;
    private static int[][] next;

    /*
     * calculate distances between all pairs of computers
store them in matrix
add fake source/sink computer with distance 0 to all other computers
do travelling salesman problem starting in fake computer
do exhaustive search
can use a bitset to keep track of used computers
dp is not required because the max computers are small, but use it to practice
     */
    public static void main(String args[]) throws NumberFormatException, IOException {
        int test = 1;
        int computers = Integer.parseInt(getLine().trim());
        while (computers != 0) {
            int[] x = new int[computers];
            int[] y = new int[computers];
            for (int c = 0; c < computers; c++) {
                StringTokenizer st = new StringTokenizer(getLine());
                x[c] = Integer.parseInt(st.nextToken());
                y[c] = Integer.parseInt(st.nextToken());
            }

            // Prepare distance matrix
            dist = new double[computers + 1][computers + 1];
            for (int i = 1; i < dist.length; i++) {
                for (int j = 1; j < dist.length; j++) {
                    dist[i][j] = getDistance(x, y, i - 1, j - 1);
                }
            }
            // Prepare memo tables
            next = new int[computers + 1][1 << dist.length];
            table = new double[computers + 1][1 << dist.length];
            for (int i = 0; i < table.length; i++) {
                Arrays.fill(table[i], -1.0);
            }
            // Calculate cheapest Hamiltonian tour
            int startingComputer = 0;
            int connected = 1; // bitset is 1 because computer 0 is starting point
            double total = getTotalLength(startingComputer, connected);
            int[] chain = getChain();

            out.println("**********************************************************");
            out.printf("Network #%d%n", test++);
            for (int j = 0; j < chain.length - 1; j++) {
                int i = chain[j] - 1; // chain contains 1-based indices
                int k = chain[j + 1] - 1;
                out.printf(Locale.US, "Cable requirement to connect (%d,%d) to (%d,%d) is %.2f feet.%n", x[i], y[i], x[k], y[k], dist[i + 1][k + 1]);
            }
            out.printf(Locale.US, "Number of feet of cable required is %.2f.%n", total);
            computers = Integer.parseInt(getLine().trim());
        }
        out.close();
    }

    private static double getTotalLength(int current, int connected) {
        if (connected == (1 << dist.length) - 1) { // dist.length bits set to 1
            return dist[current][0]; // close the network going to sink for free
        }
        if (Double.compare(table[current][connected], -1) == 0) { // unseen state
            double min = Double.MAX_VALUE;
            int best = 0;
            for (int next = 1; next < dist.length; next++) { // don't bother checking 0, as we're sure it's connected
                if ((connected & (1 << next)) == 0) { // if not connected
                    int newConnected = connected | (1 << next);
                    double remainingLength = getTotalLength(next, newConnected);
                    if (min > dist[current][next] + remainingLength) {
                        min = dist[current][next] + remainingLength;
                        best = next;
                    }
                }
            }
            table[current][connected] = min;
            next[current][connected] = best;
        }
        return table[current][connected];
    }

    private static int[] getChain() {
        int[] chain = new int[next.length - 1];
        int start = 0;
        int connected = 1;
        chain[0] = next[start][connected];
        for (int i = 1; i < chain.length; i++) {
            connected |= (1 << chain[i - 1]);
            chain[i] = next[chain[i - 1]][connected];
        }
        return chain;
    }

    private static double getDistance(int[] x, int[] y, int i, int j) {
        int horizontal = x[i] - x[j];
        int vertical = y[i] - y[j];
        return Math.sqrt(horizontal * horizontal + vertical * vertical) + 16;
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }
}
