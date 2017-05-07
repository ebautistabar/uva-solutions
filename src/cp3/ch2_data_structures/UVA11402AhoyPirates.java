package cp3.ch2_data_structures;
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

// 11402 - Tree-related Data Structures, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2397
//
// References:
// http://lbv-pc.blogspot.com.es/2012/10/ahoy-pirates_24.html
// https://github.com/boxme/cs2010_Desmond/blob/master/UVa/UVa%2011402/Main.java
class UVA11402AhoyPirates {

    private static final int MAX_PIRATES = 1024000;
    private static final int BARBARY = 0;
    private static final int BUCCANEER = 1;
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static byte[] pirates = new byte[MAX_PIRATES];

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        for (int t = 1; t <= tests; t++) {
            int piratesLen = getPirates();
            out.printf("Case %d:\n", t);
            int queries = 1;
            SegmentTree st = new SegmentTree(pirates, piratesLen);
            int commands = Integer.parseInt(in.readLine().trim());
            while (commands-- > 0) {
                StringTokenizer query = new StringTokenizer(in.readLine());
                char command = query.nextToken().charAt(0);
                int left = Integer.parseInt(query.nextToken());
                int right = Integer.parseInt(query.nextToken());
                if (command == 'F')
                    st.update(left, right, BUCCANEER);
                else if (command == 'E')
                    st.update(left, right, BARBARY);
                else if (command == 'I')
                    st.invert(left, right);
                else // assume command == 'S'
                    out.printf("Q%d: %d\n", queries++, st.rangeSumQuery(left, right));
            }
        }
        out.flush();
    }

    private static int getPirates() throws NumberFormatException, IOException {
        int p = 0;
        int sets = Integer.parseInt(in.readLine().trim());
        while (sets-- > 0) {
            int times = Integer.parseInt(in.readLine().trim());
            String line = in.readLine().trim();
            if (times > 0) {
                int len = line.length();
                for (int j = 0; j < len; j++)
                    pirates[p++] = (byte) (line.charAt(j) - '0');
                while (times-- > 1) {
                    System.arraycopy(pirates, p - len, pirates, p, len);
                    p += len;
                }
            }
        }
        return p;
    }

    private static class SegmentTree {
        private int size;
        private int[] sum;
        private Map<Integer, Integer> pendingUpdate = new HashMap<>();
        private Set<Integer> pendingInversion = new HashSet<>();

        public SegmentTree(byte[] values, int size) {
            this.size = size;
            sum = new int[getTreeSize(size)];
            int root = 1;
            int low = 0;
            int high = size - 1;
            build(root, low, high, values);
        }

        private static int getTreeSize(int n) {
            return (int) (2 * Math.pow(2.0, Math.floor((Math.log((double) n) / Math.log(2.0)) + 1)));
        }

        private void build(int node, int low, int high, byte[] values) {
            if (low == high) {
                sum[node] = values[low];
            } else {
                int mid = (low + high) / 2;
                int left = leftChild(node);
                int right = rightChild(node);
                build(left, low, mid, values);
                build(right, mid + 1, high, values);
                sum[node] = sum[left] + sum[right];
            }
        }

        private int leftChild(int root) {
            return 2 * root;
        }

        private int rightChild(int root) {
            return 2 * root + 1;
        }

        public int rangeSumQuery(int left, int right) {
            int root = 1;
            int low = 0;
            int high = size - 1;
            return rangeSumQuery(root, low, high, left, right);
        }

        private int rangeSumQuery(int node, int low, int high, int left,
                int right) {
            if (right < low || high < left) // current segment completely outside query range
                return 0;
            propagate(node, low, high);
            if (left <= low && high <= right) // current segment matches query range
                return sum[node];
            // General case: current segment overlaps query range
            int sumLeft = rangeSumQuery(leftChild(node), low, (low + high) / 2, left, right);
            int sumRight = rangeSumQuery(rightChild(node), (low + high) / 2 + 1, high, left, right);
            return sumLeft + sumRight;
        }

        private void propagate(int node, int low, int high) {
            int rangeLength = high - low + 1;
            if (pendingInversion.contains(node)) {
                propagateInversion(node, rangeLength);
            } else if (pendingUpdate.containsKey(node)){
                propagateUpdate(node, rangeLength);
            }
        }

        private void propagateInversion(int node, int rangeLength) {
            sum[node] = rangeLength - sum[node];
            pendingInversion.remove(node);
            if (rangeLength > 1) {
                markPendingInversion(leftChild(node));
                markPendingInversion(rightChild(node));
            }
        }

        private void markPendingInversion(int node) {
            Integer pendingVal = pendingUpdate.get(node);
            if (pendingVal != null)
                pendingUpdate.put(node, pendingVal.intValue() ^ 1);
            else if (pendingInversion.contains(node))
                pendingInversion.remove(node);
            else
                pendingInversion.add(node);
        }

        private void propagateUpdate(int node, int rangeLength) {
            int value = pendingUpdate.remove(node);
            sum[node] = rangeLength * value;
            if (rangeLength > 1) {
                markPendingUpdate(leftChild(node), value);
                markPendingUpdate(rightChild(node), value);
            }
        }

        private void markPendingUpdate(int node, int value) {
            pendingUpdate.put(node, value);
            pendingInversion.remove(node);
        }

        public void update(int left, int right, int value) {
            int root = 1;
            int low = 0;
            int high = size - 1;
            update(root, low, high, left, right, value);
        }

        private void update(int node, int low, int high, int left, int right,
                int value) {
            propagate(node, low, high);
            if (right < low || high < left) // current segment completely outside query range
                return;
            if (left <= low && high <= right) { // current segment matches query range
                int rangeLength = high - low + 1;
                sum[node] = value * rangeLength;
                if (rangeLength > 1) {
                    markPendingUpdate(leftChild(node), value);
                    markPendingUpdate(rightChild(node), value);
                }
                return;
            }
            // General case: current segment overlaps query range
            update(leftChild(node), low, (low + high) / 2, left, right, value);
            update(rightChild(node), (low + high) / 2 + 1, high, left, right, value);
            sum[node] = sum[leftChild(node)] + sum[rightChild(node)];
        }

        // Only makes sense for this problem. The only allowed values are 0 and
        // 1 and this method inverts them
        public void invert(int left, int right) {
            int root = 1;
            int low = 0;
            int high = size - 1;
            invert(root, low, high, left, right);
        }

        private void invert(int node, int low, int high, int left, int right) {
            propagate(node, low, high);
            if (right < low || high < left) // current segment completely outside query range
                return;
            if (left <= low && high <= right) { // current segment matches query range
                int rangeLength = high - low + 1; // == max sum
                sum[node] = rangeLength - sum[node];
                if (rangeLength > 1) {
                    markPendingInversion(leftChild(node));
                    markPendingInversion(rightChild(node));
                }
                return;
            }
            // General case: current segment overlaps query range
            invert(leftChild(node), low, (low + high) / 2, left, right);
            invert(rightChild(node), (low + high) / 2 + 1, high, left, right);
            sum[node] = sum[leftChild(node)] + sum[rightChild(node)];
        }
    }
}
