package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

// 10171 - All Pairs Shortest Paths, Standard, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1112
// Floyd Warshall, notes below
class UVA10171MeetingProfMiguel {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int LETTERS = 28;
    private static final int INFINITY = (int) 1e9; // to prevent overflow

    public static void main(String args[]) throws IOException {
        int[][] youngAdj = new int[LETTERS][LETTERS];
        int[][] oldAdj = new int[LETTERS][LETTERS];
        String line = getLine();
        while (line != null) {
            for (int i = 0; i < youngAdj.length; i++) {
                Arrays.fill(youngAdj[i], INFINITY);
                Arrays.fill(oldAdj[i], INFINITY);
                // If they are in the same place, there's no cost
                youngAdj[i][i] = 0;
                oldAdj[i][i] = 0;
            }
            int streets = Integer.parseInt(line.trim());
            if (streets == 0) break;
            for (int i = 0; i < streets; i++) {
                StringTokenizer st = new StringTokenizer(getLine());
                boolean young = st.nextToken().charAt(0) == 'Y';
                boolean bidirectional = st.nextToken().charAt(0) == 'B';
                int from = st.nextToken().charAt(0) - 'A';
                int to = st.nextToken().charAt(0) - 'A';
                // Math.min here to take care of several streets between the same pair of places
                if (young) {
                    youngAdj[from][to] = Math.min(youngAdj[from][to], Integer.parseInt(st.nextToken()));
                    if (bidirectional) {
                        youngAdj[to][from] = Math.min(youngAdj[to][from], youngAdj[from][to]);
                    }
                } else {
                    oldAdj[from][to] = Math.min(oldAdj[from][to], Integer.parseInt(st.nextToken()));
                    if (bidirectional) {
                        oldAdj[to][from] = Math.min(oldAdj[to][from], oldAdj[from][to]);
                    }
                }
            }
            StringTokenizer st = new StringTokenizer(getLine());
            int youngStart = st.nextToken().charAt(0) - 'A';
            int oldStart = st.nextToken().charAt(0) - 'A';

            // Floyd Warshall
            for (int k = 0; k < youngAdj.length; k++) {
                for (int i = 0; i < youngAdj.length; i++) {
                    for (int j = 0; j < youngAdj.length; j++) {
                        youngAdj[i][j] = Math.min(youngAdj[i][j], youngAdj[i][k] + youngAdj[k][j]);
                        oldAdj[i][j] = Math.min(oldAdj[i][j], oldAdj[i][k] + oldAdj[k][j]);
                    }
                }
            }
            int minEnergy = INFINITY;
            List<Integer> meetingPoints = new ArrayList<>();
            for (int place = 0; place < youngAdj.length; place++) {
                int candidate = youngAdj[youngStart][place] + oldAdj[oldStart][place];
                if (candidate == INFINITY) continue;
                if (minEnergy >= candidate) {
                    if (minEnergy > candidate) {
                        minEnergy = candidate;
                        // clear the points from a previous best energy
                        meetingPoints.clear();
                    }
                    meetingPoints.add(place);
                }
            }

            if (meetingPoints.isEmpty()) {
                out.println("You will never meet.");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(minEnergy);
                sb.append(' ');
                for (int point : meetingPoints) {
                    sb.append((char)(point + 'A'));
                    sb.append(' ');
                }
                sb.setLength(sb.length() - 1);
                out.println(sb.toString());
            }

            line = getLine();
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
We have 2 graphs: 1 for each person. The intersection of nodes in both graphs is the set of valid
meeting points. For each person in their own graph, calculate shortest paths to valid
meeting points. Traverse meeting points, sum distances for both people and keep the minimum.

Dijkstra seems more fitting at first glance, because we just want the distance to the meeting
points. But the size of the graph is very small (just 26 nodes), so we can use Floyd Warshall
for practice and ease of implementation. It also makes quite easy searching for the min distance
at the end. Just traverse the 1 row in the final adj. matrix.
*/
