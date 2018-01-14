package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

// 10805 - Special Graph (Others), Trees, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1746
// notes below
class UVA10805CockroachEscapeNetworks {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int INFINITY = (int) 1e9;

    public static void main(String args[]) throws IOException {
        int tests = parseInt(getLine());
        for (int test = 1; test <= tests; test++) {
            StringTokenizer st = new StringTokenizer(getLine());
            int nests = parseInt(st.nextToken());
            int trails = parseInt(st.nextToken());
            int nodes = nests + trails; // nests + center of each trail
            int[][] adjMatrix = new int[nodes][nodes];
            for (int i = 0; i < nodes; i++) {
                Arrays.fill(adjMatrix[i], INFINITY);
                adjMatrix[i][i] = 0;
            }
            for (int i = 0; i < trails; i++) {
                st = new StringTokenizer(getLine());
                int from = parseInt(st.nextToken());
                int to = parseInt(st.nextToken());
                adjMatrix[from][nests + i] = 1; // edges from extremes of the trail to the midpoint
                adjMatrix[nests + i][from] = 1;
                adjMatrix[to][nests + i] = 1;
                adjMatrix[nests + i][to] = 1;
            }

            // Floyd-Warshall for distance between all pairs of nodes
            for (int k = 0; k < nodes; k++) {
                for (int i = 0; i < nodes; i++) {
                    for (int j = 0; j < nodes; j++) {
                        adjMatrix[i][j] = Math.min(adjMatrix[i][j], adjMatrix[i][k] + adjMatrix[k][j]);
                    }
                }
            }

            int minDiameter = INFINITY;
            // Consider each node as the midpoint of a diameter and
            // find the two farthest nodes: the endpoints of the diameter.
            // The sum of the distances to them is the diameter length.
            // The solution is the diameter with min length.
            for (int i = 0; i < nodes; i++) {
                int first = 0, second = 0;
                for (int j = 0; j < nests; j++) {
                    if (adjMatrix[i][j] >= first) {
                        second = first;
                        first = adjMatrix[i][j];
                    } else if (adjMatrix[i][j] > second) {
                        second = adjMatrix[i][j];
                    }
                }
                if (minDiameter > first + second) {
                    minDiameter = first + second;
                }
            }

            // Divided by 2 because, when we split the edges in 2,
            // we actually multiplied the number of nodes by 2,
            // so the real diameter will be half of this
            out.printf("Case #%d:%n", test);
            out.println(minDiameter / 2);
            out.println();
        }
        out.close();
    }

    private static String getLine() {
        try {
            String line = in.readLine();
            while (line != null && "".equals(line.trim())) // dealing with blank lines
                line = in.readLine();
            return line;
        } catch(Exception e) {
            return null;
        }
    }
    private static int parseInt(String text) {
        return Integer.parseInt(text.trim());
    }
}
/*
Problem asks for a set of edges with minimum size which contains paths between every pair of nodes, i.e. a tree. More
specifically, it asks for the tree with the smallest diameter (in the problem named the minimum emergency response
time). In other words, this is the minimum diameter spanning tree. The problem doesn't ask for the whole tree, but just
its diameter.

To get the minimum diameter naively we could build every possible tree but that's not feasible. Instead we can consider
each node in turn as the center of the tree, or more accurately, the center of its diameter. There are several trees
with different diameters that have the same diameter center. The diameter center is the node in the middle of the
diameter: same distance to both extremes. Picking each node in turn, we get the maximum diameter which has that particular
center.

Then, over all those diameters, pick the minimum.

The last twist in the problem can be noticed by drawing several example trees with a diverse number of nodes and edges.
The issue is we are considering that the midpoint will be equidistant to the extremes of the diameter, but if the length of
the diameter is odd this cannot be: one half will be 1 unit longer than the other half. If you think about it, the midpoint
of a diameter of odd length is not a node, but the middle of an edge. Thus a solution for this is splitting every edge
in 2, by inserting a node in the middle. It's as if we considered the middle point of each edge as a possible center
too. Apart from this, we can proceed with the rest of the algorithm as already explained.
*/
