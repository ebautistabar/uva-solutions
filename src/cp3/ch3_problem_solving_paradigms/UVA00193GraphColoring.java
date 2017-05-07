package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

// 193 - Complete Search, Recursive Backtracking (Harder), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=129
class UVA00193GraphColoring {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static boolean[] maxColoring;
    private static int maxSize;
    private static Map<Integer, List<Integer>> adjList;

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        while (tests-- > 0) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            int v = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            adjList = new HashMap<Integer, List<Integer>>();
            for (int i = 0; i < e; i++) {
                st = new StringTokenizer(in.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                adjList = addEdge(adjList, a, b);
                adjList = addEdge(adjList, b, a);
            }
            maxSize = 0;
            maxColoring = new boolean[v + 1];
            boolean[] black = new boolean[v + 1];
            int start = 1;
            colorGraph(start, black);
            printResult();
        }
        out.close();
    }

    private static void printResult() {
        out.println(maxSize);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < maxColoring.length; i++) {
            if (maxColoring[i]) {
                sb.append(i);
                sb.append(' ');
            }
        }
        sb.setLength(sb.length() - 1);
        out.println(sb.toString());
    }

    private static void colorGraph(int node, boolean[] black) {
        if (node == black.length) {
            int blackCount = getBlackCount(black);
            if (maxSize < blackCount) {
                maxSize = blackCount;
                System.arraycopy(black, 1, maxColoring, 1, black.length - 1);
            }
            return;
        }
        colorGraph(node + 1, black);
        if (!hasBlackNeighbors(node, black)) {
            black[node] = true;
            colorGraph(node + 1, black);
            black[node] = false;
        }
    }

    private static boolean hasBlackNeighbors(int node, boolean[] black) {
        List<Integer> neighbors = adjList.getOrDefault(node, Collections.<Integer>emptyList());
        for (int neighbor : neighbors) {
            if (black[neighbor]) {
                return true;
            }
        }
        return false;
    }

    private static int getBlackCount(boolean[] colors) {
        int count = 0;
        for (boolean color : colors) {
            if (color) {
                count++;
            }
        }
        return count;
    }

    private static Map<Integer, List<Integer>> addEdge(
            Map<Integer, List<Integer>> adjList, int source, int destination) {
        List<Integer> neighbors = adjList.get(source);
        if (neighbors == null) {
            neighbors = new ArrayList<Integer>();
            adjList.put(source, neighbors);
        }
        neighbors.add(destination);
        return adjList;
    }
}
