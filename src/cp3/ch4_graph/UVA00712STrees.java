package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 00712 - Special Graph (Others), Trees, not starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=653
// notes below
class UVA00712STrees {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int test = 1;
        String line = getLine();
        while (line != null) {
            int height = parseInt(line);
            if (height == 0) break;
            int nodes = (1 << (height + 1)) - 1;
            int[] tree = new int[nodes + 1]; // as it will be 1-baed
            StringTokenizer st = new StringTokenizer(getLine());
            // Build tree with all its variables
            int offset = 1;
            for (int i = 0; i < height; i++) {
                int variable = st.nextToken().charAt(1) - '1';
                int nodesInLevel = 1 << i;
                for (int j = 0; j < nodesInLevel; j++) {
                    tree[offset + j] = variable;
                }
                offset += nodesInLevel;
            }
            // Values in terminal nodes
            line = getLine();
            for (int i = 0; i < line.length(); i++) {
                tree[offset + i] = line.charAt(i) - '0';
            }
            // Process assingments
            StringBuilder sb = new StringBuilder();
            int assignments = parseInt(getLine());
            for (int i = 0; i < assignments; i++) {
                int pointer = 1;
                String values = getLine();
                for (int j = 0; j < height; j++) {
                    int variable = tree[pointer];
                    int child = values.charAt(variable) - '0'; // 0 for left, 1 for right
                    pointer = 2 * pointer + child; // jump to child at next level
                }
                sb.append(tree[pointer]); // terminal node
            }
            out.printf("S-Tree #%d:%n", test++);
            out.println(sb);
            out.println();
            line = getLine();
        }
        out.close();
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
    private static int parseInt(String text) {
        return Integer.parseInt(text.trim());
    }

}
/*
S-trees are full binary trees, so we can easily use an array to represent them. If depth is d, there will be
2^(d+1) - 1 nodes. The array will be 1-based so it's easier to descend along the different levels. Each level stores
the variable id and the leaves store the f values. For each assignment, traverse the tree from root to leave.
*/
