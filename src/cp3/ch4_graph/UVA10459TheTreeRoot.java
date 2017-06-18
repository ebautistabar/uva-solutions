package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

// 10459 - Special Graph (Others), Trees, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1400
// Notes below
class UVA10459TheTreeRoot {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        StringTokenizer st = refreshTokenizer(null);
        while (st != null && st.hasMoreTokens()) {
            List<List<Integer>> adjList = new ArrayList<>();

            int nodes = parseInt(st.nextToken());
            for (int i = 0; i < nodes; i++) {
                List<Integer> adjacent = new ArrayList<Integer>();
                adjList.add(adjacent);
                st = refreshTokenizer(st);
                int neighbors = parseInt(st.nextToken());
                for (int j = 0; j < neighbors; j++) {
                    st = refreshTokenizer(st);
                    int neighbor = parseInt(st.nextToken()) - 1; // input is 1-based
                    adjacent.add(neighbor);
                }
            }

            Set<Integer> bestRoots = new TreeSet<>();
            Set<Integer> worstRoots = new TreeSet<>();
            int anyStartNode = 0;
            int furthestNode = getFurthestNode(anyStartNode, adjList);
            worstRoots.add(furthestNode);
            furthestNode = getFurthestNodes(furthestNode, adjList, bestRoots, worstRoots);
            getFurthestNodes(furthestNode, adjList, bestRoots, worstRoots);

            out.printf("Best Roots  :%s%n", format(bestRoots));
            out.printf("Worst Roots :%s%n", format(worstRoots));
            st = refreshTokenizer(st);
        }
        out.close();
    }

    private static int getFurthestNodes(int start, List<List<Integer>> adjList, Set<Integer> bestRoots, Set<Integer> worstRoots) {
        int maxLen = -1;
        int[] previous = new int[adjList.size()];
        int[] distance = new int[adjList.size()];
        Arrays.fill(distance, -1);
        distance[start] = 0;
        Queue<Integer> pending = new ArrayDeque<>();
        pending.add(start);
        while (!pending.isEmpty()) {
            int current = pending.remove();
            for (int next : adjList.get(current)) {
                if (distance[next] == -1) {
                    pending.add(next);
                    previous[next] = current;
                    distance[next] = distance[current] + 1;
                    maxLen = Math.max(maxLen, distance[next]);
                }
            }
        }
        int worst = 0;
        boolean isEven = maxLen % 2 == 0;
        for (int i = 0; i < distance.length; i++) {
            // The nodes at max distance are the worst roots (height == diameter)
            if (distance[i] == maxLen) {
                worst = i;
                worstRoots.add(i);
                // The nodes in the middle of the diameter are the best roots (height == diameter / 2)
                int len = maxLen;
                for (int n = i; n != start; n = previous[n]) {
                    if ((isEven && len == maxLen / 2)
                            || (!isEven && (len == maxLen / 2 || len == maxLen / 2 + 1))) {
                        bestRoots.add(n);
                        break;
                    }
                    len--;
                }
            }
        }
        return worst;
    }

    private static int getFurthestNode(int start, List<List<Integer>> adjList) {
        int maxLen = 0;
        int furthestNode = 0;
        int[] distance = new int[adjList.size()];
        Arrays.fill(distance, -1);
        distance[start] = 0;
        Queue<Integer> pending = new ArrayDeque<>();
        pending.add(start);
        while (!pending.isEmpty()) {
            int current = pending.remove();
            for (int next : adjList.get(current)) {
                if (distance[next] == -1) {
                    distance[next] = distance[current] + 1;
                    pending.add(next);
                    // There can be more than one node at this distance, but we just pick one
                    if (maxLen < distance[next]) {
                        maxLen = distance[next];
                        furthestNode = next;
                    }
                }
            }
        }
        return furthestNode;
    }

    private static String format(Set<Integer> roots) {
        StringBuilder sb = new StringBuilder();
        for (int root : roots) {
            sb.append(' ');
            sb.append(root + 1);
        }
        return sb.toString();
    }

    private static StringTokenizer refreshTokenizer(StringTokenizer st) throws IOException {
        if (st != null && st.hasMoreTokens()) {
            return st;
        }
        String line = getLine();
        if (line == null) {
            return null;
        }
        return new StringTokenizer(line);
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
The worst roots are nodes that, if picked as the root of the tree, will create a tree with the biggest height. The
height is just the distance from the root to the farthest leaf. If we maximize the height we are actually looking for
the diameter : the longest path between any pair of nodes. To get the diameter first do a BFS from any node. The farthest
node is a worst root already. From that worst root, perform another DFS. The farthest nodes are again worst roots. And
a third traversal gives us the final bunch of worst roots, again they are those which are the farthest from the current
batch.

Regarding the best roots, they can be computed as the middle point in a diameter path. If the length of a path is
even, there will be just 1 node in the middle. If the length is odd, there will be 2 nodes in the middle having equal
distance to their closest endpoint in their side of the diameter.
*/
