package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

// 10507 - Union-Find Disjoint Sets, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1448
class UVA10507WakingUpBrain {

    private static final int AWAKEN_FROM_START = 3;
    private static final int MAX_AREAS = 26;
    private static final int READY = 1;
    private static final int AWAKE = 2;
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    // I don't use union find because I don't think it fits the problem.
    // Using adjacency lists and a set of awaken areas instead
    public static void main(String args[]) throws NumberFormatException, IOException {
        int test = 0;
        String line = in.readLine();
        while (line != null) {
            if (test++ > 0)
                line = in.readLine(); // Blank line between test cases, so read another
            int asleep = Integer.parseInt(line.trim()) - AWAKEN_FROM_START;
            int connections = Integer.parseInt(in.readLine().trim());
            int[] state = new int[MAX_AREAS];
            String originalWokeAreas = in.readLine().trim();
            for (int i = 0; i < originalWokeAreas.length(); i++)
                state[areaToId(originalWokeAreas.charAt(i))] = AWAKE;
            List<List<Integer>> brain = buildBrainNetwork(connections);
            int years = 0;
            while (asleep > 0) {
                for (int i = 0; i < state.length; i++) {
                    if (state[i] != AWAKE) {
                        int awakenNeighbors = 0;
                        for (int neighbor : brain.get(i)) {
                            if (state[neighbor] == AWAKE)
                                awakenNeighbors++;
                        }
                        if (awakenNeighbors >= 3)
                            state[i] = READY;
                    }
                }
                int prevAsleep = asleep;
                for (int i = 0; i < state.length; i++) {
                    if (state[i] == READY) {
                        state[i] = AWAKE;
                        asleep--;
                    }
                }
                // If there are no areas to wake, stop simulation
                if (asleep == prevAsleep)
                    break;
                years++;
            }
            if (asleep > 0)
                out.println("THIS BRAIN NEVER WAKES UP");
            else
                out.printf("WAKE UP IN, %s, YEARS\n", years);
            line = in.readLine();
        }
        out.flush();
    }

    private static List<List<Integer>> buildBrainNetwork(int connections) throws IOException {
        List<List<Integer>> adjList = new ArrayList<List<Integer>>();
        for (int i = 0; i < MAX_AREAS; i++)
            adjList.add(new ArrayList<Integer>());
        for (int i = 0; i < connections; i++) {
            String connection = in.readLine().trim();
            int area1 = areaToId(connection.charAt(0));
            int area2 = areaToId(connection.charAt(1));
            adjList.get(area1).add(area2);
            adjList.get(area2).add(area1);
        }
        return adjList;
    }

    private static int areaToId(char area) {
        return area - 'A';
    }

}
