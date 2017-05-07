package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 793 - Union-Find Disjoint Sets, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=734
class UVA00793NetworkConnections {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        in.readLine(); // blank line before test cases
        while (tests-- > 0) {
            long success = 0;
            long failure = 0;
            int computers = Integer.parseInt(in.readLine().trim());
            UnionFind network = new UnionFind(computers);
            String line = in.readLine();
            while (line != null && !"".equals(line.trim())) {
                StringTokenizer st = new StringTokenizer(line.trim());
                String command = st.nextToken();
                int computer1 = Integer.parseInt(st.nextToken());
                int computer2 = Integer.parseInt(st.nextToken());
                if ("c".equals(command))
                    network.connect(computer1, computer2);
                // else I assume the command is q
                else if (network.areConnected(computer1, computer2))
                    success++;
                else
                    failure++;
                line = in.readLine();
            }
            // Print results
            out.print(success);
            out.print(',');
            out.println(failure);
            if (tests > 0)
                out.println(); // blank line between answers
        }
        out.flush();
    }

    private static class UnionFind {
        private int[] parents;
        private int[] height; // Upper bound (not exact height)

        public UnionFind(int n) {
            parents = new int[n + 1];
            height = new int[n + 1];
            for (int i = 0; i < parents.length; i++) {
                parents[i] = i;
                height[i] = 1;
            }
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
