package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

// 01056 - All Pairs Shortest Paths, Variants, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3497
// Floyd Warshall, notes below
class UVA01056DegreesOfSeparation {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int INFINITY = (int) 1e9;

    public static void main(String args[]) throws IOException {
        int test = 1;
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int people = parseInt(st.nextToken());
            int relationships = parseInt(st.nextToken());
            if (people == 0 && relationships == 0) break;
            int[][] distance = new int[people][people];
            for (int i = 0; i < people; i++) {
                Arrays.fill(distance[i], INFINITY);
            }
            Map<String, Integer> nameToIdx = new HashMap<>();
            while (relationships > 0) {
                if (!st.hasMoreTokens()) {
                    st = new StringTokenizer(getLine());
                }
                String name1 = st.nextToken();
                if (!st.hasMoreTokens()) {
                    st = new StringTokenizer(getLine());
                }
                String name2 = st.nextToken();
                if (!nameToIdx.containsKey(name1)) {
                    nameToIdx.put(name1, nameToIdx.size());
                }
                if (!nameToIdx.containsKey(name2)) {
                    nameToIdx.put(name2, nameToIdx.size());
                }
                int id1 = nameToIdx.get(name1);
                int id2 = nameToIdx.get(name2);
                distance[id1][id2] = 1;
                distance[id2][id1] = 1;
                relationships--;
            }

            for (int k = 0; k < people; k++) {
                for (int i = 0; i < people; i++) {
                    for (int j = 0; j < people; j++) {
                        distance[i][j] = Math.min(distance[i][j], distance[i][k] + distance[k][j]);
                    }
                }
            }

            int max = -INFINITY;
            for (int i = 0; i < people; i++) {
                for (int j = i + 1; j < people; j++) {
                    max = Math.max(distance[i][j], max);
                }
            }

            if (max == INFINITY) {
                out.printf("Network %d: DISCONNECTED%n%n", test);
            } else {
                out.printf("Network %d: %d%n%n", test, max);
            }

            test++;
            line = getLine();
        }
        out.close();
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
Get all pairs shortest paths with Floyd Warshall. Then get the max distance of all the pairs.
When getting the max at the end, careful with distance from a person to itself. It can be the max distance in some
cases, but we are only interested in distance between different people.
*/
