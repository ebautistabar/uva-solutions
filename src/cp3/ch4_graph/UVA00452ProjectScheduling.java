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

// 00452 - Special Graph (Directed Acyclic Graph), Single-Source Shortest/Longest Paths on DAG, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=393
// Longest path in a DAG, notes below
class UVA00452ProjectScheduling {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int MAX_LETTERS = 28;

    public static void main(String args[]) throws IOException {
        int tests = parseInt(getLine());
        while (tests-- > 0) {
            int maxTime = 0;
            List<List<Integer>> adjList = new ArrayList<>();
            for (int i = 0; i < MAX_LETTERS; i++) adjList.add(new ArrayList<Integer>());
            int[] time = new int[MAX_LETTERS];
            String line = getLine();
            while (line != null && !line.isEmpty()) {
                StringTokenizer st = new StringTokenizer(line);
                int id = st.nextToken().charAt(0) - 'A';
                time[id] = parseInt(st.nextToken());
                maxTime = Math.max(maxTime, time[id]);
                if (st.hasMoreTokens()) {
                    String previousTasks = st.nextToken();
                    for (int j = 0; j < previousTasks.length(); j++) {
                        int previous = previousTasks.charAt(j) - 'A';
                        adjList.get(previous).add(id);
                    }
                }
                line = in.readLine();
            }

            Deque<Integer> toposort = getTopologicalSort(adjList);
            int[] timeUpToTask = new int[MAX_LETTERS];
            System.arraycopy(time, 0, timeUpToTask, 0, MAX_LETTERS);
            for (int node : toposort) {
                for (int next : adjList.get(node)) {
                    if (timeUpToTask[next] < timeUpToTask[node] + time[next]) {
                        timeUpToTask[next] = timeUpToTask[node] + time[next];
                        if (maxTime < timeUpToTask[next]) {
                            maxTime = timeUpToTask[next];
                        }
                    }
                }
            }

            out.println(maxTime);
            if (tests != 0) {
                out.println();
            }
        }
        out.close();
    }

    private static Deque<Integer> getTopologicalSort(List<List<Integer>> adjList) {
        Deque<Integer> toposort = new ArrayDeque<>();
        boolean[] visited = new boolean[MAX_LETTERS];
        for (int i = 0; i < MAX_LETTERS; i++) {
            if (!visited[i]) {
                dfs(adjList, i, visited, toposort);
            }
        }
        return toposort;
    }

    private static void dfs(List<List<Integer>> adjList, int current, boolean[] visited, Deque<Integer> toposort) {
        visited[current] = true;
        for (int next : adjList.get(current)) {
            if (!visited[next]) {
                dfs(adjList, next, visited, toposort);
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
}
/*
In order to have a feasible schedule, it needs to be a DAG. The time needed to complete the project is the longest
path between any 2 vertices, i.e. the critical path. Because it's a DAG we can simply relax all vertices in topological
order to get the shortest/longest path.
*/
