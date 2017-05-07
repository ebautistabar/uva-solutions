package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

// 11503 - Union-Find Disjoint Sets, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2498
class UVA11503VirtualFriends {

    private static final int MAX_FRIENDSHIPS = 100_000;
    private static final int MAX_PEOPLE = 2 * MAX_FRIENDSHIPS;
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        while (tests-- > 0) {
            int friendships = Integer.parseInt(in.readLine().trim());
            Map<String, Integer> nameToId = new HashMap<>();
            UnionFind network = new UnionFind(MAX_PEOPLE);
            for (int i = 0; i < friendships; i++) {
                StringTokenizer st = new StringTokenizer(in.readLine().trim());
                String name1 = st.nextToken();
                Integer friend1 = nameToId.get(name1);
                if (friend1 == null) {
                    friend1 = nameToId.size();
                    nameToId.put(name1, friend1);
                }
                String name2 = st.nextToken();
                Integer friend2 = nameToId.get(name2);
                if (friend2 == null) {
                    friend2 = nameToId.size();
                    nameToId.put(name2, friend1);
                }
                network.connect(friend1, friend2);
                out.println(network.getSizeOfComponent(friend1));
            }
        }
        out.flush();
    }

    private static class UnionFind {
        private int[] parents;
        private int[] height; // Upper bound (not exact height)
        private int[] size; // Only accurate for roots

        public UnionFind(int n) {
            parents = new int[n + 1];
            height = new int[n + 1];
            size = new int[n + 1];
            for (int i = 0; i < parents.length; i++) {
                parents[i] = i;
                height[i] = 1;
                size[i] = 1;
            }
        }

        @SuppressWarnings("unused")
        public boolean areConnected(int p, int q) {
            return find(p) == find(q);
        }

        public int getSizeOfComponent(int p) {
            return size[find(p)];
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
                if (height[rootP] < height[rootQ]) {
                    parents[rootP] = rootQ;
                    size[rootQ] += size[rootP];
                } else {
                    parents[rootQ] = rootP;
                    size[rootP] += size[rootQ];
                    if (height[rootP] == height[rootQ])
                        height[rootP]++;
                }
            }
        }
    }
}
