package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

// 00615 - Special Graph (Others), Trees, not starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=556
// notes below
class UVA00615IsItATree {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int test = 1;
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            boolean endOfInput = false;
            boolean tree = true;
            Map<Integer, List<Integer>> adjList = new HashMap<>();
            Set<Integer> roots = new HashSet<>();
            Set<Integer> hasIncoming = new HashSet<>();
            while (!endOfInput && st.hasMoreTokens()) {
                int from = parseInt(st.nextToken());
                int to = parseInt(st.nextToken());
                if (from < 0 && to < 0) {
                    endOfInput = true;
                } else if (from != 0 && to != 0) { // 0 0 signals the end of the test case
                    List<Integer> children = adjList.get(from);
                    if (children == null) {
                        children = new ArrayList<Integer>();
                        adjList.put(from, children);
                    }
                    children.add(to);
                    if (!adjList.containsKey(to)) {
                        adjList.put(to, new ArrayList<Integer>());
                    }
                    if (from == to) {
                        tree = false; // self loops are not allowed
                    }
                    roots.remove(to); // cannot be root because it has at least 1 incoming edge
                    if (hasIncoming.contains(to)) { // if it has more than 1 incoming edge, it's not a tree
                        tree = false;
                    } else {
                        hasIncoming.add(to); // add incoming edge
                    }
                    if (!hasIncoming.contains(from)) { // if it has no incoming edge so far, it could be the root
                        roots.add(from);
                    }
                    if (!st.hasMoreTokens()) {
                        st = new StringTokenizer(getLine());
                    }
                }
            }
            if (endOfInput) {
                break;
            }
            tree = tree && (roots.size() == 1 || adjList.size() == 0); // trees must have 1 root, except empty trees
            if (tree && roots.size() == 1) {
                // it still could be disconnected, so check it
                Set<Integer> visited = new HashSet<>();
                traverseTree(roots.iterator().next(), visited, adjList);
                tree = visited.size() == adjList.size();
            }
            if (tree) {
                out.printf("Case %d is a tree.%n", test);
            } else {
                out.printf("Case %d is not a tree.%n", test);
            }
            test++;
            line = getLine();
        }
        out.close();
    }

    private static void traverseTree(Integer parent, Set<Integer> visited, Map<Integer, List<Integer>> adjList) {
        visited.add(parent);
        for (int child : adjList.get(parent)) {
            if (!visited.contains(child)) {
                traverseTree(child, visited, adjList);
            }
        }
    }

    private static String getLine() {
        try {
            String line = in.readLine();
            while (line != null && "".equals(line.trim())) // dealing with blank lines
                line = in.readLine();
            return line;
        } catch(Exception e) {
            return null;
        }
    }
    private static int parseInt(String text) {
        return Integer.parseInt(text.trim());
    }

}
/*
We must check that either the tree is empty or all these conditions hold:
- only one root (one node with 0 incoming edges)
- the rest of the nodes have exactly 1 incoming edge (i.e. the graph is connected: there's a path from root to all
other nodes)
*/
