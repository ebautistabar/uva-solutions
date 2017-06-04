package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

// 00988 - Special Graph (Directed Acyclic Graph), Counting paths in DAG, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=929
// Notes below
class UVA00988ManyPathsOneDestination {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int tests = 0;
        String line = getLine();
        while (line != null) {
            int events = parseInt(line);
            List<List<Integer>> adjList = new ArrayList<>(events);
            for (int e = 0; e < events; e++) adjList.add(new ArrayList<Integer>());
            for (int e = 0; e < events; e++) {
                StringTokenizer st = new StringTokenizer(getLine());
                int choices = parseInt(st.nextToken());
                for (int c = 0; c < choices; c++) {
                    int next = parseInt(st.nextToken());
                    adjList.get(e).add(next);
                }
            }

            int totalPaths = 0;
            int[] numPaths = new int[events];
            numPaths[0] = 1;
            for (int node : getToposort(adjList)) {
                if (adjList.get(node).isEmpty()) { // death
                    totalPaths += numPaths[node];
                } else {
                    for (int next : adjList.get(node)) {
                        numPaths[next] += numPaths[node];
                    }
                }
            }
            if (tests++ > 0) out.println();
            out.println(totalPaths);
            line = getLine();
        }
        out.close();
    }

    private static Deque<Integer> getToposort(List<List<Integer>> adjList) {
        Deque<Integer> reversePostOrder = new ArrayDeque<>(adjList.size());
        boolean[] visited = new boolean[adjList.size()];
        for (int i = 0; i < adjList.size(); i++) {
            if (!visited[i]) {
                dfs(i, adjList, visited, reversePostOrder);
            }
        }
        return reversePostOrder;
    }

    private static void dfs(int current, List<List<Integer>> adjList, boolean[] visited, Deque<Integer> reversePostOrder) {
        visited[current] = true;
        for (int next : adjList.get(current)) {
            if (!visited[next]) {
                dfs(next, adjList, visited, reversePostOrder);
            }
        }
        reversePostOrder.push(current);
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
The graph is a DAG, as we cannot go back in time to revisit choices. We need to count all the possible ways from event
0 (birth) to all the death events (events with no outgoing edges). Because it's a DAG, we can get the topological sort
which gives us an order such that reaching a node u means we've visited all the other nodes that had outgoing edges
pointing to u. In my opinion, the resemblance to a bottom-up DP solution is clearer in this problem. Topological sort
gives us the order in which the DP states must be filled and the edges give us the transitions between states.
*/
