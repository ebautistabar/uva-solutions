package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

// 821 - All Pairs Shortest Paths, Standard, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=762
// Floyd Warshall, notes below
class UVA00821PageHopping {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int MAX_NODES = 100;
    private static final int INFINITY = (int) 1e9; // to avoid overflow

    public static void main(String args[]) throws IOException {
        Set<Integer> seen = new HashSet<>();
        int[][] adjMatrix = new int[MAX_NODES][MAX_NODES];
        initialize(seen, adjMatrix);
        boolean finishedTest = true;
        int test = 1;
        StringTokenizer st = new StringTokenizer(getLine());
        while (true) {
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            if (from == 0 && to == 0) {
                if (finishedTest) {
                    break;
                } else {
                    // Floyd Warshall
                    for (int k = 0; k < MAX_NODES; k++) {
                        for (int i = 0; i < MAX_NODES; i++) {
                            for (int j = 0; j < MAX_NODES; j++) {
                                adjMatrix[i][j] = Math.min(adjMatrix[i][j], adjMatrix[i][k] + adjMatrix[k][j]);
                            }
                        }
                    }
                    // Calculate avg
                    int sum = 0;
                    int pairs = 0;
                    for (int a : seen) {
                        for (int b : seen) {
                            if (a == b) continue;
                            //out.println(a +","+b+":"+adjMatrix[a][b]);
                            sum += adjMatrix[a][b];
                            pairs++;
                        }
                    }
                    double avg = sum / (double) pairs;
                    out.printf("Case %d: average length between pages = %.3f clicks%n", test, avg);
                    // Mark end of this test and initialize variables for the next one
                    finishedTest = true;
                    test++;
                    initialize(seen, adjMatrix);
                    if (!st.hasMoreTokens()) {
                        st = new StringTokenizer(getLine());
                    }
                }
            } else {
                finishedTest = false;
                from--;
                to--;
                adjMatrix[from][to] = 1;
                seen.add(from);
                seen.add(to);
            }
        }
        out.close();
    }

    private static void initialize(Set<Integer> seen, int[][] adjMatrix) {
        seen.clear();
        for (int i = 0; i < adjMatrix.length; i++) {
            Arrays.fill(adjMatrix[i], INFINITY);
        }
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
All pairs shortest paths in unweighted graph. We could use BFS but the graph is small (100 nodes)
and it is an all pairs problem. Easier to use Floyd Warshall. Afterwards, traverse matrix adding
all distances and calculate avg.
*/
