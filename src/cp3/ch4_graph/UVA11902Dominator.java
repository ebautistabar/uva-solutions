package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

// 11902 - Just Graph Traversal, not starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3053
// Notes at the bottom
class UVA11902Dominator {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    private static final String lineSeparator = System.getProperty("line.separator");

    private static final int DFS = 0;
    private static final int BFS = 1;
    private static final int ADJMATRIX = 2;
    private static final int ADJLIST = 3;
    private static final int EDGELIST = 4;

    private static int traversal = BFS;
    private static int dataStructure = ADJLIST;

    private static boolean[][] adjMatrix;
    private static List<List<Integer>> edgeList;
    private static List<List<Integer>> adjList;

    public static void main(String args[]) throws Exception {
        int tests = Integer.parseInt(getLine().trim());
        for (int t = 1; t <= tests; t++) {
            int nodes = Integer.parseInt(getLine().trim());
            boolean[][] dominators = new boolean[nodes][nodes];
            if (dataStructure == ADJMATRIX) adjMatrix = buildAdjMatrix(nodes);
            if (dataStructure == EDGELIST) edgeList = buildEdgeList(nodes);
            if (dataStructure == ADJLIST) adjList = buildAdjList(nodes);
            int start = 0;
            int filter = -1;
            Set<Integer> originalVisited = getVisitedFrom(start, filter);
            for (int i = 0; i < nodes; i++) {
                filter = i;
                Set<Integer> visited = getVisitedFrom(start, filter);
                for (int node : originalVisited) {
                    if (!visited.contains(node)) {
                        dominators[i][node] = true;
                    }
                }
            }
            print(t, nodes, dominators);
        }
        out.close();
    }

    private static Set<Integer> getVisitedFrom(int start, int filter) {
        Set<Integer> visited = new HashSet<Integer>();
        if (traversal == DFS) {
            if (dataStructure == ADJMATRIX) dfsAdjMatrix(start, filter, visited);
            if (dataStructure == EDGELIST) dfsEdgeList(start, filter, visited);
            if (dataStructure == ADJLIST) dfsAdjList(start, filter, visited);
        } else if (traversal == BFS) {
            if (dataStructure == ADJMATRIX) bfsAdjMatrix(start, filter, visited);
            if (dataStructure == EDGELIST) bfsEdgeList(start, filter, visited);
            if (dataStructure == ADJLIST) bfsAdjList(start, filter, visited);
        }
        return visited;
    }

    private static void bfsAdjList(int start, int filter, Set<Integer> visited) {
       if (filter != start) {
           visited.add(start);
           Queue<Integer> queue = new ArrayDeque<Integer>();
           queue.add(start);
           while (!queue.isEmpty()) {
               int current = queue.remove();
               List<Integer> destinations = adjList.get(current);
               for (int i = 0; i < destinations.size(); i++) {
                   int destination = destinations.get(i);
                   if (!visited.contains(destination) && destination != filter) {
                       visited.add(destination);
                       queue.add(destination);
                   }
               }
           }
       }
    }

    private static void dfsAdjList(int node, int filter, Set<Integer> visited) {
        if (filter != node) {
            visited.add(node);
            for (int i = 0; i < adjList.get(node).size(); i++) {
                int destination = adjList.get(node).get(i);
                if (!visited.contains(destination) && destination != filter) {
                    dfsAdjList(destination, filter, visited);
                }
            }
        }
    }

    private static void bfsEdgeList(int start, int filter, Set<Integer> visited) {
        if (filter != start) {
            visited.add(start);
            Queue<Integer> queue = new ArrayDeque<Integer>();
            queue.add(start);
            while (!queue.isEmpty()) {
                int current = queue.remove();
                int idx = binarySearch(edgeList, current);
                if (idx != -1) { // found edge with origin == current
                    while (idx < edgeList.size()
                            && edgeList.get(idx).get(0) == current) { // traverse edges with origin == current
                        int destination = edgeList.get(idx).get(1);
                        if (!visited.contains(destination) && destination != filter) {
                            visited.add(destination);
                            queue.add(destination);
                        }
                        idx++;
                    }
                }
            }
        }
    }

    private static void dfsEdgeList(int node, int filter, Set<Integer> visited) {
        if (filter != node) {
            visited.add(node);
            int idx = binarySearch(edgeList, node);
            if (idx != -1) { // found edge with origin == node
                while (idx < edgeList.size()
                        && edgeList.get(idx).get(0) == node) { // traverse edges with origin == node
                    int destination = edgeList.get(idx).get(1);
                    if (!visited.contains(destination)) {
                        dfsEdgeList(destination, filter, visited);
                    }
                    idx++;
                }
            }
        }
    }

    private static void bfsAdjMatrix(int start, int filter, Set<Integer> visited) {
        if (filter != start) {
            visited.add(start);
            Queue<Integer> queue = new ArrayDeque<Integer>();
            queue.add(start);
            while (!queue.isEmpty()) {
                int current = queue.remove();
                for (int i = 0; i < adjMatrix[current].length; i++) {
                    if (!visited.contains(i) && adjMatrix[current][i] && i != filter) {
                        visited.add(i);
                        queue.add(i);
                    }
                }
            }
        }
    }

    private static void dfsAdjMatrix(int node, int filter, Set<Integer> visited) {
        if (filter != node) {
            visited.add(node);
            for (int i = 0; i < adjMatrix.length; i++) {
                if (adjMatrix[node][i] && !visited.contains(i)) {
                    dfsAdjMatrix(i, filter, visited);
                }
            }
        }
    }

    private static boolean[][] buildAdjMatrix(int nodes) throws IOException {
        boolean[][] adjMatrix = new boolean[nodes][nodes];
        for (int i = 0; i < nodes; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            for (int j = 0; j < nodes; j++) {
                adjMatrix[i][j] = "1".equals(st.nextToken());
            }
        }
        return adjMatrix;
    }

    private static List<List<Integer>> buildAdjList(int nodes) throws IOException {
        List<List<Integer>> adjList = new ArrayList<List<Integer>>();
        for (int i = 0; i < nodes; i++) {
            StringTokenizer st = new StringTokenizer(getLine());
            List<Integer> destinations = new ArrayList<Integer>();
            adjList.add(destinations);
            for (int j = 0; j < nodes; j++) {
                if (Integer.parseInt(st.nextToken()) == 1) {
                    destinations.add(j);
                }
            }
        }
        return adjList;
    }

    private static List<List<Integer>> buildEdgeList(int nodes) throws IOException {
        List<List<Integer>> edgeList = new ArrayList<List<Integer>>();
        for (int i = 0; i < nodes; i++) {
            StringTokenizer st = new StringTokenizer(getLine());
            for (int j = 0; j < nodes; j++) {
                if (Integer.parseInt(st.nextToken()) == 1) {
                    List<Integer> edge = new ArrayList<Integer>();
                    edge.add(i);
                    edge.add(j);
                    edgeList.add(edge);
                }
            }
        }
        Collections.sort(edgeList, new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> arg0, List<Integer> arg1) {
                return Integer.compare(arg0.get(0), arg1.get(0));
            }
        });
        return edgeList;
    }

    // Returns index of first item with value == target. Given array is sorted
    // Can't use Java's binarySearch as it can return any item with value == target,
    // not necessarily the first one
    private static int binarySearch(List<List<Integer>> values, int target) {
        int start = 0;
        int end = values.size() - 1;
        while (end - start + 1 > 1) { // finish on length < 2 (1 or 0)
            int mid = start + (end - start) / 2;
            if (values.get(mid).get(0) < target) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        if (start < values.size() && target == values.get(start).get(0)) {
            return start;
        }
        return -1;
    }

    private static void print(int t, int nodes, boolean[][] dominators) {
        out.printf("Case %d:%n", t);
        StringBuilder sb = new StringBuilder();
        sb.append('+');
        for (int i = 0; i < nodes * 2 - 1; i++) sb.append('-');
        sb.append('+');
        sb.append(lineSeparator);
        for (int i = 0; i < nodes; i++) {
            sb.append('|');
            for (int j = 0; j < nodes; j++) {
                if (dominators[i][j]) {
                    sb.append('Y');
                } else {
                    sb.append('N');
                }
                sb.append('|');
            }
            sb.append(lineSeparator);
            sb.append('+');
            for (int j = 0; j < nodes * 2 - 1; j++) sb.append('-');
            sb.append('+');
            sb.append(lineSeparator);
        }
        out.print(sb.toString());
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

Deals with reachability, use DFS/BFS.
First, DFS from 0: nodes reachable from 0.
Next, for each node != 0:
  disable output edges of the current node
  run DFS from 0 again
  get difference of nodes between original and current DFS
  current node is their dominator, as they are only reachable through it
*/