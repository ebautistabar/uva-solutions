package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

// 872 - Topological Sort, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=813
class UVA00872Ordering {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int tests = Integer.parseInt(getLine().trim());
        for (int t = 0; t < tests; t++) {
            StringTokenizer st = new StringTokenizer(getLine());
            Map<Character, List<Character>> adjList = new TreeMap<>(); // treemap to visit nodes in order
            while (st.hasMoreTokens()) {
                char name = st.nextToken().charAt(0);
                adjList.put(name, new ArrayList<Character>());
            }
            st = new StringTokenizer(getLine());
            while (st.hasMoreElements()) {
                String constraint = st.nextToken();
                char origin = constraint.charAt(0);
                char destination = constraint.charAt(2);
                adjList.get(origin).add(destination);
            }

            List<Character> topologicalSort = new ArrayList<>();
            Set<Character> visited = new HashSet<>();
            boolean found = dfs(adjList, visited, topologicalSort);
            if (!found) {
                out.println("NO");
            }

            if (t < tests - 1) {
                out.println(); // separate test cases
            }
        }
        out.close();
    }

    private static boolean dfs(Map<Character, List<Character>> adjList, Set<Character> visited,
            List<Character> topologicalSort) {
        if (topologicalSort.size() == adjList.size()) {
            StringBuilder sb = new StringBuilder();
            sb.append(topologicalSort.get(0));
            for (int i = 1; i < topologicalSort.size(); i++) {
                sb.append(' ');
                sb.append(topologicalSort.get(i));
            }
            out.println(sb.toString());
            return true;
        } else {
            boolean found = false;
            for (char vertex : adjList.keySet()) {
                if (!visited.contains(vertex) && respectsContraints(vertex, visited, adjList)) {
                    visited.add(vertex);
                    topologicalSort.add(vertex);
                    // before I used logical or like this: found || dfs
                    // it was shortcircuiting and dfs was not called
                    // we have to either put dfs first or use bitwise or
                    found |= dfs(adjList, visited, topologicalSort);
                    topologicalSort.remove(topologicalSort.size() - 1);
                    visited.remove(vertex);
                }
            }
            return found;
        }
    }

    private static boolean respectsContraints(char vertex, Set<Character> visited,
            Map<Character, List<Character>> adjList) {
        for (int i = 0; i < adjList.get(vertex).size(); i++) {
            char destination = adjList.get(vertex).get(i);
            if (visited.contains(destination)) {
                // for A<B, B was visited before A
                return false;
            }
        }
        return true;
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }
}
/*
Cannot use regular algo for topological sort because:
- when processing 1 node, it only visits nodes in its adj list, e.g. it doesn't
intersperse nodes from different connected components
- it builds the sorted list as the last step of the recursive call, i.e. it
doesn't do another call afterwards so it's not backtracking

The algo must then:
- try all unvisited nodes on each step, not only the ones in the adj list for
the last node
- build the list while going deeper in the call stack, then backtrack and try
again other options

As we try all nodes, we must also check that the ordering constraints hold
(A<B, A<C), i.e. when we pick a node A, all its neighbors (B, C) must be
unvisited for this path to be valid

If we think about it, it's just listing all the permutations where the
constraints hold. Instead of using recursion, we could use next_permutation,
which is faster, and check the constraints over each resulting permutation.
The check is 1 pass over the string per each constraint.
*/