package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 11235 - Tree-related Data Structures, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2176
//
// References:
// http://www.algorithmist.com/index.php/UVa_11235
// http://niceproblems.blogspot.com.es/2012/05/uva-11235-frequent-values.html
// https://www.topcoder.com/community/data-science/data-science-tutorials/range-minimum-query-and-lowest-common-ancestor
class UVA11235FrequentValues {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null && !"0".equals(line.trim())) {
            StringTokenizer st = new StringTokenizer(line.trim());
            int numbers = Integer.parseInt(st.nextToken());
            int queries = Integer.parseInt(st.nextToken());
            int[] values = new int[numbers];
            // The numbers are ordered, i.e. repeated numbers cluster together
            // This tracks the cluster id of the cluster to which the given position belongs
            int[] clusterIds = new int[numbers];
            // Start of cluster i. Could be a smaller array but we don't know the number of clusters in advance
            int[] startOfCluster = new int[numbers];
            // Length of cluster i. Could be a smaller array but we don't know the number of clusters in advance
            int[] lengths = new int[numbers];
            st = new StringTokenizer(in.readLine().trim());
            int clusterId = 0;
            int length = 1;
            int start = 0;
            values[0] = Integer.parseInt(st.nextToken());
            clusterIds[0] = clusterId;
            startOfCluster[0] = start;
            int prevNum = values[0];
            for (int i = 1; i < numbers; i++) {
                values[i] = Integer.parseInt(st.nextToken());;
                int currentNum = values[i];
                if (currentNum == prevNum) {
                    length++;
                } else {
                    lengths[clusterId] = length;
                    start = i;
                    length = 1;
                    clusterId++;
                    startOfCluster[clusterId] = start;
                    prevNum = currentNum;
                }
                clusterIds[i] = clusterId;
            }
            lengths[clusterId] = length;
            SegmentTree segmentTree = new SegmentTree(clusterIds, lengths);
            // Process the queries
            while (queries > 0) {
                line = in.readLine().trim();
                if ("".equals(line))
                    continue;
                queries--;
                st = new StringTokenizer(line);
                int low = Integer.parseInt(st.nextToken()) - 1;
                int high = Integer.parseInt(st.nextToken()) - 1;
                int maxFreq = -1;
                if (values[low] == values[high]) {
                    // Equal numbers are clustered together, so the frequency
                    // in this range is the length of the range
                    maxFreq = high - low + 1;
                } else {
                    // I need to partition the range into 3 ranges:
                    // The left and right ranges are clusters at the extremes of
                    // the query's boundaries, and they could be split. As we
                    // know the actual start of the cluster and its length (frequency), we can know how many
                    // of its members are actually inside the query's boundaries
                    // The max frequency of these 2 ranges is its length.
                    int newLow = startOfCluster[clusterIds[low]] + lengths[clusterIds[low]];
                    maxFreq = newLow - low;
                    int newHigh = startOfCluster[clusterIds[high]] - 1;
                    maxFreq = Math.max(maxFreq, high - newHigh);
                    if (newLow <= newHigh) {
                        // The center range may be composed of 1 or several complete
                        // clusters, where the Range Maximum Query of the segment
                        // tree can be applied to get an accurate result
                        maxFreq = Math.max(maxFreq, segmentTree.rangeMaximumQuery(newLow, newHigh));
                    }
                }
                out.println(maxFreq);
            }
            line = in.readLine();
        }
        out.flush();
    }

    private static class SegmentTree {
        private int size;
        private int[] st;

        public SegmentTree(int[] ids, int[] values) {
            size = ids.length;
            st = new int[size * 4]; // 4n, large enough to hold the tree
            int root = 1;
            int low = 0;
            int high = size - 1;
            build(root, low, high, ids, values);
        }

        private void build(int root, int low, int high, int[] ids, int[] values) {
            if (low == high) {
                st[root] = values[ids[low]];
            } else {
                build(leftChild(root), low, (low + high ) / 2, ids, values);
                build(rightChild(root), (low + high ) / 2 + 1, high, ids, values);
                int maxLeft = st[leftChild(root)];
                int maxRight = st[rightChild(root)];
                st[root] = Math.max(maxLeft, maxRight);
            }
        }

        private int leftChild(int root) {
            return 2 * root;
        }

        private int rightChild(int root) {
            return 2 * root + 1;
        }

        public int rangeMaximumQuery(int left, int right) {
            int root = 1;
            int low = 0;
            int high = size - 1;
            return rangeMaximumQuery(root, low, high, left, right);
        }

        private int rangeMaximumQuery(int root, int low, int high, int left,
                int right) {
            if (right < low || high < left) // current segment completely outside query range
                return -1;
            if (left <= low && high <= right) // current segment perfectly matches query range
                return st[root];
            // General case: current segment overlaps query range
            int maxLeft = rangeMaximumQuery(leftChild(root), low, (low + high) / 2, left, right);
            int maxRight = rangeMaximumQuery(rightChild(root), (low + high) / 2 + 1, high, left, right);
            if (maxLeft == -1) // if we try to access segment outside query range
                return maxRight;
            if (maxRight == -1) // same as above
                return maxLeft;
            return Math.max(maxLeft, maxRight);
        }
    }

    @SuppressWarnings("unused")
    private static class SparseTable {
        private int size;
        private int [][] maxTable;

        public SparseTable(int[] ids, int[] values) {
            size = ids.length;
            int logSize = (int) (Math.log(size) / Math.log(2));
            // maxTable[i][j] is the max value in the sequence of length 2^j that starts in i
            maxTable = new int[size][logSize + 1];
            for (int i = 0; i < size; i++)
                maxTable[i][0] = values[ids[i]];
            // 1 << j is the same as 2^j, thus 2^j <= size
            // Applying log on both sides: log 2^j <= log size
            // Which is the same as j <= log size
            // for (int j = 1; (1 << j) <= size; j++)
            // Traverses all valid sequence sizes
            for (int j = 1; j <= logSize; j++)
                // Condition is i + 2^j - 1 < size, i.e. start + length - 1 < size
                // That means that the sequence must fit inside the original array
                // Traverses all starting points that can fit the current sequence size
                for (int i = 0; i + (1 << j) - 1 < size; i++)
                    // We calculate the answer for sequence size 2^j by using the
                    // answers to the two 2^(j-1) sequence sizes contained within
                    maxTable[i][j] = Math.max(maxTable[i][j - 1], maxTable[i + (1 << (j - 1))][j - 1]);
        }

        public int rangeMaximumQuery(int left, int right) {
            int length = right - left + 1;
            // Idx corresponding to the length that better fits the query's range
            // The length will be either exactly right, or the next best in which
            // case we need to look at 2 overlapping subsequences: one starting
            // at the left and the other ending at the right
            int lookupLengthIdx = (int) (Math.log(length) / Math.log(2.0));
            return Math.max(maxTable[left][lookupLengthIdx],
                    maxTable[right - (1 << lookupLengthIdx) + 1][lookupLengthIdx]);
        }
    }
}
