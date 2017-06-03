package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

// 10350 - Special Graph (Directed Acyclic Graph), Single-Source Shortest/Longest Paths on DAG, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1291
// Shortest path in a DAG, notes below
class UVA10350LiftlessEME {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            String name = line.trim();
            StringTokenizer st = new StringTokenizer(getLine());
            int ceilings = parseInt(st.nextToken());
            int holesPerCeiling = parseInt(st.nextToken());
            int nodes = ceilings * holesPerCeiling;
            List<List<Edge>> adjList = new ArrayList<>(nodes);
            for (int n = 0; n < nodes; n++) adjList.add(new ArrayList<Edge>());
            for (int ceiling = 0; ceiling < ceilings - 1; ceiling++) {
                for (int currentHole = 0; currentHole < holesPerCeiling; currentHole++) {
                    st = new StringTokenizer(getLine());
                    for (int nextHole = 0; nextHole < holesPerCeiling; nextHole++) {
                        int currentId = ceiling * holesPerCeiling + currentHole;
                        int nextId = (ceiling + 1) * holesPerCeiling + nextHole;
                        int time = parseInt(st.nextToken());
                        adjList.get(currentId).add(new Edge(nextId, time));
                    }
                }
            }

            int[] time = new int[nodes];
            Arrays.fill(time, Integer.MAX_VALUE);
            for (int node : getToposort(adjList)) { // iterate nodes in topological order
                if (time[node] == Integer.MAX_VALUE // if unvisited so far, initialize
                        && !adjList.get(node).isEmpty()) { // but only if it has outgoing edges
                    time[node] = 0;
                }
                for (Edge next : adjList.get(node)) { // relax the node
                    if (time[next.node] > time[node] + next.time) {
                        time[next.node] = time[node] + next.time;
                    }
                }
            }

            // Search for the minimum among the times to the last floor
            int minTime = Integer.MAX_VALUE;
            int lastCeiling = ceilings - 1;
            for (int hole = 0; hole < holesPerCeiling; hole++) {
                minTime = Math.min(minTime, time[lastCeiling * holesPerCeiling + hole]);
            }
            minTime += (ceilings - 1) * 2; // 2 minutes to climb each ladder
            out.println(name);
            out.println(minTime);
            line = getLine();
        }
        out.close();
    }

    private static Deque<Integer> getToposort(List<List<Edge>> adjList) {
        Deque<Integer> toposort = new ArrayDeque<>(adjList.size());
        boolean[] visited = new boolean[adjList.size()];
        for (int i = 0; i < adjList.size(); i++) {
            if (!visited[i]) {
                dfs(i, adjList, visited, toposort);
            }
        }
        //out.println(adjList);
        //out.println(toposort);
        return toposort;
    }

    private static void dfs(int current, List<List<Edge>> adjList, boolean[] visited, Deque<Integer> toposort) {
        visited[current] = true;
        for (Edge next : adjList.get(current)) {
            if (!visited[next.node]) {
                dfs(next.node, adjList, visited, toposort);
            }
        }
        toposort.push(current);
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


    private static class Edge {
        int node;
        int time;
        public Edge (int node, int time) {
            this.node = node;
            this.time = time;
        }
        public String toString() {
            return String.format("(%d,%d)", node, time);
        }
    }
}
/*
The way the input is described is a bit confusing. The first m lines represent the times needed to go from the holes
in the first floor to the ladders, the second m lines represent the times needed to go from the holes in the second
floor to the ladders, etc. Climbing each ladder has a constant cost. We ignore the cost of the ladder in the graph,
as it can be added at the end. So we only count the time spent walking from holes to ladders, or expressing it in a
different way, the time going from holes to the holes of the next floor without taking into account the ladders (as
if we could teleport once we are behind the next floor's hole). In the case of the first floor, imagine there are no
holes per se, but rather starting points.
So to clarify, when reading the times from floor k to floor k+1:
- the first line of the group corresponds to the first hole in floor k and the numbers are the times to reach the
holes of floor k+1
- the second line of the group corresponds to the second hole in floor k and the numbers are the times to reach the
holes floor k+1
- etc.
*/
