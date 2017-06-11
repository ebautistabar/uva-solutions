package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

// 00122 - Special Graph (Others), Trees, not starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=58
// Notes below
class UVA00122TreesOnTheLevel {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            int nodesCreated = 1;
            int valuesAssigned = 0;
            Tree root = new Tree();
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens()) {
                String item = st.nextToken();
                if (!"()".equals(item)) {
                    String[] parts = item.split(",");
                    int value = parseInt(parts[0].substring(1)); // ignore parenthesis at the start
                    String path = parts[1].substring(0, parts[1].length() - 1); // ignore parens at the end
                    if (path.length() == 0) { // the value corresponds to the root
                        root.value = value;
                    } else {
                        Tree node = root;
                        for (int i = 0; i < path.length(); i++) {
                            if (path.charAt(i) == 'L') {
                                if (node.left == null) {
                                    node.left = new Tree();
                                    nodesCreated++;
                                }
                                node = node.left;
                            } else {
                                if (node.right == null) {
                                    node.right = new Tree();
                                    nodesCreated++;
                                }
                                node = node.right;
                            }
                            if (i == path.length() - 1) {
                                node.value = value;
                            }
                        }
                    }
                    valuesAssigned++;
                    if (!st.hasMoreTokens()) {
                        line = getLine();
                        if (line != null) {
                            st = new StringTokenizer(line);
                        }
                    }
                }
            }

            if (nodesCreated != valuesAssigned) {
                out.println("not complete");
            } else {
                StringBuilder sb = new StringBuilder();
                Queue<Tree> pending = new ArrayDeque<>();
                pending.add(root);
                while (!pending.isEmpty()) {
                    Tree current = pending.remove();
                    sb.append(current.value);
                    sb.append(' ');
                    if (current.left != null) {
                        pending.add(current.left);
                    }
                    if (current.right != null) {
                        pending.add(current.right);
                    }
                }
                sb.setLength(sb.length() - 1); // remove trailing space
                out.println(sb);
            }
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

    private static class Tree {
        Integer value;
        Tree left;
        Tree right;
    }
}
/*
The trickiest part in Java is parsing the tree. Once it's built, just do BFS.
*/
