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

// 11838 - Finding Strongly Connected Components, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2938
// Direct application of strongly connected components: Kosaraju-Sharir
class UVA11838ComeAndGo {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int intersections = Integer.parseInt(st.nextToken());
            int streets = Integer.parseInt(st.nextToken());
            if (intersections == 0 && streets == 0) break;
            List<List<Integer>> adjList = new ArrayList<List<Integer>>();
            for (int i = 0; i < intersections; i++) adjList.add(new ArrayList<Integer>());
            for (int i = 0; i < streets; i++) {
                st = new StringTokenizer(getLine());
                int origin = Integer.parseInt(st.nextToken()) - 1;
                int destination = Integer.parseInt(st.nextToken()) - 1;
                boolean isTwoWay = Integer.parseInt(st.nextToken()) == 2;
                adjList.get(origin).add(destination);
                if (isTwoWay) {
                    adjList.get(destination).add(origin);
                }
            }

            List<List<Integer>> reverseAdjList = getReverseGraph(adjList);
            Deque<Integer> topologicalSort = getTopologicalSort(reverseAdjList);
            Set<Integer> visited = new HashSet<Integer>();
            int stronglyConnectedComponents = 0;
            while (!topologicalSort.isEmpty()) {
                int intersection = topologicalSort.pop();
                if (!visited.contains(intersection)) {
                    // don't care about the topological sort here: pass new object
                    dfs(intersection, visited, adjList, new ArrayDeque<Integer>());
                    stronglyConnectedComponents++;
                }
            }

            if (stronglyConnectedComponents == 1) {
                out.println(1);
            } else {
                out.println(0);
            }
            line = getLine();
        }
        out.close();
    }

    private static Deque<Integer> getTopologicalSort(
            List<List<Integer>> adjList) {
        Deque<Integer> topologicalSort = new ArrayDeque<Integer>();
        Set<Integer> visited = new HashSet<Integer>();
        for (int vertex = 0; vertex < adjList.size(); vertex++) {
            if (!visited.contains(vertex)) {
                dfs(vertex, visited, adjList, topologicalSort);
            }
        }
        return topologicalSort;
    }

    private static List<List<Integer>> getReverseGraph(List<List<Integer>> adjList) {
        List<List<Integer>> reverseGraph = new ArrayList<List<Integer>>();
        for (int i = 0; i < adjList.size(); i++) reverseGraph.add(new ArrayList<Integer>());
        for (int i = 0; i < adjList.size(); i++) {
            for (int j = 0; j < adjList.get(i).size(); j++) {
                int origin = i;
                int destination = adjList.get(i).get(j);
                reverseGraph.get(destination).add(origin);
            }
        }
        return reverseGraph;
    }

    private static void dfs(int node, Set<Integer> visited, List<List<Integer>> adjList, Deque<Integer> topologicalSort) {
        visited.add(node);
        List<Integer> neighbors = adjList.get(node);
        for (int i = 0; i < neighbors.size(); i++) {
            int neighbor = neighbors.get(i);
            if (!visited.contains(neighbor)) {
                dfs(neighbor, visited, adjList, topologicalSort);
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
