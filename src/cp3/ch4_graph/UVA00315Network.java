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

// 315 - Finding Articulation Points/Bridges, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=251
// Direct application of technique explained in book
class UVA00315Network {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int UNVISITED = -1;

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            int places = Integer.parseInt(line.trim());
            if (places == 0) break;
            List<List<Integer>> adjList = new ArrayList<List<Integer>>();
            for (int i = 0; i < places; i++) adjList.add(new ArrayList<Integer>());
            line = getLine();
            while (!"0".equals(line.trim())) {
                StringTokenizer st = new StringTokenizer(line);
                int origin = Integer.parseInt(st.nextToken()) - 1;
                while (st.hasMoreTokens()) {
                    int destination = Integer.parseInt(st.nextToken()) - 1;
                    adjList.get(origin).add(destination);
                    adjList.get(destination).add(origin);
                }
                line = getLine();
            }

            int critical = getCriticalPlaces(adjList);

            out.println(critical);
            line = getLine();
        }
        out.close();
    }

    private static int getCriticalPlaces(List<List<Integer>> adjList) {
        int[] dfsNum = new int[adjList.size()];
        Arrays.fill(dfsNum, UNVISITED);
        int[] dfsLow = new int[adjList.size()];
        int[] parent = new int[adjList.size()];
        int[] dfsCounter = new int[]{0};
        boolean[] criticalPlaces = new boolean[adjList.size()];
        for (int i = 0; i < adjList.size(); i++) {
            if (dfsNum[i] == UNVISITED) {
                boolean isRoot = true;
                int[] rootChildren = new int[]{0};
                getCriticalPlaces(i, adjList, dfsNum, dfsLow, parent,
                        dfsCounter, rootChildren, isRoot, criticalPlaces);
                criticalPlaces[i] = rootChildren[0] > 1;
            }
        }
        int criticalCounter = 0;
        for (int i = 0; i < criticalPlaces.length; i++) {
            if (criticalPlaces[i]) {
                criticalCounter++;
            }
        }
        return criticalCounter;
    }

    private static void getCriticalPlaces(int node,
            List<List<Integer>> adjList, int[] dfsNum, int[] dfsLow, int[] parent,
            int[] dfsCounter, int[] rootChildren, boolean isRoot, boolean[] criticalPlaces) {
        dfsNum[node] = dfsLow[node] = dfsCounter[0]++;
        List<Integer> neighbors = adjList.get(node);
        for (int i = 0; i < neighbors.size(); i++) {
            int neighbor = neighbors.get(i);
            if (dfsNum[neighbor] == UNVISITED) {
                parent[neighbor] = node;
                if (isRoot) rootChildren[0]++;

                getCriticalPlaces(neighbor, adjList, dfsNum, dfsLow, parent,
                        dfsCounter, rootChildren, false, criticalPlaces);

                if (dfsLow[neighbor] >= dfsNum[node]) {
                    criticalPlaces[node] = true;
                }
                dfsLow[node] = Math.min(dfsLow[node], dfsLow[neighbor]);
            } else if (parent[node] != neighbor) { // visited and not direct edge
                dfsLow[node] = Math.min(dfsLow[node], dfsNum[neighbor]);
            }
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
