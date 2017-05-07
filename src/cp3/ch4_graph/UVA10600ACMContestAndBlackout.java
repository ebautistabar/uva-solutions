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
import java.util.StringTokenizer;

// 10600 - Minimum Spanning Tree, Variants, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1541
// MST-Second cheapest MST: find the MST. Then, for each edge in the MST, flag
//  it as invalid and search MST again. Ignore the invalid edge while building
//  the MST. Store the best MST in this loop.
class UVA10600ACMContestAndBlackout {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int tests = Integer.parseInt(getLine());
        while (tests-- > 0) {
            StringTokenizer st = new StringTokenizer(getLine());
            int schools = Integer.parseInt(st.nextToken());
            int possibleConnections = Integer.parseInt(st.nextToken());
            List<Edge> edgeList = new ArrayList<>();
            for (int i = 0; i < possibleConnections; i++) {
                st = new StringTokenizer(getLine());
                int a = Integer.parseInt(st.nextToken()) - 1; // 1-based
                int b = Integer.parseInt(st.nextToken()) - 1;
                int cost = Integer.parseInt(st.nextToken());
                Edge edge = new Edge(a, b, cost);
                edgeList.add(edge);
            }

            Collections.sort(edgeList);
            List<Edge> mst = new ArrayList<>(schools - 1);
            int minCost = getMST(edgeList, mst, schools, null);
            int secondMinCost = Integer.MAX_VALUE;
            for (int i = 0; i < mst.size(); i++) {
                Edge ignored = mst.get(i);
                // we don't save a reference to the new full MST here, as we
                //  only need the cost
                secondMinCost = Math.min(secondMinCost,
                        getMST(edgeList, new ArrayList<Edge>(schools - 1), schools, ignored));
            }
            out.printf("%d %d%n", minCost, secondMinCost);
        }
        out.close();
    }

    private static int getMST(List<Edge> edgeList, List<Edge> mst, int nodes, Edge ignored) {
        int cost = 0;
        UnionFind uf = new UnionFind(nodes);
        for (int i = 0; i < edgeList.size() && uf.count() > 1; i++) {
            if (edgeList.get(i) != ignored) { // use identity, as we use the same objects everywhere
                Edge edge = edgeList.get(i);
                if (!uf.areConnected(edge.a, edge.b)) {
                    uf.connect(edge.a, edge.b);
                    mst.add(edge);
                    cost += edge.weight;
                }
            }
        }
        if (uf.count() > 1) {
            // couldn't connect all schools, so the cost doesn't represent
            //  a valid solution
            return Integer.MAX_VALUE;
        } else {
            return cost;
        }
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
