package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

// 10496 - Dynamic Programming, Traveling Salesman Problem (TSP), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1437
class UVA10496CollectingBeepers {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static int[][] dist;
    private static int[][] table;

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(getLine().trim());
        while (tests-- > 0) {
            StringTokenizer st = new StringTokenizer(getLine());
            Integer.parseInt(st.nextToken()); // width, unused
            Integer.parseInt(st.nextToken()); // height, unused
            st = new StringTokenizer(getLine());
            int startX = Integer.parseInt(st.nextToken());
            int startY = Integer.parseInt(st.nextToken());
            int beepers = Integer.parseInt(getLine().trim());
            int[] x = new int[beepers + 1];
            int[] y = new int[beepers + 1];
            x[0] = startX;
            y[0] = startY;
            for (int i = 1; i < x.length; i++) {
                st = new StringTokenizer(getLine());
                x[i] = Integer.parseInt(st.nextToken());
                y[i] = Integer.parseInt(st.nextToken());
            }

            // Prepare memo table
            table = new int[x.length][1 << x.length];
            for (int i = 0; i < table.length; i++) {
                Arrays.fill(table[i], -1);
            }
            // Prepare dist matrix
            dist = new int[x.length][x.length];
            for (int i = 0; i < dist.length; i++) {
                for (int j = 0; j < i; j++) {
                    dist[i][j] = dist[j][i] = getDistance(x, y, i, j);
                }
            }
            // Calculate total min length from starting position
            int start = 0;
            int visited = 1; // bitset of visited positions
            int length = getTotalLength(start, visited);

            out.printf("The shortest path has length %d%n", length);
        }
        out.close();
    }

    private static int getTotalLength(int current, int visited) {
        if (visited == (1 << dist.length) - 1) { // visited all positions
            return dist[current][0]; // return to starting position
        }
        if (table[current][visited] == -1) {
            int min = Integer.MAX_VALUE;
            for (int next = 1; next < dist.length; next++) { // don't check 0, it's always visited
                if ((visited & (1 << next)) == 0) {
                    int remainingLength = getTotalLength(next, visited | (1 << next));
                    min = Math.min(min, dist[current][next] + remainingLength);
                }
            }
            table[current][visited] = min;
        }
        return table[current][visited];
    }

    private static int getDistance(int[] x, int[] y, int i, int j) {
        // Manhattan distance
        return Math.abs(x[i] - x[j]) + Math.abs(y[i] - y[j]);
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }
}
