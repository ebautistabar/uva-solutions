package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 00536 - Special Graph (Others), Trees, not starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=477
// notes below
class UVA00536TreeRecovery {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static int preorderIdx = 0;

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            String preorder = st.nextToken();
            String inorder = st.nextToken();
            preorderIdx = 0;
            Tree tree = buildTree(preorder, inorder, 0, inorder.length());
            StringBuilder sb = new StringBuilder();
            postorder(tree, sb);
            out.println(sb);
            line = getLine();
        }
        out.close();
    }

    private static void postorder(Tree tree, StringBuilder sb) {
        if (tree == null) return;
        postorder(tree.left, sb);
        postorder(tree.right, sb);
        sb.append(tree.value);
    }

    private static Tree buildTree(String preorder, String inorder, int start, int end) {
        if (start == end) {
            return null;
        }
        char value = preorder.charAt(preorderIdx++);
        Tree tree = new Tree(value);
        int inorderIdx = inorder.indexOf(value, start);
        tree.left = buildTree(preorder, inorder, start, inorderIdx);
        tree.right = buildTree(preorder, inorder, inorderIdx + 1, end);
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

    private static class Tree {
        char value;
        Tree left;
        Tree right;
        public Tree(char value) {
            this.value = value;
        }
    }
}
/*
Reconstructing tree from preorder and inorder. We know the first item in the preorder is the root. If we then search
for that item in the inorder, we can split the inorder in two: items in the left subtree and items in the right
subtree. We then recurse on each of the halves, picking items from the preorder 1 by 1 from left to right. Once the
tree is built, postorder is straightforward.
*/
