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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

// 247 - Finding Strongly Connected Components, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=183
// Direct application of strongly connected components: Kosaraju-Sharir
class UVA00247CallingCircles {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int test = 1;
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int people = Integer.parseInt(st.nextToken());
            int calls = Integer.parseInt(st.nextToken());
            if (people == 0 && calls == 0) break;
            Map<String, Integer> nameToIndex = new HashMap<>();
            List<String> indexToName = new ArrayList<>();
            List<List<Integer>> adjList = new ArrayList<>();
            List<List<Integer>> reverseAdjList = new ArrayList<>();
            for (int i = 0; i < people; i++) {
                adjList.add(new ArrayList<Integer>());
                reverseAdjList.add(new ArrayList<Integer>());
            }
            for (int i = 0; i < calls; i++) {
                st = new StringTokenizer(getLine());
                String origin = st.nextToken();
                String destination = st.nextToken();
                if (!nameToIndex.containsKey(origin)) {
                    indexToName.add(origin);
                    nameToIndex.put(origin, indexToName.size() - 1);
                }
                if (!nameToIndex.containsKey(destination)) {
                    indexToName.add(destination);
                    nameToIndex.put(destination, indexToName.size() - 1);
                }
                adjList.get(nameToIndex.get(origin)).add(nameToIndex.get(destination));
                reverseAdjList.get(nameToIndex.get(destination)).add(nameToIndex.get(origin));
            }

            if (test > 1) {
                out.println();
            }
            out.printf("Calling circles for data set %d:%n", test);
            if (calls > 0) {
                Deque<Integer> topologicalSort = getTopoSort(reverseAdjList);
                Set<Integer> visited = new HashSet<>();
                for (int node : topologicalSort) {
                    if (!visited.contains(node)) {
                        Deque<Integer> connected = new ArrayDeque<>();
                        dfs(node, adjList, visited, connected);
                        // Print here to make it easier. As the order of the names
                        // doesn't matter we can leverage the Deque that can be
                        // filled by dfs() to get the nodes inside this component
                        StringBuilder sb = new StringBuilder();
                        sb.append(indexToName.get(connected.pop()));
                        for (int idx : connected) {
                            sb.append(", ");
                            sb.append(indexToName.get(idx));
                        }
                        out.println(sb);
                    }
                }
            }
            test++;

            line = getLine();
        }
        out.close();
    }

    private static Deque<Integer> getTopoSort(List<List<Integer>> adjList) {
        Deque<Integer> toposort = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        for (int i = 0; i < adjList.size(); i++) {
            if (!visited.contains(i)) {
                dfs(i, adjList, visited, toposort);
            }
        }
        return toposort;
    }

    private static void dfs(int node, List<List<Integer>> adjList,
            Set<Integer> visited, Deque<Integer> toposort) {
        visited.add(node);
        for (int neighbor : adjList.get(node)) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, adjList, visited, toposort);
            }
        }
        toposort.push(node);
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }
}
