package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

// 11463 - All Pairs Shortest Paths, Standard, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2458
// Floyd Warshall, notes below
class UVA11463Commandos {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int MAX_BUILDINGS = 100;
    private static final int INFINITY = (int) 1e9;

    public static void main(String args[]) throws IOException {
        int[][] time = new int[MAX_BUILDINGS][MAX_BUILDINGS];
        int tests = Integer.parseInt(getLine().trim());
        for (int t = 1; t <= tests; t++) {
            for (int i = 0; i < time.length; i++) {
                Arrays.fill(time[i], INFINITY);
                time[i][i] = 0;
            }
            int buildings = Integer.parseInt(getLine().trim());
            int roads = Integer.parseInt(getLine().trim());
            for (int r = 0; r < roads; r++) {
                StringTokenizer st = new StringTokenizer(getLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                time[from][to] = 1;
                time[to][from] = 1;
            }
            StringTokenizer st = new StringTokenizer(getLine());
            int start = Integer.parseInt(st.nextToken());
            int goal = Integer.parseInt(st.nextToken());

            for (int k = 0; k < time.length; k++) {
                for (int i = 0; i < time.length; i++) {
                    for (int j = 0; j < time.length; j++) {
                        time[i][j] = Math.min(time[i][j], time[i][k] + time[k][j]);
                    }
                }
            }
            int max = 0;
            for (int i = 0; i < buildings; i++) {
                if (time[start][i] != INFINITY && time[i][goal] != INFINITY)
                    max = Math.max(max, time[start][i] + time[i][goal]);
            }

            out.printf("Case %d: %d%n", t, max);
        }
        out.close();
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
We have infinite soldiers, so we can send 1 to each building at the same time. Then they must
meet at the goal. The soldier with the biggest round trip time is the one who defines the
maximum time of the operation.

We need cheapest path from source to all nodes, and from all nodes to goal (or goal to all nodes,
as the roads are undirected). We could do 2 BFS, or 1 Floyd Warshall for ease of implementation.
Afterwards, traverse each node calculating the sum of its distance to the goal and to the source.
The max sum will be the time.
*/
