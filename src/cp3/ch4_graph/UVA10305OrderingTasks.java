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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

// 10305 - Topological Sort, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1246
// Direct application of topological sort
class UVA10305OrderingTasks {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null && !line.trim().isEmpty()) {
            StringTokenizer st = new StringTokenizer(line);
            int tasks = Integer.parseInt(st.nextToken());
            int relations = Integer.parseInt(st.nextToken());
            if (tasks == 0 && relations == 0) break;
            List<List<Integer>> adjList = new ArrayList<List<Integer>>();
            for (int i = 0; i < tasks; i++) {
                adjList.add(new ArrayList<Integer>());
            }
            for (int i = 0; i < relations; i++) {
                st = new StringTokenizer(getLine());
                int before = Integer.parseInt(st.nextToken()) - 1;
                int after = Integer.parseInt(st.nextToken()) - 1;
                adjList.get(before).add(after);
            }

            Deque<Integer> topologicalSort = new ArrayDeque<Integer>();
            Set<Integer> visited = new HashSet<Integer>();
            for (int i = 0; i < tasks; i++) {
                if (!visited.contains(i)) {
                    dfs(i, adjList, visited, topologicalSort);
                }
            }

            StringBuilder sb = new StringBuilder();
            for (int node : topologicalSort) {
                sb.append(node + 1);
                sb.append(' ');
            }
            sb.setLength(sb.length() - 1);
            out.println(sb.toString());
            line = getLine();
        }
        out.close();
    }

    private static void dfs(int node, List<List<Integer>> adjList, Set<Integer> visited,
            Deque<Integer> topologicalSort) {
        visited.add(node);
        List<Integer> neighbors = adjList.get(node);
        for (int i = 0; i < neighbors.size(); i++) {
            int neighbor = neighbors.get(i);
            if (!visited.contains(neighbor)) {
                dfs(neighbor, adjList, visited, topologicalSort);
            }
        }
        topologicalSort.push(node);
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }
}
