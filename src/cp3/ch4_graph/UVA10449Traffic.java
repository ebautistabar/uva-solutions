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

// 10449 - Single Source Shortest Paths (SSSP), On Graph With Negative Weight Cycle: Bellman Ford's, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1390
// Bellman Ford, beware of Infinity, notes below
class UVA10449Traffic {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int INFINITY = Integer.MAX_VALUE;

    public static void main(String args[]) throws IOException {
        int test = 1;
        String line = getLine();
        while (line != null) {
            int junctions = 0;
            int roads = 0;
            int[] busyness = null;
            List<List<Tuple>> adjList = new ArrayList<>();
            try { // In case there's bad input
                StringTokenizer st = new StringTokenizer(line);
                junctions = Integer.parseInt(st.nextToken());
                busyness = new int[junctions];
                for (int i = 0; i < junctions; i++) {
                    busyness[i] = Integer.parseInt(st.nextToken());
                }
                roads = Integer.parseInt(getLine().trim());
                adjList = new ArrayList<>(junctions);
                for (int i = 0; i < junctions; i++) {
                    adjList.add(new ArrayList<Tuple>());
                }
                for (int i = 0; i < roads; i++) {
                    st = new StringTokenizer(getLine());
                    int from = Integer.parseInt(st.nextToken()) - 1; // 1-based
                    int to = Integer.parseInt(st.nextToken()) - 1;
                    adjList.get(from).add(new Tuple(to, busyness[from], busyness[to]));
                }
            } catch (Exception e) {
                break;
            }

            int start = 0;
            // Bellman Ford
            int[] amount = new int[junctions];
            Arrays.fill(amount, (int) INFINITY);
            if (junctions > 0) amount[start] = 0;
            for (int i = 0; i < junctions - 1; i++) {
                for (int u = 0; u < junctions; u++) {
                    for (int j = 0; j < adjList.get(u).size(); j++) {
                        Tuple next = adjList.get(u).get(j);
                        if (amount[u] != INFINITY &&
                                amount[next.junction] > amount[u] + next.cost) {
                            amount[next.junction] = amount[u] + next.cost;
                        }
                    }
                }
            }
            boolean[] reachedFromCycle = new boolean[junctions];
            for (int u = 0; u < junctions; u++) {
                for (int j = 0; j < adjList.get(u).size(); j++) {
                    Tuple next = adjList.get(u).get(j);
                    if (amount[u] != INFINITY &&
                            amount[next.junction] > amount[u] + next.cost) {
                        dfs(next.junction, adjList, reachedFromCycle);
                    }
                }
            }

            out.printf("Set #%d%n", test++);
            int queries = Integer.parseInt(getLine().trim());
            for (int q = 0; q < queries; q++) {
                int goal = Integer.parseInt(getLine().trim()) - 1;
                if (goal < 0 || goal >= junctions || // bad input
                        amount[goal] < 3 || // condition from problem statement
                        amount[goal] == INFINITY || // unreachable
                        reachedFromCycle[goal]) { // can be reached from a negative cycle, so it can be < 3
                    out.println('?');
                } else {
                    out.println(amount[goal]);
                }
            }

            line = getLine();
        }
        out.close();
    }

    private static void dfs(int node, List<List<Tuple>> adjList, boolean[] visited) {
        visited[node] = true;
        for (int i = 0; i < adjList.get(node).size(); i++) {
            int next = adjList.get(node).get(i).junction;
            if (!visited[next]) {
                dfs(next, adjList, visited);
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

    private static class Tuple implements Comparable<Tuple> {
        int junction;
        int cost;
        public Tuple(int junction, int busynessSrc, int busynessDst) {
            this.junction = junction;
            int difference = busynessDst - busynessSrc;
            this.cost = difference * difference * difference;
        }
        public int compareTo(Tuple o) {
            return Integer.compare(cost, o.cost);
        }
    }
}
/*
The weights of the edges can be negative and there can be cycles, so apply Bellman Ford.
Other items of interest:
- the input of the judge is badly formed; this solution passed only after I added a try catch
clause at the top
- the goal can be out of bounds
- the goal can be unreachable
- the goal can be connected to a negative cycle
- when relaxing, it's better to avoid modifying the infinite distances; using a "small" infinity
and treating it like a normal number can lead to wrong results. Infinity is not a regular number
and subtracting/adding something to it should equal Infinity.
*/
