package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

// 00548 - Special Graph (Others), Trees, not starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=489
// notes below
class UVA00548Tree {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static int postorderIdx;
    private static int leaf;
    private static int sumBestPath;

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            List<Integer> inorder = new ArrayList<>();
            while (st.hasMoreTokens()) {
                inorder.add(parseInt(st.nextToken()));
            }
            st = new StringTokenizer(getLine());
            List<Integer> postorder = new ArrayList<>();
            while (st.hasMoreTokens()) {
                postorder.add(parseInt(st.nextToken()));
            }

            postorderIdx = postorder.size() - 1;
            Tree tree = buildTree(inorder, postorder, 0, inorder.size());
            sumBestPath = Integer.MAX_VALUE;
            findLeastValuePath(tree, 0);
            out.println(leaf);

            line = getLine();
        }
        out.close();
    }

    private static void findLeastValuePath(Tree tree, int sum) {
        sum += tree.value;
        if (tree.left == null && tree.right == null) {
            if (sumBestPath > sum) {
                sumBestPath = sum;
                leaf = tree.value;
            } else if (sumBestPath == sum && leaf > tree.value) {
                leaf = tree.value;
            }
        } else {
            if (tree.left != null)
                findLeastValuePath(tree.left, sum);
            if (tree.right != null)
                findLeastValuePath(tree.right, sum);
        }
    }

    private static Tree buildTree(List<Integer> inorder, List<Integer> postorder, int start, int end) {
        if (start == end) {
            return null;
        }
        int value = postorder.get(postorderIdx--);
        Tree tree = new Tree(value);
        int inorderIdx = inorder.indexOf(value);
        tree.right = buildTree(inorder, postorder, inorderIdx + 1, end);
        tree.left = buildTree(inorder, postorder, start, inorderIdx);
        return tree;
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
        int value;
        Tree left;
        Tree right;
        public Tree(int value) {
            this.value = value;
        }
    }
}
/*
First build tree from inorder and postorder. The last item in the postorder is the root. If we search for that same
item in the inorder, we can split the inorder in two halves: the left subtree and the right subtree. The next to last
item in th postorder will be in the right subtree in the inorder. We can repeat the same procedure to build the tree.
Once the tree is built, run DFS accumulating the sum of values along the path. Each time we reach a leaf, store the
value if it's the cheapest.
*/
