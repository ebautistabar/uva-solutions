package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

// 10369 - Minimum Spanning Tree, Variants, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1310
// MST-minimum spanning forest: we want k connected components with the least
//  cost. Perform a MST algorithm, but on each step check if the number of
//  components is k. The easiest algo for this is Kruskal, as it uses unionfind
// In this problem, the satellite channels are k, the number of components. Then
//  we begin building an MST. As we start picking the smallest edges, we ensure
//  that the longest distance D between outputs of the same component is minimized
class UVA10369ArcticNetwork {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int tests = Integer.parseInt(getLine().trim());
        while (tests-- > 0) {
            StringTokenizer st = new StringTokenizer(getLine());
            int satelliteChannels = Integer.parseInt(st.nextToken());
            int outposts = Integer.parseInt(st.nextToken());
            int[] x = new int[outposts];
            int[] y = new int[outposts];
            for (int i = 0; i < outposts; i++) {
                st = new StringTokenizer(getLine());
                x[i] = Integer.parseInt(st.nextToken());
                y[i] = Integer.parseInt(st.nextToken());
            }

            List<Edge> edgeList = buildEdgeList(x, y);
            Collections.sort(edgeList);

            int i = 0;
            int maxSquaredDistance = 0;
            UnionFind uf = new UnionFind(outposts);
            while (i < edgeList.size() && uf.count() > satelliteChannels) {
                Edge edge = edgeList.get(i);
                if (!uf.areConnected(edge.a, edge.b)) {
                    uf.connect(edge.a, edge.b);
                    maxSquaredDistance = Math.max(maxSquaredDistance, edge.weight);
                }
                i++;
            }

            out.printf(Locale.US, "%.2f%n", Math.sqrt(maxSquaredDistance));
        }
        out.close();
    }

    private static List<Edge> buildEdgeList(int[] x, int[] y) {
        List<Edge> edgeList = new ArrayList<>();
        for (int i = 0; i < x.length; i++) {
            for (int j = i + 1; j < x.length; j++) {
                edgeList.add(new Edge(i, j, getSquaredDistance(i, j, x, y)));
            }
        }
        return edgeList;
    }

    // Avoid sqrt if possible
    private static int getSquaredDistance(int i, int j, int[] x, int[] y) {
        int dx = x[i] - x[j];
        int dy = y[i] - y[j];
        return dx * dx + dy * dy;
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }

    private static class Edge implements Comparable<Edge> {
        int a;
        int b;
        int weight;
        public Edge(int a, int b, int weight) {
            this.a = a;
            this.b = b;
            this.weight = weight;
        }
        public int compareTo(Edge other) {
            return Integer.compare(weight, other.weight);
        }
        @Override
        public String toString() {
            return String.format("[%d-%d:%d]", a, b, weight);
        }
    }

    private static class UnionFind {
        private int count;
        private int[] parents;
        private int[] height; // Upper bound (not exact height)

        public UnionFind(int n) {
            count = n;
            parents = new int[n + 1];
            height = new int[n + 1];
            for (int i = 0; i < parents.length; i++) {
                parents[i] = i;
                height[i] = 1;
            }
        }

        public int count() {
            return count;
        }

        public boolean areConnected(int p, int q) {
            return find(p) == find(q);
        }

        public int find(int p) {
            int root = p;
            while (root != parents[root])
                root = parents[root];
            while (p != root) {
                int parent = parents[p];
                parents[p] = root;
                p = parent;
            }
            return root;
        }

        public void connect(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP != rootQ) {
                count--;
                if (height[rootP] < height[rootQ]) {
                    parents[rootP] = rootQ;
                } else {
                    parents[rootQ] = rootP;
                    if (height[rootP] == height[rootQ])
                        height[rootP]++;
                }
            }
        }
    }
}
