package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

// 00115 - Special Graph (Others), Trees, not starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=51
// Lowest Common Ancestor
class UVA00115ClimbingTrees {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        Set<String> haveParents = new HashSet<>();
        Set<String> roots = new HashSet<>();
        Map<String, Set<String>> adjList = new HashMap<>();
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            String child = st.nextToken();
            if ("no.child".equals(child)) {
                break;
            }
            String parent = st.nextToken();
            Set<String> children = adjList.get(parent);
            if (children == null) {
                children = new HashSet<String>();
                adjList.put(parent, children);
            }
            children.add(child);
            if (adjList.get(child) == null) {
                adjList.put(child, new HashSet<String>());
            }
            roots.remove(child);
            haveParents.add(child);
            if (!haveParents.contains(parent)) {
                roots.add(parent);
            }
            line = getLine();
        }

        line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            String node1 = st.nextToken();
            String node2 = st.nextToken();
            LCA lca = null;
            for (String root : roots) { // There are several trees to traverse
                lca = getLCA(root, node1, node2, adjList);
                if (lca != null) {
                    break;
                }
            }
            if (lca == null || !lca.complete) {
                out.println("no relation");
            } else if (lca.node.equals(node1) || lca.node.equals(node2)) {
                printDirectDescendant(lca, node1, node2);
            } else if (lca.distance1 == 0 & lca.distance2 == 0) {
                out.println("sibling");
            } else {
                int min = Math.min(lca.distance1, lca.distance2);
                int removed = Math.abs(lca.distance1 - lca.distance2);
                if (removed == 0) {
                    out.printf("%d cousin%n", min);
                } else {
                    out.printf("%d cousin removed %d%n", min, removed);
                }
            }
            line = getLine();
        }
        out.close();
    }

    private static LCA getLCA(String root, String node1, String node2, Map<String, Set<String>> adjList) {
        if (!adjList.containsKey(node1) || !adjList.containsKey(node2) || node1.equals(node2)) {
            return null;
        }
        if (root.equals(node1) || root.equals(node2)) {
            // Found one of the nodes. The other could be a descendant or could be in another separate part of the
            // tree. We don't know, so let's do DFS.
            String target = root.equals(node1) ? node2 : node1;
            int distance = getDistance(root, target, adjList);
            return new LCA(root, 0, distance, distance != -1);
        }
        LCA lca = null;
        for (String child : adjList.get(root)) {
            LCA lca2 = getLCA(child, node1, node2, adjList);
            if (lca == null) {
                if (lca2 != null) {
                    lca = lca2;
                }
            } else {
                if (lca2 != null) {
                    // We are sure that this is the LCA, because the 2 targets are among its descendants, so mark the
                    // LCA structure as complete.
                    return new LCA(root, lca.distance1, lca2.distance1, LCA.COMPLETE);
                }
            }
        }
        if (lca != null && !lca.complete) {
            // Increase the distance, so that we know how many levels of depth separate the found target and any
            // possible LCA that we find above it.
            lca.distance1 += 1;
        }
        return lca;
    }

    private static void printDirectDescendant(LCA lca, String node1, String node2) {
        int distance = lca.distance2;
        StringBuilder sb = new StringBuilder();
        for (int d = distance; d > 1; d--) {
            sb.append("great ");
        }
        if (distance > 0) {
            sb.append("grand ");
        }
        if (lca.node.equals(node1)) {
            sb.append("parent");
        } else {
            sb.append("child");
        }
        out.println(sb);
    }

    private static int getDistance(String current, String goal, Map<String, Set<String>> adjList) {
        for (String child : adjList.get(current)) {
            if (child.equals(goal)) {
                return 0;
            }
            int distance = getDistance(child, goal, adjList);
            if (distance != -1) {
                return distance + 1;
            }
        }
        return -1;
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


    private static class LCA {
        static final boolean COMPLETE = true;
        String node;
        int distance1;
        int distance2;
        boolean complete;
        public LCA(String node, int distance1, int distance2, boolean complete) {
            this.node = node;
            this.distance1 = distance1;
            this.distance2 = distance2;
            this.complete = complete;
        }
    }
}
/*
The problem actually is finding the Least Common Ancestor of the query pairs, as well as the distance of the nodes to
said ancestor. It can be found in linear time, doing a recursive traversal of the tree.

When we find one of the target nodes we return it. Whenever we receive different nodes from the recursive calls of the
children, we know the current node is an LCA. If we finish the traversal and we only found 1 of the nodes we do DFS to
find the other node and their distance. If it cannot be found, then the other node is not in this tree and there is no
relation. In the end I decided to do the DFS inside the LCA method and use a flag to track whether the LCA structure
represents a true LCA or we just found 1 of the 2 nodes.

Additional considerations, not specified in problem statement:
- Several roots, i.e. several trees.
- Queries like 'a a', i.e. relation of a node with himself. Will be answered with 'no relation' as the statement
doesn't indicate that this falls into one of the other cases.
*/
